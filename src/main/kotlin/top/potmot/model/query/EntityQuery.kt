package top.potmot.model.query

import org.babyfish.jimmer.sql.kt.ast.expression.KNonNullExpression
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.babyfish.jimmer.sql.kt.ast.expression.ilike
import org.babyfish.jimmer.sql.kt.ast.expression.isNull
import org.babyfish.jimmer.sql.kt.ast.expression.or
import org.babyfish.jimmer.sql.kt.ast.expression.valueIn
import org.babyfish.jimmer.sql.kt.ast.table.KNonNullTable
import top.potmot.model.GenEntity
import top.potmot.model.base.BaseQuery
import top.potmot.model.comment
import top.potmot.model.modelId
import top.potmot.model.name

class EntityQuery : BaseQuery<GenEntity>() {
    val keywords: List<String>? = null
    val names: List<String>? = null
    val modelIds: List<Long>? = null
    val nonModel: Boolean? = null

    override fun toPredicateList(table: KNonNullTable<GenEntity>): MutableList<KNonNullExpression<Boolean>?> {
        val predicates = super.toPredicateList(table)

        keywords?.takeIf { it.isNotEmpty() }?.forEach {
            predicates += or(
                table.name ilike it,
                table.comment ilike it,
            )
        }
        names?.takeIf { it.isNotEmpty() }?.forEach {
            predicates += table.name eq it
        }

        nonModel?.let {
            predicates += table.modelId.isNull()
        }
        modelIds?.takeIf { it.isNotEmpty() }?.let {
            predicates += table.modelId valueIn it
        }

        return predicates
    }
}
