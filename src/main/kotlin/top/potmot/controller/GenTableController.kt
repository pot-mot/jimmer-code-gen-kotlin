package top.potmot.controller

import org.babyfish.jimmer.client.FetchBy
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import top.potmot.dao.GenTableRepository
import top.potmot.model.GenTable
import top.potmot.model.output.GenTableFetchers
import top.potmot.model.output.GenTableFetchers.CLASS_COLUMN_FETCHER
import top.potmot.model.output.GenTableFetchers.CLASS_FETCHER
import top.potmot.model.output.GenTableFetchers.COLUMN_FETCHER
import top.potmot.model.output.GenTableFetchers.SIMPLE_FETCHER
import top.potmot.model.output.GenTableFetchers.TABLE_COLUMN_FETCHER
import top.potmot.model.output.GenTableFetchers.TABLE_FETCHER

@RestController
@RequestMapping("/genTable")
@CrossOrigin
class GenTableController(
    @Autowired val genTableRepository: GenTableRepository,
) {
    @GetMapping("/list")
    fun list(): List<@FetchBy("SIMPLE_FETCHER", ownerType = GenTableFetchers::class) GenTable> {
        return genTableRepository.findAll(SIMPLE_FETCHER)
    }

    @GetMapping("/{id}")
    fun get(@PathVariable id: Long): @FetchBy("COLUMN_FETCHER", ownerType = GenTableFetchers::class) GenTable {
        return genTableRepository.findById(id, COLUMN_FETCHER).get()
    }

    @GetMapping("/table/list")
    fun listTables(): List<@FetchBy("TABLE_FETCHER", ownerType = GenTableFetchers::class) GenTable> {
        return genTableRepository.findAll(TABLE_FETCHER)
    }

    @GetMapping("/table/{id}")
    fun getTable(@PathVariable id: Long): @FetchBy("TABLE_COLUMN_FETCHER", ownerType = GenTableFetchers::class) GenTable {
        return genTableRepository.findById(id, TABLE_COLUMN_FETCHER).get()
    }

    @GetMapping("/class/list")
    fun listClasses(): List<@FetchBy("CLASS_FETCHER", ownerType = GenTableFetchers::class) GenTable> {
        return genTableRepository.findAll(CLASS_FETCHER)
    }

    @GetMapping("/class/{id}")
    fun getClass(@PathVariable id: Long): @FetchBy("CLASS_COLUMN_FETCHER", ownerType = GenTableFetchers::class) GenTable {
        return genTableRepository.findById(id, CLASS_COLUMN_FETCHER).get()
    }
}
