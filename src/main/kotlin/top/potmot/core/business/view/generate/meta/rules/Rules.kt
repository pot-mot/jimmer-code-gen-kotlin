package top.potmot.core.business.view.generate.meta.rules

import top.potmot.core.business.meta.AssociationProperty
import top.potmot.core.business.meta.EntityBusiness
import top.potmot.core.business.meta.ExistValidItem
import top.potmot.core.business.meta.PropertyBusiness
import top.potmot.core.business.meta.TypeEntityProperty
import top.potmot.core.business.type.typeStrToTypeScriptType
import top.potmot.core.business.view.generate.apiPath
import top.potmot.core.common.typescript.Import
import top.potmot.core.business.view.generate.utilPath
import top.potmot.utils.string.buildScopeString

sealed interface Rule {
    val triggers: Collection<String>

    fun stringify(): String

    val stringifyTriggers
        get() = triggers
            .joinToString(", ") { "\"$it\"" }
            .let { if (triggers.size > 1) "[$it]" else it }
}

private val triggerOnlyBlur = listOf("blur")

data class RequiredRule(
    val comment: String,
    val message: String = "${comment}不能为空",
    override val triggers: Collection<String> = triggerOnlyBlur,
) : Rule {
    override fun stringify() =
        "{required: true, message: \"${message}\", trigger: ${stringifyTriggers}}"
}

data class ArrayRule(
    val comment: String,
    val message: String = "${comment}必须是列表",
    override val triggers: Collection<String> = triggerOnlyBlur,
) : Rule {
    override fun stringify() =
        "{type: \"array\", message: \"${message}\", trigger: ${stringifyTriggers}}"
}

data class EnumRule(
    val comment: String,
    val enumItems: Collection<String> = emptyList(),
    val message: String = "${comment}必须是${enumItems.joinToString("/")}",
    override val triggers: Collection<String> = triggerOnlyBlur,
) : Rule {
    override fun stringify(): String {
        val enumItems = enumItems.joinToString(", ") { "\"$it\"" }
        return "{type: \"enum\", enum: [$enumItems], message: \"${message}\", trigger: ${stringifyTriggers}}"
    }
}

data class StringLengthRule(
    val comment: String,
    val min: Int?,
    val max: Int?,
    val message: String =
        if (max == null && min == null)
            "${comment}必须是字符串"
        else if (max == null)
            "${comment}长度必须大于${min}"
        else if (min == null)
            "${comment}长度必须小于${max}"
        else
            "${comment}长度必须在${min}-${max}之间",
    override val triggers: Collection<String> = triggerOnlyBlur,
) : Rule {
    override fun stringify() =
        "{type: \"string\", ${if (min != null) "min: $min, " else ""}${if (max != null) "max: $max, " else ""}message: \"${message}\", trigger: ${stringifyTriggers}}"
}

data class BooleanRule(
    val comment: String,
    val message: String = "${comment}必须是开启或关闭",
    override val triggers: Collection<String> = triggerOnlyBlur,
) : Rule {
    override fun stringify() =
        "{type: \"boolean\", message: \"${message}\", trigger: ${stringifyTriggers}}"
}

data class DateRule(
    val comment: String,
    val message: String = "${comment}必须是日期",
    override val triggers: Collection<String> = triggerOnlyBlur,
) : Rule {
    override fun stringify() =
        "{type: \"date\", message: \"${message}\", trigger: ${stringifyTriggers}}"
}

data class IntRule(
    val comment: String,
    val message: String = "${comment}必须是整数",
    override val triggers: Collection<String> = triggerOnlyBlur,
) : Rule {
    override fun stringify() =
        "{type: \"integer\", message: \"${message}\", trigger: ${stringifyTriggers}}"
}

data class NumberRule(
    val comment: String,
    val message: String = "${comment}必须是数字",
    override val triggers: Collection<String> = triggerOnlyBlur,
) : Rule {
    override fun stringify() =
        "{type: \"number\", message: \"${message}\", trigger: ${stringifyTriggers}}"
}

