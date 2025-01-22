package top.potmot.core.business.view.generate.builder.component

import top.potmot.core.business.view.generate.meta.vue3.Component

interface ComponentBuilder {
    fun build(component: Component): String
}