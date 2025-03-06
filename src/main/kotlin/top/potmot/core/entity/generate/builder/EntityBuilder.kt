package top.potmot.core.entity.generate.builder

import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.DissociateAction
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.ForeignKeyType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.JoinColumn
import org.babyfish.jimmer.sql.JoinTable
import org.babyfish.jimmer.sql.Key
import org.babyfish.jimmer.sql.MappedSuperclass
import org.babyfish.jimmer.sql.OnDissociate
import org.babyfish.jimmer.sql.Table
import top.potmot.core.config.getContextOrGlobal
import top.potmot.core.database.generate.identifier.IdentifierType
import top.potmot.core.database.generate.identifier.getIdentifierProcessor
import top.potmot.entity.dto.GenEntityGenerateView
import top.potmot.entity.sub.AnnotationWithImports
import top.potmot.enumeration.TableType
import top.potmot.utils.collection.flatSetOf
import top.potmot.utils.collection.forEachJoinDo
import top.potmot.utils.string.buildScopeString

typealias EntityView = GenEntityGenerateView
typealias PropertyView = GenEntityGenerateView.TargetOf_properties

abstract class EntityBuilder : CodeBuilder() {
    abstract val associationAnnotationBuilder: AssociationAnnotationBuilder

    abstract fun entityLine(entity: EntityView): String

    abstract fun propertyBlock(property: PropertyView): String

    private fun String.quotationEscape(): String = replace("\"", "\\\"")

    fun build(entity: EntityView): String =
        buildScopeString {
            line(packageLine(entity.packagePath))

            val entityAnnotations = annotationWithImports(entity)
            val propertyAnnotationsMap = entity.properties.associateWith { annotationWithImports(it) }
            val allImports = flatSetOf(
                entityAnnotations.imports,
                propertyAnnotationsMap.flatMap { it.value.imports }
            )

            line()
            lines(filterImports(entity.packagePath, allImports)) { importLine(it) }
            line()

            block(blockComment(entity))
            entityAnnotations.annotations.forEach { block(it) }
            line(entityLine(entity) + " {")

            scope {
                propertyAnnotationsMap.forEachJoinDo({ _, _ ->
                    line()
                }) { property, annotationWithImports ->
                    block(blockComment(property))
                    annotationWithImports.annotations.forEach { block(it) }
                    block(propertyBlock(property))
                }
            }

            line("}")
        }

    open fun EntityView.tableAnnotation(): String =
        buildString {
            val context = getContextOrGlobal()
            val identifiers = context.dataSourceType.getIdentifierProcessor()

            append("@Table(name = \"")

            context.tablePath.takeIf { it.isNotBlank() }?.let {
                append(it.let { identifiers.process(it, IdentifierType.DEFAULT) })
                append(".")
            }
            append(
                table.name.let { identifiers.process(it, IdentifierType.TABLE_NAME) }.quotationEscape()
            )

            append("\")")
        }

    open fun PropertyView.columnAnnotation(): String? =
        column?.let {
            val context = getContextOrGlobal()
            val identifiers = context.dataSourceType.getIdentifierProcessor()

            buildString {
                append("@Column(name = \"")
                append(
                    it.name.let { identifiers.process(it, IdentifierType.COLUMN_NAME) }.quotationEscape()
                )
                append("\")")
            }
        }


    open fun blockComment(entity: EntityView): String? =
        createBlockComment(
            entity.comment,
            entity.remark,
            params = entity.author.ifEmpty { getContextOrGlobal().author }.let { author ->
                if (author.isNotBlank()) {
                    mapOf(
                        Pair("author", author),
                    )
                } else emptyMap()
            }
        )

    open fun blockComment(property: PropertyView): String? =
        createBlockComment(
            property.comment,
            property.remark,
            params =
            if (property.mappedBy != null) {
                mapOf(
                    Pair("see", property.type + "." + property.mappedBy),
                )
            } else emptyMap()
        )

    /**
     * 获取属性的简单类型名
     * 判断过程如下：
     *  1. 优先采用枚举类型
     *  2. 使用 typeTable 对应的 Entity
     *  3. 使用映射后的 type
     */
    fun PropertyView.shortType(): String {
        val baseType =
            typeTable?.entity?.name ?: enum?.name ?: let {
                type.split(".").last()
            }

        return if (listType) {
            "List<$baseType>"
        } else {
            baseType
        }
    }

