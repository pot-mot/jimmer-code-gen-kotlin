package top.potmot.dao
import org.babyfish.jimmer.spring.repository.KRepository
import top.potmot.model.GenAssociation

/**
 * 生成关联数据交互类
 *
 * @author potmot
 * @since 2023-08-06 17:20:03
 */
interface GenAssociationRepository : KRepository<GenAssociation, Long>

