package top.potmot.jimmercodegen.service

import org.babyfish.jimmer.sql.kt.fetcher.newFetcher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import top.potmot.jimmercodegen.dao.GenTableAssociationRepository
import top.potmot.jimmercodegen.dao.GenTableRepository
import top.potmot.jimmercodegen.model.GenTable
import top.potmot.jimmercodegen.model.GenTableAssociation
import top.potmot.jimmercodegen.model.by

@RestController
class ReturnService (
    @Autowired val tableRepository: GenTableRepository,
    @Autowired val associationRepository: GenTableAssociationRepository
) {
    @GetMapping("/{id}")
    fun getTable(@PathVariable id: Long): GenTable? {
        return tableRepository.findNullable(id, TABLE_NAME_FETCHER)
    }

    @GetMapping("/tables/simple")
    fun getTablesSimple(): List<GenTable> {
        return tableRepository.findAll(TABLE_SIMPLE_FETCHER)
    }

    @GetMapping("/tables/multi")
    fun getTablesMulti(): List<GenTable> {
        return tableRepository.findAll(TABLE_MULTI_FETCHER)
    }

    @GetMapping("/tables")
    fun getAllTable(): List<GenTable> {
        return tableRepository.findAll()
    }

    @GetMapping("/associations")
    fun getAllAssociation(): List<GenTableAssociation> {
        return associationRepository.findAll()
    }



    companion object {
        val TABLE_NAME_FETCHER = newFetcher(GenTable::class).by {
            tableName()
            tableComment()
            masterTables {
                tableName()
                tableComment()
            }
            slaveTables {
                tableName()
                tableComment()
            }
        }

        val TABLE_SIMPLE_FETCHER = newFetcher(GenTable::class).by {
            tableName()
            masterAssociation {
                slaveTableId()
                slaveColumnId()
            }
            slaveAssociation {
                masterTableId()
                masterColumnId()
            }
        }

        val TABLE_MULTI_FETCHER = newFetcher(GenTable::class).by {
            allTableFields()
            columns {
                allTableFields()
            }
            masterAssociation {
                allTableFields()
            }
            slaveTables {
                allTableFields()
            }
            slaveAssociation {
                allTableFields()
            }
            masterTables {
                allTableFields()
            }
        }
    }
}