package top.potmot.entity.database

import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.ManyToOne
import org.babyfish.jimmer.sql.OneToMany

@Entity
interface DbTable {
    @Id
    val id: String

    @ManyToOne
    val database: DbDatabase

    val schema: String

    val name: String

    val comment: String

    @OneToMany(mappedBy = "table")
    val columns: List<DbColumn>

    @OneToMany(mappedBy = "table")
    val indexes: List<DbIndex>

    @OneToMany(mappedBy = "table")
    val foreignKeys: List<DbForeignKey>
}