package top.potmot.core.business.service.generate.impl.java

import top.potmot.core.business.meta.EntityBusiness
import top.potmot.core.business.service.generate.ServiceGenerator
import top.potmot.core.business.view.generate.builder.rules.existValidItems
import top.potmot.core.business.utils.type.typeStrToJavaType
import top.potmot.core.business.utils.mark.upperName
import top.potmot.error.GenerateException
import top.potmot.utils.string.buildScopeString
import top.potmot.utils.string.clearBlankLine
import top.potmot.utils.string.entityNameToTableName
import top.potmot.utils.string.trimBlankLine

object JavaServiceGenerator : ServiceGenerator() {
    override fun getFileSuffix() = ".java"

    private val EntityBusiness.tableProxy
        get() = entityNameToTableName(name) + "_TABLE"

    @Throws(GenerateException::class)
    override fun stringifyService(
        entity: EntityBusiness,
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
        val idNullableType = typeStrToJavaType(idProperty.type, false)

        val isTreeEntity = entity.isTree
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
                    "${packages.entity}.${name}Draft",
                    "java.util.function.Function",
                    "java.util.stream.Collectors",
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
    Map<$idNullableType, ${treeView}> map = new HashMap<>();
    for (${treeView} node : list) {
        map.put(node.get${idProperty.upperName}(), node);
    }

    List<@NotNull ${treeView}> roots = new ArrayList<>();

    for (${treeView} node : list) {
        $treeView parent = map.get(node.get${parentIdProperty.upperName}());
        if (parent == null) {
            roots.add(node);
        } else {
            if (parent.get${childrenProperty.upperName}() == null) {
                parent.set${childrenProperty.upperName}(new ArrayList<>());
            }
            parent.get${childrenProperty.upperName}().add(node);
        }
    }

    return roots;
}

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
        @RequestBody @NotNull $spec spec
) throws AuthorizeException {
    List<@NotNull ${treeView}> list = sqlClient.createQuery($tableProxy)
            .where(spec)
            .select($tableProxy.fetch(${treeView}.METADATA.getFetcher().remove("${childrenProperty.name}")))
            .execute()
            .stream().map(${treeView}::new).toList();

    return buildTree(list);
}

private List<${name}> buildIdTree(
        List<Tuple2<$idNullableType, $idNullableType>> idParentIdList
) {
    Map<$idNullableType, Tuple2<$idNullableType, $idNullableType>> idMap = new HashMap<>();
    Map<$idNullableType, List<Tuple2<$idNullableType, $idNullableType>>> parentMap = new HashMap<>();

    for (Tuple2<$idNullableType, $idNullableType> tuple : idParentIdList) {
        idMap.put(tuple.get_1(), tuple);
        parentMap.computeIfAbsent(tuple.get_2(), k -> new ArrayList<>()).add(tuple);
    }

    Function<Tuple2<$idNullableType, $idNullableType>, ${name}> buildSubTree = new Function<>() {
        @Override
        public $name apply(Tuple2<$idNullableType, $idNullableType> node) {
            List<$name> children = parentMap.getOrDefault(node.get_1(), Collections.emptyList())
                    .stream()
                    .map(this)
                    .collect(Collectors.toList());
            return ${name}Draft.${'$'}.produce(draft -> draft
                    .setId(node.get_1())
                    .set${childrenProperty.upperName}(children)
            );
        }
    };

    List<${name}> roots = idParentIdList.stream()
            .filter(tuple -> tuple.get_2() == null || !idMap.containsKey(tuple.get_2()))
            .map(buildSubTree)
            .collect(Collectors.toList());

    return roots;
}

private List<$idNullableType> flatIds(List<${name}> list) {
    List<$idNullableType> result = new ArrayList<>();
    for (${name} entity : list) {
        result.add(entity.${idName}());
        result.addAll(flatIds(entity.${childrenProperty.name}()));
    }
    return result;
}

/**
 * 根据提供的查询参数分页查询树形的${comment}。
 *
 * @param query 分页查询参数。
 * @return ${comment}树状分页数据。
 */
@PostMapping("/tree/page")
@SaCheckPermission("${permissions.list}")
@NotNull
public Page<@NotNull ${treeView}> treePage(
        @RequestBody @NotNull PageQuery<${spec}> query
) throws AuthorizeException {
    List<Tuple2<$idNullableType, $idNullableType>> idParentIdList = sqlClient.createQuery($tableProxy)
            .where(query.getSpec())
            .select($tableProxy.$idName(), $tableProxy.${parentIdProperty.name}())
            .execute();

    List<${name}> idTreeList = buildIdTree(idParentIdList);

    int startIndex = Math.min(query.getPageIndex() * query.getPageSize(), idTreeList.size());
    int endIndex = Math.min((query.getPageIndex() + 1) * query.getPageSize(), idTreeList.size());

    List<$idNullableType> idList = flatIds(idTreeList.subList(startIndex, endIndex));

    List<${treeView}> list = sqlClient.createQuery($tableProxy)
            .where($tableProxy.${idName}().in(idList))
            .select($tableProxy.fetch(${treeView}.METADATA.getFetcher().remove("${childrenProperty.name}")))
            .execute()
            .stream()
            .map(${treeView}::new)
            .collect(Collectors.toList());

    List<@NotNull ${treeView}> treeList = buildTree(list);

    return new Page<>(treeList, idTreeList.size(), idTreeList.size() / query.getPageSize());
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