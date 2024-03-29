package top.potmot.query

import org.babyfish.jimmer.sql.kt.ast.expression.KNonNullExpression
import org.babyfish.jimmer.sql.kt.ast.expression.`eq?`
import org.babyfish.jimmer.sql.kt.ast.expression.ilike
import org.babyfish.jimmer.sql.kt.ast.expression.or
import org.babyfish.jimmer.sql.kt.ast.expression.valueIn
import org.babyfish.jimmer.sql.kt.ast.table.KNonNullTable
import top.potmot.model.GenColumn
import top.potmot.model.comment
import top.potmot.model.name
import top.potmot.model.partOfPk
import top.potmot.model.rawType
import top.potmot.model.tableId
import top.potmot.model.typeCode

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
