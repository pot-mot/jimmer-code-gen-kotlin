package top.potmot.entity.sub

import org.babyfish.jimmer.sql.ForeignKeyType
import org.babyfish.jimmer.sql.Serialized

@Serialized
data class JoinTableMeta(
    val tableName: String,
    val columnNamePairs: List<Pair<String, String>>,
    val foreignKeyType: ForeignKeyType? = null,
) {
    fun realFk() = foreignKeyType == ForeignKeyType.REAL
}
