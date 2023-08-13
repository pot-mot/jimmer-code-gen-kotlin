package top.potmot.service

import top.potmot.model.dto.GenAssociationCommonInput
import top.potmot.model.dto.GenAssociationCommonView
import top.potmot.model.dto.GenAssociationPreviewView
import top.potmot.model.query.AssociationQuery
import java.util.*

/**
 * 关联业务类
 */
interface AssociationService {
    /**
     * 寻找匹配关联
     */
    fun selectAssociations(tableIds: List<Long>): List<GenAssociationPreviewView>

    /**
     * 保存关联
     */
    fun saveAssociations(associations: List<GenAssociationCommonInput>): List<Optional<GenAssociationCommonView>>

    /**
     * 查询关联
     */
    fun queryAssociations(query: AssociationQuery): List<GenAssociationCommonView>

    /**
     * 删除关联
     */
    fun deleteAssociations(ids: List<Long>): Int
}
