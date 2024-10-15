package top.potmot.core.service.generate.impl.kotlin

import top.potmot.core.service.generate.ServiceCodeGenerator
import top.potmot.entity.dto.GenEntityPropertiesView

object KotlinServiceCodeGenerator : ServiceCodeGenerator() {
    override fun getFileSuffix(): String = ".kt"

    override fun stringify(entity: GenEntityPropertiesView): String {
        val idProperty = entity.properties.first { it.idProperty }
        val idName = idProperty.name
        val idType = "${idProperty.type}${if (idProperty.typeNotNull) "" else "?"}"

        val listView = "${entity.name}ListView"
        val detailView = "${entity.name}DetailView"
        val insertInput = "${entity.name}InsertInput"
        val updateInput = "${entity.name}UpdateInput"
        val spec = "${entity.name}Spec"

        val servicePackagePath = entity.packagePath.replaceAfterLast(".", ".service")
        val utilsPackagePath = entity.packagePath.replaceAfterLast(".", ".utils")
        val exceptionPackagePath = entity.packagePath.replaceAfterLast(".", ".exception")
        val requestPath = entity.name.replaceFirstChar { it.lowercase() }
        
        val permissionPrefix = entity.name.replaceFirstChar { it.lowercase() }

        return """
package $servicePackagePath

import cn.dev33.satoken.annotation.SaCheckPermission
import org.babyfish.jimmer.View
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
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ${entity.packagePath}.${entity.name}
import ${entity.packagePath}.dto.${listView}
import ${entity.packagePath}.dto.${detailView}
import ${entity.packagePath}.dto.${insertInput}
import ${entity.packagePath}.dto.${updateInput}
import ${entity.packagePath}.dto.${spec}
import ${entity.packagePath}.query.PageQuery
import ${exceptionPackagePath}.AuthorizeException
import ${utilsPackagePath}.sqlClient.queryPage

@RestController
@RequestMapping("/${requestPath}")
class ${entity}Service(
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
    @SaCheckPermission("${permissionPrefix}:get")
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
    @SaCheckPermission("${permissionPrefix}:list")
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
    @SaCheckPermission("${permissionPrefix}:list")
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
    @SaCheckPermission("${permissionPrefix}:insert")
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
    @SaCheckPermission("${permissionPrefix}:update")
    @Transactional
    @Throws(AuthorizeException::class)
    fun update(@RequestBody input: ${updateInput}) = 
        sqlClient.update(input).modifiedEntity.${idName}

    /**
     * 删除指定ID的${entity.comment}。
     *
     * @param ids 要删除的${entity.comment}ID列表。
     * @return 删除的${entity.comment}的行数。
     */
    @DeleteMapping
    @SaCheckPermission("${permissionPrefix}:delete")
    @Transactional
    @Throws(AuthorizeException::class)
    fun delete(@RequestParam ids: List<${idType}>) = 
        sqlClient.deleteByIds(${entity.name}::class, ids).affectedRowCount(${entity.name}::class)
}
    """.trim()
    }
}