package top.potmot.entity.database

import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.ManyToOne
import org.babyfish.jimmer.sql.OneToMany

@Entity
interface DbForeignKey {
    @Id
    val id: String

    @ManyToOne
    val table: DbTable

    val name: String

    val comment: String

    val referencedTableName: String

    val referencedTableSchema: String

    val onUpdate: String?

    val onDelete: String?

    @OneToMany(mappedBy = "foreignKey")
    val columnRefs: List<DbColumnRef>
}
