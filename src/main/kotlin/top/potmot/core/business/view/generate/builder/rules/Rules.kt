package top.potmot.core.business.view.generate.builder.rules

import top.potmot.core.business.utils.PropertyFormType
import top.potmot.core.business.utils.formType
import top.potmot.entity.dto.GenEntityBusinessView
import top.potmot.enumeration.EnumType

sealed interface Rule {
    fun stringify(): String
}

data class RequiredRule(
    val comment: String,
    val message: String = "${comment}不能为空",
    val trigger: String? = "blur",
) : Rule {
    override fun stringify() =
        "{required: true, message: \"${message}\", trigger: \"${trigger}\"}"
}

data class ArrayRule(
    val comment: String,
    val message: String = "${comment}必须是列表",
    val trigger: String? = "blur",
) : Rule {
    override fun stringify() =
        "{type: \"array\", message: \"${message}\", trigger: \"${trigger}\"}"
}

data class EnumRule(
    val comment: String,
    val enumItems: Iterable<String> = emptyList(),
    val message: String = "${comment}必须是枚举",
    val trigger: String? = "blur",
) : Rule {
    override fun stringify(): String {
        val enumItems = enumItems.joinToString(", ") { "\"$it\"" }
        return "{type: \"enum\", enum: [$enumItems], message: \"${message}\", trigger: \"${trigger}\"}"
    }
}

data class StringLengthRule(
    val comment: String,
    val min: Long?,
    val max: Long?,
    val message: String =
        if (max == null && min == null)
            "${comment}必须是字符串"
        else if (max == null)
            "${comment}长度必须大于${min}"
        else if (min == null)
            "${comment}长度必须小于${max}"
        else
            "${comment}长度必须在${min}-${max}之间",
    val trigger: String? = "blur",
) : Rule {
    override fun stringify() =
        "{type: \"string\", ${if (min != null) "min: $min, " else ""}${if (max != null) "max: $max, " else ""}message: \"${message}\", trigger: \"${trigger}\"}"
}

data class BooleanRule(
    val comment: String,
    val message: String = "${comment}必须是开启或关闭",
    val trigger: String? = "blur",
) : Rule {
    override fun stringify() =
        "{type: \"boolean\", message: \"${message}\", trigger: \"${trigger}\"}"
}

data class DateRule(
    val comment: String,
    val message: String = "${comment}必须是日期",
    val trigger: String? = "blur",
) : Rule {
    override fun stringify() =
        "{type: \"date\", message: \"${message}\", trigger: \"${trigger}\"}"
}

data class IntRule(
    val comment: String,
    val message: String = "${comment}必须是整数",
    val trigger: String? = "blur",
) : Rule {
    override fun stringify() =
        "{type: \"integer\", message: \"${message}\", trigger: \"${trigger}\"}"
}

data class NumberRule(
    val comment: String,
    val message: String = "${comment}必须是数字",
    val trigger: String? = "blur",
) : Rule {
    override fun stringify() =
        "{type: \"number\", message: \"${message}\", trigger: \"${trigger}\"}"
}

data class IntSizeRule(
    val comment: String,
    val min: Double?,
    val max: Double?,
    val message: String =
        if (max == null && min == null)
            "${comment}必须是整数"
        else if (max == null)
            "${comment}必须大于${min}"
        else if (min == null)
            "${comment}必须小于${max}"
        else
            "${comment}必须在${min}-${max}之间",
    val trigger: String? = "blur",
) : Rule {
    override fun stringify() =
        "{type: \"integer\", ${if (min != null) "min: $min, " else ""}${if (max != null) "max: $max, " else ""}message: \"${message}\", trigger: \"${trigger}\"}"
}

data class NumberSizeRule(
    val comment: String,
    val min: Double?,
    val max: Double?,
    val message: String =
        if (max == null && min == null)
            "${comment}必须是数字"
        else if (max == null)
            "${comment}必须大于${min}"
        else if (min == null)
            "${comment}必须小于${max}"
        else
            "${comment}必须在${min}-${max}之间",
    val trigger: String? = "blur",
) : Rule {
    override fun stringify() =
        "{type: \"number\", ${if (min != null) "min: $min, " else ""}${if (max != null) "max: $max, " else ""}message: \"${message}\", trigger: \"${trigger}\"}"
}

data class PatternRule(
    val pattern: String,
    val message: String,
    val trigger: String? = "blur",
) : Rule {
    override fun stringify() =
        "{pattern: /$pattern/, message: \"${message}\", trigger: \"${trigger}\"}"
}

val GenEntityBusinessView.TargetOf_properties.rules: List<Rule>
    get() {
        val rules = mutableListOf<Rule>()

        if (typeNotNull) {
            rules += RequiredRule(comment)
        }

        if (listType) {
            rules += ArrayRule(comment)
        } else {
            if (enum != null) {
                val items = when (enum.enumType) {
                    EnumType.ORDINAL -> enum.items.map { it.mappedValue }
                    EnumType.NAME -> enum.items.map { "\"${it.mappedValue}\"" }
                    null -> enum.items.map { "\"${it.name}\"" }
                }

                rules += EnumRule(comment, items)
            } else {
                val formType = formType

                when (formType) {
                    PropertyFormType.INT -> {
                        rules += IntRule(comment)
                    }

                    PropertyFormType.FLOAT -> {
                        rules += NumberRule(comment)
                    }

                    PropertyFormType.TIME ->
                        rules += PatternRule("[0-9]{2}:[0-9]{2}:[0-9]{2}", "${comment}必须是时间")

                    PropertyFormType.DATE,
                    PropertyFormType.DATETIME,
                    ->
                        rules += DateRule(comment)

                    PropertyFormType.SWITCH ->
                        rules += BooleanRule(comment)

                    else -> Unit
                }

                if (column != null) {
                    if (column.dataSize != 0L) {
                        when (formType) {
                            PropertyFormType.INT -> {
                                val min = numberMin
                                val max = numberMax
                                rules += IntSizeRule(comment, min, max)
                            }

                            PropertyFormType.FLOAT -> {
                                val min = numberMin
                                val max = numberMax
                                rules += NumberSizeRule(comment, min, max)
                            }

                            PropertyFormType.INPUT -> {
                                val max = column.dataSize
                                rules += StringLengthRule(comment, null, max)
                            }

                            else -> Unit
                        }
                    }
                }
            }
        }

        return rules
    }