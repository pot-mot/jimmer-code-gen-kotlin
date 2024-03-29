package top.potmot.service

import org.babyfish.jimmer.sql.ast.mutation.DeleteMode
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import top.potmot.error.DataSourceException
import top.potmot.model.GenDataSource
import top.potmot.model.copy
import top.potmot.model.defaultValue.defaultDataSources
import top.potmot.model.dto.GenDataSourceInput
import top.potmot.model.dto.GenDataSourceTemplateView
import top.potmot.model.dto.GenDataSourceView
import top.potmot.model.extension.test

@RestController
@RequestMapping("/dataSource")
class DataSourceService(
    @Autowired val sqlClient: KSqlClient
) {
    /**
     * 列出所有数据源
     */
    @GetMapping
    fun list(): List<GenDataSourceView> {
        return sqlClient.createQuery(GenDataSource::class) {
            select(table.fetch(GenDataSourceView::class))
        }.execute()
    }

    /**
     * 获取单个数据源
     */
    @GetMapping("/{id}")
    fun get(@PathVariable id: Long): GenDataSourceView? {
        return sqlClient.findById(GenDataSourceView::class, id)
    }

    /**
     * 获取默认数据库配置
     */
    @GetMapping("/defaults")
    fun getDefaults(): List<Pair<String, GenDataSourceTemplateView>> =
        defaultDataSources()

    /**
     * 创建数据源
     */
    @PostMapping
    @Transactional
    @Throws(DataSourceException.ConnectFail::class)
    fun create(@RequestBody dataSource: GenDataSourceInput): Long {
        dataSource.toEntity().test()
        return sqlClient.insert(dataSource).modifiedEntity.id
    }

    /**
     * 编辑数据源
     */
    @PutMapping("/{id}")
    @Transactional
    @Throws(DataSourceException.ConnectFail::class)
    fun edit(@PathVariable id: Long, @RequestBody dataSource: GenDataSourceInput): Long {
        dataSource.toEntity().test()
        return sqlClient.update(dataSource.toEntity().copy {
            this.id = id
        }).modifiedEntity.id
    }

    /**
     * 测试数据源
     */
    @PostMapping("/test")
    @Transactional
    @Throws(DataSourceException.ConnectFail::class)
    fun test(@RequestBody dataSource: GenDataSourceInput): Boolean {
        return try {
            dataSource.toEntity().test()
            true
        } catch (e: DataSourceException) {
            false
        }
    }

    /**
     * 删除数据源
     */
    @DeleteMapping("/{ids}")
    @Transactional
    fun delete(@PathVariable ids: List<Long>): Int {
        return sqlClient.deleteByIds(GenDataSource::class, ids, DeleteMode.PHYSICAL).totalAffectedRowCount
    }
}
