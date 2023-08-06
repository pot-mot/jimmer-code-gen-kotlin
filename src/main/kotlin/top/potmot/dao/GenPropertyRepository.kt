package top.potmot.dao
import org.babyfish.jimmer.spring.repository.KRepository
import top.potmot.model.GenProperty

/**
 * 生成字段数据交互类
 *
 * @author potmot
 * @since 2023-08-06 17:22:28
 */
interface GenPropertyRepository : KRepository<GenProperty, Long>

