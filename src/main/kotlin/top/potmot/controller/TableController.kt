package top.potmot.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import top.potmot.model.dto.GenColumnCommonView
import top.potmot.model.dto.GenTableColumnsView
import top.potmot.model.query.ColumnQuery
import top.potmot.model.query.TableQuery
import top.potmot.service.TableService

@RestController
@RequestMapping("/table")
class TableController(
    @Autowired val tableService: TableService
) {
    @PutMapping("/move")
    fun move(@RequestBody ids: List<Long>, @RequestParam groupId: Long): Int {
        return tableService.moveTables(ids, groupId)
    }

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

    @DeleteMapping
    fun delete(@RequestBody ids: Iterable<Long>): Int {
        return tableService.deleteTables(ids)
    }
}
