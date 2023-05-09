package top.potmot.jimmercodegen.service

import org.babyfish.jimmer.sql.kt.ast.expression.ilike
import org.babyfish.jimmer.sql.kt.ast.expression.or
import org.babyfish.jimmer.sql.kt.fetcher.newFetcher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import top.potmot.jimmercodegen.dao.GenTableAssociationRepository
import top.potmot.jimmercodegen.dao.GenTableRepository
import top.potmot.jimmercodegen.model.*

@RestController
@CrossOrigin
class ReturnService(
    @Autowired val tableRepository: GenTableRepository,
    @Autowired val associationRepository: GenTableAssociationRepository
) {
    @GetMapping("/tables/{keyword}")
    fun getAllTable(@PathVariable keyword: String): List<GenTable> {
        val keywords = keyword.split(" ")
        return tableRepository.sql.createQuery(GenTable::class) {
            for (keyword1 in keywords.withIndex()) {
                if (keyword1.index == 0) {
                    where(
                        table.tableName ilike keyword1.value
                    )
                } else {
                    or(
                        table.tableName ilike keyword1.value
                    )
                }
            }
            select(table.fetch(TABLE_FETCHER))
        }.execute()
    }

    @GetMapping("/associations/{keyword}")
    fun getAllAssociation(@PathVariable keyword: String): List<GenTableAssociation> {
        val keywords = keyword.split(" ")
        return tableRepository.sql.createQuery(GenTableAssociation::class) {
            for (keyword1 in keywords.withIndex()) {
                if (keyword1.index == 0) {
                    where(
                        table.tableAssociationName ilike keyword1.value
                    )
                } else {
                    or(
                        table.tableAssociationName ilike keyword1.value
                    )
                }
            }
            select(table.fetch(ASSOCIATION_FETCHER))
        }.execute()
    }


    companion object {
        val TABLE_FETCHER = newFetcher(GenTable::class).by {
            tableName()
            tableComment()
            columns {
                columnName()
                columnComment()
            }
        }

        val ASSOCIATION_FETCHER = newFetcher(GenTableAssociation::class).by {
            slaveColumn()
            masterColumn()
            tableAssociationName()
            remark()
        }
    }
}