package top.potmot.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import top.potmot.model.GenDataSource
import top.potmot.model.GenSchema
import top.potmot.model.GenTableGroup
import top.potmot.model.dto.GenDataSourceInput
import top.potmot.model.dto.GenSchemaInsertInput
import top.potmot.service.SchemaService

@RestController
@RequestMapping("/dataSource")
class DataSourceController (
    @Autowired val schemaService: SchemaService
) {

    @PostMapping
    fun save(@RequestBody input: GenDataSourceInput): GenDataSource {
        return schemaService.saveDataSource(input)
    }

    @GetMapping("/{dataSourceId}/schemas")
    fun viewSchemas(@PathVariable dataSourceId: Long): List<GenSchema> {
        return schemaService.viewSchemas(dataSourceId)
    }

    @DeleteMapping
    fun deleteDataSources(@RequestBody ids: List<Long>?): Int {
        return schemaService.deleteDataSources(ids ?: emptyList())
    }

    @GetMapping
    fun list(): List<GenDataSource> {
        return schemaService.getDataSources()
    }

    @PostMapping("/import")
    fun importTables(
        @RequestBody input: GenSchemaInsertInput,
        @RequestParam(required = false) groupId: Long?
    ): List<GenTableGroup> {
        return schemaService.importTables(input, groupId)
    }
}
