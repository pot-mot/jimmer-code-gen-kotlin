package top.potmot.entity.model

import org.babyfish.jimmer.sql.DissociateAction
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.ManyToOne
import org.babyfish.jimmer.sql.OnDissociate
import top.potmot.entity.database.DatabaseType
import top.potmot.entity.database.DbNameStrategy
import java.time.LocalDateTime
import org.babyfish.jimmer.sql.meta.UUIDIdGenerator
import java.util.UUID

@Entity
interface ModelHistory {
    @Id
    @GeneratedValue(generatorType = UUIDIdGenerator::class)
    val id: UUID

    @ManyToOne
    @OnDissociate(DissociateAction.DELETE)
    val model: Model

    val name: String

    val description: String

    val modifiedTime: LocalDateTime

    val databaseType: DatabaseType

    val databaseNameStrategy: DbNameStrategy

    val defaultForeignKeyType: ModelForeignKeyType

    val jvmLanguage: JvmLanguage

    val defaultEnumerationStrategy: EnumerationStrategy

    val jsonData: String
}