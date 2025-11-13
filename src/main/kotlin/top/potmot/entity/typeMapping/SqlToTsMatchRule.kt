package top.potmot.entity.typeMapping

import org.babyfish.jimmer.sql.DissociateAction
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.ManyToOne
import org.babyfish.jimmer.sql.OnDissociate
import org.babyfish.jimmer.sql.meta.UUIDIdGenerator
import top.potmot.entity.database.DatabaseTypeOrAny
import java.util.UUID

@Entity
interface SqlToTsMatchRule {
    @Id
    @GeneratedValue(generatorType = UUIDIdGenerator::class)
    val id: UUID

    val orderKey: Int

    val databaseSource: DatabaseTypeOrAny

    val matchRegExp: String

    @ManyToOne
    @OnDissociate(DissociateAction.DELETE)
    val result: TsType
}
