package top.potmot.service.analysis

import org.babyfish.jimmer.sql.kt.fetcher.newFetcher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import top.potmot.config.AssociationConfig
import top.potmot.dao.GenTableAssociationRepository
import top.potmot.dao.GenTableColumnRepository
import top.potmot.model.GenTable
import top.potmot.model.GenTableColumn
import top.potmot.model.by
import top.potmot.model.input.GenTableAssociationInput

@Service
class AssociationService(
    @Autowired val associationRepository: GenTableAssociationRepository,
    @Autowired val tableColumnRepository: GenTableColumnRepository,
) {
    fun clearAssociations() {
        associationRepository.deleteAll()
    }

    /**
     * 根据策略引入目前数据库中所有表之间的关联
     */
    fun importAssociations(
        associationStrategy: (GenTableColumn, GenTableColumn) -> Boolean = AssociationStrategy.estimate
    ) {
        for (targetColumn in tableColumnRepository.findAll(COLUMN_TABLE_FETCHER)) {
            if (AssociationConfig.ignoreColumns.contains(targetColumn.columnName)) continue
            for (sourceColumn in tableColumnRepository.findAll(COLUMN_TABLE_FETCHER)) {
                if (AssociationConfig.ignoreColumns.contains(targetColumn.columnName)) continue
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


    }
}