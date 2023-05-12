package top.potmot.external.dao;

import org.babyfish.jimmer.spring.repository.KRepository
import org.babyfish.jimmer.sql.fetcher.Fetcher
import top.potmot.external.model.GenTableAssociation;

/**
 * 代码生成业务表关联数据交互类
 *
 * @author potmot
 * @since 2023-05-07 09:36:26
 */
interface GenTableAssociationRepository : KRepository<GenTableAssociation, Long> {
    fun findByTableAssociationNameLikeIgnoreCase(
        tableAssociationName: String,
        fetcher: Fetcher<GenTableAssociation>,
    ): List<GenTableAssociation>
}
