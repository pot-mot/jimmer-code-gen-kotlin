package top.potmot.core.business.view.generate

import top.potmot.core.business.view.generate.impl.vue3elementPuls.Vue3ElementPlusViewGenerator
import top.potmot.enumeration.ViewType

fun ViewType.getViewGenerator(): ViewGenerator =
    when (this) {
        ViewType.VUE3_ELEMENT_PLUS -> Vue3ElementPlusViewGenerator
    }
