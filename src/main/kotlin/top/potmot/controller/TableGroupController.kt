package top.potmot.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import top.potmot.model.dto.GenTableGroupCommonInput
import top.potmot.model.dto.GenTableGroupCommonView
import top.potmot.model.dto.GenTableGroupMoveInput
import top.potmot.model.dto.GenTableGroupTreeView
import top.potmot.model.query.TableGroupQuery
import top.potmot.service.TableGroupService

@RestController
@RequestMapping("/table/group")
class TableGroupController (
    @Autowired val tableGroupService: TableGroupService
) {
    @PostMapping
    fun create(@RequestBody group: GenTableGroupCommonInput): GenTableGroupCommonView {
        return tableGroupService.createGroup(group)
    }

    @PutMapping
    fun edit(@RequestBody group: GenTableGroupCommonInput): GenTableGroupCommonView {
        return tableGroupService.editGroup(group)
    }

    @PutMapping("move")
    fun move(@RequestBody group: GenTableGroupMoveInput): GenTableGroupCommonView {
        return tableGroupService.moveGroup(group)
    }

    @GetMapping("/tree")
    fun getTrees(@RequestParam(required = false) groupIds: List<Long>?): List<GenTableGroupTreeView> {
        return tableGroupService.getTableTrees(groupIds)
    }

    @PostMapping("/query")
    fun query(@RequestBody query: TableGroupQuery): List<GenTableGroupCommonView> {
        return tableGroupService.queryGroups(query)
    }

    @DeleteMapping
    fun delete(@RequestBody ids: List<Long>): Int {
        return tableGroupService.deleteGroups(ids)
    }
}
