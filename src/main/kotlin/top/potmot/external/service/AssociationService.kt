package top.potmot.external.service

import org.babyfish.jimmer.sql.kt.fetcher.newFetcher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import top.potmot.external.dao.GenTableAssociationRepository
import top.potmot.external.dao.GenTableColumnRepository
import top.potmot.external.dao.GenTableRepository
import top.potmot.external.model.GenTable
import top.potmot.external.model.GenTableColumn
import top.potmot.external.model.by
import top.potmot.external.model.input.GenTableAssociationInput

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
        for (targetColumn in tableColumnRepository.findAll(COLUMN_TABLE_FETCHER)) {
            for (sourceColumn in tableColumnRepository.findAll(COLUMN_TABLE_FETCHER)) {
                if (associationStrategy(sourceColumn, targetColumn)) {
                    println("${sourceColumn.genTable.tableComment}-${sourceColumn.columnComment} -> ${targetColumn.genTable.tableComment}-${targetColumn.columnComment}")
                    insertColumn(sourceColumn, targetColumn)
                }
            }
        }
    }

    fun insertColumn(sourceColumn: GenTableColumn, targetColumn: GenTableColumn) {
        associationRepository.insert(
            GenTableAssociationInput(
                null,
                "${sourceColumn.genTable.tableName}.${sourceColumn.columnName} to ${targetColumn.genTable.tableName}.${targetColumn.columnName}",
                sourceColumn.genTableId,
                sourceColumn.id,
                targetColumn.genTableId,
                targetColumn.id,
                "ManyToOne",
                "${sourceColumn.genTable.tableComment}-${sourceColumn.columnComment} -> ${targetColumn.genTable.tableComment}-${targetColumn.columnComment}"
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

        val defaultAssociationStrategy: (GenTableColumn, GenTableColumn) -> Boolean = { sourceColumn, targetColumn ->
            suffixAssociation(sourceColumn, targetColumn)
        }

        private val simpleAssociation: (GenTableColumn, GenTableColumn) -> Boolean = { sourceColumn, targetColumn ->
            var result = false
            if (
                targetColumn.pk == "1" &&
                sourceColumn.idView == "1" &&
                "${targetColumn.genTable.tableName}_${targetColumn.columnName}" == sourceColumn.columnName
            ) {
                result = true
            }
            result
        }

        private val mappingAssociation: (GenTableColumn, GenTableColumn) -> Boolean = { sourceColumn, targetColumn ->
            var result = false
            if (sourceColumn.genTable.tableName.lowercase().endsWith("mapping") &&
                targetColumn.columnName.lowercase().endsWith("id") &&
                "${targetColumn.genTable.tableName}_${targetColumn.columnName}" == sourceColumn.columnName
            ) {
                result = true
            }
            result
        }

        private val suffixAssociation: (GenTableColumn, GenTableColumn) -> Boolean = { sourceColumn, targetColumn ->
            var result = false
            if (targetColumn.genTableId != sourceColumn.genTableId &&
                targetColumn.pk == "1" &&
                suffixMatch(targetColumn.columnName, sourceColumn.columnName) > 1
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