package top.potmot.entity.query

import org.babyfish.jimmer.sql.kt.ast.expression.KNonNullExpression
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.babyfish.jimmer.sql.kt.ast.expression.ilike
import org.babyfish.jimmer.sql.kt.ast.expression.isNull
import org.babyfish.jimmer.sql.kt.ast.expression.or
import org.babyfish.jimmer.sql.kt.ast.expression.valueIn
import org.babyfish.jimmer.sql.kt.ast.table.KNonNullTable
import top.potmot.entity.GenEnum
import top.potmot.entity.comment
import top.potmot.entity.modelId
import top.potmot.entity.name

data class EnumQuery(
    val keywords: List<String>? = null,
    val names: List<String>? = null,
    val modelIds: List<Long>? = null,
    val nonModel: Boolean? = null,
) : BaseEntityQuery<GenEnum>() {
    override fun toPredicateList(table: KNonNullTable<GenEnum>): MutableList<KNonNullExpression<Boolean>?> {
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
