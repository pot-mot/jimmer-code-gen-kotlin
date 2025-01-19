package top.potmot.entity.resolver

import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.KTransientResolver
import org.babyfish.jimmer.sql.kt.ast.expression.count
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.babyfish.jimmer.sql.kt.ast.expression.valueIn
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import top.potmot.entity.GenProperty
import top.potmot.entity.entityId
import top.potmot.entity.id
import top.potmot.entity.idProperty
import top.potmot.entity.logicalDelete
import top.potmot.entity.orderKey

@Component
class GenEntityLogicalDeleteResolver(
    @Autowired val sqlClient: KSqlClient
) : KTransientResolver<Long, Boolean> {
    override fun resolve(ids: Collection<Long>): Map<Long, Boolean> =
        sqlClient.createQuery(GenProperty::class) {
            where(table.entityId valueIn ids)
            where(table.logicalDelete eq true)
            groupBy(table.entityId)
            select(table.entityId, count(table.id))
        }.execute()
            .associate { it._1 to (it._2 > 0) }

    override fun getDefaultValue(): Boolean = false
}

@Component
class GenEntityIdPropertiesResolver(
    @Autowired val sqlClient: KSqlClient
) : KTransientResolver<Long, List<Long>> {
    override fun resolve(ids: Collection<Long>): Map<Long, List<Long>> =
        sqlClient.createQuery(GenProperty::class) {
            where(table.entityId valueIn ids)
            where(table.idProperty eq true)
            orderBy(table.orderKey)
            select(table.entityId, table.id)
        }.execute()
            .groupBy({it._1}) { it._2 }
}
