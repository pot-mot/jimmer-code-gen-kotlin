package top.potmot.entity.sub

import org.babyfish.jimmer.sql.ForeignKeyType
import org.babyfish.jimmer.sql.Serialized

@Serialized
data class JoinColumnMeta(
    var joinColumnName: String,
    var referencedColumnName: String? = null,
    var foreignKeyType: ForeignKeyType? = null,
) {
    fun realFk() = foreignKeyType == ForeignKeyType.REAL
}
