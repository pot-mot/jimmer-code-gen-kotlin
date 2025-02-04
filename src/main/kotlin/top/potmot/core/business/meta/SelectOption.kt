package top.potmot.core.business.meta

import top.potmot.core.business.view.generate.meta.typescript.ImportType
import top.potmot.core.business.view.generate.meta.vue3.Prop

data class SelectOption(
    val name: String,
    val comment: String,
    val type: String,
    val typePath: String,
    val apiServiceName: String,
) {
    val import = ImportType(typePath, type)

    val prop = Prop(name, "LazyOptions<$type>")
}