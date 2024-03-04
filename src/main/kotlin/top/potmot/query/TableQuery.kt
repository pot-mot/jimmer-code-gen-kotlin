package top.potmot.query

import org.babyfish.jimmer.sql.kt.ast.expression.KNonNullExpression
import org.babyfish.jimmer.sql.kt.ast.expression.`eq?`
import org.babyfish.jimmer.sql.kt.ast.expression.ilike
import org.babyfish.jimmer.sql.kt.ast.expression.isNull
import org.babyfish.jimmer.sql.kt.ast.expression.or
import org.babyfish.jimmer.sql.kt.ast.expression.valueIn
import org.babyfish.jimmer.sql.kt.ast.table.KNonNullTable
import top.potmot.enumeration.TableType
import top.potmot.model.GenTable
import top.potmot.model.comment
import top.potmot.model.modelId
import top.potmot.model.name
import top.potmot.model.schemaId
import top.potmot.model.type

data class TableQuery(
    val keywords: List<String>? = null,
    val schemaIds: List<Long>? = null,
    val nonSchema: Boolean? = null,
    val modelIds: List<Long>? = null,
    val nonModel: Boolean? = null,
    val type: TableType? = null
) : BaseEntityQuery<GenTable>() {
    override fun toPredicateList(table: KNonNullTable<GenTable>): MutableList<KNonNullExpression<Boolean>?> {
        val predicates = super.toPredicateList(table)

        keywords?.takeIf { it.isNotEmpty() }?.forEach {
            predicates += or(
                table.name ilike it,
                table.comment ilike it
            )
        }

        nonModel?.let {
            predicates += table.modelId.isNull()
        }
        modelIds?.takeIf { it.isNotEmpty() }?.let {
            predicates += table.modelId valueIn it
        }

        nonSchema?.let {
            predicates += table.schemaId.isNull()
        }
        schemaIds?.takeIf { it.isNotEmpty() }?.let {
            predicates += table.schemaId valueIn it
        }

        predicates += table.type `eq?` type


        return predicates
    }
}
