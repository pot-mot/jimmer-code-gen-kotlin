package top.potmot.entity.database

import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.ManyToOne
import org.babyfish.jimmer.sql.Serialized

@Entity
interface DbIndex {
    @Id
    val id: String

    @ManyToOne
    val table: DbTable

    val name: String

    @Serialized
    val columnNames: List<String>

    val uniqueIndex: Boolean

    val wherePredicates: String?
}
