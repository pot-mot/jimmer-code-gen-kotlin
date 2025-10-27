package top.potmot.entity.database

import org.babyfish.jimmer.sql.DissociateAction
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.Key
import org.babyfish.jimmer.sql.ManyToOne
import org.babyfish.jimmer.sql.OnDissociate
import org.babyfish.jimmer.sql.OneToMany
import org.babyfish.jimmer.sql.meta.UUIDIdGenerator
import java.util.UUID

@Entity
interface DbTable {
    @Id
    @GeneratedValue(generatorType = UUIDIdGenerator::class)
    val id: UUID

    @ManyToOne
    @OnDissociate(DissociateAction.DELETE)
    @Key
    val database: DbDatabase

    @Key
    val schema: String

    @Key
    val name: String

    val comment: String

    @OneToMany(mappedBy = "table")
    val columns: List<DbColumn>

    @OneToMany(mappedBy = "table")
    val indexes: List<DbIndex>

    @OneToMany(mappedBy = "table")
    val foreignKeys: List<DbForeignKey>
}