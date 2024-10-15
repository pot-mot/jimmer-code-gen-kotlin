package top.potmot.core.service.generate

import top.potmot.core.service.generate.impl.java.JavaServiceCodeGenerator
import top.potmot.core.service.generate.impl.kotlin.KotlinServiceCodeGenerator
import top.potmot.enumeration.GenLanguage

fun GenLanguage.getServiceGenerator(): ServiceCodeGenerator =
    when (this) {
        GenLanguage.KOTLIN -> KotlinServiceCodeGenerator
        GenLanguage.JAVA -> JavaServiceCodeGenerator
    }
