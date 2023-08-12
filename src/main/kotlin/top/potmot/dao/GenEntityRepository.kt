package top.potmot.dao
import org.babyfish.jimmer.spring.repository.KRepository
import top.potmot.model.GenEntity

/**
 * 生成实体数据交互类
 *
 * @author potmot
 * @since 2023-08-10 15:09:51
 */
interface GenEntityRepository : KRepository<GenEntity, Long>

