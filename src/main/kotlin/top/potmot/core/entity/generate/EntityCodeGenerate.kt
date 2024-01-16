package top.potmot.core.entity.generate

import top.potmot.context.getContextGenConfig
import top.potmot.core.entity.generate.java.JavaEntityCodeGenerator
import top.potmot.core.entity.generate.kotlin.KotlinEntityCodeGenerator
import top.potmot.enumeration.GenLanguage

private val KOTLIN_GENERATOR = KotlinEntityCodeGenerator()

private val JAVA_GENERATOR = JavaEntityCodeGenerator()

fun GenLanguage?.getEntityGenerator(): EntityCodeGenerator =
    when (this ?: getContextGenConfig().language) {
        GenLanguage.KOTLIN -> KOTLIN_GENERATOR
        GenLanguage.JAVA -> JAVA_GENERATOR
    }
