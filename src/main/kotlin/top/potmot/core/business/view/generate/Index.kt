package top.potmot.core.business.view.generate

import top.potmot.core.business.view.generate.impl.vue3elementPuls.Vue3ElementPlusViewCodeGenerator
import top.potmot.enumeration.ViewType

fun ViewType.getViewGenerator(): ViewCodeGenerator =
    when (this) {
        ViewType.VUE3_ELEMENT_PLUS -> Vue3ElementPlusViewCodeGenerator
    }
