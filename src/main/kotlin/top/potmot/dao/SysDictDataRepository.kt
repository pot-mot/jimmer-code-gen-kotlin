package top.potmot.dao;

import org.babyfish.jimmer.spring.repository.KRepository
import top.potmot.model.SysDictData;

/**
 * 字典数据表数据交互类
 *
 * @author potmot
 * @since 2023-05-06 19:13:45 */
interface SysDictDataRepository : KRepository<SysDictData, Long> {
}
