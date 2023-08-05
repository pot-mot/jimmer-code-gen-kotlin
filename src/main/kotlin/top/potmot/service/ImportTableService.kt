package top.potmot.service

import org.babyfish.jimmer.sql.ast.mutation.SaveMode
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import top.potmot.dao.GenTableColumnRepository
import top.potmot.dao.GenTableRepository
import top.potmot.dao.GenTypeMappingRepository
import top.potmot.dao.MetadataDao
import top.potmot.model.GenTable
import top.potmot.util.GenUtils.initClassInfo

@Service
class ImportTableService (
    @Autowired val metadataDao: MetadataDao,
    @Autowired val genTableRepository: GenTableRepository,
    @Autowired val genTypeMappingRepository: GenTypeMappingRepository
) {
    fun getTables(tablePattern: String? = null): List<GenTable> {
        val tables = mutableListOf<GenTable>()
        val typeMappings = genTypeMappingRepository.findAll()
        metadataDao.getTables(tablePattern).forEach {
            tables += initClassInfo(it, typeMappings)
        }
        return tables
    }

    @Transactional
    fun importTables(tablePattern: String? = null) {
        importTables(getTables(tablePattern))
    }

    @Transactional
    fun importTables(tables: List<GenTable>) {
        tables.forEach {
            genTableRepository.save(it)
        }
    }

    @Transactional
    fun clearTables() {
        genTableRepository.deleteAll()
    }
}
