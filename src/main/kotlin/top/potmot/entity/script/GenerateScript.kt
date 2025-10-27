package top.potmot.entity.script

import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.Id
import top.potmot.entity.database.ScriptDatabaseType
import top.potmot.entity.model.ScriptJvmLanguage
import org.babyfish.jimmer.sql.meta.UUIDIdGenerator
import java.util.UUID

@Entity
interface GenerateScript {
    @Id
    @GeneratedValue(generatorType = UUIDIdGenerator::class)
    val id: UUID

    val name: String

    val type: ScriptType

    val enabled: Boolean

    val databaseType: ScriptDatabaseType

    val jvmLanguage: ScriptJvmLanguage

    val scriptContent: String
}
