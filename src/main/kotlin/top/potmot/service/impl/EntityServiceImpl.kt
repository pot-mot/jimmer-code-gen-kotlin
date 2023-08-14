package top.potmot.service.impl

import org.springframework.stereotype.Service
import top.potmot.model.dto.GenEntityConfigInput
import top.potmot.model.dto.GenEntityPropertiesInput
import top.potmot.model.dto.GenEntityPropertiesView
import top.potmot.model.dto.GenTableColumnsInput
import top.potmot.model.query.EntityQuery
import top.potmot.service.EntityService
import java.util.*

@Service
class EntityServiceImpl(

): EntityService {
    override fun mapEntity(table: GenTableColumnsInput): GenEntityPropertiesView {
        TODO("Not yet implemented")
    }

    override fun syncEntity(tableId: Long): List<GenEntityPropertiesView> {
        TODO("Not yet implemented")
    }

    override fun saveEntities(entities: List<GenEntityPropertiesInput>): List<Optional<GenEntityPropertiesView>> {
        TODO("Not yet implemented")
    }

    override fun configEntity(entity: GenEntityConfigInput): Optional<GenEntityPropertiesView> {
        TODO("Not yet implemented")
    }

    override fun queryEntities(query: EntityQuery): List<GenEntityPropertiesView> {
        TODO("Not yet implemented")
    }

    override fun deleteEntities(ids: List<Long>): Int {
        TODO("Not yet implemented")
    }
}
