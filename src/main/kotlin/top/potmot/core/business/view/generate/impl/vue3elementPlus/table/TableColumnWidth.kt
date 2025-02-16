package top.potmot.core.business.view.generate.impl.vue3elementPlus.table

import top.potmot.core.business.meta.AssociationProperty
import top.potmot.core.business.meta.CommonProperty
import top.potmot.core.business.meta.EnumProperty
import top.potmot.core.business.meta.ForceIdViewProperty
import top.potmot.core.business.meta.PropertyBusiness
import top.potmot.core.business.meta.PropertyFormType

private const val BOOLEAN_MIN_WIDTH = 43
private const val DATETIME_MIN_WIDTH = 193
private const val NUMBER_MIN_WIDTH = 161
private const val ENUM_MIN_WIDTH = 161
private const val ASSOCIATION_ID_MIN_WIDTH = 161
private const val COMMON_MIN_WIDTH = 129

val PropertyBusiness.tableMinWidth: Int?
    get() = when (this) {
        is CommonProperty -> when (formType) {
            PropertyFormType.BOOLEAN -> BOOLEAN_MIN_WIDTH
            PropertyFormType.DATETIME -> DATETIME_MIN_WIDTH
            PropertyFormType.INT -> NUMBER_MIN_WIDTH
            PropertyFormType.FLOAT -> NUMBER_MIN_WIDTH
            else -> COMMON_MIN_WIDTH
        }

        is EnumProperty -> ENUM_MIN_WIDTH
        is ForceIdViewProperty -> ASSOCIATION_ID_MIN_WIDTH
        is AssociationProperty -> if (isLongAssociation) null else COMMON_MIN_WIDTH
    }
