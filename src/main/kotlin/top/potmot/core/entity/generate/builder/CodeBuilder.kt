package top.potmot.core.entity.generate.builder

import top.potmot.core.common.judgeImportPathInDefaultPackage
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
                block(it) { line -> " * $line" }
            }

            remark.takeIf { it.isNotBlank() }?.let {
                block(it) { line -> " * $line" }
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

    /**
     * 过滤不必要和不合理的 import
     */
    open fun filterImports(currentPackagePath: String, imports: Iterable<String>): List<String> =
        imports.filter { importItem ->
            // 非默认导入包下的 import
            !judgeImportPathInDefaultPackage(importItem)
                    &&
                    // 非当前包下的 import
                    (importItem.substringBeforeLast(".") != currentPackagePath)
        }
            .distinct()
            .sorted()
}