data class IntSizeRule(
    val comment: String,
    val min: String?,
    val max: String?,
    val message: String =
        if (max == null && min == null)
            "${comment}必须是整数"
        else if (max == null)
            "${comment}必须大于${min}"
        else if (min == null)
            "${comment}必须小于${max}"
        else
            "${comment}必须在${min}-${max}之间",
    override val triggers: Collection<String> = triggerOnlyBlur,
) : Rule {
    override fun stringify() =
        "{type: \"integer\", ${if (min != null) "min: $min, " else ""}${if (max != null) "max: $max, " else ""}message: \"${message}\", trigger: ${stringifyTriggers}}"
}

data class NumberSizeRule(
    val comment: String,
    val min: String?,
    val max: String?,
    val message: String =
        if (max == null && min == null)
            "${comment}必须是数字"
        else if (max == null)
            "${comment}必须大于${min}"
        else if (min == null)
            "${comment}必须小于${max}"
        else
            "${comment}必须在${min}-${max}之间",
    override val triggers: Collection<String> = triggerOnlyBlur,
) : Rule {
    override fun stringify() =
        "{type: \"number\", ${if (min != null) "min: $min, " else ""}${if (max != null) "max: $max, " else ""}message: \"${message}\", trigger: ${stringifyTriggers}}"
}

data class PatternRule(
    val pattern: String,
    val message: String,
    override val triggers: Collection<String> = triggerOnlyBlur,
) : Rule {
    override fun stringify() =
        "{pattern: /$pattern/, message: \"${message}\", trigger: ${stringifyTriggers}}"
}

private const val asyncValidExist = "asyncValidExist"

val existValidRuleImport = listOf(
    Import(apiPath, "api"),
    Import("$utilPath/$asyncValidExist", asyncValidExist),
)

data class ExistValidRule(
    val item: ExistValidItem,
    val property: PropertyBusiness,
    val entity: EntityBusiness,
    val withId: Boolean,
    override val triggers: Collection<String> = listOf("blur"),
) : Rule {
    fun stringify(formData: String, formDataNotNull: Boolean, indent: String): String {
        val idProperty = entity.idProperty
        val idName = idProperty.name

        val propertyName =
            if (property is AssociationProperty) {
                property.nameWithId
            } else {
                property.name
            }

        val propertyType =
            if (property is TypeEntityProperty) {
                val typeEntityIdProperty = property.typeEntityBusiness.idProperty
                typeStrToTypeScriptType(typeEntityIdProperty.type, property.typeNotNull)
            } else {
                typeStrToTypeScriptType(property.type, property.typeNotNull)
            }

        val formDataValue = "$formData.value${if (formDataNotNull) "" else "?"}"

        return buildScopeString(indent) {
            line("{")
            scope {
                line("asyncValidator: $asyncValidExist(\"${property.comment}\", async (${propertyName}: ${propertyType}) => {")
                scope {
                    line("return await api.${entity.apiServiceName}.${item.functionName}({")
                    scope {
                        line("body: {")
                        scope {
                            if (withId) {
                                line("$idName: $formDataValue.$idName,")
                            }
                            item.properties.forEach {
                                if (it.id != idProperty.id) {
                                    val name = when (it) {
                                        is AssociationProperty -> it.nameWithId
                                        else -> it.name
                                    }

                                    if (name != propertyName) {
                                        line("$name: $formDataValue.$name,")
                                    } else {
                                        line("$name,")
                                    }
                                }
                            }
                        }
                        line("}")
                    }
                    line("})")
                }
                line("}),")
                line("trigger: $stringifyTriggers")
            }
            append("}")
        }
    }

    override fun stringify(): String = stringify("formData", true, "   ")
}

data class Rules(
    val functionName: String,

    val isPlural: Boolean = false,
    val formData: String,
    val formDataType: String,
    val formDataNotNull: Boolean = true,
    val formDataTypePath: String,

    val ruleDataType: String,
    val ruleDataTypePath: String,

    val propertyRules: Map<PropertyBusiness, Iterable<Rule>>,
)