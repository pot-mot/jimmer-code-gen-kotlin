package top.potmot.core.business.view.generate.impl.vue3elementPlus.form

import top.potmot.core.business.meta.AssociationProperty
import top.potmot.core.business.meta.EnumBusiness
import top.potmot.core.business.meta.EnumProperty
import top.potmot.core.business.meta.PropertyBusiness
import top.potmot.core.business.meta.SubEntityBusiness
import top.potmot.core.business.type.typeStrToTypeScriptType
import top.potmot.core.business.view.generate.meta.typescript.TsComplexType
import top.potmot.core.business.view.generate.meta.typescript.TsRawType
import top.potmot.core.business.view.generate.meta.typescript.TsType
import top.potmot.core.business.view.generate.meta.typescript.TsTypeProperty
import top.potmot.core.business.view.generate.meta.typescript.TsWithGenericType

interface FormType {
    fun Iterable<PropertyBusiness>.formType(
        canUndefined: Boolean = false,
        propertyProducer: (entity: SubEntityBusiness) -> Iterable<PropertyBusiness>,
    ): TsComplexType =
        TsComplexType(
            map { TsTypeProperty(it.name, it.formType(propertyProducer)) },
            canUndefined,
        )

    fun PropertyBusiness.formType(propertyProducer: (entity: SubEntityBusiness) -> Iterable<PropertyBusiness>): TsType {
        val formItemTypeNotNull = if (editNullable && typeNotNull) false else typeNotNull
        val canUndefined = !formItemTypeNotNull

        if (this is EnumProperty) {
            return TsRawType(typeStrToTypeScriptType(enum.name, formItemTypeNotNull), canUndefined = canUndefined)
        }

        val baseType = if (this is AssociationProperty && isLongAssociation)
            propertyProducer(typeEntityBusiness).formType(canUndefined, propertyProducer)
        else
            TsRawType(typeStrToTypeScriptType(type, formItemTypeNotNull), canUndefined = canUndefined)

        return if (listType) {
            TsWithGenericType("Array", baseType, canUndefined = canUndefined)
        } else {
            baseType
        }
    }

    fun Iterable<PropertyBusiness>.editEnums(propertyProducer: (entity: SubEntityBusiness) -> Iterable<PropertyBusiness>): List<EnumBusiness> =
        filterIsInstance<EnumProperty>().map { it.enum } +
                filterIsInstance<AssociationProperty>()
                    .filter { it.isLongAssociation }
                    .flatMap { propertyProducer(it.typeEntityBusiness).editEnums(propertyProducer) }
}