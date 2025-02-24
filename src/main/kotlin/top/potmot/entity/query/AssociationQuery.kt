package top.potmot.entity.query

import org.babyfish.jimmer.sql.kt.ast.expression.KNonNullExpression
import org.babyfish.jimmer.sql.kt.ast.expression.and
import org.babyfish.jimmer.sql.kt.ast.expression.or
import org.babyfish.jimmer.sql.kt.ast.expression.valueIn
import org.babyfish.jimmer.sql.kt.ast.table.KNonNullTable
import top.potmot.entity.GenAssociation
import top.potmot.entity.sourceTableId
import top.potmot.entity.targetTableId
import top.potmot.enumeration.SelectType

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
