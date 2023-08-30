package top.potmot.service

import org.babyfish.jimmer.View
import top.potmot.model.GenAssociation
import top.potmot.model.dto.GenAssociationCommonInput
import top.potmot.model.dto.GenAssociationCommonView
import top.potmot.model.query.AssociationQuery
import kotlin.reflect.KClass

/**
 * 关联业务类
 */
interface AssociationService {
    /**
     * 从 GenColumn 中寻找匹配关联并存储
     */
    fun <T : View<GenAssociation>> selectAssociations(tableIds: List<Long>, viewClass: KClass<T>): List<T>

    /**
     * 保存关联
     */
    fun saveAssociations(associations: List<GenAssociationCommonInput>): List<GenAssociationCommonView>

    /**
     * 查询关联
     */
    fun <T : View<GenAssociation>> queryAssociations(query: AssociationQuery, viewClass: KClass<T>): List<T>

    /**
     * 删除关联
     */
    fun deleteAssociations(ids: List<Long>): Int
}
