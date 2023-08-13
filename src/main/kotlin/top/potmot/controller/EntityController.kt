package top.potmot.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import top.potmot.model.dto.GenEntityConfigInput
import top.potmot.model.dto.GenEntityPropertiesInput
import top.potmot.model.dto.GenEntityPropertiesView
import top.potmot.model.dto.GenTableColumnsInput
import top.potmot.model.query.EntityQuery
import top.potmot.service.EntityService
import java.util.*

@RestController
@RequestMapping("/entity")
class EntityController @Autowired constructor(private val entityService: EntityService) {

    @PostMapping("/map")
    fun mapEntity(@RequestBody table: GenTableColumnsInput): GenEntityPropertiesView {
        return entityService.mapEntity(table)
    }

    @GetMapping("/sync/{tableId}")
    fun previewSyncEntity(@PathVariable tableId: Long): List<GenEntityPropertiesView> {
        return entityService.previewSyncEntity(tableId)
    }

    @PutMapping("/save")
    fun saveEntities(@RequestBody entities: List<GenEntityPropertiesInput>): List<Optional<GenEntityPropertiesView>> {
        return entityService.saveEntities(entities)
    }

    @PutMapping("/config")
    fun configEntity(@RequestBody entity: GenEntityConfigInput): Optional<GenEntityPropertiesView> {
        return entityService.configEntity(entity)
    }

    @PostMapping("/query")
    fun queryEntities(@RequestBody query: EntityQuery): List<GenEntityPropertiesView> {
        return entityService.queryEntities(query)
    }

    @DeleteMapping
    fun deleteEntities(@RequestBody ids: List<Long>): Int {
        return entityService.deleteEntities(ids)
    }
}
