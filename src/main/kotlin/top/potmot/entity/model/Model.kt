package top.potmot.entity.model

import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.OneToMany
import org.babyfish.jimmer.sql.OrderedProp
import top.potmot.entity.database.DatabaseType
import top.potmot.entity.database.DbNameStrategy
import java.time.LocalDateTime
import org.babyfish.jimmer.sql.meta.UUIDIdGenerator
import java.util.UUID

@Entity
interface Model {
    @Id
    @GeneratedValue(generatorType = UUIDIdGenerator::class)
    val id: UUID

    val name: String

    val description: String

    val createdTime: LocalDateTime

    val modifiedTime: LocalDateTime

    val databaseType: DatabaseType

    val databaseNameStrategy: DbNameStrategy

    val defaultForeignKeyType: ModelForeignKeyType

    val jvmLanguage: JvmLanguage

    val defaultEnumerationStrategy: EnumerationStrategy

    val viewport: ModelViewport

    val jsonData: String

    @OneToMany(
        mappedBy = "model",
        orderedProps = [OrderedProp(value = "modifiedTime")]
    )
    val histories: List<ModelHistory>
}

