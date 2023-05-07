package top.potmot.jimmercodegen.service

import org.babyfish.jimmer.sql.kt.fetcher.newFetcher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import top.potmot.jimmercodegen.dao.GenTableAssociationRepository
import top.potmot.jimmercodegen.dao.GenTableColumnRepository
import top.potmot.jimmercodegen.model.GenTable
import top.potmot.jimmercodegen.model.GenTableColumn
import top.potmot.jimmercodegen.model.by
import top.potmot.jimmercodegen.model.input.GenTableAssociationInput

@Service
class AssociationService(
    @Autowired val associationRepository: GenTableAssociationRepository,
    @Autowired val tableColumnRepository: GenTableColumnRepository
) {
    companion object {
        val COLUMN_TABLE_FETCHER = newFetcher(GenTableColumn::class).by {
            allTableFields()
            table(newFetcher(GenTable::class).by {
                allTableFields()
            })
        }

        fun defaultAssociationStrategy(): (GenTableColumn, GenTableColumn) -> Boolean {
            return fun(masterColumn: GenTableColumn, slaveColumn: GenTableColumn): Boolean {
                if (masterColumn.columnType != slaveColumn.columnType) return false

                if (masterColumn.idView == "1"
                    &&
                    "${slaveColumn.table.tableName}_${slaveColumn.columnName}" == masterColumn.columnName
                ) {
                    println("${slaveColumn.table.tableName}_${slaveColumn.columnName}")
                    println(masterColumn.columnName)
                    return true
                }

                return false
            }
        }
    }

    /**
     * 根据策略获取所有表之间的关联
     */
    fun getAssociations(associationStrategy: (GenTableColumn, GenTableColumn) -> Boolean = defaultAssociationStrategy()) {
        for (slaveColumn in tableColumnRepository.findAll(COLUMN_TABLE_FETCHER)) {
            if (slaveColumn.pk != "1") continue

            for (masterColumn in tableColumnRepository.findAll(COLUMN_TABLE_FETCHER)) {
                if (masterColumn.tableId == slaveColumn.tableId) continue

                if (associationStrategy(masterColumn, slaveColumn)) {
                    println(
                        GenTableAssociationInput(
                            null,
                            "${slaveColumn.table.tableName}.${slaveColumn.columnName} to ${masterColumn.table.tableName}.${masterColumn.columnName}",
                            masterColumn.tableId,
                            masterColumn.genTableColumnId,
                            slaveColumn.tableId,
                            slaveColumn.genTableColumnId,
                            null
                        ).toEntity()
                    )
                }
            }
        }
    }
}