package top.potmot.entity.model

import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.ForeignKeyType
import org.babyfish.jimmer.sql.Id
import top.potmot.entity.database.DatabaseType
import top.potmot.entity.database.DbNameStrategy
import java.time.LocalDateTime

@Entity
interface Model {
    @Id
    val id: String

    val name: String

    val description: String

    val createdTime: LocalDateTime

    val modifiedTime: LocalDateTime

    val databaseType: DatabaseType

    val databaseNameStrategy: DbNameStrategy

    val defaultForeignKeyType: ForeignKeyType

    val jvmLanguage: JvmLanguage

    val defaultEnumerationStrategy: EnumerationStrategy

    val jsonData: String
}
