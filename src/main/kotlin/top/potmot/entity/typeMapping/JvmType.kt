package top.potmot.entity.typeMapping

import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.OneToMany
import org.babyfish.jimmer.sql.Serialized
import org.babyfish.jimmer.sql.meta.UUIDIdGenerator
import java.util.UUID

@Entity
interface JvmType {
    @Id
    @GeneratedValue(generatorType = UUIDIdGenerator::class)
    val id: UUID

    val typeExpression: String

    val serialized: Boolean

    @Serialized
    val extraImports: List<String>

    @Serialized
    val extraAnnotations: List<String>

    @OneToMany(mappedBy = "result")
    val sqlToJvmMappingRules: List<SqlToJvmMappingRule>
}