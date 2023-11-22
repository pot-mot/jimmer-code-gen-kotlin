package top.potmot.core.template.entity

import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.DissociateAction
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.EnumItem
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
import top.potmot.config.GenConfig
import top.potmot.core.template.TemplateBuilder
import top.potmot.enumeration.EnumType
import top.potmot.model.dto.GenEntityPropertiesView
import top.potmot.model.extension.fullType
import kotlin.reflect.KClass

open class EntityStringifyContext : TemplateBuilder() {
    open fun GenEntityPropertiesView.tableAnnotation(): String =
        "@Table(name = \"${table.schema?.name?.let { "$it." } ?: ""}${table.name}\")"

    open fun GenEntityPropertiesView.TargetOf_properties.columnAnnotation(): String? =
        column?.let { "@Column(name = \"${it.name}\")" }

    open fun GenEntityPropertiesView.TargetOf_properties.TargetOf_enum_2.TargetOf_items_3.enumItemAnnotation(enumType: EnumType?): String? =
        enumType?.let {
            when (it) {
                EnumType.NAME -> "@EnumItem(name = \"$value\")\n"
                EnumType.ORDINAL -> "@EnumItem(ordinal = $value)\n"
            }
        }

    private fun createBlockComment(
        comment: String,
        remark: String = "",
        indentation: Int = 0,
        params: Map<String, String> = emptyMap(),
    ): String? =
        TemplateBuilder(
            indentation,
        ).apply {
            block(comment) { " * $it" }
            block(remark) { " * $it" }

            if (params.isNotEmpty() && !isEmptyOrBlank()) {
                line(" * ")
            }

            params.forEach { (key, value) ->
                line(" * @$key $value")
            }
        }.build().let {
            if (it.isBlank()) null else "/**\n$it\n */"
        }

    fun GenEntityPropertiesView.blockComment(): String? =
        createBlockComment(
            comment, remark, params = mapOf(
                Pair("author", author.ifEmpty { GenConfig.author }),
                Pair("since", now())
            )
        )

    fun GenEntityPropertiesView.TargetOf_properties.blockComment(): String? =
        createBlockComment(comment, remark)

    fun GenEntityPropertiesView.TargetOf_properties.TargetOf_enum_2.blockComment(): String? =
        createBlockComment(comment, remark)

    open fun classListToLines(classList: List<KClass<*>>): List<String> =
        classList.mapNotNull { it.qualifiedName }

    /**
     * 过滤不必要和不合理的 import
     */
    open fun importLinesFilter(importLines: List<String>): List<String> =
        importLines.filter { importItem ->
            !(importItem.startsWith("kotlin.") && importItem.split(".").size == 2)
                    &&
                    !(importItem.startsWith("java.lang.") && importItem.split(".").size == 3)
                    &&
                    (importItem.split(".").filter { it.isNotBlank() }.size >= 2)
        }

    open fun getImportClasses(property: GenEntityPropertiesView.TargetOf_properties): List<KClass<*>> {
        val result = mutableListOf<KClass<*>>()

        property.apply {
            if (GenConfig.columnAnnotation && column != null) {
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

            if (associationType != null) {
                if (associationAnnotation != null) {
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
                } else if (idView) {
                    result += IdView::class
                }
            }

            if (listType) {
                result += List::class
            }
        }

        return result
    }

    fun GenEntityPropertiesView.TargetOf_properties.importClassList(): List<KClass<*>> =
        getImportClasses(this)

    open fun getImportClasses(entity: GenEntityPropertiesView): List<KClass<*>> {
        val result = mutableListOf<KClass<*>>()

        entity.apply {
            if (GenConfig.tableAnnotation) {
                result += Table::class
            }
            result += Entity::class
        }

        return result
    }

    fun GenEntityPropertiesView.importClassList(): List<KClass<*>> =
        getImportClasses(this)

    open fun getImportLines(property: GenEntityPropertiesView.TargetOf_properties): List<String> =
        classListToLines(getImportClasses(property)) +
                property.fullType()

    fun GenEntityPropertiesView.TargetOf_properties.importLines(): List<String> =
        getImportLines(this)

    open fun getImportLines(entity: GenEntityPropertiesView): List<String> =
        importLinesFilter(
            (
                    classListToLines(entity.importClassList()) +
                            entity.properties.flatMap {
                                it.importLines()
                            }
                    ).distinct()
        ).sorted()

    fun GenEntityPropertiesView.importLines(): List<String> =
        getImportLines(this)

    open fun getImportClasses(enum: GenEntityPropertiesView.TargetOf_properties.TargetOf_enum_2): List<KClass<*>> {
        val result = mutableListOf<KClass<*>>()

        enum.apply {
            if (enumType != null) {
                result += org.babyfish.jimmer.sql.EnumType::class
            }
            result += EnumItem::class
        }

        return result
    }


    fun GenEntityPropertiesView.TargetOf_properties.TargetOf_enum_2.importClassList(): List<KClass<*>> =
        getImportClasses(this)

    open fun getImportLines(enum: GenEntityPropertiesView.TargetOf_properties.TargetOf_enum_2): List<String> =
        classListToLines(getImportClasses(enum))

    fun GenEntityPropertiesView.TargetOf_properties.TargetOf_enum_2.importLines(): List<String> =
        getImportLines(this)


    open fun getAnnotationLines(entity: GenEntityPropertiesView): List<String> {
        val list = mutableListOf<String>()

        entity.apply {
            list += "@Entity"

            if (GenConfig.tableAnnotation) {
                list += tableAnnotation()
            }
        }

        return list
    }

    fun GenEntityPropertiesView.annotationLines(): List<String> =
        getAnnotationLines(this)

    open fun getAnnotationLines(property: GenEntityPropertiesView.TargetOf_properties): List<String> {
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
                list += GenConfig.logicalDeletedAnnotation
            }

            if (associationType != null) {
                if (associationAnnotation != null) {
                    list += associationAnnotation.split("\n")
                    if (dissociateAnnotation != null) {
                        list += dissociateAnnotation
                    }
                } else if (idView && idViewAnnotation != null) {
                    list += idViewAnnotation
                }
            } else if (GenConfig.columnAnnotation) {
                columnAnnotation()?.let {
                    list += it
                }
            }

            if (!otherAnnotation.isNullOrBlank()) {
                list += otherAnnotation.split("\n")
            }
        }

        return list
    }

    fun GenEntityPropertiesView.TargetOf_properties.annotationLines(): List<String> =
        getAnnotationLines(this)

    open fun getAnnotationLines(enum: GenEntityPropertiesView.TargetOf_properties.TargetOf_enum_2): List<String> {
        val list = mutableListOf<String>()

        enum.enumType?.let {
            list += "@EnumType(EnumType.Strategy.$it)"
        }

        return list
    }

    fun GenEntityPropertiesView.TargetOf_properties.TargetOf_enum_2.annotationLines(): List<String> =
        getAnnotationLines(this)
}
