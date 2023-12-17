package top.potmot.core.entity.build

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
import top.potmot.constant.judgeImportPathInDefaultPackage
import top.potmot.utils.template.TemplateBuilder
import top.potmot.enumeration.EnumType
import top.potmot.model.dto.GenEntityPropertiesView
import top.potmot.model.extension.fullType
import top.potmot.model.extension.now
import top.potmot.model.extension.packagePath
import kotlin.reflect.KClass

open class EntityCodeBuilder: TemplateBuilder() {
    open fun GenEntityPropertiesView.tableAnnotation(): String =
        "@Table(name = \"${table.schema?.name?.let { "$it." } ?: ""}${table.name}\")"

    open fun GenEntityPropertiesView.TargetOf_properties.columnAnnotation(): String? =
        column?.let { "@Column(name = \"${it.name}\")" }

    open fun GenEntityPropertiesView.TargetOf_properties.TargetOf_enum_2.TargetOf_items_3.annotation(enumType: EnumType?): String =
        when (enumType) {
            EnumType.NAME -> "@EnumItem(name = \"$value\")\n"
            EnumType.ORDINAL -> "@EnumItem(ordinal = $value)\n"
            null -> ""
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

    fun GenEntityPropertiesView.TargetOf_properties.TargetOf_enum_2.TargetOf_items_3.blockComment(): String? =
        createBlockComment(comment, remark)

    open fun classesToLines(classes: Set<KClass<*>>): Set<String> =
        classes.mapNotNull { it.qualifiedName }.toSet()

    open fun getImportClasses(property: GenEntityPropertiesView.TargetOf_properties): Set<KClass<*>> {
        val result = mutableSetOf<KClass<*>>()

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

    open fun getImportClasses(entity: GenEntityPropertiesView): Set<KClass<*>> {
        val result = mutableSetOf<KClass<*>>()

        entity.apply {
            if (GenConfig.tableAnnotation) {
                result += Table::class
            }
            result += Entity::class
        }

        return result
    }

    open fun getImportLines(property: GenEntityPropertiesView.TargetOf_properties): Set<String> =
        classesToLines(getImportClasses(property)) + property.fullType()

    /**
     * 过滤不必要和不合理的 import
     */
    open fun importLinesFilter(entity: GenEntityPropertiesView, importLines: List<String>): List<String> =
        importLines.filter { importItem ->
            // 非默认导入包下的 import
            !judgeImportPathInDefaultPackage(importItem)
                    &&
                    // 非当前包下的 import
                    (importItem.substringBeforeLast(".") != entity.packagePath())
                    &&
                    // 至少有 2 级包
                    (importItem.split(".").filter { it.isNotBlank() }.size >= 2)
        }

    open fun getImportLines(entity: GenEntityPropertiesView): List<String> {
        val importLines = mutableListOf<String>()
        importLines.addAll(classesToLines(getImportClasses(entity)))
        entity.properties.flatMapTo(importLines) { getImportLines(it) }
        return importLines.sorted().distinct().let {importLinesFilter(entity, it)}
    }

    fun GenEntityPropertiesView.importLines(): List<String> =
        getImportLines(this)

    open fun getImportClasses(enum: GenEntityPropertiesView.TargetOf_properties.TargetOf_enum_2): Set<KClass<*>> {
        val result = mutableSetOf<KClass<*>>()

        enum.apply {
            if (enumType != null) {
                result += org.babyfish.jimmer.sql.EnumType::class
            }
            if (items.isNotEmpty() && enumType != null) {
                result += EnumItem::class
            }
        }

        return result
    }

    open fun getImportLines(enum: GenEntityPropertiesView.TargetOf_properties.TargetOf_enum_2): Set<String> =
        classesToLines(getImportClasses(enum))

    fun GenEntityPropertiesView.TargetOf_properties.TargetOf_enum_2.importLines(): Set<String> =
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
