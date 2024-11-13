package top.potmot.core.business.view.generate.meta.rules

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
    val message: String = "${comment}必须是${enumItems.joinToString("/")}",
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