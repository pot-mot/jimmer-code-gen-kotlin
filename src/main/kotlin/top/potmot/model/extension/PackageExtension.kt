package top.potmot.model.extension

import top.potmot.model.GenPackage

fun GenPackage.toFilePath(): String =
    toPathList().joinToString("/")

fun GenPackage.toPackagePath(): String =
    toPathList().joinToString(".")

fun GenPackage.toPathList(): List<String> {
    val tempPath = mutableListOf<String>()

    tempPath += name
    var tempParentPackage = parent
    while (tempParentPackage != null) {
        tempPath += tempParentPackage.name
        tempParentPackage = tempParentPackage.parent
    }

    return tempPath.reversed()
}

