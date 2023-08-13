package top.potmot.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import top.potmot.model.dto.GenTableGroupCommonInput
import top.potmot.model.dto.GenTableGroupCommonView
import top.potmot.model.dto.GenTableGroupTreeView
import top.potmot.model.query.TableGroupQuery
import top.potmot.service.TableManageService
import java.util.*

@RestController
@RequestMapping("/table/group")
class TableManageController @Autowired constructor(private val tableManageService: TableManageService) {

    @PostMapping
    fun createGroup(@RequestBody group: GenTableGroupCommonInput): Optional<GenTableGroupCommonView> {
        return tableManageService.createGroup(group)
    }

    @PutMapping
    fun editGroup(@RequestBody group: GenTableGroupCommonInput): GenTableGroupCommonView {
        return tableManageService.editGroup(group)
    }

    @PutMapping("/move")
    fun moveTables(@RequestBody ids: List<Long>, @RequestParam groupId: Long): Int {
        return tableManageService.moveTables(ids, groupId)
    }

    @GetMapping("/tree")
    fun getTableTrees(@RequestParam(required = false) groupIds: List<Long>?): List<GenTableGroupTreeView> {
        return tableManageService.getTableTrees(groupIds)
    }

    @PostMapping("/query")
    fun queryGroups(@RequestBody query: TableGroupQuery): List<GenTableGroupCommonView> {
        return tableManageService.queryGroups(query)
    }

    @DeleteMapping
    fun deleteGroups(@RequestBody ids: List<Long>): Int {
        return tableManageService.deleteGroups(ids)
    }
}
