package top.potmot.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import top.potmot.dao.GenTableRepository
import top.potmot.model.GenTable
import top.potmot.service.TableImportService

@Service
class TableImportServiceImpl(
    @Autowired val genTableRepository: GenTableRepository
): TableImportService {
    override fun importTable(table: GenTable): GenTable {
        return genTableRepository.save(table)
    }

    override fun importTables(tables: List<GenTable>): List<GenTable> {
        return genTableRepository.saveAll(tables)
    }
}
