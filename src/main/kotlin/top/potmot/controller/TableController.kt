package top.potmot.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import top.potmot.model.dto.GenColumnCommonView
import top.potmot.model.dto.GenTableColumnsView
import top.potmot.model.dto.GenTableGroupTreeView
import top.potmot.model.query.ColumnQuery
import top.potmot.model.query.TableQuery
import top.potmot.service.TableService

@RestController
@RequestMapping("/table")
class TableController(
    @Autowired val tableService: TableService
) {
    @GetMapping("/query")
    fun query(
        @RequestBody query: TableQuery
    ): List<GenTableColumnsView> {
        return tableService.queryTables(query)
    }

    @GetMapping("/column/query")
    fun queryColumns(
        @RequestBody query: ColumnQuery
    ): List<GenColumnCommonView> {
        return tableService.queryColumns(query)
    }

    @DeleteMapping("/{ids}")
    fun delete(@PathVariable ids: List<Long>): Int {
        return tableService.deleteTables(ids)
    }
}
