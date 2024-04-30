package top.potmot.entity.query

import org.babyfish.jimmer.sql.kt.ast.expression.KNonNullExpression
import org.babyfish.jimmer.sql.kt.ast.expression.`eq?`
import org.babyfish.jimmer.sql.kt.ast.expression.ilike
import org.babyfish.jimmer.sql.kt.ast.expression.or
import org.babyfish.jimmer.sql.kt.ast.expression.valueIn
import org.babyfish.jimmer.sql.kt.ast.table.KNonNullTable
import top.potmot.entity.GenColumn
import top.potmot.entity.comment
import top.potmot.entity.name
import top.potmot.entity.partOfPk
import top.potmot.entity.rawType
import top.potmot.entity.tableId
import top.potmot.entity.typeCode

data class ColumnQuery(
    val keywords: List<String>? = null,
    val tableIds: List<Long>? = null,
    val typeCode: Int? = null,
    val rawType: String? = null,
    val partOfPk: Boolean? = null
) : BaseEntityQuery<GenColumn>() {
    override fun toPredicateList(table: KNonNullTable<GenColumn>): MutableList<KNonNullExpression<Boolean>?> {
        val predicates = super.toPredicateList(table)

        keywords?.takeIf { it.isNotEmpty() }?.forEach {
            predicates += or(
                table.comment ilike it,
                table.name ilike it
            )
        }

        tableIds?.takeIf { it.isNotEmpty() }?.let {
            predicates += table.tableId valueIn it
        }

        predicates += table.typeCode `eq?` typeCode

        predicates += table.rawType `eq?` rawType

        predicates += table.partOfPk `eq?` partOfPk

        return predicates
    }
}
