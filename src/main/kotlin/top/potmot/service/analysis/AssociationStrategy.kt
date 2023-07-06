package top.potmot.service.analysis

import top.potmot.constant.GenConstants
import top.potmot.model.GenTableColumn

/**
 * 两个库表之间关联判断的策略
 *
 * 其中 source （源实体）对应表为主表，target （目标实体）对应表为子表 */
object AssociationStrategy {
    /**
     * 评估两个列之间的关联关系
     */
    val estimate: (GenTableColumn, GenTableColumn) -> Boolean = { sourceColumn, targetColumn ->
        matchAssociation(sourceColumn, targetColumn)
    }

    /**
     * 简单关联，只匹配字段名称
     */
    private val simpleAssociation: (GenTableColumn, GenTableColumn) -> Boolean = { sourceColumn, targetColumn ->
        var result = false
        if (
            targetColumn.pk == "1" &&
            sourceColumn.idView == "1" &&
            "${targetColumn.genTable.tableName}_${targetColumn.columnName}" == sourceColumn.columnName
        ) {
            result = true
        }
        result
    }

    /**
     * 多对多映射关系
     */
    private val mappingAssociation: (GenTableColumn, GenTableColumn) -> Boolean = { sourceColumn, targetColumn ->
        var result = false
        if (sourceColumn.genTable.tableName.lowercase().endsWith("mapping") &&
            targetColumn.columnName.lowercase().endsWith("id") &&
            "${targetColumn.genTable.tableName}_${targetColumn.columnName}" == sourceColumn.columnName
        ) {
            result = true
        }
        result
    }

    /**
     * 后缀匹配大于 2
     */
    private val suffixAssociation: (GenTableColumn, GenTableColumn) -> Boolean = { sourceColumn, targetColumn ->
        var result = false
        if (targetColumn.genTableId != sourceColumn.genTableId &&
            targetColumn.pk == "1" &&
            suffixMatch(targetColumn.columnName, sourceColumn.columnName) > 1
        ) {
            result = true
        }
        result
    }

    private val suffixMatch: (String, String) -> Int = { s1, s2 ->
        val s1List = s1.split(GenConstants.SEPARATOR).reversed()
        val s2List = s2.split(GenConstants.SEPARATOR).reversed()
        var matchLength = 0

        for (i in 0 until (if (s1List.size < s2List.size) s1List.size else s2List.size)) {
            if (s1List[i] == s2List[i]) matchLength++
        }
        matchLength
    }

    /**
     * 符合特定规则
     */
    private val matchAssociation: (GenTableColumn, GenTableColumn) -> Boolean = { sourceColumn, targetColumn ->
        var result = false
        if (sourceColumn.genTable.tableName == "wms_material_info" &&
            sourceColumn.pk == "1" &&
            (targetColumn.columnName == "material_id" || targetColumn.columnName == "product_id")
        ) {
            result = true
        }
        result
    }
}
