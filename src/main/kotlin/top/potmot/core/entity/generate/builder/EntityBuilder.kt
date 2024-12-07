package top.potmot.core.entity.generate.builder

import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.DissociateAction
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.ForeignKeyType
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.JoinColumn
import org.babyfish.jimmer.sql.JoinTable
import org.babyfish.jimmer.sql.Key
import org.babyfish.jimmer.sql.LogicalDeleted
import org.babyfish.jimmer.sql.MappedSuperclass
import org.babyfish.jimmer.sql.OnDissociate
import org.babyfish.jimmer.sql.Table
import org.babyfish.jimmer.sql.meta.UUIDIdGenerator
import top.potmot.context.getContextOrGlobal
import top.potmot.core.database.generate.identifier.IdentifierType
import top.potmot.core.database.generate.identifier.getIdentifierProcessor
import top.potmot.core.entity.generate.getAssociationAnnotationBuilder
import top.potmot.enumeration.TableType
import top.potmot.entity.dto.GenEntityGenerateView
import top.potmot.entity.dto.GenPropertyView
import top.potmot.utils.string.appendBlock
import top.potmot.utils.string.appendLines
import top.potmot.utils.time.now
import java.util.*
import kotlin.reflect.KClass

abstract class EntityBuilder : CodeBuilder() {
    abstract fun entityLine(entity: GenEntityGenerateView): String

    abstract fun propertyBlock(property: GenPropertyView): String

    private fun String.quotationEscape(): String = replace("\"", "\\\"")

    fun build(entity: GenEntityGenerateView): String =
        buildString {
            appendLine(packageLine(entity.packagePath))

            appendLine()
            appendLines(importItems(entity)) { importLine(it) }
            appendLine()

            appendBlock(blockComment(entity))
            appendLines(annotationLines(entity))

            appendLine(entityLine(entity) + " {")

            entity.properties.forEachIndexed { index, property ->
                appendBlock(blockComment(property)) { "    $it" }
                appendLines(annotationLines(property)) { "    $it" }
                appendBlock(propertyBlock(property)) { "    $it" }
                if (index < entity.properties.size - 1) appendLine()
            }

            appendLine("}")
        }

    open fun GenEntityGenerateView.tableAnnotation(): String =
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

    open fun GenPropertyView.columnAnnotation(): String? =
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


    open fun blockComment(entity: GenEntityGenerateView): String? =
        createBlockComment(
            entity.comment,
            entity.remark,
            params = mapOf(
                Pair("author", entity.author.ifEmpty { getContextOrGlobal().author }),
                Pair("since", now())
            )
        )

    open fun blockComment(property: GenPropertyView): String? =
        createBlockComment(
            property.comment,
            property.remark
        )

    /**
     * 获取属性的简单类型名
     * 判断过程如下：
     *  1. 优先采用枚举类型
     *  2. 使用 typeTable 对应的 Entity
     *  3. 使用映射后的 type
     */
    fun GenPropertyView.shortType(): String {
        enum?.let { return it.name }

        val baseType =
            typeTable?.entity?.name ?: let {
                type.split(".").last()
            }

        return if (listType) {
            "List<$baseType>"
        } else {
            baseType
        }
    }

    private fun GenPropertyView.fullType(): String {
        enum?.let {
            return if (it.packagePath.isNotBlank()) {
                it.packagePath + "." + it.name
            } else {
                it.name
            }
        }

        typeTable?.let { table ->
            table.entity?.let {
                return if (it.packagePath.isNotBlank()) {
                    it.packagePath + "." + it.name
                } else {
                    it.name
                }
            }
        }

        return type
    }

    open fun importClasses(property: GenPropertyView): Set<KClass<*>> {
        val result = mutableSetOf<KClass<*>>()

        val context = getContextOrGlobal()

        property.apply {
            if (context.columnAnnotation && column != null) {
                result += Column::class
            }

            if (idProperty) {
                result += Id::class

                idGenerationAnnotation?.let {
                    result += GeneratedValue::class
                    if (it.contains("GenerationType")) {
                        result += GenerationType::class
                    } else if (it.contains("generatorType") && it.contains("UUIDIdGenerator")) {
                        result += UUIDIdGenerator::class
                        result += UUID::class
                    }
                }
            } else if (keyProperty) {
                result += Key::class
            }

            if (logicalDelete) {
                result += LogicalDeleted::class
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

    open fun importClasses(entity: GenEntityGenerateView): Set<KClass<*>> {
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

    open fun importItems(property: GenPropertyView): Set<String> {
        val imports = classesToLines(importClasses(property)).toMutableSet()

        property.otherAnnotation?.importLines?.let {
            imports += it
        }

        property.body?.importLines?.let {
            imports += it
        }

        property.fullType()
            .takeIf { type -> type.split(".").filter { it.isNotBlank() }.size >= 2 }
            ?.let { imports += it }

        return imports
    }

    open fun importItems(entity: GenEntityGenerateView): List<String> {
        val imports = mutableListOf<String>()
        imports += classesToLines(importClasses(entity))
        entity.otherAnnotation?.importLines?.let { imports += it }
        entity.properties.flatMapTo(imports) { importItems(it) }
        return imports.sorted().distinct().let { importItemsFilter(entity, it) }
    }

    open fun annotationLines(entity: GenEntityGenerateView): List<String> {
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

    open fun annotationLines(property: GenPropertyView): List<String> {
        val list = mutableListOf<String>()

        val context = getContextOrGlobal()

        property.apply {
            if (idProperty) {
                list += "@Id"
                idGenerationAnnotation.takeIf { !idGenerationAnnotation.isNullOrBlank() }?.let {
                    list += it
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
                list += context.logicalDeletedAnnotation
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

            if (otherAnnotation != null && otherAnnotation.annotations.isNotEmpty()) {
                list += otherAnnotation.annotations
            }
        }

        return list.flatMap { it.lineSequence() }
    }
}
