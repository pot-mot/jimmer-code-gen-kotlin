package top.potmot.entity.database

import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.ManyToOne


@Entity
interface DbColumnRef {
    @Id
    val id: String

    @ManyToOne
    val foreignKey: DbForeignKey

    val columnName: String

    val referencedColumnName: String
}
