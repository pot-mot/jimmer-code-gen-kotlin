package top.potmot.core.business.test.generate

import top.potmot.core.business.test.generate.impl.java.JavaTestGenerator
import top.potmot.core.business.test.generate.impl.kotlin.KotlinTestGenerator
import top.potmot.enumeration.GenLanguage

fun GenLanguage.getTestGenerator(): TestGenerator =
    when (this) {
        GenLanguage.KOTLIN -> KotlinTestGenerator
        GenLanguage.JAVA -> JavaTestGenerator
    }
