package top.potmot.core.business.service.generate.impl.java

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
import top.potmot.core.business.utils.typeStrToJavaType
import top.potmot.core.business.utils.upperName
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Vue3ElementPlusViewGenerator.childrenProperty
import top.potmot.entity.dto.GenEntityBusinessView
import top.potmot.error.GenerateException
import top.potmot.utils.string.buildScopeString
import top.potmot.utils.string.clearBlankLine
import top.potmot.utils.string.entityNameToTableName
import top.potmot.utils.string.trimBlankLine

object JavaServiceGenerator : ServiceGenerator() {
    override fun getFileSuffix() = ".java"

    private val GenEntityBusinessView.tableProxy
        get() = entityNameToTableName(name) + "_TABLE"

    @Throws(GenerateException::class)
    override fun stringifyService(
        entity: GenEntityBusinessView,
    ): String {
        val name = entity.name
        val comment = entity.comment

        val serviceName = entity.serviceName
        val tableProxy = entity.tableProxy

        val packages = entity.packages
        val permissions = entity.permissions
        val (listView, treeView, detailView, insertInput, updateFillView, updateInput, spec, optionView) = entity.dto
        val existValidItemWithNames = entity.existValidItems.map {
            it.dtoName to it
        }

        val idProperty = entity.idProperty
        val idName = idProperty.name
        val idType = typeStrToJavaType(idProperty.type, idProperty.typeNotNull)

        val isTreeEntity = entity.isTreeEntity()
        val parentIdProperty by lazy {
            entity.parentIdProperty
        }
        val childrenProperty by lazy {
            entity.childrenProperty
        }

        return buildScopeString {
            line("package ${packages.service};")
            line()

            val imports = mutableSetOf<String>()
            imports += listOf(
                "cn.dev33.satoken.annotation.SaCheckPermission",
                "org.babyfish.jimmer.Page",
                "org.babyfish.jimmer.sql.JSqlClient",
                "org.springframework.web.bind.annotation.GetMapping",
                "org.springframework.web.bind.annotation.PathVariable",
                "org.springframework.web.bind.annotation.PostMapping",
                "org.springframework.web.bind.annotation.RequestBody",
                "org.springframework.web.bind.annotation.RequestMapping",
                "org.springframework.web.bind.annotation.RestController",
                "${packages.entity}.${name}",
                "${packages.entity}.Tables",
                "${packages.dto}.${listView}",
                "${packages.dto}.${detailView}",
                "${packages.dto}.${spec}",
                "${packages.dto}.${optionView}",
                "${packages.entity}.query.PageQuery",
                "${packages.exception}.AuthorizeException",
                "org.jetbrains.annotations.NotNull",
                "jakarta.annotation.Nullable",
                "java.util.List"
            )
            imports += existValidItemWithNames.map {
                "${packages.dto}.${it.first}"
            }

            if (isTreeEntity) {
                imports += listOf(
                    "${packages.dto}.${treeView}",
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

            lines(imports.sorted()) { "import $it;" }

            line()
            lines(
                "@RestController",
                "@RequestMapping(\"/${entity.requestPath}\")",
                "public class $serviceName implements Tables {"
            )
            scope {
                line("private final JSqlClient sqlClient;")
                line()
                line("public $serviceName(JSqlClient sqlClient) {")
                scope { line("this.sqlClient = sqlClient;") }
                line("}")

                line()
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
@Nullable
public $detailView get(@PathVariable $idType id) throws AuthorizeException { 
    return sqlClient.findById(${detailView}.class, id);
}
                    """.trimIndent()
                )

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
@NotNull
public List<@NotNull ${listView}> list(@RequestBody @NotNull $spec spec) throws AuthorizeException {
    return sqlClient.createQuery(${tableProxy})
            .where(spec)
            .select(${tableProxy}.fetch(${listView}.class))
            .execute();
}
                    """.trimIndent()
                )

                line()
                block(
                    """
/**
 * 根据提供的查询参数列出${comment}。
 *
 * @param query 分页查询参数。
 * @return ${comment}分页数据。
 */
@PostMapping("/page")
@SaCheckPermission("${permissions.list}")
@NotNull
public Page<@NotNull ${listView}> page(@RequestBody @NotNull PageQuery<${spec}> query) throws AuthorizeException {
    return sqlClient.createQuery(${tableProxy})
            .where(query.getSpec())
            .select(${tableProxy}.fetch(${listView}.class))
            .fetchPage(query.getPageIndex(), query.getPageSize());
}
                    """.trimIndent()
                )

                if (isTreeEntity) {
                    line()
                    block(
                        """
@NotNull
private List<@NotNull ${treeView}> buildTree(
        @NotNull List<@NotNull ${treeView}> list
) {
    Map<$idType, ${treeView}> map = new HashMap<>();
    for (${treeView} node : list) {
        map.put(node.${idName}, node);
    }

    List<@NotNull ${treeView}> roots = new ArrayList<>();

    for (${treeView} node : list) {
        $treeView parent = map.get(node.${parentIdProperty.name});
        if (parent == null)
            roots.add(node);
        } else {
            if (parent.${childrenProperty.name} == null) {
                parent.set${childrenProperty.upperName}(new ArrayList<>());
            }
            parent.${childrenProperty.name}.add(node);
        }
    }

    return roots;
}
                        """.trimIndent()
                    )

                    line()
                    block(
                        """
/**
 * 根据提供的查询参数列出树形的${comment}。
 *
 * @param spec 查询参数。
 * @return ${comment}列表数据。
 */
@PostMapping("/tree")
@SaCheckPermission("${permissions.list}")
@NotNull
public List<@NotNull ${treeView}> tree(
        @RequestBody @NotNull $spec spec,
) throws AuthorizeException {
    List<@NotNull ${treeView}> list = sqlClient.createQuery($tableProxy)
            .where(spec)
            .select($tableProxy.fetch(${treeView}.METADATA.getFetcher().remove("${childrenProperty.name}")))
            .execute()
            .stream().map(${treeView}::new).toList();

    return buildTree(list);
}
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
@NotNull
public List<@NotNull ${optionView}> listOptions(@RequestBody @NotNull $spec spec) throws AuthorizeException {
    return sqlClient.createQuery(${tableProxy})
            .where(spec)
            .select(${tableProxy}.fetch(${optionView}.class))
            .execute();
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
@Transactional
public $idType insert(@RequestBody @NotNull $insertInput input) throws AuthorizeException {
    return sqlClient.insert(input).getModifiedEntity().${idName}();
}
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
@Nullable
public $updateFillView getForUpdate(@PathVariable $idType id) throws AuthorizeException {
    return sqlClient.findById(${updateFillView}.class, id);
}

/**
 * 更新${comment}。
 *
 * @param input ${comment}更新输入对象。
 * @return 更新的${comment}的ID。
 */
@PutMapping
@SaCheckPermission("${permissions.update}")
@Transactional
public $idType update(@RequestBody @NotNull $updateInput input) throws AuthorizeException {
    return sqlClient.update(input, AssociatedSaveMode.REPLACE).getModifiedEntity().${idName}();
}
                        """.trimIndent()
                    )
                }

                if (entity.canDelete) {
                    line()
                    val nullableIdType = typeStrToJavaType(idProperty.type, false)
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
@Transactional
public int delete(@RequestParam @NotNull List<@NotNull $nullableIdType> ids) throws AuthorizeException {
    return sqlClient.deleteByIds(${name}.class, ids).getAffectedRowCount(${name}.class);
}
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
public boolean ${validItem.functionName}(@RequestBody @NotNull $name spec) throws AuthorizeException {
    return sqlClient.createQuery($tableProxy)
            .where(spec)
            .select($tableProxy)
            .exists();
}
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