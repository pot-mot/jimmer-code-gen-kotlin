package top.potmot.core.entity.convert.base

import top.potmot.context.getContextOrGlobal
import top.potmot.entity.dto.share.ColumnTypeMeta
import top.potmot.entity.dto.GenEntityInput
import top.potmot.entity.dto.GenTableConvertView
import top.potmot.utils.string.clearTableComment
import top.potmot.utils.string.tableNameToEntityName

typealias TypeMapping = (column: ColumnTypeMeta) -> String

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
        packagePath = "${context.packagePath}.entity",
        superEntityIds = emptyList(),
        properties = emptyList(),
        canAdd = true,
        canQuery = true,
        canEdit = true,
        canDelete = true,
        hasPage = true,
    )
}
