package top.potmot.service

import org.babyfish.jimmer.sql.ast.mutation.DeleteMode
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.support.TransactionTemplate
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import top.potmot.error.DataSourceException
import top.potmot.entity.GenDataSource
import top.potmot.constant.defaultDataSources
import top.potmot.entity.dto.GenDataSourceInput
import top.potmot.entity.dto.GenDataSourceTemplateView
import top.potmot.entity.dto.GenDataSourceView
import top.potmot.entity.extension.test

@RestController
@RequestMapping("/dataSource")
class DataSourceService(
    @Autowired val sqlClient: KSqlClient,
    @Autowired val transactionTemplate: TransactionTemplate
) {
    /**
     * 列出所有数据源
     */
    @GetMapping
    fun list(): List<GenDataSourceView> =
        sqlClient.createQuery(GenDataSource::class) {
            select(table.fetch(GenDataSourceView::class))
        }.execute()

    /**
     * 获取单个数据源
     */
    @GetMapping("/{id}")
    fun get(@PathVariable id: Long): GenDataSourceView? =
        sqlClient.findById(GenDataSourceView::class, id)

    /**
     * 获取默认数据库配置
     */
    @GetMapping("/defaults")
    fun getDefaults(): List<Pair<String, GenDataSourceTemplateView>> =
        defaultDataSources()

    /**
     * 保存数据源
     */
    @PostMapping
    @Throws(DataSourceException.ConnectFail::class)
    fun save(@RequestBody dataSource: GenDataSourceInput): Long {
        dataSource.toEntity().test()
        return transactionTemplate.execute {
            sqlClient.save(dataSource).modifiedEntity.id
        }!!
    }


    /**
     * 测试数据源
     */
    @PostMapping("/test")
    @Throws(DataSourceException.ConnectFail::class)
    fun test(@RequestBody dataSource: GenDataSourceInput): Boolean =
        try {
            dataSource.toEntity().test()
            true
        } catch (e: DataSourceException) {
            false
        }

    /**
     * 删除数据源
     */
    @DeleteMapping("/{ids}")
    fun delete(@PathVariable ids: List<Long>): Int =
        transactionTemplate.execute {
            sqlClient.deleteByIds(GenDataSource::class, ids, DeleteMode.PHYSICAL).totalAffectedRowCount
        }!!
}
