package top.potmot.core.business.view.generate.impl.vue3elementPlus.edit

import top.potmot.core.business.meta.AssociationProperty
import top.potmot.core.business.meta.EnumProperty
import top.potmot.core.business.meta.PropertyBusiness
import top.potmot.core.business.meta.SubEntityBusiness
import top.potmot.core.business.type.typeStrToTypeScriptDefault
import top.potmot.core.common.typescript.TsObject
import top.potmot.core.common.typescript.TsProperty
import top.potmot.core.common.typescript.TsRawValue
import top.potmot.core.common.typescript.TsValue
import top.potmot.error.ModelException

interface EditFormDefault {
    fun Iterable<PropertyBusiness>.formDefault(propertyProducer: (entity: SubEntityBusiness) -> Iterable<PropertyBusiness>): TsObject =
        TsObject(
            map { TsProperty(it.name, it.formDefault(propertyProducer)) }
        )

    @Throws(ModelException.DefaultItemNotFound::class)
    fun PropertyBusiness.formDefault(propertyProducer: (entity: SubEntityBusiness) -> Iterable<PropertyBusiness>): TsValue =
        if (listType) {
            TsRawValue("[]")
        } else if (this is EnumProperty && typeNotNull) {
            TsRawValue("\"${enum.defaultItem.name}\"")
        } else {
            if (editNullable)
                TsRawValue("undefined")
            else if (this is AssociationProperty && isLongAssociation && typeNotNull)
                propertyProducer(typeEntityBusiness).formDefault(propertyProducer)
            else
                TsRawValue(typeStrToTypeScriptDefault(type, typeNotNull))
        }
}