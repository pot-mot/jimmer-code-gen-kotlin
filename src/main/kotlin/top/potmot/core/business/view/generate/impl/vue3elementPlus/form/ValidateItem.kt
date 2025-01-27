package top.potmot.core.business.view.generate.impl.vue3elementPlus.form

import top.potmot.core.business.meta.AssociationProperty
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
) : ValidateItem {
    private val componentLowerName = componentName.replaceFirstChar { it.lowercase() }

    val ref = ConstVariable(refName, null, "ref<$formExposeType>()")

    override val name: String = "${componentLowerName}Valid"

    override val expression: String = "await ${refName}.value?.validate().catch(() => false) ?? false"

    override val imports = listOf(formExposeImport)
}

fun Iterable<PropertyBusiness>.toFormRefValidateItems(): Collection<FormRefValidateItem> =
    filterIsInstance<AssociationProperty>()
        .filter { it.isLongAssociation }
        .map {
            FormRefValidateItem(
                componentName = it.longComponent.name,
                refName = it.longComponentRefName
            )
        }