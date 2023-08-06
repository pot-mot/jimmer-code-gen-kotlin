package top.potmot.dao
import org.babyfish.jimmer.spring.repository.KRepository
import top.potmot.model.GenTypeMapping

/**
 * 列到字段类型映射数据交互类
 *
 * @author potmot
 * @since 2023-08-06 17:23:37
 */
interface GenTypeMappingRepository : KRepository<GenTypeMapping, Long>

