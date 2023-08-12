package top.potmot.dao
import org.babyfish.jimmer.spring.repository.KRepository
import top.potmot.model.GenTableGroup

/**
 * 生成表分组数据交互类
 *
 * @author potmot
 * @since 2023-08-10 16:06:57
 */
interface GenTableGroupRepository : KRepository<GenTableGroup, Long>

