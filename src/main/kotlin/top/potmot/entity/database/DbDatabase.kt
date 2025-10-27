package top.potmot.entity.database

import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.OneToMany
import org.babyfish.jimmer.sql.meta.UUIDIdGenerator
import java.util.UUID

@Entity
interface DbDatabase {
    @Id
    @GeneratedValue(generatorType = UUIDIdGenerator::class)
    val id: UUID

    val type: DatabaseType

    val name: String

    val url: String

    val username: String

    val password: String

    @OneToMany(mappedBy = "database")
    val tables: List<DbTable>
}
