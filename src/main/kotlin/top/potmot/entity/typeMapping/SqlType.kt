package top.potmot.entity.typeMapping

import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.OneToMany
import org.babyfish.jimmer.sql.OrderedProp
import org.babyfish.jimmer.sql.meta.UUIDIdGenerator
import java.util.UUID

@Entity
interface SqlType {
    @Id
    @GeneratedValue(generatorType = UUIDIdGenerator::class)
    val id: UUID

    val orderKey: Int

    val type: String

    val dataSize: Int?

    val numericPrecision: Int?

    val defaultValue: String?

    @OneToMany(mappedBy = "result", orderedProps = [OrderedProp("orderKey")])
    val jvmToSqlMappingRule: List<JvmToSqlMappingRule>
}
