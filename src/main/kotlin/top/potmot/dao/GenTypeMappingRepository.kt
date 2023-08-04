package top.potmot.dao
import org.babyfish.jimmer.spring.repository.KRepository
import top.potmot.model.GenTypeMapping

/**
 * 数据交互类
 *
 * @author potmot
 * @since 2023-08-04 13:31:17
 */
interface GenTypeMappingRepository : KRepository<GenTypeMapping, Long>

