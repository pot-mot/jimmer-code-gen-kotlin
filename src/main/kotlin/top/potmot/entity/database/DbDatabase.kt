package top.potmot.entity.database

import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.OneToMany

@Entity
interface DbDatabase {
    @Id
    val id: String

    val type: DatabaseType

    val name: String

    val url: String

    val username: String

    val password: String

    @OneToMany(mappedBy = "database")
    val tables: List<DbTable>
}
