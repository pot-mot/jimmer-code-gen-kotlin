package top.potmot.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import top.potmot.model.dto.GenAssociationCommonInput
import top.potmot.model.dto.GenAssociationCommonView
import top.potmot.model.query.AssociationQuery
import top.potmot.service.AssociationService

@RestController
@RequestMapping("/association")
class AssociationController(
    @Autowired val associationService: AssociationService
) {
    @GetMapping("/select")
    fun select(@RequestBody tableIds: List<Long>): List<GenAssociationCommonView> {
        return associationService.selectAssociations(tableIds, GenAssociationCommonView::class)
    }

    @PutMapping("/save")
    fun save(@RequestBody associations: List<GenAssociationCommonInput>): List<GenAssociationCommonView> {
        return associationService.saveAssociations(associations)
    }

    @PostMapping("/query")
    fun query(@RequestBody query: AssociationQuery): List<GenAssociationCommonView> {
        return associationService.queryAssociations(query, GenAssociationCommonView::class)
    }

    @DeleteMapping("/{ids}")
    fun delete(@PathVariable ids: List<Long>): Int {
        return associationService.deleteAssociations(ids)
    }
}
