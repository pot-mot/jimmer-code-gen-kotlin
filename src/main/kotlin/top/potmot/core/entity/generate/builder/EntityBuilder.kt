package top.potmot.core.entity.generate.builder

import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.DissociateAction
import org.babyfish.jimmer.sql.Entity
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
import top.potmot.config.GlobalGenConfig
import top.potmot.context.getContextGenConfig
import top.potmot.core.database.generate.getIdentifierFilter
import top.potmot.model.dto.GenEntityPropertiesView
import top.potmot.model.extension.fullType
import top.potmot.model.extension.now
import top.potmot.utils.string.appendBlock
import top.potmot.utils.string.appendLines
import kotlin.reflect.KClass

abstract class EntityBuilder: CodeBuilder() {
    abstract fun entityLine(entity: GenEntityPropertiesView): String

    abstract fun propertyLine(property:  GenEntityPropertiesView.TargetOf_properties): String

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
                if (index < entity.properties.size - 1) appendLine("    ")
            }

            appendLine("}")
        }

    fun GenEntityPropertiesView.tableAnnotation(): String =
        "@Table(name = \"${table.schema?.name?.let { "${it.changeCase()}." } ?: ""}${getContextGenConfig().dataSourceType.getIdentifierFilter().getIdentifier(table.name).changeCase()}\")"

    fun GenEntityPropertiesView.TargetOf_properties.columnAnnotation(): String? =
        column?.takeUnless { idView || !associationAnnotation.isNullOrBlank() }?.let { "@Column(name = \"${getContextGenConfig().dataSourceType.getIdentifierFilter().getIdentifier(it.name).changeCase()}\")" }


    open fun blockComment(entity: GenEntityPropertiesView): String? =
        createBlockComment(
            entity.comment,
            entity.remark,
            params = mapOf(
                Pair("author", entity.author.ifEmpty { GlobalGenConfig.author }),
                Pair("since", now())
            )
        )

    open fun blockComment(property: GenEntityPropertiesView.TargetOf_properties): String? =
        createBlockComment(
            property.comment,
            property.remark
        )

    open fun importClasses(property: GenEntityPropertiesView.TargetOf_properties): Set<KClass<*>> {
        val result = mutableSetOf<KClass<*>>()

        property.apply {
            if (GlobalGenConfig.columnAnnotation && column != null) {
                result += Column::class
            }

            if (idProperty) {
                result += Id::class
                if (idGenerationType != null) {
                    result += GeneratedValue::class
                    result += GenerationType::class
                }
            } else if (keyProperty) {
                result += Key::class
            }

            if (logicalDelete) {
                result += LogicalDeleted::class
            }

            associationType?.let {associationType ->
                associationAnnotation?.let {associationAnnotation ->
                    result += associationType.toAnnotation()

                    if (associationAnnotation.contains("@JoinTable")) {
                        result += JoinTable::class
                    }

                    if (associationAnnotation.contains("@JoinColumn")) {
                        result += JoinColumn::class
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
            if (GlobalGenConfig.tableAnnotation) {
                result += Table::class
            }
            result += Entity::class
        }

        return result
    }

    open fun importItems(property: GenEntityPropertiesView.TargetOf_properties): Set<String> =
        classesToLines(importClasses(property)) + property.fullType()

    open fun importItems(entity: GenEntityPropertiesView): List<String> {
        val imports = mutableListOf<String>()
        imports.addAll(classesToLines(importClasses(entity)))
        entity.properties.flatMapTo(imports) { importItems(it) }
        return imports.sorted().distinct().let {importItemsFilter(entity, it)}
    }

    open fun annotationLines(entity: GenEntityPropertiesView): List<String> {
        val list = mutableListOf<String>()

        entity.apply {
            list += "@Entity"

            if (GlobalGenConfig.tableAnnotation) {
                list += tableAnnotation()
            }
        }

        return list
    }

    open fun annotationLines(property: GenEntityPropertiesView.TargetOf_properties): List<String> {
        val list = mutableListOf<String>()

        property.apply {
            if (idProperty) {
                list += "@Id"
                if (idGenerationType != null) {
                    list += "@GeneratedValue(strategy = GenerationType.${idGenerationType})"
                }
            } else if (keyProperty) {
                list += "@Key"
            }

            if (logicalDelete) {
                list += GlobalGenConfig.logicalDeletedAnnotation
            }

            associationType?.let {
                associationAnnotation?.let {associationAnnotation ->
                    list += associationAnnotation.split("\n")

                    dissociateAnnotation?.let { list += it }
                }

                if (associationAnnotation == null && idView) {
                    idViewAnnotation?.let { list += it }
                }
            }

            if (GlobalGenConfig.columnAnnotation) {
                columnAnnotation()?.let { list += it }
            }

            otherAnnotation.takeIf { !otherAnnotation.isNullOrBlank() }?.let { list += it }
        }

        return list
    }
}
