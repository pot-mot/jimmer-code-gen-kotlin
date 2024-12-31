package top.potmot.core.business.view.generate.builder.rules

import top.potmot.core.business.property.PropertyFormType
import top.potmot.core.business.property.formType
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
import top.potmot.core.business.view.generate.meta.rules.numberMax
import top.potmot.core.business.view.generate.meta.rules.numberMin
import top.potmot.entity.dto.GenEntityBusinessView

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
                    PropertyFormType.DATETIME ->
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