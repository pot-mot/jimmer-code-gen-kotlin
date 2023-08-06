package top.potmot.dao
import org.babyfish.jimmer.spring.repository.KRepository
import top.potmot.model.GenDict

/**
 * 字典类型数据交互类
 *
 * @author potmot
 * @since 2023-08-06 17:21:18
 */
interface GenDictRepository : KRepository<GenDict, Long>

