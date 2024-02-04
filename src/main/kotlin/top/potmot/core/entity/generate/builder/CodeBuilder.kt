package top.potmot.core.entity.generate.builder

import top.potmot.context.getContextOrGlobal
import top.potmot.model.dto.GenEntityPropertiesView
import top.potmot.utils.string.appendLines
import top.potmot.utils.string.toBlockLines
import kotlin.reflect.KClass

abstract class CodeBuilder {
    abstract fun packageLine(path: String): String

    abstract fun importLine(item: String): String

    fun String.changeCase(): String =
        this.let { if (getContextOrGlobal().lowerCaseName) lowercase() else uppercase() }

    fun createBlockComment(
        comment: String,
        remark: String = "",
        params: Map<String, String> = emptyMap(),
    ): String? =
        buildString {
            comment.takeIf { it.isNotBlank() }?.let {
                appendLines(it.toBlockLines()) { line -> " * $line" }
            }

            remark.takeIf { it.isNotBlank() }?.let {
                appendLines(it.toBlockLines()) { line -> " * $line" }
            }

            if (params.isNotEmpty() && toString().isNotBlank()) {
                appendLine(" * ")
            }
            params.forEach { (key, value) ->
                appendLine(" * @$key $value")
            }
        }.let {
            if (it.isBlank()) null else "/**\n$it */"
        }

    open fun classesToLines(classes: Set<KClass<*>>): Set<String> =
        classes.mapNotNull { it.qualifiedName }.toSet()

    /**
     * 过滤不必要和不合理的 import
     */
    open fun importItemsFilter(entity: GenEntityPropertiesView, importItems: List<String>): List<String> =
        importItems.filter { importItem ->
            // 非默认导入包下的 import
            !judgeImportPathInDefaultPackage(importItem)
                    &&
                    // 非当前包下的 import
                    (importItem.substringBeforeLast(".") != entity.packagePath)
                    &&
                    // 至少有 2 级包
                    (importItem.split(".").filter { it.isNotBlank() }.size >= 2)
        }
}
