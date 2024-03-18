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
import org.babyfish.jimmer.sql.OnDissociate
import org.babyfish.jimmer.sql.Table
import org.babyfish.jimmer.sql.meta.UUIDIdGenerator
import top.potmot.context.getContextOrGlobal
import top.potmot.core.database.generate.identifier.getIdentifierFilter
import top.potmot.model.dto.GenEntityPropertiesView
import top.potmot.model.dto.GenPropertyView
import top.potmot.utils.string.appendBlock
import top.potmot.utils.string.appendLines
import top.potmot.utils.string.changeCase
import top.potmot.utils.time.now
import java.util.UUID
import kotlin.reflect.KClass

abstract class EntityBuilder : CodeBuilder() {
    abstract fun entityLine(entity: GenEntityPropertiesView): String

    abstract fun propertyLine(property: GenPropertyView): String

    fun build(entity: GenEntityPropertiesView): String =
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
                appendLine("    ${propertyLine(property)}")
                if (index < entity.properties.size - 1) appendLine()
            }

            appendLine("}")
        }

    open fun GenEntityPropertiesView.tableAnnotation(): String =
        buildString {
            val context = getContextOrGlobal()

            append("@Table(name = \"")

            context.tablePath.takeIf { it.isNotBlank() }?.let {
                append(it.trim().changeCase())
                append(".")
            }
            append(
                context.dataSourceType.getIdentifierFilter()
                    .getIdentifier(table.name.trim())
                    .replace("\"", "\\\"")
                    .changeCase()
            )

            append("\")")
        }

    open fun GenPropertyView.columnAnnotation(): String? =
        column?.takeUnless { idView || !associationAnnotation.isNullOrBlank() }?.let {
            buildString {
                append("@Column(name = \"")
                append(
                    getContextOrGlobal().dataSourceType.getIdentifierFilter()
                        .getIdentifier(it.name.trim())
                        .replace("\"", "\\\"")
                        .changeCase()
                )
                append("\")")
            }
        }


    open fun blockComment(entity: GenEntityPropertiesView): String? =
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

    open fun importClasses(property: GenPropertyView): Set<KClass<*>> {
        val result = mutableSetOf<KClass<*>>()

        property.apply {
            if (getContextOrGlobal().columnAnnotation && column != null) {
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
                associationAnnotation?.let { associationAnnotation ->
                    result += type.toAnnotation()

                    if (associationAnnotation.contains("@JoinTable")) {
                        result += JoinTable::class
                        if (
                            associationAnnotation.contains("joinColumns = ") ||
                            associationAnnotation.contains("inverseJoinColumns = ")
                        ) {
                            result += JoinColumn::class
                        }
                    } else if (associationAnnotation.contains("@JoinColumn")) {
                        result += JoinColumn::class
                    }
                    if (associationAnnotation.contains("ForeignKeyType")) {
                        result += ForeignKeyType::class
                    }

                    if (dissociateAnnotation != null) {
                        result += OnDissociate::class
                        result += DissociateAction::class
                    }
                }

                if (associationAnnotation == null && idView) {
                    result += IdView::class
                }
            }

            if (listType) {
                result += List::class
            }
        }

        return result
    }

    open fun importClasses(entity: GenEntityPropertiesView): Set<KClass<*>> {
        val result = mutableSetOf<KClass<*>>()

        entity.apply {
            if (getContextOrGlobal().tableAnnotation) {
                result += Table::class
            }
            result += Entity::class
        }

        return result
    }

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

    fun GenPropertyView.fullType(): String {
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

    open fun importItems(property: GenPropertyView): Set<String> =
        classesToLines(importClasses(property)) + property.fullType()

    open fun importItems(entity: GenEntityPropertiesView): List<String> {
        val imports = mutableListOf<String>()
        imports.addAll(classesToLines(importClasses(entity)))
        entity.properties.flatMapTo(imports) { importItems(it) }
        return imports.sorted().distinct().let { importItemsFilter(entity, it) }
    }

    open fun annotationLines(entity: GenEntityPropertiesView): List<String> {
        val list = mutableListOf<String>()

        entity.apply {
            list += "@Entity"

            if (getContextOrGlobal().tableAnnotation) {
                list += tableAnnotation()
            }
        }

        return list
    }

    open fun annotationLines(property: GenPropertyView): List<String> {
        val list = mutableListOf<String>()

        property.apply {
            if (idProperty) {
                list += "@Id"
                idGenerationAnnotation.takeIf { !idGenerationAnnotation.isNullOrBlank() }?.let {
                    list += it
                }
            } else if (keyProperty) {
                list += "@Key"
            }

            if (logicalDelete) {
                list += getContextOrGlobal().logicalDeletedAnnotation
            }

            associationType?.let {
                associationAnnotation?.let { associationAnnotation ->
                    list += associationAnnotation
                    dissociateAnnotation?.let { list += it }
                }

                if (associationAnnotation == null && idView) {
                    idViewAnnotation?.let { list += it }
                }
            }

            if (getContextOrGlobal().columnAnnotation) {
                columnAnnotation()?.let { list += it }
            }

            otherAnnotation.takeIf { !otherAnnotation.isNullOrBlank() }?.let { list += it }
        }

        return list.flatMap { it.split("\n") }
    }
}
