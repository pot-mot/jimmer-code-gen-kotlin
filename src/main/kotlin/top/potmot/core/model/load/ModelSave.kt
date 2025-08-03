package top.potmot.core.model.load

import org.babyfish.jimmer.kt.unload
import org.babyfish.jimmer.sql.ast.mutation.AssociatedSaveMode
import org.babyfish.jimmer.sql.ast.mutation.SaveMode
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.babyfish.jimmer.sql.kt.ast.expression.valueNotIn
import org.springframework.transaction.support.TransactionTemplate
import top.potmot.entity.GenAssociation
import top.potmot.entity.GenEnum
import top.potmot.entity.GenModelDraft
import top.potmot.entity.GenTable
import top.potmot.entity.GenTableIndex
import top.potmot.entity.dto.GenModelInput
import top.potmot.entity.dto.GenTableLoadView
import top.potmot.entity.dto.IdName
import top.potmot.entity.id
import top.potmot.entity.modelId
import top.potmot.entity.tableId
import top.potmot.error.LoadFromModelException
import top.potmot.utils.transaction.executeNotNull

interface ModelSave {
    val transactionTemplate: TransactionTemplate

    @Throws(LoadFromModelException::class)
    fun KSqlClient.saveModel(
        input: GenModelInput,
    ): Long = transactionTemplate.executeNotNull {
        // 1. 保存 model, subGroups
        val savedModel = save(
            input.toEntity { unload(this, GenModelDraft::enums) },
            if (input.id == null) SaveMode.INSERT_ONLY else SaveMode.UPDATE_ONLY,
            AssociatedSaveMode.REPLACE,
        ).modifiedEntity

        // 创建 subGroup name -> id map，用于映射 table.subGroup 和 enum.subGroup
        val subGroupNameIdMap = savedModel.subGroups.associate { it.name to it.id }

        // 1.2 保存 enums
        val savedEnums = saveEntities(
            input.enums.map {
                it.toEntity {
                    modelId = savedModel.id
                    subGroupId = subGroupNameIdMap[it.subGroup?.name]
                }
            }
        ).items.map { it.modifiedEntity }

        // 1.3 移除遗留 enums
        executeDelete(GenEnum::class) {
            where(table.modelId eq savedModel.id)
            where(table.id valueNotIn savedEnums.map { it.id })
        }

        // 创建 enum name -> id map，用于映射 table.columns.enum
        val enumNameIdMap = savedEnums.associate { it.name to it.id }

        parseGraphData(savedModel.id, input.graphData).let { (tableModelInputs, associationModelInputs) ->
            val tableInputsList =
                tableModelInputs.map {
                    it.toInputs(subGroupNameIdMap, enumNameIdMap)
                }

            // 2. 保存 tables
            val tableInputs = tableInputsList.map { it.table }
            val savedTables = saveInputs(tableInputs).items.map {
                GenTableLoadView(it.modifiedEntity)
            }

            // 2.2 移除遗留 tables
            executeDelete(GenTable::class) {
                where(table.modelId eq savedModel.id)
                where(table.id valueNotIn savedTables.map { it.id })
            }

            val savedTableNameMap = savedTables.associateBy { it.name }

            val savedTableSuperTableMap = mutableMapOf<GenTableLoadView, List<GenTableLoadView>>()

            // 3. 保存 superTables
            tableInputsList.forEach { (table, _, superTableNames) ->
                val inheritTable = savedTableNameMap[table.name]
                    ?: throw LoadFromModelException.tableNotFound(
                        "Table [${table.name}] not found",
                        tableName = table.name
                    )

                val superTables = superTableNames.map { superTableName ->
                    savedTableNameMap[superTableName]
                        ?: throw LoadFromModelException.tableSuperTableNotFound(
                            "Super table [${superTableName}] not found",
                            table = IdName(inheritTable.id, inheritTable.name),
                            superTableNames = superTableNames,
                            notFoundSuperTableName = superTableName
                        )
                }

                savedTableSuperTableMap[inheritTable] = superTables

                getAssociations(GenTable::superTables)
                    .saveAll(listOf(inheritTable.id), superTables.map { it.id })
            }

            // 4. 保存 indexes
            tableInputsList.forEach { (table, indexes) ->
                val savedTable = savedTableNameMap[table.name]
                    ?: throw LoadFromModelException.indexesTableNotFound(
                        "Indexes ${indexes.map { it.name }} create fail: \nTable [${table.name}] not found",
                        indexNames = indexes.map { it.name },
                        notFoundTableName = table.name
                    )

                val superTables = savedTableSuperTableMap[savedTable]
                    ?: throw LoadFromModelException.indexesTableSuperTableNotFound(
                        "Indexes ${indexes.map { it.name }} create fail: \nTable [${table.name}] superTables not found",
                        indexNames = indexes.map { it.name },
                        table = IdName(savedTable.id, savedTable.name),
                        superTableIds = savedTable.superTableIds
                    )

                val columnNameIdMap =
                    (superTables.flatMap { it.columns } + savedTable.columns).associate { it.name to it.id }

                val indexInputs = indexes.map { it.toInput(savedTable, columnNameIdMap) }
                val savedIndexes = saveInputs(indexInputs).items.map { it.modifiedEntity }

                // 4.2 移除遗留 indexes
                executeDelete(GenTableIndex::class) {
                    where(this.table.tableId eq savedTable.id)
                    where(this.table.id valueNotIn savedIndexes.map { it.id })
                }
            }

            // 5. 保存 associations
            val associationInputs = associationModelInputs.map { it.toInput(savedTables) }
            val savedAssociations = saveEntities(associationInputs.map {
                it.toEntity {
                    modelId = savedModel.id
                }
            }).items.map { it.modifiedEntity }

            // 5.2 移除遗留 associations
            executeDelete(GenAssociation::class) {
                where(table.modelId eq savedModel.id)
                where(table.id valueNotIn savedAssociations.map { it.id })
            }
        }

        savedModel.id
    }
}