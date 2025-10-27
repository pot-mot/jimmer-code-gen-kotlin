package top.potmot.entity.database

import org.babyfish.jimmer.sql.DissociateAction
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.Key
import org.babyfish.jimmer.sql.ManyToOne
import org.babyfish.jimmer.sql.OnDissociate
import org.babyfish.jimmer.sql.Serialized
import org.babyfish.jimmer.sql.meta.UUIDIdGenerator
import java.util.UUID

@Entity
interface DbColumn {
    @Id
    @GeneratedValue(generatorType = UUIDIdGenerator::class)
    val id: UUID

    @ManyToOne
    @OnDissociate(DissociateAction.DELETE)
    @Key
    val table: DbTable

    @Key
    val name: String

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
