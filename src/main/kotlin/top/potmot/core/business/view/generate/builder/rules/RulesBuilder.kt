package top.potmot.core.business.view.generate.builder.rules

import top.potmot.core.business.view.generate.meta.rules.Rules

interface RulesBuilder {
    fun build(rules: Rules): String
}