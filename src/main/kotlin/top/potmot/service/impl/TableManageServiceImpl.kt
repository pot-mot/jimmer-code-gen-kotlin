package top.potmot.service.impl

import org.babyfish.jimmer.sql.ast.mutation.DeleteMode
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import top.potmot.dao.GenTableRepository
import top.potmot.model.GenTable
import top.potmot.service.TableManageService
@Service
class TableManageServiceImpl(
    @Autowired val genTableRepository: GenTableRepository
): TableManageService {
    override fun removeTableById(tableId: Long): Boolean {
        return genTableRepository.deleteById(tableId, DeleteMode.PHYSICAL) == 1
    }

    override fun removeTable(table: GenTable): Boolean {
        return genTableRepository.delete(table, DeleteMode.PHYSICAL) == 1
    }

    override fun removeTablesByIds(tableIds: Iterable<Long>): Int {
        return genTableRepository.deleteByIds(tableIds, DeleteMode.PHYSICAL)
    }

    override fun removeTables(tables: Iterable<GenTable>): Int {
        return genTableRepository.deleteAll(tables, DeleteMode.PHYSICAL)
    }
}