    private fun PropertyView.fullType(): String {
        enum?.let {
            return if (it.packagePath.isNotBlank()) {
                it.packagePath + "." + it.name
            } else {
                it.name
            }
        }

        typeTable?.entity?.let {
            return if (it.packagePath.isNotBlank()) {
                it.packagePath + "." + it.name
            } else {
                it.name
            }
        }

        return type
    }

    open fun annotationWithImports(entity: EntityView): AnnotationWithImports {
        val imports = mutableListOf<String>()
        val annotations = mutableListOf<String>()

        val context = getContextOrGlobal()

        entity.apply {
            if (entity.table.type == TableType.SUPER_TABLE) {
                imports += MappedSuperclass::class.java.name
                annotations += "@MappedSuperclass"
            } else {
                imports += Entity::class.java.name
                annotations += "@Entity"

                if (context.tableAnnotation) {
                    imports += Table::class.java.name
                    annotations += tableAnnotation()
                }
            }

            if (otherAnnotation != null) {
                imports += otherAnnotation.imports
                annotations += otherAnnotation.annotations
            }
        }

        return AnnotationWithImports(
            imports = imports,
            annotations = annotations,
        )
    }

    abstract fun validateAnnotations(property: PropertyView): AnnotationWithImports

    open fun annotationWithImports(property: PropertyView): AnnotationWithImports {
        val imports = mutableListOf<String>()
        val annotations = mutableListOf<String>()

        val context = getContextOrGlobal()

        property.apply {
            if (idProperty) {
                imports += Id::class.java.name
                annotations += "@Id"
                if (generatedId) {
                    (property.generatedIdAnnotation ?: context.generatedIdAnnotation).let {
                        imports += it.imports
                        annotations += it.annotations
                    }
                }
            } else {
                if (keyProperty) {
                    imports += Key::class.java.name
                    annotations += buildString {
                        if (keyGroups.isEmpty()) {
                            append("@Key")
                        } else {
                            append(keyGroups.joinToString("\n") {
                                if (it.isBlank()) "@Key" else "@Key(group = \"${it}\")"
                            })
                        }
                    }
                } else if (logicalDelete) {
                    (property.logicalDeletedAnnotation ?: context.logicalDeletedAnnotation).let {
                        imports += it.imports
                        annotations += it.annotations
                    }
                }
            }

            if (associationType != null) {
                if (idView) {
                    imports += IdView::class.java.name
                    annotations += idViewTarget?.let { "@IdView(\"$it\")" } ?: "@IdView"
                } else {
                    imports += associationType.annotation.java.name
                    ("@" + associationType.annotation.java.simpleName).let {
                        annotations += if (mappedBy != null) {
                            "$it(mappedBy = \"$mappedBy\")"
                        } else {
                            it
                        }
                    }

                    joinTableMeta?.let {
                        imports += JoinTable::class.java.name

                        if (it.columnNamePairs.size > 1) {
                            imports += JoinColumn::class.java.name
                        }

                        if (context.realFk != it.realFk()) {
                            imports += ForeignKeyType::class.java.name
                            imports += JoinColumn::class.java.name
                        }

                        annotations += associationAnnotationBuilder.build(it)
                    }

                    joinColumnMetas?.takeIf { it.isNotEmpty() }?.let {
                        imports += JoinColumn::class.java.name
                        if (it.any { joinTableMeta -> context.realFk != joinTableMeta.realFk() }) {
                            imports += ForeignKeyType::class.java.name
                        }
                        it.forEach { joinColumnMeta ->
                            annotations += associationAnnotationBuilder.build(joinColumnMeta)
                        }
                    }

                    if (dissociateAnnotation != null) {
                        imports += OnDissociate::class.java.name
                        imports += DissociateAction::class.java.name
                        annotations += dissociateAnnotation
                    }
                }
            }

            if (context.columnAnnotation && column != null && associationType == null) {
                imports += Column::class.java.name
                columnAnnotation()?.let { annotations += it }
            }

            if (listType) {
                imports += List::class.java.name
            }
        }

        validateAnnotations(property).let {
            imports += it.imports
            annotations += it.annotations
        }

        property.otherAnnotation?.let {
            imports += it.imports
            annotations += it.annotations
        }

        property.body?.let {
            imports += it.imports
        }

        property.fullType()
            .takeIf { type -> type.split(".").filter { it.isNotBlank() }.size >= 2 }
            ?.let { imports += it }

        return AnnotationWithImports(
            imports = imports,
            annotations = annotations,
        )
    }
}
