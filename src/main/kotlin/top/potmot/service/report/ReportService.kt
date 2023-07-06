package top.potmot.service.report

import org.babyfish.jimmer.sql.fetcher.Fetcher
import org.babyfish.jimmer.sql.kt.ast.expression.ilike
import org.babyfish.jimmer.sql.kt.ast.expression.or
import org.babyfish.jimmer.sql.kt.fetcher.newFetcher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import top.potmot.dao.GenTableAssociationRepository
import top.potmot.dao.GenTableRepository
import top.potmot.model.*


@RestController
@CrossOrigin
class ReportService(
    @Autowired val tableRepository: GenTableRepository,
    @Autowired val associationRepository: GenTableAssociationRepository
) {
    @GetMapping("/tables/{keyword}")
    fun getAllTable(@PathVariable keyword: String): List<GenTable> {
        val temp = keyword.replace("_", "\\_").replace("%", "\\%")
        val keywords = temp.split(" ")
        return tableRepository.sql.createQuery(GenTable::class) {
            for (keyword1 in keywords) {
                where(
                    or(
                        table.tableName ilike keyword1
                    )
                )
            }
            select(table.fetch(TABLE_FETCHER))
        }.execute()
    }

    @GetMapping("/associations/{keyword}")
    fun getAllAssociation(@PathVariable keyword: String): List<GenTableAssociation> {
        val temp = keyword.replace("_", "\\_").replace("%", "\\%")
        val keywords = temp.split(" ")
        return associationRepository.sql.createQuery(GenTableAssociation::class) {
            for (keyword1 in keywords) {
                where(
                    or(
                        table.tableAssociationName ilike keyword1
                    )
                )
            }
            select(table.fetch(ASSOCIATION_FETCHER))
        }.execute()
    }

    fun getAssociation(fetcher: Fetcher<GenTableAssociation> = ALL_TABLE_FETCHER): List<GenTableAssociation> {
        return associationRepository.sql.createQuery(GenTableAssociation::class) {
            select(table.fetch(fetcher))
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
            targetColumn()
            sourceColumn()
            tableAssociationName()
            remark()
        }

        val ALL_TABLE_FETCHER = newFetcher(GenTableAssociation::class).by {
            allScalarFields()
            targetTable {
                allScalarFields()
            }
            sourceTable {
                allScalarFields()
            }
        }
    }
}