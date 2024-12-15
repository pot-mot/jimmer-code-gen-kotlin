package top.potmot.core.business.service.generate.impl.kotlin

import top.potmot.core.business.dto.generate.DtoGenerator.isTreeEntity
import top.potmot.core.business.dto.generate.DtoGenerator.parentIdProperty
import top.potmot.core.business.service.generate.ServiceGenerator
import top.potmot.core.business.utils.dto
import top.potmot.core.business.utils.existValidItems
import top.potmot.core.business.utils.idProperty
import top.potmot.core.business.utils.packages
import top.potmot.core.business.utils.permissions
import top.potmot.core.business.utils.requestPath
import top.potmot.core.business.utils.serviceName
import top.potmot.core.business.utils.typeStrToKotlinType
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Vue3ElementPlusViewGenerator.childrenProperty
import top.potmot.entity.dto.GenEntityBusinessView
import top.potmot.error.GenerateException
import top.potmot.utils.string.buildScopeString
import top.potmot.utils.string.clearBlankLine
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

        val packages = entity.packages
        val permissions = entity.permissions
        val (listView, detailView, insertInput, updateFillView, updateInput, spec, optionView) = entity.dto
        val existValidItemWithNames = entity.existValidItems.map {
            it.dtoName to it
        }

        val idProperty = entity.idProperty
        val idName = idProperty.name
        val idType = typeStrToKotlinType(idProperty.type, idProperty.typeNotNull)

        val isTreeEntity = entity.isTreeEntity()
        val parentIdProperty by lazy {
            entity.parentIdProperty
        }
        val childrenProperty by lazy {
            entity.childrenProperty
        }

        return buildScopeString {
            line("package ${packages.service}")
            line()

            val imports = mutableSetOf<String>()
            imports += listOf(
                "cn.dev33.satoken.annotation.SaCheckPermission",
                "org.babyfish.jimmer.View",
                "org.babyfish.jimmer.sql.kt.KSqlClient",
                "org.springframework.web.bind.annotation.GetMapping",
                "org.springframework.web.bind.annotation.PathVariable",
                "org.springframework.web.bind.annotation.PostMapping",
                "org.springframework.web.bind.annotation.RequestBody",
                "org.springframework.web.bind.annotation.RequestMapping",
                "org.springframework.web.bind.annotation.RestController",
                "${packages.entity}.${name}",
                "${packages.dto}.${listView}",
                "${packages.dto}.${detailView}",
                "${packages.dto}.${spec}",
                "${packages.dto}.${optionView}",
                "${packages.entity}.query.PageQuery",
                "${packages.exception}.AuthorizeException",
            )
            imports += existValidItemWithNames.map {
                "${packages.dto}.${it.first}"
            }

            if (isTreeEntity) {
                imports += listOf(
                    "org.springframework.web.bind.annotation.RequestParam"
                )
            }

            if (entity.canAdd || entity.canEdit || entity.canDelete) {
                imports += "org.springframework.transaction.annotation.Transactional"
            }
            if (entity.canAdd) {
                imports += listOf(
                    "${packages.dto}.${insertInput}"
                )
            }
            if (entity.canEdit) {
                imports += listOf(
                    "org.babyfish.jimmer.sql.ast.mutation.AssociatedSaveMode",
                    "org.springframework.web.bind.annotation.PutMapping",
                    "${packages.dto}.${updateInput}",
                    "${packages.dto}.${updateFillView}",
                )
            }
            if (entity.canDelete) {
                imports += listOf(
                    "org.springframework.web.bind.annotation.DeleteMapping",
                    "org.springframework.web.bind.annotation.RequestParam"
                )
            }

            lines(imports.sorted()) { "import $it" }

            line()
            lines(
                "@RestController",
                "@RequestMapping(\"/${entity.requestPath}\")",
                "class $serviceName(",
            )
            scope {
                line("private val sqlClient: KSqlClient")
            }
            line(") {")

            scope {
                block(
                    """
/**
 * 根据ID获取${comment}。
 *
 * @param id ${comment}的ID。
 * @return ${comment}的详细信息。
 */
@GetMapping("/{id}")
@SaCheckPermission("${permissions.get}")
@Throws(AuthorizeException::class)
fun get(@PathVariable id: $idType): $detailView? =
    sqlClient.findById(${detailView}::class, id)
                    """.trimIndent()
                )

                if (isTreeEntity) {
                    line()
                    block(
                        """
/**
 * 根据提供的查询参数列出${comment}。
 *
 * @param spec 查询参数。
 * @param tree 是否以树形结构返回。
 * @return ${comment}列表数据。
 */
@PostMapping("/list")
@SaCheckPermission("${permissions.list}")
@Throws(AuthorizeException::class)
fun list(
    @RequestBody spec: $spec,
    @RequestParam tree: Boolean
): List<$listView> =
    if (tree) {
        sqlClient.executeQuery(${name}::class) {
            where(spec)
            where(table.${parentIdProperty.name}.isNull())
            select(table.fetch(${listView}::class))
        }
    } else {
        sqlClient.executeQuery(${name}::class) {
            where(spec)
            select(table.fetch(${listView}.METADATA.getFetcher().remove("${childrenProperty.name}")))
        }.map { ${listView}(it) }
    }
                        """.trimIndent()
                    )

                    line()
                    block(
                        """
/**
 * 根据提供的查询参数分页查询${comment}。
 *
 * @param query 分页查询参数。
 * @param tree 是否以树形结构返回。
 * @return ${comment}分页数据。
 */
@PostMapping("/page")
@SaCheckPermission("${permissions.list}")
@Throws(AuthorizeException::class)
fun page(
    @RequestBody query: PageQuery<$spec>,
    @RequestParam tree: Boolean
): Page<$listView> =
    if (tree) {
        sqlClient.createQuery(${name}::class) {
            where(query.spec)
            where(table.${parentIdProperty.name}.isNull())
            select(table.fetch(${listView}::class))
        }.fetchPage(query.pageIndex, query.pageSize)
    } else {
        val page = sqlClient.createQuery(${name}::class) {
            where(query.spec)
            select(table.fetch(${listView}.METADATA.getFetcher().remove("${childrenProperty.name}")))
        }.fetchPage(query.pageIndex, query.pageSize)

        Page(page.rows.map { ${listView}(it) }, page.totalRowCount, page.totalPageCount)
    }
                        """.trimIndent()
                    )
                } else {
                    line()
                    block(
                        """
/**
 * 根据提供的查询参数列出${comment}。
 *
 * @param spec 查询参数。
 * @return ${comment}列表数据。
 */
@PostMapping("/list")
@SaCheckPermission("${permissions.list}")
@Throws(AuthorizeException::class)
fun list(@RequestBody spec: $spec): List<$listView> =
    sqlClient.executeQuery(${name}::class) {
        where(spec)
        select(table.fetch(${listView}::class))
    }
                        """.trimIndent()
                    )

                    line()
                    block(
                        """
/**
 * 根据提供的查询参数分页查询${comment}。
 *
 * @param query 分页查询参数。
 * @return ${comment}分页数据。
 */
@PostMapping("/page")
@SaCheckPermission("${permissions.list}")
@Throws(AuthorizeException::class)
fun page(@RequestBody query: PageQuery<$spec>): Page<$listView> =
    sqlClient.createQuery(${name}::class) {
        where(query.spec)
        select(table.fetch(${listView}::class))
    }.fetchPage(query.pageIndex, query.pageSize)
                        """.trimIndent()
                    )
                }

                line()
                block(
                    """
/**
 * 根据提供的查询参数列出${comment}选项。
 *
 * @param spec 查询参数。
 * @return ${comment}列表数据。
 */
@PostMapping("/list/options")
@SaCheckPermission("${permissions.select}")
@Throws(AuthorizeException::class)
fun listOptions(@RequestBody spec: $spec): List<$optionView> =
    sqlClient.executeQuery(${name}::class) {
        where(spec)
        select(table.fetch(${optionView}::class))
    }
                    """.trimIndent()
                )

                if (entity.canAdd) {
                    line()
                    block(
                        """
/**
 * 插入新的${comment}。
 *
 * @param input ${comment}插入输入对象。
 * @return 插入的${comment}的ID。
 */
@PostMapping
@SaCheckPermission("${permissions.insert}")
@Throws(AuthorizeException::class)
@Transactional
fun insert(@RequestBody input: $insertInput): $idType =
    sqlClient.insert(input).modifiedEntity.$idName
                        """.trimIndent()
                    )
                }

                if (entity.canEdit) {
                    line()
                    block(
                        """
/**
 * 根据ID获取${comment}的更新回填信息。
 *
 * @param id ${comment}的ID。
 * @return ${comment}的更新回填信息。
 */
@GetMapping("/{id}/forUpdate")
@SaCheckPermission("${permissions.update}")
@Throws(AuthorizeException::class)
fun getForUpdate(@PathVariable id: $idType): $updateFillView? =
    sqlClient.findById(${updateFillView}::class, id)

/**
 * 更新${comment}。
 *
 * @param input ${comment}更新输入对象。
 * @return 更新的${comment}的ID。
 */
@PutMapping
@SaCheckPermission("${permissions.update}")
@Throws(AuthorizeException::class)
@Transactional
fun update(@RequestBody input: $updateInput): $idType =
    sqlClient.update(input, AssociatedSaveMode.REPLACE).modifiedEntity.$idName
                        """.trimIndent()
                    )
                }

                if (entity.canDelete) {
                    line()
                    block(
                        """
/**
 * 删除指定ID的${comment}。
 *
 * @param ids 要删除的${comment}ID列表。
 * @return 删除的${comment}的行数。
 */
@DeleteMapping
@SaCheckPermission("${permissions.delete}")
@Throws(AuthorizeException::class)
@Transactional
fun delete(@RequestParam ids: List<$idType>): Int =
    sqlClient.deleteByIds(${name}::class, ids).affectedRowCount(${name}::class)
                        """.trimIndent()
                    )
                }

                existValidItemWithNames.forEach { (name, validItem) ->
                    line()
                    block(
                        """
/**
 * 根据${validItem.properties.joinToString(", ") { it.comment }}校验${comment}是否存在。
 *
 * @param spec ${comment}校验规格对象。
 * @return ${comment}是否存在。
 */
@PostMapping("/${validItem.functionName}")
@SaCheckPermission("${permissions.list}")
@Throws(AuthorizeException::class)
fun ${validItem.functionName}(@RequestBody spec: $name): Boolean =
    sqlClient.createQuery(${name}::class) {
        where(spec)
        select(table)
    }.exists()
                        """.trimIndent()
                    )
                }
            }
            line("}")
        }
            .trimBlankLine()
            .clearBlankLine()
    }
}