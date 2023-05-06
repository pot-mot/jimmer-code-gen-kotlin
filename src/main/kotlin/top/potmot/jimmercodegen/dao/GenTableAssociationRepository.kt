package top.potmot.jimmercodegen.dao;
import org.babyfish.jimmer.spring.repository.KRepository
import top.potmot.jimmercodegen.model.GenTableAssociation;

/**
 * 代码生成业务表关联数据交互类
 *
 * @author potmot
 * @since 2023-05-06 18:45:32
 */
interface GenTableAssociationRepository : KRepository<GenTableAssociation, Long> {
}
