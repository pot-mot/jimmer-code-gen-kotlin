package top.potmot.core.business.view.generate.impl.vue3elementPlus.selectOptions

import top.potmot.core.business.meta.ForceIdViewProperty
import top.potmot.core.business.view.generate.meta.typescript.ConstVariable
import top.potmot.core.business.view.generate.meta.typescript.ImportType
import top.potmot.core.business.view.generate.meta.vue3.Prop
import top.potmot.core.business.view.generate.staticPath

data class SelectOption(
    val name: String,
    val type: String,
    val apiServiceName: String,
    val typePath: String = staticPath,
) {
    val upperName: String = name.replaceFirstChar { it.uppercaseChar() }

    fun toImport() =
        ImportType(typePath, type)

    fun toProp() =
        Prop(name, "Array<$type>")

    fun toVariable() =
        ConstVariable(name, null, "ref<Array<$type>>()")
}

val ForceIdViewProperty.selectOption
    get() = SelectOption(
        name + "Options",
        typeEntityBusiness.dto.optionView,
        typeEntityBusiness.apiServiceName
    )

val Iterable<ForceIdViewProperty>.selectOptions
    get() = map { it.selectOption }