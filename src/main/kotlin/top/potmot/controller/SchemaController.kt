package top.potmot.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import top.potmot.model.dto.GenTableCommonView
import top.potmot.model.query.TableQuery
import top.potmot.service.DataSourceService
import top.potmot.service.TableService

@RestController
@RequestMapping("/schema")
class SchemaController(
    @Autowired val dataSourceService: DataSourceService,
    @Autowired val tableService: TableService
) {
    @GetMapping("/{schemaId}/table")
    fun listTables(@PathVariable schemaId: Long): List<GenTableCommonView> {
        return tableService.queryTables(TableQuery(
            schemaIds = listOf(schemaId)
        ), GenTableCommonView::class)
    }

    @DeleteMapping("/{ids}")
    fun delete(@PathVariable ids: List<Long>): Int {
        return dataSourceService.deleteSchemas(ids)
    }

    @PutMapping("/{id}/refresh")
    fun refresh(@PathVariable id: Long): Int {
        return dataSourceService.refreshSchema(id)
    }
}
