package top.potmot.entity.resolver

import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.KTransientResolver
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.babyfish.jimmer.sql.kt.ast.expression.valueIn
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import top.potmot.entity.GenEnumItem
import top.potmot.entity.enumId
import top.potmot.entity.id
import top.potmot.entity.orderKey

@Component
class GenEnumDefaultItemsResolver(
    @Autowired val sqlClient: KSqlClient
) : KTransientResolver<Long, List<Long>> {
    override fun resolve(ids: Collection<Long>): Map<Long, List<Long>> =
        sqlClient.createQuery(GenEnumItem::class) {
            where(table.enumId valueIn ids)
            where(table.orderKey eq 0)
            select(table.enumId, table.id)
        }.execute()
            .groupBy({it._1}) { it._2 }
}