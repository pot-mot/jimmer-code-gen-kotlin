package top.potmot.dao
import org.babyfish.jimmer.spring.repository.KRepository
import top.potmot.model.GenTable

/**
 * 生成表数据交互类
 *
 * @author potmot
 * @since 2023-08-06 17:23:08
 */
interface GenTableRepository : KRepository<GenTable, Long>

