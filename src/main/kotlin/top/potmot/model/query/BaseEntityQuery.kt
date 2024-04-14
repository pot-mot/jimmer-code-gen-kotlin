package top.potmot.model.query

import org.babyfish.jimmer.sql.kt.ast.expression.KNonNullExpression
import org.babyfish.jimmer.sql.kt.ast.expression.`between?`
import org.babyfish.jimmer.sql.kt.ast.expression.valueIn
import org.babyfish.jimmer.sql.kt.ast.table.KNonNullTable
import top.potmot.model.query.param.TimeRangeQueryParam
import java.time.LocalDateTime

abstract class BaseEntityQuery<E : Any> : Query<E> {
    val ids: List<Long>? = null
    val createdTime: TimeRangeQueryParam? = null
    val modifiedTime: TimeRangeQueryParam? = null

    open fun toPredicateList(table: KNonNullTable<E>): MutableList<KNonNullExpression<Boolean>?> {
        val predicates = mutableListOf<KNonNullExpression<Boolean>?>()

        ids?.apply {
            predicates += table.get<Long>("id") valueIn this
        }

        predicates += table.get<LocalDateTime>("createdTime").`between?`(createdTime?.start, createdTime?.end)

        predicates += table.get<LocalDateTime>("modifiedTime").`between?`(modifiedTime?.start, modifiedTime?.end)

        return predicates
    }

    override fun toPredicates(table: KNonNullTable<E>): Array<KNonNullExpression<Boolean>?> =
        toPredicateList(table).toTypedArray()
}
