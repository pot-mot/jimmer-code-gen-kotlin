package top.potmot.core.entity.convert.base

import top.potmot.context.getContextOrGlobal
import top.potmot.entity.dto.GenEntityInput
import top.potmot.entity.dto.GenTableConvertView
import top.potmot.utils.string.clearTableComment
import top.potmot.utils.string.tableNameToEntityName

/**
 * 表到实体转换
 */
fun tableToEntity(
    genTable: GenTableConvertView,
    modelId: Long?,
): GenEntityInput {
    val context = getContextOrGlobal()

    return GenEntityInput(
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
        canQuery = true,
        canEdit = true,
        canDelete = true,
        hasPage = true,
    )
}

// TODO 当未来要添加 subPackagePath 时，调整生成路径
fun tableToEntityPackagePath(
    table: GenTableConvertView,
    modelBasePackagePath: String,
): String =
    "${modelBasePackagePath}.entity"