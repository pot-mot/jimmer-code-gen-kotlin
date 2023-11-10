package top.potmot.core.convert

import top.potmot.extension.valueToData
import top.potmot.model.GenTypeMapping
import top.potmot.model.copy
import top.potmot.model.dto.GenAssociationModelInput
import top.potmot.model.dto.GenEntityPropertiesView
import top.potmot.model.dto.GenModelView
import top.potmot.model.dto.GenTableAssociationsView
import top.potmot.model.dto.GenTableColumnsInput
import java.time.LocalDateTime
import top.potmot.model.dto.GenTableAssociationsView.TargetOf_columns as ColumnView
import top.potmot.model.dto.GenTableAssociationsView.TargetOf_columns.TargetOf_table_2 as ColumnTableInfo
import top.potmot.model.dto.GenTableAssociationsView.TargetOf_columns.TargetOf_inAssociations_2 as InAssociation
import top.potmot.model.dto.GenTableAssociationsView.TargetOf_columns.TargetOf_inAssociations_2.TargetOf_sourceColumn_3 as SourceColumn
import top.potmot.model.dto.GenTableAssociationsView.TargetOf_columns.TargetOf_inAssociations_2.TargetOf_sourceColumn_3.TargetOf_table_4 as SourceTable
import top.potmot.model.dto.GenTableAssociationsView.TargetOf_columns.TargetOf_outAssociations_2 as OutAssociation
import top.potmot.model.dto.GenTableAssociationsView.TargetOf_columns.TargetOf_outAssociations_2.TargetOf_targetColumn_3 as TargetColumn
import top.potmot.model.dto.GenTableAssociationsView.TargetOf_columns.TargetOf_outAssociations_2.TargetOf_targetColumn_3.TargetOf_table_4 as TargetTable

/**
 * ！！！ 仅可用于直接通过 Model 生成 Entity，不可进行保存  ！！！
 */

/**
 * 将模型中的 Input 伪造成可以直接生成实体的 View，以实现 convertTableToEntity，用模型直接生成实体
 */
fun mockModelDataView(
    tableInputs: List<GenTableColumnsInput>,
    associationInputs: List<GenAssociationModelInput>
): List<GenTableAssociationsView> {
    val tableNameMap = mutableMapOf<String, GenTableColumnsInput>()

    tableInputs.forEach {
        tableNameMap[it.name] = it
    }

    val sourceNameAssociationMap = mutableMapOf<String, MutableList<GenAssociationModelInput>>()
    val targetNameAssociationMap = mutableMapOf<String, MutableList<GenAssociationModelInput>>()

    associationInputs.forEach {
        if (!sourceNameAssociationMap.containsKey(it.sourceColumn.table.name)) {
            sourceNameAssociationMap[it.sourceColumn.table.name] = mutableListOf(it)
        } else {
            sourceNameAssociationMap[it.sourceColumn.table.name]!! += it
        }

        if (!targetNameAssociationMap.containsKey(it.targetColumn.table.name)) {
            targetNameAssociationMap[it.targetColumn.table.name] = mutableListOf(it)
        } else {
            targetNameAssociationMap[it.targetColumn.table.name]!! += (it)
        }
    }

    return tableNameMap.map { (name, tableInput) ->
        // 源表名称与当前表相同的关联，即出关联
        val outAssociationInputs = sourceNameAssociationMap[name] ?: listOf()
        // 目标表名称与当前表相同的关联，即入关联
        val inAssociationInputs = targetNameAssociationMap[name] ?: listOf()

        val columns = tableInput.columns.map { column ->
            val outAssociations: List<OutAssociation> =
                outAssociationInputs
                    .filter { it.sourceColumn.name == column.name }
                    .map {
                        OutAssociation(
                            id = 0,
                            associationType = it.associationType,
                            fake = true,
                            targetColumn = TargetColumn(
                                id = 0,
                                name = it.targetColumn.name,
                                comment = it.targetColumn.comment,
                                table = TargetTable(
                                    id = 0,
                                    name = it.targetColumn.table.name,
                                    comment = it.targetColumn.table.comment,
                                )
                            ),
                        )
                    }

            val inAssociations: List<InAssociation> =
                inAssociationInputs
                    .filter { it.targetColumn.name == column.name }
                    .map {
                        InAssociation(
                            id = 0,
                            associationType = it.associationType,
                            fake = true,
                            sourceColumn = SourceColumn(
                                id = 0,
                                name = it.sourceColumn.name,
                                comment = it.sourceColumn.comment,
                                table = SourceTable(
                                    id = 0,
                                    name = it.sourceColumn.table.name,
                                    comment = it.sourceColumn.table.comment
                                )
                            ),
                        )
                    }

            ColumnView(
                id = 0,
                createdTime = LocalDateTime.now(),
                modifiedTime = LocalDateTime.now(),
                remark = column.remark,
                orderKey = column.orderKey,
                name = column.name,
                typeCode = column.typeCode,
                type = column.type,
                displaySize = column.displaySize,
                numericPrecision = column.numericPrecision,
                defaultValue = column.defaultValue,
                comment = column.comment,
                partOfPk = column.partOfPk,
                autoIncrement = column.autoIncrement,
                partOfFk = column.partOfFk,
                partOfUniqueIdx = column.partOfUniqueIdx,
                typeNotNull = column.typeNotNull,
                table = ColumnTableInfo(
                    id = 0,
                    name = tableInput.name,
                    comment = tableInput.comment
                ),
                inAssociations = inAssociations,
                outAssociations = outAssociations,
            )
        }

        GenTableAssociationsView(
            id = 0,
            createdTime = LocalDateTime.now(),
            modifiedTime = LocalDateTime.now(),
            name = tableInput.name,
            comment = tableInput.comment,
            remark = tableInput.remark,
            orderKey = 0,
            type = tableInput.type,
            columns = columns
        )
    }
}

fun GenModelView.mockModelDataView(): List<GenTableAssociationsView> {
    val data = this.valueToData()

    return mockModelDataView(
        tableInputs = data.first,
        associationInputs = data.second
    )
}

fun GenModelView.mockModelEntity(typeMappings: List<GenTypeMapping>): List<GenEntityPropertiesView> {
    return this
        .mockModelDataView()
        .map { tableView ->
            tableView
                .toGenEntity(typeMappings)
                .copy {
                    id = 0
                    createdTime = LocalDateTime.now()
                    modifiedTime = LocalDateTime.now()
                    orderKey = 0
                    table().name = tableView.name
                    table().comment = tableView.comment
                    table().remark = tableView.remark
                    table().type = tableView.type

                    tableView.schema?.let { schema ->
                        table().schema().id = schema.id
                        table().schema().name = schema.name

                        tableView.schema.dataSource.let { dataSource ->
                            table().schema().dataSource().id = dataSource.id
                            table().schema().dataSource().name = dataSource.name
                            table().schema().dataSource().type = dataSource.type
                        }
                    }


                    properties().forEach { property ->
                        property.entity().id = 0
                        property.id = 0
                        property.createdTime = LocalDateTime.now()
                        property.modifiedTime = LocalDateTime.now()
                    }
                }
                .let { entity ->
                    GenEntityPropertiesView(entity)
                }
        }
}
