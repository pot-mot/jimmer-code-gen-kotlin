package top.potmot.core.business.view.generate.impl.vue3elementPlus

import top.potmot.core.business.view.generate.meta.rules.Rules
import top.potmot.core.business.view.generate.meta.vue3.Component

interface Generator {
    val componentBuilder: Vue3ComponentBuilder

    val rulesBuilder: Vue3ElementPlusRulesBuilder

    val indent: String

    val wrapThreshold: Int

    fun stringify(component: Component): String = componentBuilder.build(component)

    fun stringify(rules: Rules): String = rulesBuilder.build(rules)
}