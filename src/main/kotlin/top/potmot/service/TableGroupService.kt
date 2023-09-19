//package top.potmot.service
//
//import org.babyfish.jimmer.View
//import org.babyfish.jimmer.sql.kt.KSqlClient
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.stereotype.Service
//import org.springframework.transaction.annotation.Transactional
//import top.potmot.model.GenAssociation
//import top.potmot.model.dto.GenTableGroupCommonInput
//import top.potmot.model.dto.GenTableGroupCommonView
//import top.potmot.model.query.TableGroupQuery
//import kotlin.reflect.KClass
//
//@Service
//class TableGroupService(
//    @Autowired val sqlClient: KSqlClient
//) {
//    @Transactional
//    fun create(group: GenTableGroupCommonInput): Int {
//        return sqlClient.insert(group).totalAffectedRowCount
//    }
//
//    @Transactional
//    fun edit(group: GenTableGroupCommonInput): GenTableGroupCommonView {
//        return GenTableGroupCommonView(sqlClient.save(group).modifiedEntity)
//    }
//
//    @Transactional
//    fun move(ids: List<Long>, groupId: Long): Int {
//        TODO("Not yet implemented")
//    }
//
//    fun <T : View<GenAssociation>> query(query: TableGroupQuery, viewCLass: KClass<T>): List<T> {
//        TODO("Not yet implemented")
//    }
//
//    @Transactional
//    fun delete(ids: List<Long>): Int {
//        TODO("Not yet implemented")
//    }
//}
