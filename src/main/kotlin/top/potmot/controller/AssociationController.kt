package top.potmot.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import top.potmot.model.dto.GenAssociationCommonInput
import top.potmot.model.dto.GenAssociationCommonView
import top.potmot.model.dto.GenAssociationPreviewView
import top.potmot.model.query.AssociationQuery
import top.potmot.service.AssociationService
import java.util.*

@RestController
@RequestMapping("/association")
class AssociationController @Autowired constructor(private val associationService: AssociationService) {

    @GetMapping("/select")
    fun selectAssociations(@RequestParam tableIds: List<Long>): List<GenAssociationPreviewView> {
        return associationService.selectAssociations(tableIds)
    }

    @PutMapping("/save")
    fun saveAssociations(@RequestBody associations: List<GenAssociationCommonInput>): List<Optional<GenAssociationCommonView>> {
        return associationService.saveAssociations(associations)
    }

    @PostMapping("/query")
    fun queryAssociations(@RequestBody query: AssociationQuery): List<GenAssociationCommonView> {
        return associationService.queryAssociations(query)
    }

    @DeleteMapping
    fun deleteAssociations(@RequestBody ids: List<Long>): Int {
        return associationService.deleteAssociations(ids)
    }
}
