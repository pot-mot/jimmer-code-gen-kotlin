package top.potmot.dao
import org.babyfish.jimmer.spring.repository.KRepository
import top.potmot.model.GenSchema

/**
 * 生成数据源数据交互类
 *
 * @author potmot
 * @since 2023-08-14 15:29:00
 */
interface GenSchemaRepository : KRepository<GenSchema, Long>

