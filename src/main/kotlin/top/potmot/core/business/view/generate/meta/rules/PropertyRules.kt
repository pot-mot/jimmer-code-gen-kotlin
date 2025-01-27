package top.potmot.core.business.view.generate.meta.rules

import top.potmot.core.business.meta.CommonProperty
import top.potmot.core.business.meta.EnumProperty
import top.potmot.core.business.meta.PropertyBusiness
import top.potmot.core.business.meta.PropertyFormType

val PropertyBusiness.rules: List<Rule>
    get() {
        val rules = mutableListOf<Rule>()

        if (listType) {
            rules += ArrayRule(comment)
        } else {
            if (typeNotNull) {
                rules += RequiredRule(comment)
            }

             if (this is EnumProperty) {
                rules += EnumRule(comment, enum.items.map { it.name })
            } else if (this is CommonProperty) {
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

                    PropertyFormType.BOOLEAN ->
                        rules += BooleanRule(comment)

                    else -> Unit
                }

                if (dataSize != null && dataSize != 0) {
                    when (formType) {
                        PropertyFormType.INT -> {
                            rules += IntSizeRule(comment, numberMin, numberMax)
                        }

                        PropertyFormType.FLOAT -> {
                            rules += NumberSizeRule(comment, numberMin, numberMax)
                        }

                        PropertyFormType.INPUT -> {
                            val max = dataSize
                            rules += StringLengthRule(comment, null, max)
                        }

                        else -> Unit
                    }
                }
            }
        }

        return rules
    }