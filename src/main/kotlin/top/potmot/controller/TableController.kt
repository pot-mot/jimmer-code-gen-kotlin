package top.potmot.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import top.potmot.model.GenTable
import top.potmot.model.dto.GenColumnCommonView
import top.potmot.model.dto.GenTableColumnsInput
import top.potmot.model.dto.GenTableColumnsView
import top.potmot.model.query.ColumnQuery
import top.potmot.model.query.TableQuery
import top.potmot.service.TableService
import java.util.*

@RestController
@RequestMapping("/table")
class TableController(
    @Autowired val tableService: TableService
) {
    @GetMapping("/view")
    fun viewTables(@RequestParam namePattern: String? = null): List<GenTable> {
        return tableService.viewTables(namePattern)
    }

    @PostMapping("/importAll")
    fun importAllTables(@RequestParam groupId: Long? = null): List<GenTableColumnsView> {
        return tableService.importAllTables(groupId)
    }

    @PostMapping("/import")
    fun importTables(
        @RequestBody tables: List<GenTableColumnsInput>,
        @RequestParam groupId: Long? = null
    ): List<Optional<GenTableColumnsView>> {
        return tableService.importTables(tables, groupId)
    }

    @PutMapping("/refreshAll")
    fun refreshAllTables(@RequestParam groupId: Long? = null): List<GenTableColumnsView> {
        return tableService.refreshAllTables(groupId)
    }

    @PutMapping("/refresh")
    fun refreshTables(
        @RequestBody tables: List<GenTableColumnsInput>,
        @RequestParam groupId: Long? = null
    ): List<Optional<GenTableColumnsView>> {
        return tableService.refreshTables(tables, groupId)
    }

    @GetMapping("/query")
    fun queryTables(
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
    fun deleteTables(@RequestBody ids: Iterable<Long>): Int {
        return tableService.deleteTables(ids)
    }
}
