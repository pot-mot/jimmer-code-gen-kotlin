package top.potmot.core.business.service.generate.impl.kotlin

import top.potmot.core.business.service.generate.ServiceGenerator
import top.potmot.core.business.utils.ExistValidItem
import top.potmot.core.business.utils.dto
import top.potmot.core.business.utils.existValidItems
import top.potmot.core.business.utils.idProperty
import top.potmot.core.business.utils.packages
import top.potmot.core.business.utils.permissionPrefix
import top.potmot.core.business.utils.requestPath
import top.potmot.core.business.utils.serviceName
import top.potmot.core.business.utils.typeStrToKotlinType
import top.potmot.entity.dto.GenEntityBusinessView
import top.potmot.error.GenerateException
import top.potmot.utils.string.trimBlankLine

object KotlinServiceGenerator : ServiceGenerator() {
    override fun getFileSuffix() = ".kt"

    @Throws(GenerateException::class)
    override fun stringifyService(
        entity: GenEntityBusinessView,
    ): String {
        val name = entity.name
        val comment = entity.comment

        val serviceName = entity.serviceName
        val permissionPrefix = entity.permissionPrefix

        val packages = entity.packages
        val (listView, detailView, insertInput, updateInput, spec, optionView) = entity.dto
        val existValidItemWithName = entity.existValidItems.map {
            it.dtoName to it
        }
        val existValidDtoImports = existValidItemWithName.joinToString("\n") {
            "import ${packages.dto}.${it.first}"
        }.let {
            if (it.isNotBlank()) "\n$it"
            else ""
        }

        val idProperty = entity.idProperty
        val idName = idProperty.name
        val idType = typeStrToKotlinType(idProperty.type, idProperty.typeNotNull)


        return ("""
package ${packages.service}

import cn.dev33.satoken.annotation.SaCheckPermission
import org.babyfish.jimmer.View
import org.babyfish.jimmer.sql.kt.KSqlClient""" +
                (if (!entity.canEdit) "" else "\nimport org.babyfish.jimmer.sql.ast.mutation.AssociatedSaveMode") +
"""
import org.springframework.beans.factory.annotation.Autowired""" +
                (if (!entity.canAdd && !entity.canEdit && !entity.canDelete) "" else "\nimport org.springframework.transaction.annotation.Transactional") +
                (if (!entity.canDelete) "" else "\nimport org.springframework.web.bind.annotation.DeleteMapping") +
"""
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping""" +
                (if (!entity.canEdit) "" else "\nimport org.springframework.web.bind.annotation.PutMapping") +
"""
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping""" +
                (if (!entity.canDelete) "" else "\nimport org.springframework.web.bind.annotation.RequestParam") +
"""
import org.springframework.web.bind.annotation.RestController
import ${packages.entity}.${name}
import ${packages.dto}.${listView}
import ${packages.dto}.${detailView}""" +
                (if (!entity.canAdd) "" else "\nimport ${packages.dto}.${insertInput}") +
                (if (!entity.canEdit) "" else "\nimport ${packages.dto}.${updateInput}") +
                """
import ${packages.dto}.${spec}
import ${packages.dto}.${optionView}${existValidDtoImports}
import ${packages.entity}.query.PageQuery
import ${packages.exception}.AuthorizeException
import ${packages.utils}.sqlClient.query
import ${packages.utils}.sqlClient.queryPage

@RestController
@RequestMapping("/${entity.requestPath}")
class $serviceName(
    @Autowired
    private val sqlClient: KSqlClient,
) {
    /**
     * 根据ID获取${comment}。
     *
     * @param id ${comment}的ID。
     * @return ${comment}的详细信息。
     */
    @GetMapping("/{id}")
    @SaCheckPermission("${permissionPrefix}:get")
    @Throws(AuthorizeException::class)
    fun get(@PathVariable id: ${idType}) = 
        sqlClient.findById(${detailView}::class, id)

    /**
     * 根据提供的查询参数列出${comment}。
     *
     * @param spec 查询参数。
     * @return ${comment}列表数据。
     */
    @PostMapping("/list")
    @SaCheckPermission("${permissionPrefix}:list")
    @Throws(AuthorizeException::class)
    fun list(@RequestBody spec: ${spec}) = 
        sqlClient.query(${listView}::class, spec)

    /**
     * 根据提供的查询参数分页查询${comment}。
     *
     * @param query 分页查询参数。
     * @return ${comment}分页数据。
     */
    @PostMapping("/page")
    @SaCheckPermission("${permissionPrefix}:list")
    @Throws(AuthorizeException::class)
    fun page(@RequestBody query: PageQuery<${spec}>) = 
        sqlClient.queryPage(${listView}::class, query)

    /**
     * 根据提供的查询参数列出${comment}选项。
     *
     * @param spec 查询参数。
     * @return ${comment}列表数据。
     */
    @PostMapping("/list/options")
    @SaCheckPermission("${permissionPrefix}:select")
    @Throws(AuthorizeException::class)
    fun listOptions(@RequestBody spec: ${spec}) = 
        sqlClient.query(${optionView}::class, spec)
""" + (if (!entity.canAdd) "" else """
    /**
     * 插入新的${comment}。
     *
     * @param input ${comment}插入输入对象。
     * @return 插入的${comment}的ID。
     */
    @PostMapping
    @SaCheckPermission("${permissionPrefix}:insert")
    @Transactional
    @Throws(AuthorizeException::class)
    fun insert(@RequestBody input: ${insertInput}) = 
        sqlClient.insert(input).modifiedEntity.${idName}
""") + (if (!entity.canEdit) "" else """
    /**
     * 更新${comment}。
     *
     * @param input ${comment}更新输入对象。
     * @return 更新的${comment}的ID。
     */
    @PutMapping
    @SaCheckPermission("${permissionPrefix}:update")
    @Transactional
    @Throws(AuthorizeException::class)
    fun update(@RequestBody input: ${updateInput}) = 
        sqlClient.update(input, AssociatedSaveMode.REPLACE).modifiedEntity.${idName}
""") + (if (!entity.canDelete) "" else """
    /**
     * 删除指定ID的${comment}。
     *
     * @param ids 要删除的${comment}ID列表。
     * @return 删除的${comment}的行数。
     */
    @DeleteMapping
    @SaCheckPermission("${permissionPrefix}:delete")
    @Transactional
    @Throws(AuthorizeException::class)
    fun delete(@RequestParam ids: List<${idType}>) = 
        sqlClient.deleteByIds(${name}::class, ids).affectedRowCount(${name}::class)
""")).trim() + existValidItemWithName.joinToString("") { (name, validItem) ->
            """

    /**
     * 根据${validItem.properties.joinToString(", ") { it.comment }}校验${comment}是否存在。
     *
     * @param spec ${comment}校验规格对象。
     * @return ${comment}是否存在。
     */
    @PostMapping("/${validItem.functionName}")
    @SaCheckPermission("${permissionPrefix}:list")
    @Throws(AuthorizeException::class)
""" + stringifyExistValidQueryMethod(entity.name, name, validItem)
        } + "\n}"
    }

    private fun stringifyExistValidQueryMethod(
        entityName: String,
        name: String,
        validItem: ExistValidItem,
    ): String {
        return """
    fun ${validItem.functionName}(@RequestBody spec: $name): Boolean =
        sqlClient.createQuery($entityName::class) {
            where(spec)
            select(table)
        }.exists()
        """.trimBlankLine()
    }
}