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
import top.potmot.core.entity.generate.getAssociationAnnotationBuilder
import top.potmot.entity.dto.GenEntityGenerateView
import top.potmot.entity.sub.AnnotationWithImports
import top.potmot.enumeration.TableType
import top.potmot.utils.string.buildScopeString
import kotlin.reflect.KClass

typealias EntityView = GenEntityGenerateView
typealias PropertyView = GenEntityGenerateView.TargetOf_properties

abstract class EntityBuilder : CodeBuilder() {
    abstract fun entityLine(entity: EntityView): String

    abstract fun propertyBlock(property: PropertyView): String

    private fun String.quotationEscape(): String = replace("\"", "\\\"")

    fun build(entity: EntityView): String =
        buildScopeString {
            line(packageLine(entity.packagePath))

            line()
            lines(importItems(entity)) { importLine(it) }
            line()

            block(blockComment(entity))
            lines(annotationLines(entity))

            line(entityLine(entity) + " {")

            scope {
                entity.properties.forEachIndexed { index, property ->
                    block(blockComment(property))
                    lines(annotationLines(property))
                    block(propertyBlock(property))
                    if (index < entity.properties.size - 1) line()
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

    open fun importClasses(property: PropertyView): Set<KClass<*>> {
        val result = mutableSetOf<KClass<*>>()

        val context = getContextOrGlobal()

        property.apply {
            if (context.columnAnnotation && column != null && associationType == null) {
                result += Column::class
            }

            if (idProperty) {
                result += Id::class
            } else if (keyProperty) {
                result += Key::class
            }

            associationType?.let { type ->
                if (idView) {
                    result += IdView::class
                } else {
                    result += type.toAnnotation()

                    joinTableMeta?.let {
                        result += JoinTable::class

                        if (it.columnNamePairs.size > 1) {
                            result += JoinColumn::class
                        }

                        if (context.realFk != it.realFk()) {
                            result += ForeignKeyType::class
                            result += JoinColumn::class
                        }
                    }

                    joinColumnMetas?.takeIf { it.isNotEmpty() }?.let {
                        result += JoinColumn::class
                        if (it.any { joinTableMeta -> context.realFk != joinTableMeta.realFk() }) {
                            result += ForeignKeyType::class
                        }
                    }

                    if (dissociateAnnotation != null) {
                        result += OnDissociate::class
                        result += DissociateAction::class
                    }
                }
            }

            if (listType) {
                result += List::class
            }
        }

        return result
    }

    abstract fun validateAnnotations(property: PropertyView): AnnotationWithImports

    open fun importClasses(entity: EntityView): Set<KClass<*>> {
        val result = mutableSetOf<KClass<*>>()

        val context = getContextOrGlobal()

        entity.apply {
            if (entity.table.type == TableType.SUPER_TABLE) {
                result += MappedSuperclass::class
            } else {
                result += Entity::class
                if (context.tableAnnotation) {
                    result += Table::class
                }
            }
        }

        return result
    }

    open fun importItems(property: PropertyView): Set<String> {
        val imports = classesToLines(importClasses(property)).toMutableSet()

        val context = getContextOrGlobal()

        if (property.generatedId) {
            imports += property.generatedIdAnnotation?.imports ?: context.generatedIdAnnotation.imports
        }

        if (property.logicalDelete) {
            imports += property.logicalDeletedAnnotation?.imports ?: context.logicalDeletedAnnotation.imports
        }

        imports +=validateAnnotations(property).imports

        property.otherAnnotation?.imports?.let {
            imports += it
        }

        property.body?.imports?.let {
            imports += it
        }

        property.fullType()
            .takeIf { type -> type.split(".").filter { it.isNotBlank() }.size >= 2 }
            ?.let { imports += it }

        return imports
    }

    open fun importItems(entity: EntityView): List<String> {
        val imports = mutableListOf<String>()
        imports += classesToLines(importClasses(entity))
        entity.otherAnnotation?.imports?.let { imports += it }
        entity.properties.flatMapTo(imports) { importItems(it) }
        return imports.sorted().distinct().let { importItemsFilter(entity, it) }
    }

    open fun annotationLines(entity: EntityView): List<String> {
        val list = mutableListOf<String>()

        val context = getContextOrGlobal()

        entity.apply {
            if (entity.table.type == TableType.SUPER_TABLE) {
                list += "@MappedSuperclass"
            } else {
                list += "@Entity"

                if (context.tableAnnotation) {
                    list += tableAnnotation()
                }
            }

            if (otherAnnotation != null) {
                list += otherAnnotation.annotations
            }
        }

        return list
    }

    open fun annotationLines(property: PropertyView): List<String> {
        val list = mutableListOf<String>()

        val context = getContextOrGlobal()

        property.apply {
            if (idProperty) {
                list += "@Id"
                if (property.generatedId) {
                    list += property.generatedIdAnnotation?.annotations ?: context.generatedIdAnnotation.annotations
                }
            } else if (keyProperty) {
                list += buildString {
                    if (keyGroups.isEmpty()) {
                        append("@Key")
                    } else {
                        append(keyGroups.joinToString("\n") {
                            if (it.isBlank()) "@Key" else "@Key(group = \"${it}\")"
                        })
                    }
                }
            }

            if (logicalDelete) {
                list += property.logicalDeletedAnnotation?.annotations ?: context.logicalDeletedAnnotation.annotations
            }

            if (associationType != null) {
                if (idView) {
                    idViewTarget?.let { list += "@IdView(\"$it\")" }
                } else {
                    val associationAnnotation = "@" + associationType.toAnnotation().simpleName
                    val annotationBuilder = context.language.getAssociationAnnotationBuilder()

                    if (mappedBy != null) {
                        list += "$associationAnnotation(mappedBy = \"$mappedBy\")"
                    } else {
                        list += associationAnnotation

                        if (context.joinColumnAnnotation) {
                            joinColumnMetas?.forEach { list += annotationBuilder.build(it) }
                        }

                        if (context.joinTableAnnotation) {
                            joinTableMeta?.let { list += annotationBuilder.build(it) }
                        }

                        dissociateAnnotation?.let { list += it }
                    }
                }
            } else if (context.columnAnnotation) {
                columnAnnotation()?.let { list += it }
            }

            list += validateAnnotations(property).annotations

            if (otherAnnotation != null && otherAnnotation.annotations.isNotEmpty()) {
                list += otherAnnotation.annotations
            }
        }

        return list.flatMap { it.lineSequence() }
    }
}
