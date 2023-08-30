package top.potmot.service.impl

import org.babyfish.jimmer.View
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.babyfish.jimmer.sql.kt.ast.expression.gt
import org.babyfish.jimmer.sql.kt.ast.expression.ilike
import org.babyfish.jimmer.sql.kt.ast.expression.lt
import org.babyfish.jimmer.sql.kt.ast.expression.valueIn
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import top.potmot.dao.GenAssociationRepository
import top.potmot.model.GenAssociation
import top.potmot.model.comment
import top.potmot.model.createdTime
import top.potmot.model.dto.GenAssociationCommonInput
import top.potmot.model.dto.GenAssociationCommonView
import top.potmot.model.id
import top.potmot.model.query.AssociationQuery
import top.potmot.model.sourceColumn
import top.potmot.model.sourceColumnId
import top.potmot.model.tableId
import top.potmot.model.targetColumn
import top.potmot.model.targetColumnId
import top.potmot.service.AssociationService
import kotlin.reflect.KClass

@Service
class AssociationServiceImpl(
    @Autowired val associationRepository: GenAssociationRepository
): AssociationService {
    override fun <T : View<GenAssociation>> selectAssociations(tableIds: List<Long>, viewClass: KClass<T>): List<T> {
        TODO("Not yet implemented")
    }

    @Transactional
    override fun saveAssociations(associations: List<GenAssociationCommonInput>): List<GenAssociationCommonView> {
        TODO("Not yet implemented")
    }

    override fun <T : View<GenAssociation>> queryAssociations(query: AssociationQuery, viewClass: KClass<T>): List<T> {
        return associationRepository.sql.createQuery(GenAssociation::class) {
            query.keywords?.takeIf { it.isNotEmpty() }?.let {
                query.keywords.forEach {
                    where(table.comment ilike it)
                }
            }

            query.sourceColumnId?.let {
                where(table.sourceColumnId eq it)
            }
            query.sourceTableId?.let {
                where(table.sourceColumn.tableId eq it)
            }

            query.targetColumnId?.let {
                where(table.targetColumnId eq it)
            }
            query.targetTableId?.let {
                where(table.targetColumn.tableId eq it)
            }

            query.ids?.takeIf { it.isNotEmpty() }?.let {
                where(table.id valueIn it)
            }
            query.createdTime?.let {
                where(table.createdTime gt it.start)
                where(table.createdTime lt it.end)
            }
            query.modifiedTime?.let {
                where(table.createdTime gt it.start)
                where(table.createdTime lt it.end)
            }

            select(table.fetch(viewClass))
        }.execute()
    }

    @Transactional
    override fun deleteAssociations(ids: List<Long>): Int {
        TODO("Not yet implemented")
    }
}
