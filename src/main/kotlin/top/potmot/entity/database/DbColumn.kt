package top.potmot.entity.database

import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.ManyToOne
import org.babyfish.jimmer.sql.Serialized

@Entity
interface DbColumn {
    @Id
    val id: String

    val name: String

    @ManyToOne
    val table: DbTable

    val comment: String

    val type: String

    val dataSize: Int?

    val numericPrecision: Int?

    val nullable: Boolean

    val defaultValue: String?

    val partOfPrimaryKey: Boolean?

    val autoIncrement: Boolean?

    @Serialized
    val otherConstraints: List<String>?
}
