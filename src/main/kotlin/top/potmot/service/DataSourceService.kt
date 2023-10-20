package top.potmot.service

import org.babyfish.jimmer.client.ThrowsAll
import org.babyfish.jimmer.sql.ast.mutation.DeleteMode
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
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
import top.potmot.contract.dataSourceTemplate
import top.potmot.enumeration.DataSourceType
import top.potmot.error.DataSourceErrorCode
import top.potmot.error.DataSourceException
import top.potmot.extension.test
import top.potmot.model.GenDataSource
import top.potmot.model.copy
import top.potmot.model.dto.GenDataSourceInput
import top.potmot.model.dto.GenDataSourceTemplateView
import top.potmot.model.dto.GenDataSourceView
import top.potmot.model.id

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
        return sqlClient.createQuery(GenDataSource::class) {
            where(table.id eq id)
            select(table.fetch(GenDataSourceView::class))
        }.execute()
            .firstOrNull()
    }

    /**
     * 获取数据库类型
     */
    @GetMapping("/type")
    fun listType(): List<DataSourceType> {
        return DataSourceType.values().toList()
    }

    /**
     * 获取默认数据库配置
     */
    @GetMapping("/type/{dataSourceType}/default")
    fun getDefaultDataSource(
        @PathVariable dataSourceType: DataSourceType
    ): GenDataSourceTemplateView {
        return dataSourceType.dataSourceTemplate()
    }

    /**
     * 插入数据源
     */
    @PostMapping
    @ThrowsAll(DataSourceErrorCode::class)
    @Transactional
    fun insert(@RequestBody dataSource: GenDataSourceInput): Long {
        dataSource.toEntity().test()
        return sqlClient.insert(dataSource).modifiedEntity.id
    }

    /**
     * 编辑数据源
     */
    @PutMapping("/{id}")
    @ThrowsAll(DataSourceErrorCode::class)
    @Transactional
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
