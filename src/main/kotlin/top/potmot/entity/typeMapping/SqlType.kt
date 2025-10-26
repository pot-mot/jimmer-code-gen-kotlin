package top.potmot.entity.typeMapping

import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.Id

@Entity
interface SqlType {
    @Id
    val id: String

    val type: String

    val dataSize: Int?

    val numericPrecision: Int?

    val defaultValue: String?
}
