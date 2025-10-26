package top.potmot.entity.typeMapping

import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.ManyToOne
import top.potmot.entity.database.DatabaseSource
import top.potmot.entity.model.JvmSource

@Entity
interface JvmToSqlMappingRule {
    @Id
    val id: String

    val jvmSource: JvmSource

    val databaseSource: DatabaseSource

    val matchRegExp: String

    @ManyToOne
    val result: SqlType
}
