package top.potmot.model.base

import org.babyfish.jimmer.sql.kt.ast.expression.KNonNullExpression
import org.babyfish.jimmer.sql.kt.ast.table.KNonNullTable

interface Query<E : Any> {
    fun toPredicates(table: KNonNullTable<E>): Array<KNonNullExpression<Boolean>?>
}
