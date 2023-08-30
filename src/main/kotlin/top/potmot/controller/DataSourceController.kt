package top.potmot.controller

import org.babyfish.jimmer.client.ThrowsAll
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import top.potmot.constant.DataSourceType
import top.potmot.error.DataSourceErrorCode
import top.potmot.model.GenSchema
import top.potmot.model.dto.GenDataSourceInput
import top.potmot.model.dto.GenDataSourceView
import top.potmot.model.dto.GenSchemaView
import top.potmot.service.DataSourceService

@RestController
@RequestMapping("/dataSource")
class DataSourceController (
    @Autowired val dataSourceService: DataSourceService
) {
    @GetMapping("/types")
    fun listTypes(): List<String> {
        return enumValues<DataSourceType>().map {
            it.name
        }
    }

    @PostMapping
    @ThrowsAll(DataSourceErrorCode::class)
    fun save(@RequestBody dataSource: GenDataSourceInput): GenDataSourceView {
        return GenDataSourceView(dataSourceService.saveDataSource(dataSource))
    }

    @GetMapping
    fun list(): List<GenDataSourceView> {
        return dataSourceService.listDataSources()
    }

    @DeleteMapping("/{ids}")
    fun delete(@PathVariable ids: List<Long>): Int {
        return dataSourceService.deleteDataSources(ids)
    }

    @GetMapping("/{dataSourceId}/schema/all")
    @ThrowsAll(DataSourceErrorCode::class)
    fun viewSchemas(@PathVariable dataSourceId: Long): List<GenSchema> {
        return dataSourceService.viewSchemas(dataSourceId)
    }

    @GetMapping("/{dataSourceId}/schema/imported")
    fun listSchemas(@PathVariable dataSourceId: Long): List<GenSchemaView> {
        return dataSourceService.listSchemas(dataSourceId)
    }

    @PostMapping("/{dataSourceId}/schema/{name}")
    @ThrowsAll(DataSourceErrorCode::class)
    fun importSchema(
        @PathVariable dataSourceId: Long,
        @PathVariable name: String
    ): List<GenSchema> {
        return dataSourceService.importSchema(dataSourceId, name)
    }
}
