package top.potmot.jimmercodegen.service

import org.babyfish.jimmer.sql.kt.fetcher.newFetcher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import top.potmot.jimmercodegen.dao.GenTableAssociationRepository
import top.potmot.jimmercodegen.dao.GenTableColumnRepository
import top.potmot.jimmercodegen.dao.GenTableRepository
import top.potmot.jimmercodegen.model.GenTable
import top.potmot.jimmercodegen.model.GenTableColumn
import top.potmot.jimmercodegen.model.by
import top.potmot.jimmercodegen.model.input.GenTableAssociationInput

@Service
class AssociationService(
    @Autowired val associationRepository: GenTableAssociationRepository,
    @Autowired val tableRepository: GenTableRepository,
    @Autowired val tableColumnRepository: GenTableColumnRepository
) {
    fun clearAssociations() {
        associationRepository.deleteAll()
    }

    /**
     * 根据策略引入目前数据库中所有表之间的关联
     */
    fun importAssociations(
        associationStrategy: (GenTableColumn, GenTableColumn) -> Boolean = defaultAssociationStrategy
    ) {
        for (slaveColumn in tableColumnRepository.findAll(COLUMN_TABLE_FETCHER)) {
            for (masterColumn in tableColumnRepository.findAll(COLUMN_TABLE_FETCHER)) {
                if (associationStrategy(masterColumn, slaveColumn)) {
                    println("${masterColumn.genTable.tableComment}-${masterColumn.columnComment} -> ${slaveColumn.genTable.tableComment}-${slaveColumn.columnComment}")
                    insertColumn(masterColumn, slaveColumn)
                }
            }
        }
    }

    fun insertColumn(masterColumn: GenTableColumn, slaveColumn: GenTableColumn) {
        associationRepository.insert(
            GenTableAssociationInput(
                null,
                "${masterColumn.genTable.tableName}.${masterColumn.columnName} to ${slaveColumn.genTable.tableName}.${slaveColumn.columnName}",
                masterColumn.genTableId,
                masterColumn.id,
                slaveColumn.genTableId,
                slaveColumn.id,
                "ManyToOne",
                "${masterColumn.genTable.tableComment}-${masterColumn.columnComment} -> ${slaveColumn.genTable.tableComment}-${slaveColumn.columnComment}"
            )
        )
    }

    companion object {
        val COLUMN_TABLE_FETCHER = newFetcher(GenTableColumn::class).by {
            allTableFields()
            genTable(newFetcher(GenTable::class).by {
                allTableFields()
            })
        }

        val defaultAssociationStrategy: (GenTableColumn, GenTableColumn) -> Boolean = { masterColumn, slaveColumn ->
            suffixAssociation(masterColumn, slaveColumn)
        }

        private val simpleAssociation: (GenTableColumn, GenTableColumn) -> Boolean = { masterColumn, slaveColumn ->
            var result = false
            if (
                slaveColumn.pk == "1" &&
                masterColumn.idView == "1" &&
                "${slaveColumn.genTable.tableName}_${slaveColumn.columnName}" == masterColumn.columnName
            ) {
                result = true
            }
            result
        }

        private val mappingAssociation: (GenTableColumn, GenTableColumn) -> Boolean = { masterColumn, slaveColumn ->
            var result = false
            if (masterColumn.genTable.tableName.lowercase().endsWith("mapping") &&
                slaveColumn.columnName.lowercase().endsWith("id") &&
                "${slaveColumn.genTable.tableName}_${slaveColumn.columnName}" == masterColumn.columnName
            ) {
                result = true
            }
            result
        }

        private val suffixAssociation: (GenTableColumn, GenTableColumn) -> Boolean = { masterColumn, slaveColumn ->
            var result = false
            if (slaveColumn.genTableId != masterColumn.genTableId &&
                slaveColumn.pk == "1" &&
                suffixMatch(slaveColumn.columnName, masterColumn.columnName) > 1
            ) {
                result = true
            }
            result
        }

        private val suffixMatch: (String, String) -> Int = { s1, s2 ->
            val s1List = s1.split("_").reversed()
            val s2List = s2.split("_").reversed()
            var matchLength = 0

            for (i in 0 until (if (s1List.size < s2List.size) s1List.size else s2List.size)) {
                if (s1List[i] == s2List[i]) matchLength++
            }
            matchLength
        }
    }
}