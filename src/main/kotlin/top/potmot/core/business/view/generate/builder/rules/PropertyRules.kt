package top.potmot.core.business.view.generate.builder.rules

import top.potmot.core.business.meta.EnumProperty
import top.potmot.core.business.meta.PropertyBusiness
import top.potmot.core.business.meta.PropertyFormType
import top.potmot.core.business.view.generate.meta.rules.ArrayRule
import top.potmot.core.business.view.generate.meta.rules.BooleanRule
import top.potmot.core.business.view.generate.meta.rules.DateRule
import top.potmot.core.business.view.generate.meta.rules.EnumRule
import top.potmot.core.business.view.generate.meta.rules.IntRule
import top.potmot.core.business.view.generate.meta.rules.IntSizeRule
import top.potmot.core.business.view.generate.meta.rules.NumberRule
import top.potmot.core.business.view.generate.meta.rules.NumberSizeRule
import top.potmot.core.business.view.generate.meta.rules.PatternRule
import top.potmot.core.business.view.generate.meta.rules.RequiredRule
import top.potmot.core.business.view.generate.meta.rules.Rule
import top.potmot.core.business.view.generate.meta.rules.StringLengthRule

val PropertyBusiness.rules: List<Rule>
    get() {
        val rules = mutableListOf<Rule>()

        if (typeNotNull) {
            rules += RequiredRule(comment)
        }

        if (listType) {
            rules += ArrayRule(comment)
        } else {
            if (this is EnumProperty) {
                rules += EnumRule(comment, enum.items.map { it.name })
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