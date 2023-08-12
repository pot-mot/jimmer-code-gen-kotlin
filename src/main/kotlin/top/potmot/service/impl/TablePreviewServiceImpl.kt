package top.potmot.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import top.potmot.dao.MetadataDao
import top.potmot.model.GenTable
import top.potmot.service.TablePreviewService

@Service
class TablePreviewServiceImpl (
    @Autowired val metadataDao: MetadataDao
): TablePreviewService {
    override fun previewTables(tablePattern: String?): List<GenTable> {
        return metadataDao.getTables(tablePattern)
    }
}
