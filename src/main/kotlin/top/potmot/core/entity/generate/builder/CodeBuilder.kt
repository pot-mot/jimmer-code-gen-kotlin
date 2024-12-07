package top.potmot.core.entity.generate.builder

import top.potmot.core.entity.meta.judgeImportPathInDefaultPackage
import top.potmot.entity.dto.GenEntityGenerateView
import top.potmot.utils.string.toBlockLines
import kotlin.reflect.KClass
import top.potmot.utils.string.buildScopeString

abstract class CodeBuilder {
    abstract fun packageLine(path: String): String

    abstract fun importLine(item: String): String

    fun createBlockComment(
        comment: String,
        remark: String = "",
        params: Map<String, String> = emptyMap(),
    ): String? =
        buildScopeString {
            comment.takeIf { it.isNotBlank() }?.let {
                lines(it.toBlockLines()) { line -> " * $line" }
            }

            remark.takeIf { it.isNotBlank() }?.let {
                lines(it.toBlockLines()) { line -> " * $line" }
            }

            if (params.isNotEmpty() && stringBuilder.isNotBlank()) {
                line(" * ")
            }
            params.forEach { (key, value) ->
                line(" * @$key $value")
            }
        }.let {
            if (it.isBlank()) null else "/**\n$it */"
        }

    open fun classesToLines(classes: Set<KClass<*>>): Set<String> =
        classes.mapNotNull { it.qualifiedName }.toSet()

    /**
     * 过滤不必要和不合理的 import
     */
    open fun importItemsFilter(entity: GenEntityGenerateView, importItems: List<String>): List<String> =
        importItems.filter { importItem ->
            // 非默认导入包下的 import
            !judgeImportPathInDefaultPackage(importItem)
                    &&
                    // 非当前包下的 import
                    (importItem.substringBeforeLast(".") != entity.packagePath)
        }
}
