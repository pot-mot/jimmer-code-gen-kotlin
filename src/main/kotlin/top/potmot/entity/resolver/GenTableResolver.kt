package top.potmot.entity.resolver

import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.KTransientResolver
import org.babyfish.jimmer.sql.kt.ast.expression.count
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.babyfish.jimmer.sql.kt.ast.expression.valueIn
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import top.potmot.entity.GenColumn
import top.potmot.entity.id
import top.potmot.entity.logicalDelete
import top.potmot.entity.tableId

@Component
class GenTableLogicalDeleteResolver(
    @Autowired val sqlClient: KSqlClient
) : KTransientResolver<Long, Boolean> {
    override fun resolve(ids: Collection<Long>): Map<Long, Boolean> =
        sqlClient.createQuery(GenColumn::class) {
            where(table.tableId valueIn ids)
            where(table.logicalDelete eq true)
            groupBy(table.tableId)
            select(table.tableId, count(table.id))
        }.execute()
            .associate {
                it._1 to (it._2 > 0)
            }

    override fun getDefaultValue(): Boolean = false
}