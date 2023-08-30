package top.potmot.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import top.potmot.model.dto.GenEntityConfigInput
import top.potmot.model.dto.GenEntityPropertiesInput
import top.potmot.model.dto.GenEntityPropertiesView
import top.potmot.model.query.EntityQuery
import top.potmot.service.EntityService

@RestController
@RequestMapping("/entity")
class EntityController (
    @Autowired val entityService: EntityService
) {
    @PostMapping("/map")
    fun map(@RequestBody tableIds: List<Long>): List<GenEntityPropertiesView> {
        return entityService.mapEntities(tableIds)
    }

    @GetMapping("/sync")
    fun sync(@RequestBody tableIds: List<Long>): List<GenEntityPropertiesView> {
        return entityService.syncEntities(tableIds)
    }

    @PutMapping("/save")
    fun save(@RequestBody entities: List<GenEntityPropertiesInput>): List<GenEntityPropertiesView> {
        return entityService.saveEntities(entities)
    }

    @PutMapping("/config")
    fun config(@RequestBody entity: GenEntityConfigInput): GenEntityPropertiesView {
        return entityService.configEntity(entity)
    }

    @PostMapping("/query")
    fun query(@RequestBody query: EntityQuery): List<GenEntityPropertiesView> {
        return entityService.queryEntities(query, GenEntityPropertiesView::class)
    }

    @DeleteMapping("/{ids}")
    fun delete(@PathVariable ids: List<Long>): Int {
        return entityService.deleteEntities(ids)
    }
}
