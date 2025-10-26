package top.potmot.entity.typeMapping

import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.Serialized

@Entity
interface TsType {
    @Id
    val id: String

    val typeExpression: String

    @Serialized
    val extraImports: List<TsImport>
}
