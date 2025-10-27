package top.potmot.entity.typeMapping

import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.ManyToOne
import top.potmot.entity.database.DatabaseSource
import top.potmot.entity.model.JvmSource
import org.babyfish.jimmer.sql.meta.UUIDIdGenerator
import java.util.UUID

@Entity
interface CrossType {
    @Id
    @GeneratedValue(generatorType = UUIDIdGenerator::class)
    val id: UUID

    val jvmSource: JvmSource

    val databaseSource: DatabaseSource

    @ManyToOne
    val sqlType: SqlType

    @ManyToOne
    val jvmType: JvmType

    @ManyToOne
    val tsType: TsType
}