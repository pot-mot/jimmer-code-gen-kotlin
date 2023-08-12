package top.potmot.dao
import org.babyfish.jimmer.spring.repository.KRepository
import top.potmot.model.GenColumn

/**
 * 生成列数据交互类
 *
 * @author potmot
 * @since 2023-08-10 15:09:18
 */
interface GenColumnRepository : KRepository<GenColumn, Long>

