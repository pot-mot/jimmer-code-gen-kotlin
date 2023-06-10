package top.potmot.dao;

import org.babyfish.jimmer.spring.repository.KRepository
import top.potmot.model.SysDictType;

/**
 * 字典类型表数据交互类
 *
 * @author potmot
 * @since 2023-05-06 19:10:25
 */
interface SysDictTypeRepository : KRepository<SysDictType, Long> {
}
