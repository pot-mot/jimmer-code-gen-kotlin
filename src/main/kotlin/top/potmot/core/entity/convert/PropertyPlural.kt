package top.potmot.core.entity.convert

import top.potmot.entity.GenPropertyDraft
import top.potmot.utils.string.toPlural

fun GenPropertyDraft.toPlural() {
    name = name.toPlural()
    listType = true
    typeNotNull = true
    keyProperty = false
    logicalDelete = false
}