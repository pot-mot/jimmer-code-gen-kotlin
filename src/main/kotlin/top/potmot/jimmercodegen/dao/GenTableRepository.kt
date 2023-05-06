package top.potmot.jimmercodegen.dao;
import org.babyfish.jimmer.spring.repository.KRepository
import top.potmot.jimmercodegen.model.GenTable;

/**
 * 代码生成业务表数据交互类
 *
 * @author potmot
 * @since 2023-05-06 18:45:51
 */
interface GenTableRepository : KRepository<GenTable, Long> {
    fun findByTableName(
        name: String
    ): List<GenTable>
}
