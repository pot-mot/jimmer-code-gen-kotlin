package top.potmot.model.query

import org.babyfish.jimmer.sql.kt.ast.expression.KNonNullExpression
import org.babyfish.jimmer.sql.kt.ast.expression.`eq?`
import org.babyfish.jimmer.sql.kt.ast.expression.ilike
import org.babyfish.jimmer.sql.kt.ast.expression.or
import org.babyfish.jimmer.sql.kt.ast.expression.valueIn
import org.babyfish.jimmer.sql.kt.ast.table.KNonNullTable
import top.potmot.model.GenColumn
import top.potmot.model.base.BaseQuery
import top.potmot.model.comment
import top.potmot.model.name
import top.potmot.model.partOfPk
import top.potmot.model.tableId
import top.potmot.model.type
import top.potmot.model.typeCode

class ColumnQuery : BaseQuery<GenColumn>() {
    val keywords: List<String>? = null
    val tableIds: List<Long>? = null
    val type: String? = null
    val typeCode: Int? = null
    val partOfPk: Boolean? = null

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

        predicates += table.type `eq?` type

        predicates += table.typeCode `eq?` typeCode

        predicates += table.partOfPk `eq?` partOfPk

        return predicates
    }
}
