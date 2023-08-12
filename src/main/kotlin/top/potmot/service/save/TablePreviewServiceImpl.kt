package top.potmot.service.save

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import top.potmot.dao.MetadataDao
import top.potmot.model.GenTable

@Service
class TablePreviewServiceImpl (
    @Autowired val metadataDao: MetadataDao
) {
    fun previewTables(tablePattern: String?): List<GenTable> {
        return metadataDao.getTables(tablePattern)
    }
}
