package top.potmot.dao
import org.babyfish.jimmer.spring.repository.KRepository
import top.potmot.model.GenDictData

/**
 * 字典数据数据交互类
 *
 * @author potmot
 * @since 2023-08-06 17:21:29
 */
interface GenDictDataRepository : KRepository<GenDictData, Long>

