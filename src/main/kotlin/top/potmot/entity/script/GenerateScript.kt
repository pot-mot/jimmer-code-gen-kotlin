package top.potmot.entity.script

import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.meta.UUIDIdGenerator
import top.potmot.entity.database.DatabaseTypeOrAny
import top.potmot.entity.model.JvmLanguageOrAny
import java.util.UUID

@Entity
interface GenerateScript {
    @Id
    @GeneratedValue(generatorType = UUIDIdGenerator::class)
    val id: UUID

    val name: String

    val type: ScriptType

    val enabled: Boolean

    val databaseType: DatabaseTypeOrAny

    val jvmLanguage: JvmLanguageOrAny

    val scriptContent: String
}
