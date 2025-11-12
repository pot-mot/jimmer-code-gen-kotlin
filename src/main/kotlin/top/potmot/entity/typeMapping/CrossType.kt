package top.potmot.entity.typeMapping

import org.babyfish.jimmer.sql.DissociateAction
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.ManyToOne
import org.babyfish.jimmer.sql.OnDissociate
import top.potmot.entity.database.DatabaseSource
import top.potmot.entity.model.JvmSource
import org.babyfish.jimmer.sql.meta.UUIDIdGenerator
import java.util.UUID

@Entity
interface CrossType {
    @Id
    @GeneratedValue(generatorType = UUIDIdGenerator::class)
    val id: UUID

    val orderKey: Int

    val jvmSource: JvmSource

    val databaseSource: DatabaseSource

    @ManyToOne
    @OnDissociate(DissociateAction.DELETE)
    val sqlType: SqlType

    @ManyToOne
    @OnDissociate(DissociateAction.DELETE)
    val jvmType: JvmType

    @ManyToOne
    @OnDissociate(DissociateAction.DELETE)
    val tsType: TsType
}