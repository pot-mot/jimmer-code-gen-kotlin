package top.potmot.core.business.service.generate.impl.kotlin

import top.potmot.core.business.service.generate.ServiceGenerator
import top.potmot.core.business.utils.dto
import top.potmot.core.business.utils.packages
import top.potmot.core.business.utils.permissionPrefix
import top.potmot.core.business.utils.requestPath
import top.potmot.core.business.utils.serviceName
import top.potmot.entity.dto.GenEntityBusinessView

object KotlinServiceGenerator : ServiceGenerator() {
    override fun getFileSuffix() = ".kt"

    override fun stringifyService(
        entity: GenEntityBusinessView,
    ): String {
        val serviceName = entity.serviceName

        val (_, servicePackage,  utilsPackage, exceptionPackage, dtoPackage) = entity.packages
        val (_, listView, detailView, insertInput, updateInput, spec) = entity.dto

        val idProperty = entity.properties.first { it.idProperty }
        val idName = idProperty.name
        val idType = "${idProperty.type}${if (idProperty.typeNotNull) "" else "?"}"

        return """
package $servicePackage

import cn.dev33.satoken.annotation.SaCheckPermission
import org.babyfish.jimmer.View
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.ast.mutation.AssociatedSaveMode
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ${entity.packagePath}.${entity.name}
import ${dtoPackage}.${listView}
import ${dtoPackage}.${detailView}
import ${dtoPackage}.${insertInput}
import ${dtoPackage}.${updateInput}
import ${dtoPackage}.${spec}
import ${entity.packagePath}.query.PageQuery
import ${exceptionPackage}.AuthorizeException
import ${utilsPackage}.sqlClient.queryPage

@RestController
@RequestMapping("/${entity.requestPath}")
class $serviceName(
    @Autowired
    private val sqlClient: KSqlClient,
) {
    /**
     * 根据ID获取${entity.comment}。
     *
     * @param id ${entity.comment}的ID。
     * @return ${entity.comment}的详细信息。
     */
    @GetMapping("/{id}")
    @SaCheckPermission("${entity.permissionPrefix}:get")
    @Throws(AuthorizeException::class)
    fun get(@PathVariable id: ${idType}) = 
        sqlClient.findById(${detailView}::class, id)
    
    /**
     * 根据提供的查询参数列出${entity.comment}。
     *
     * @param spec 查询参数。
     * @return ${entity.comment}列表。
     */
    @PostMapping("/list")
    @SaCheckPermission("${entity.permissionPrefix}:list")
    @Throws(AuthorizeException::class)
    fun list(@RequestBody spec: ${spec}) = 
        sqlClient.query(${listView}::class, spec)

    /**
     * 根据提供的查询参数分页查询${entity.comment}。
     *
     * @param spec 查询参数。
     * @return ${entity.comment}分页数据。
     */
    @PostMapping("/page")
    @SaCheckPermission("${entity.permissionPrefix}:list")
    @Throws(AuthorizeException::class)
    fun page(@RequestBody query: PageQuery<${spec}>) = 
        sqlClient.queryPage(${listView}::class, query)

    /**
     * 插入新的${entity.comment}。
     *
     * @param input ${entity.comment}插入输入对象。
     * @return 插入的${entity.comment}的ID。
     */
    @PostMapping
    @SaCheckPermission("${entity.permissionPrefix}:insert")
    @Transactional
    @Throws(AuthorizeException::class)
    fun insert(@RequestBody input: ${insertInput}) = 
        sqlClient.insert(input).modifiedEntity.${idName}

    /**
     * 更新${entity.comment}。
     *
     * @param input ${entity.comment}更新输入对象。
     * @return 更新的${entity.comment}的ID。
     */
    @PutMapping
    @SaCheckPermission("${entity.permissionPrefix}:update")
    @Transactional
    @Throws(AuthorizeException::class)
    fun update(@RequestBody input: ${updateInput}) = 
        sqlClient.update(input, AssociatedSaveMode.REPLACE).modifiedEntity.${idName}

    /**
     * 删除指定ID的${entity.comment}。
     *
     * @param ids 要删除的${entity.comment}ID列表。
     * @return 删除的${entity.comment}的行数。
     */
    @DeleteMapping
    @SaCheckPermission("${entity.permissionPrefix}:delete")
    @Transactional
    @Throws(AuthorizeException::class)
    fun delete(@RequestParam ids: List<${idType}>) = 
        sqlClient.deleteByIds(${entity.name}::class, ids).affectedRowCount(${entity.name}::class)
}
    """.trim()
    }
}