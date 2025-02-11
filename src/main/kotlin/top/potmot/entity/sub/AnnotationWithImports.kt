package top.potmot.entity.sub

import org.babyfish.jimmer.sql.Serialized

@Serialized
data class AnnotationWithImports(
    val imports: List<String>,
    val annotations: List<String>,
)