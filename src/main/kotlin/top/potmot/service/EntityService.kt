//package top.potmot.service
//
//import org.babyfish.jimmer.View
//import org.babyfish.jimmer.sql.kt.KSqlClient
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.transaction.annotation.Transactional
//import org.springframework.web.bind.annotation.DeleteMapping
//import org.springframework.web.bind.annotation.PathVariable
//import org.springframework.web.bind.annotation.PostMapping
//import org.springframework.web.bind.annotation.PutMapping
//import org.springframework.web.bind.annotation.RequestMapping
//import org.springframework.web.bind.annotation.RestController
//import top.potmot.model.GenEntity
//import top.potmot.model.dto.GenEntityConfigInput
//import top.potmot.model.dto.GenEntityPropertiesInput
//import top.potmot.model.dto.GenEntityPropertiesView
//import top.potmot.model.query.EntityQuery
//import kotlin.reflect.KClass
//
//@RestController
//@RequestMapping("/entity")
//class EntityService(
//    @Autowired val sqlClient: KSqlClient
//) {
//    @PostMapping("/map")
//    @Transactional
//    fun map(tableIds: List<Long>): List<GenEntityPropertiesView> {
//        TODO("Not yet implemented")
//    }
//
//    @PostMapping("/save")
//    @Transactional
//    fun save(entities: List<GenEntityPropertiesInput>): List<GenEntityPropertiesView> {
//        TODO("Not yet implemented")
//    }
//
//    @PutMapping("/sync")
//    @Transactional
//    fun sync(tableIds: List<Long>): List<GenEntityPropertiesView> {
//        TODO("Not yet implemented")
//    }
//
//    @PutMapping("/config")
//    @Transactional
//    fun config(entity: GenEntityConfigInput): GenEntityPropertiesView {
//        TODO("Not yet implemented")
//    }
//
//    @PostMapping("/query")
//    fun query(query: EntityQuery): List<GenEntityPropertiesView> {
//        return query(query, GenEntityPropertiesView::class)
//    }
//
//    fun <T : View<GenEntity>> query(query: EntityQuery, viewCLass: KClass<T>): List<T> {
//        TODO("Not yet implemented")
//    }
//
//
//    @DeleteMapping("/{ids}")
//    fun delete(@PathVariable ids: List<Long>): Int {
//        TODO("Not yet implemented")
//    }
//}
