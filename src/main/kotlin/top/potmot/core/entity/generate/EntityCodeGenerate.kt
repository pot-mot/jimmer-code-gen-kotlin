package top.potmot.core.entity.generate

import top.potmot.context.getContextGenConfig
import top.potmot.core.entity.generate.java.JavaEntityCodeGenerator
import top.potmot.core.entity.generate.kotlin.KotlinEntityCodeGenerator
import top.potmot.enumeration.GenLanguage

fun GenLanguage?.getEntityGenerator(): EntityCodeGenerator =
    when (this ?: getContextGenConfig().language) {
        GenLanguage.KOTLIN -> KotlinEntityCodeGenerator
        GenLanguage.JAVA -> JavaEntityCodeGenerator
    }
