package top.potmot.core.business.view.generate.impl.vue3elementPlus.form

import top.potmot.core.business.meta.LazyGenerated
import top.potmot.core.common.typescript.TsCode
import top.potmot.core.common.typescript.TsImport
import top.potmot.core.common.vue3.Element

data class FormItemData(
    val elements: Collection<Element>,
    val imports: Collection<TsImport> = emptyList(),
    val scripts: Collection<TsCode> = emptyList(),
    val lazyItems: List<LazyGenerated> = emptyList(),
    val formItemNotAround: Boolean = false,
)

fun FormItemData(
    vararg element: Element?,
) = FormItemData(element.filterNotNull())