package top.potmot.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import top.potmot.dao.GenTypeMappingRepository
import top.potmot.dao.MetadataDao
import top.potmot.model.GenTable
import top.potmot.util.GenUtils.initClassInfo

@Service
class ImportTableService (
    @Autowired val metadataDao: MetadataDao,
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
}
