package top.potmot.core.business.meta

import top.potmot.core.common.typescript.ImportType
import top.potmot.core.common.vue3.Prop

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