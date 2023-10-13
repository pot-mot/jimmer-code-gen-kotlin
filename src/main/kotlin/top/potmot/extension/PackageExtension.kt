package top.potmot.extension

import top.potmot.model.GenPackage

fun GenPackage.toPath(): String {
    val tempPath = mutableListOf<String>()

    tempPath += name
    var tempParentPackage = parent
    while (tempParentPackage != null) {
        tempPath += tempParentPackage.name
        tempParentPackage = tempParentPackage.parent
    }

    return tempPath.joinToString(".")
}
