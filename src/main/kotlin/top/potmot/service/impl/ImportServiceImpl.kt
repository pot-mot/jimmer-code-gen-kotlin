package top.potmot.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import top.potmot.dao.GenTableRepository
import top.potmot.dao.MetadataDao
import top.potmot.model.GenEntity
import top.potmot.model.GenTable
import top.potmot.util.convert.tableToEntity

@Service
class ImportServiceImpl (
    @Autowired val metadataDao: MetadataDao,
    @Autowired val genTableRepository: GenTableRepository
) {
    fun getTables(tablePattern: String? = null): List<GenTable> {
        return metadataDao.getTables(tablePattern)
    }

    @Transactional
    fun importTables(tablePattern: String? = null): Int {
        return importTables(getTables(tablePattern))
    }

    @Transactional
    fun importTables(tables: List<GenTable>): Int {
        var saveCount = 0
        tables.forEach {
            genTableRepository.save(it)
            saveCount ++
        }
        return saveCount
    }

    @Transactional
    fun clearTables() {
        genTableRepository.deleteAll()
    }

    fun getEntities(tablePattern: String? = null): List<GenEntity> {
        return metadataDao.getTables(tablePattern).map {
            tableToEntity(it)
        }
    }
}
