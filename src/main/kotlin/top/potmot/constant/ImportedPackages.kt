package top.potmot.constant

import top.potmot.utils.string.startsWithAny

val kotlinImportedPackage = listOf(
    "kotlin.annotation",
    "kotlin.collections",
    "kotlin.comparisons",
    "kotlin.io",
    "kotlin.ranges",
    "kotlin.sequences",
    "kotlin.text",
    "kotlin", // 一定要把较短的后置以使 startWith 正确判断
)

val javaImportedPackage = listOf(
    "java.lang"
)

fun judgeImportPathInDefaultPackage(importPath: String): Boolean {
    var prefix: String?

    prefix = importPath.startsWithAny(kotlinImportedPackage)

    if (prefix == null) {
        prefix = importPath.startsWithAny(javaImportedPackage)
    }

    if (prefix != null && importPath.removePrefix(prefix).split(".").filterNot { it.isBlank() }.size == 1) {
        return true
    }

    return false
}
