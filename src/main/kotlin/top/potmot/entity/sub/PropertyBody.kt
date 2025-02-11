package top.potmot.entity.sub

import org.babyfish.jimmer.sql.Serialized

@Serialized
data class PropertyBody(
    val imports: List<String>,
    val codeBlock: String,
)