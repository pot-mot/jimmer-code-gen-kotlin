package top.potmot.service.save

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import top.potmot.dao.GenTableRepository
import top.potmot.model.GenTable

@Service
class TableImportServiceImpl(
    @Autowired val genTableRepository: GenTableRepository
) {
    fun importTable(table: GenTable): GenTable {
        return genTableRepository.save(table)
    }

    fun importTables(tables: List<GenTable>): List<GenTable> {
        return genTableRepository.saveAll(tables)
    }
}
