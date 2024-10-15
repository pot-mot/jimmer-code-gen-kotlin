package top.potmot.core.business.service.generate

import top.potmot.core.business.service.generate.impl.java.JavaServiceCodeGenerator
import top.potmot.core.business.service.generate.impl.kotlin.KotlinServiceCodeGenerator
import top.potmot.enumeration.GenLanguage

fun GenLanguage.getServiceGenerator(): ServiceCodeGenerator =
    when (this) {
        GenLanguage.KOTLIN -> KotlinServiceCodeGenerator
        GenLanguage.JAVA -> JavaServiceCodeGenerator
    }
