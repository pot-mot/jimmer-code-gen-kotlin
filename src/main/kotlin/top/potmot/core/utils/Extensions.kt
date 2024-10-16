package top.potmot.core.utils

import top.potmot.entity.dto.share.GenerateItem

private fun formatFilePath(packagePath: String): String =
    packagePath.replace(".", "/") + "/"

val GenerateItem.filePath
    get() = formatFilePath(packagePath)
