package top.potmot.jimmercodegen.dao;
import org.babyfish.jimmer.spring.repository.KRepository
import top.potmot.jimmercodegen.model.SysDictType;

/**
 * 字典类型表数据交互类
 *
 * @author potmot
 * @since 2023-05-06 19:10:25
 */
interface SysDictTypeRepository : KRepository<SysDictType, Long> {
}
