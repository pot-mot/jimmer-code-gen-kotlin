package top.potmot.core.business.view.generate.impl.vue3elementPlus.formItem

import top.potmot.core.business.view.generate.meta.typescript.CodeItem
import top.potmot.core.business.view.generate.meta.typescript.ImportItem
import top.potmot.core.business.view.generate.meta.vue3.Element

data class FormItemData(
    val elements: Collection<Element>,
    val imports: Collection<ImportItem> = emptyList(),
    val scripts: Collection<CodeItem> = emptyList(),
)

fun FormItemData(
    vararg element: Element?
) = FormItemData(element.filterNotNull())