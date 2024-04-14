package top.potmot.model.query

import org.babyfish.jimmer.sql.kt.ast.expression.KNonNullExpression
import org.babyfish.jimmer.sql.kt.ast.expression.and
import org.babyfish.jimmer.sql.kt.ast.expression.`eq?`
import org.babyfish.jimmer.sql.kt.ast.expression.ilike
import org.babyfish.jimmer.sql.kt.ast.expression.or
import org.babyfish.jimmer.sql.kt.ast.expression.valueIn
import org.babyfish.jimmer.sql.kt.ast.table.KNonNullTable
import top.potmot.enumeration.AssociationType
import top.potmot.enumeration.SelectType
import top.potmot.model.GenAssociation
import top.potmot.model.columnReferences
import top.potmot.model.name
import top.potmot.model.sourceColumnId
import top.potmot.model.sourceTableId
import top.potmot.model.targetColumnId
import top.potmot.model.targetTableId
import top.potmot.model.type

data class AssociationQuery(
    val keywords: List<String>? = null,
    val type: AssociationType? = null,
    val sourceTableId: Long? = null,
    val targetTableId: Long? = null,
    val sourceColumnId: Long? = null,
    val targetColumnId: Long? = null
) : BaseEntityQuery<GenAssociation>() {
    override fun toPredicateList(table: KNonNullTable<GenAssociation>): MutableList<KNonNullExpression<Boolean>?> {
        val predicates = super.toPredicateList(table)

        keywords?.takeIf { it.isNotEmpty() }?.forEach {
            predicates += table.name ilike it
        }

        predicates += table.type `eq?` type

        predicates += table.sourceTableId `eq?` sourceTableId

        predicates += table.targetTableId `eq?` targetTableId

        predicates += table.columnReferences {
            sourceColumnId `eq?` this@AssociationQuery.sourceColumnId
        }
        predicates += table.columnReferences {
            targetColumnId `eq?` this@AssociationQuery.targetColumnId
        }

        return predicates
    }
}

data class AssociationTableQuery(
    val tableIds: List<Long>? = null,
    val sourceTableIds: List<Long>? = null,
    val targetTableIds: List<Long>? = null,
    val selectType: SelectType,
) : Query<GenAssociation> {
    override fun toPredicates(table: KNonNullTable<GenAssociation>): Array<KNonNullExpression<Boolean>?> {
        val predicates = mutableListOf<KNonNullExpression<Boolean>?>()

        tableIds?.takeIf { it.isNotEmpty() }?.let {
            predicates += listOf(
                table.sourceTableId valueIn it,
                table.targetTableId valueIn it
            )
        }

        sourceTableIds?.takeIf { it.isNotEmpty() }?.let {
            predicates += table.sourceTableId valueIn it
        }

        targetTableIds?.takeIf { it.isNotEmpty() }?.let {
            predicates += table.targetTableId valueIn it
        }

        return arrayOf(
            when (selectType) {
                SelectType.AND -> and(*predicates.toTypedArray())
                SelectType.OR -> or(*predicates.toTypedArray())
            }
        )
    }
}

data class AssociationColumnQuery(
    val columnIds: List<Long>? = null,
    val sourceColumnIds: List<Long>? = null,
    val targetColumnIds: List<Long>? = null,
    val selectType: SelectType,
) : Query<GenAssociation> {
    override fun toPredicates(table: KNonNullTable<GenAssociation>): Array<KNonNullExpression<Boolean>?> {
        val predicates = mutableListOf<KNonNullExpression<Boolean>?>()

        columnIds?.takeIf { it.isNotEmpty() }?.let {
            predicates += listOf(
                table.columnReferences { sourceColumnId valueIn it },
                table.columnReferences { targetColumnId valueIn it }
            )
        }

        sourceColumnIds?.takeIf { it.isNotEmpty() }?.let {
            predicates += table.columnReferences { sourceColumnId valueIn it }
        }

        targetColumnIds?.takeIf { it.isNotEmpty() }?.let {
            predicates += table.columnReferences { targetColumnId valueIn it }
        }

        return arrayOf(
            when (selectType) {
                SelectType.AND -> and(*predicates.toTypedArray())
                SelectType.OR -> or(*predicates.toTypedArray())
            }
        )
    }
}


