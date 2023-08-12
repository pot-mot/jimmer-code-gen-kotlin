package top.potmot.dao
import org.babyfish.jimmer.spring.repository.KRepository
import top.potmot.model.GenTypeMapping

/**
 * 列到字段类型映射数据交互类
 *
 * @author potmot
 * @since 2023-08-10 15:12:35
 */
interface GenTypeMappingRepository : KRepository<GenTypeMapping, Long>

