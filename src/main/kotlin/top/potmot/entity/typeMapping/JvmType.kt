package top.potmot.entity.typeMapping

import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.Serialized

@Entity
interface JvmType {
    @Id
    val id: String

    val typeExpression: String

    val serialized: Boolean

    @Serialized
    val extraImports: List<String>

    @Serialized
    val extraAnnotations: List<String>
}