package top.potmot.jimmercodegen.dao;
import org.babyfish.jimmer.spring.repository.KRepository
import top.potmot.jimmercodegen.model.GenTableColumn;

/**
 * 代码生成业务表字段数据交互类
 *
 * @author potmot
 * @since 2023-05-06 23:20:01
 */
interface GenTableColumnRepository : KRepository<GenTableColumn, Long> {
}
