package top.potmot.core.entity.generate

import top.potmot.core.entity.generate.builder.AssociationAnnotationBuilder
import top.potmot.core.entity.generate.impl.java.JavaAssociationAnnotationBuilder
import top.potmot.core.entity.generate.impl.java.JavaEntityCodeGenerator
import top.potmot.core.entity.generate.impl.kotlin.KotlinAssociationAnnotationBuilder
import top.potmot.core.entity.generate.impl.kotlin.KotlinEntityCodeGenerator
import top.potmot.enumeration.GenLanguage

fun GenLanguage.getEntityGenerator(): EntityCodeGenerator =
    when (this) {
        GenLanguage.KOTLIN -> KotlinEntityCodeGenerator
        GenLanguage.JAVA -> JavaEntityCodeGenerator
    }

fun GenLanguage.getAssociationAnnotationBuilder(): AssociationAnnotationBuilder =
    when (this) {
        GenLanguage.KOTLIN -> KotlinAssociationAnnotationBuilder
        GenLanguage.JAVA -> JavaAssociationAnnotationBuilder
    }
