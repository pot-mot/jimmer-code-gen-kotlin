package top.potmot.service.impl

import org.babyfish.jimmer.View
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import top.potmot.model.GenEntity
import top.potmot.model.dto.GenEntityConfigInput
import top.potmot.model.dto.GenEntityPropertiesInput
import top.potmot.model.dto.GenEntityPropertiesView
import top.potmot.model.query.EntityQuery
import top.potmot.service.EntityService
import java.util.*
import kotlin.reflect.KClass

@Service
class EntityServiceImpl(

): EntityService {
    @Transactional
    override fun mapEntities(tableIds: List<Long>): List<GenEntityPropertiesView> {
        TODO("Not yet implemented")
    }

    @Transactional
    override fun syncEntities(tableIds: List<Long>): List<GenEntityPropertiesView> {
        TODO("Not yet implemented")
    }

    @Transactional
    override fun saveEntities(entities: List<GenEntityPropertiesInput>): List<GenEntityPropertiesView> {
        TODO("Not yet implemented")
    }

    @Transactional
    override fun configEntity(entity: GenEntityConfigInput): GenEntityPropertiesView {
        TODO("Not yet implemented")
    }

    override fun <T : View<GenEntity>> queryEntities(query: EntityQuery, viewCLass: KClass<T>): List<T> {
        TODO("Not yet implemented")
    }


    @Transactional
    override fun deleteEntities(ids: List<Long>): Int {
        TODO("Not yet implemented")
    }
}
