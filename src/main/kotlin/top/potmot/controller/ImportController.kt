package top.potmot.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import top.potmot.model.GenTable
import top.potmot.model.base.PageQuery
import top.potmot.service.ImportService

@RestController
@CrossOrigin
class ImportController(
   @Autowired val importService: ImportService
) {
    @GetMapping("/previewTables")
    fun preview(@RequestParam(required = false) tablePattern: String? = null): List<GenTable> {
        return importService.previewTables(tablePattern)
    }

    @GetMapping("/genTable/list")
    fun listGenTable(page: PageQuery): Page<GenTable> {
        return importService.getTables(page.toPageRequest())
    }
}
