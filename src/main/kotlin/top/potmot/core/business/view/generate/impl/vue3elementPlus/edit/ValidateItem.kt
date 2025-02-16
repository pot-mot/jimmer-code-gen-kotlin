package top.potmot.core.business.view.generate.impl.vue3elementPlus.edit

import top.potmot.core.business.meta.AssociationProperty
import top.potmot.core.business.meta.LazySubEdit
import top.potmot.core.business.meta.PropertyBusiness
import top.potmot.core.business.view.generate.meta.typescript.ConstVariable
import top.potmot.core.business.view.generate.meta.typescript.TsImport

interface ValidateItem {
    val imports: List<TsImport>

    val name: String

    val expression: String
}

data class CommonValidateItem(
    override val name: String,
    override val expression: String,
    override val imports: List<TsImport> = emptyList(),
) : ValidateItem

data class FormRefValidateItem(
    val componentName: String,
    val refName: String,
    val multiple: Boolean,
) : ValidateItem {
    private val componentLowerName = componentName.replaceFirstChar { it.lowercase() }

    val ref = ConstVariable(refName, null, "ref<${if (multiple) "Array<$formExposeType>" else formExposeType}>()")

    override val name: String = "${componentLowerName}Valid"

    override val expression: String =
        if (multiple)
            "await Promise.all(\n" +
                    "    ${refName}.value?.map(\n" +
                    "        it => it.validate().catch(() => false)\n" +
                    "    )\n" +
                    ").catch(() => false)"
        else
            "await ${refName}.value?.validate().catch(() => false) ?? false"

    override val imports = listOf(formExposeImport)
}

fun Iterable<PropertyBusiness>.toRefValidateItems(): Collection<FormRefValidateItem> =
    filterIsInstance<AssociationProperty>()
        .filter { it.isLongAssociation }
        .map {
            val subEdit = LazySubEdit(it.typeEntityBusiness, it.listType, !it.typeNotNull)
            FormRefValidateItem(
                componentName = subEdit.component.name,
                refName = subEdit.componentRef,
                multiple = it.listType
            )
        }