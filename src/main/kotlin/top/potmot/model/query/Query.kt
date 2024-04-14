package top.potmot.model.query

import org.babyfish.jimmer.sql.kt.ast.expression.KNonNullExpression
import org.babyfish.jimmer.sql.kt.ast.query.KMutableRootQuery
import org.babyfish.jimmer.sql.kt.ast.table.KNonNullTable

interface Query<E : Any> {
    fun toPredicates(table: KNonNullTable<E>): Array<KNonNullExpression<Boolean>?>
}

fun <E : Any> KMutableRootQuery<E>.where(query: Query<E>) {
    where(query)
}
