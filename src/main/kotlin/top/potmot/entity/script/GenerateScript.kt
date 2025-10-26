package top.potmot.entity.script

import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.Id
import top.potmot.entity.database.ScriptDatabaseType
import top.potmot.entity.model.ScriptJvmLanguage

@Entity
interface GenerateScript {
    @Id
    val id: String

    val name: String

    val type: ScriptType

    val enabled: Boolean

    val databaseType: ScriptDatabaseType

    val jvmLanguage: ScriptJvmLanguage

    val scriptContent: String
}
