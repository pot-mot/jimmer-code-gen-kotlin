package top.potmot.core.business.service.generate

import top.potmot.core.business.service.generate.impl.java.JavaServiceGenerator
import top.potmot.core.business.service.generate.impl.kotlin.KotlinServiceGenerator
import top.potmot.enumeration.GenLanguage

fun GenLanguage.getServiceGenerator(): ServiceGenerator =
    when (this) {
        GenLanguage.KOTLIN -> KotlinServiceGenerator
        GenLanguage.JAVA -> JavaServiceGenerator
    }
