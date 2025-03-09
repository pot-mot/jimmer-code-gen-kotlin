package top.potmot.core.entity.convert.base

import top.potmot.core.config.getContextOrGlobal
import top.potmot.core.entity.convert.EntityInput
import top.potmot.entity.dto.GenTableConvertView
import top.potmot.utils.string.clearTableComment
import top.potmot.utils.string.tableNameToEntityName

/**
 * 表到实体转换
 */
fun tableToEntity(
    genTable: GenTableConvertView,
    modelId: Long?,
): EntityInput {
    val context = getContextOrGlobal()

    return EntityInput(
        tableId = genTable.id,
        modelId = modelId,
        author = context.author,
        name = tableNameToEntityName(genTable.name),
        comment = genTable.comment.clearTableComment(),
        remark = genTable.remark,
        packagePath = tableToEntityPackagePath(genTable, context.packagePath),
        superEntityIds = emptyList(),
        properties = emptyList(),
        canAdd = true,
        canEdit = true,
        canQuery = true,
        canDelete = true,
        hasPage = true,
        pageCanAdd = true,
        pageCanEdit = true,
        pageCanViewDetail = false,
        pageCanQuery = true,
        pageCanDelete = true,
        queryByPage = true,
    )
}

fun tableToEntityPackagePath(
    table: GenTableConvertView,
    modelBasePackagePath: String,
): String =
    buildString {
        append(modelBasePackagePath)
        append(".entity")
        if (table.subGroup != null) {
            append(".")
            append(table.subGroup.subPackagePath)
        }
    }
