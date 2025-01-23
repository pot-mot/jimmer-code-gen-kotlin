package top.potmot.core.business.view.generate.impl.vue3elementPlus.form

import top.potmot.core.business.view.generate.meta.typescript.TsCode
import top.potmot.core.business.view.generate.meta.typescript.TsImport
import top.potmot.core.business.view.generate.meta.vue3.Element

data class FormItemData(
    val elements: Collection<Element>,
    val imports: Collection<TsImport> = emptyList(),
    val scripts: Collection<TsCode> = emptyList(),
)

fun FormItemData(
    vararg element: Element?
) = FormItemData(element.filterNotNull())