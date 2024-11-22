package top.potmot.core.business.service.generate.impl.java

import top.potmot.core.business.dto.generate.dtoName
import top.potmot.core.business.service.generate.ServiceGenerator
import top.potmot.core.business.utils.ExistValidItem
import top.potmot.core.business.utils.dto
import top.potmot.core.business.utils.existValidItems
import top.potmot.core.business.utils.idProperty
import top.potmot.core.business.utils.packages
import top.potmot.core.business.utils.permissionPrefix
import top.potmot.core.business.utils.requestPath
import top.potmot.core.business.utils.serviceName
import top.potmot.core.business.utils.typeStrToJavaType
import top.potmot.entity.dto.GenEntityBusinessView
import top.potmot.error.GenerateException
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
        val permissionPrefix = entity.permissionPrefix
        val tableProxy = entity.tableProxy

        val packages = entity.packages
        val (listView, detailView, insertInput, updateInput, spec, optionView) = entity.dto
        val existValidItemWithNames = entity.existValidItems.map {
            it.dtoName(entity.name) to it
        }
        val existValidDtoImports = existValidItemWithNames.joinToString("\n") {
            "import ${packages.dto}.${it.first};"
        }.let {
            if (it.isNotBlank()) "\n$it"
            else ""
        }

        val idProperty = entity.idProperty
        val idName = idProperty.name
        val idType = typeStrToJavaType(idProperty.type, idProperty.typeNotNull)

        return ("""
package ${packages.service};

import cn.dev33.satoken.annotation.SaCheckPermission;
import org.babyfish.jimmer.Page;
import org.babyfish.jimmer.sql.JSqlClient;""" +
                (if (!entity.canEdit) "" else "\nimport org.babyfish.jimmer.sql.ast.mutation.AssociatedSaveMode;") +
                (if (!entity.canAdd && !entity.canEdit && !entity.canDelete) "" else "\nimport org.springframework.transaction.annotation.Transactional;") +
                (if (!entity.canDelete) "" else "\nimport org.springframework.web.bind.annotation.DeleteMapping;") +
"""
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;""" +
                (if (!entity.canEdit) "" else "\nimport org.springframework.web.bind.annotation.PutMapping;") +
"""
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;""" +
                (if (!entity.canDelete) "" else "\nimport org.springframework.web.bind.annotation.RequestParam;") +
"""
import org.springframework.web.bind.annotation.RestController;
import ${packages.entity}.${name};
import ${packages.entity}.Tables;
import ${packages.dto}.${listView};
import ${packages.dto}.${detailView};""" +
                (if (!entity.canAdd) "" else "\nimport ${packages.dto}.${insertInput};") +
                (if (!entity.canEdit) "" else "\nimport ${packages.dto}.${updateInput};") +
                """
import ${packages.dto}.${spec};
import ${packages.dto}.${optionView};${existValidDtoImports}
import ${packages.entity}.query.PageQuery;
import ${packages.exception}.AuthorizeException;
import org.jetbrains.annotations.NotNull;
import jakarta.annotation.Nullable;
import java.util.List;

@RestController
@RequestMapping("/${entity.requestPath}")
public class $serviceName implements Tables {
    private final JSqlClient sqlClient;

    public $serviceName(JSqlClient sqlClient) {
        this.sqlClient = sqlClient;
    }

    /**
     * 根据ID获取${comment}。
     *
     * @param id ${comment}的ID。
     * @return ${comment}的详细信息。
     */
    @GetMapping("/{id}")
    @SaCheckPermission("${permissionPrefix}:get")
    @Nullable
    public $detailView get(@PathVariable $idType id) throws AuthorizeException { 
        return sqlClient.findById(${detailView}.class, id);
    }

    /**
     * 根据提供的查询参数列出${comment}。
     *
     * @param spec 查询参数。
     * @return ${comment}列表数据。
     */
    @PostMapping("/list")
    @SaCheckPermission("${permissionPrefix}:list")
    @NotNull
    public List<@NotNull ${listView}> list(@RequestBody @NotNull $spec spec) throws AuthorizeException {
        return sqlClient.createQuery(${tableProxy})
                .where(spec)
                .select(${tableProxy}.fetch(${listView}.class))
                .execute();
    }

    /**
     * 根据提供的查询参数列出${comment}。
     *
     * @param query 分页查询参数。
     * @return ${comment}分页数据。
     */
    @PostMapping("/page")
    @SaCheckPermission("${permissionPrefix}:list")
    @NotNull
    public Page<@NotNull ${listView}> page(@RequestBody @NotNull PageQuery<${spec}> query) throws AuthorizeException {
        return sqlClient.createQuery(${tableProxy})
                .where(query.getSpec())
                .select(${tableProxy}.fetch(${listView}.class))
                .fetchPage(query.getPageIndex() - 1, query.getPageSize());
    }

    /**
     * 根据提供的查询参数列出${comment}选项。
     *
     * @param spec 查询参数。
     * @return ${comment}列表数据。
     */
    @PostMapping("/list/options")
    @SaCheckPermission("${permissionPrefix}:select")
    @NotNull
    public List<@NotNull ${optionView}> listOptions(@RequestBody @NotNull $spec spec) throws AuthorizeException {
        return sqlClient.createQuery(${tableProxy})
                .where(spec)
                .select(${tableProxy}.fetch(${optionView}.class))
                .execute();
    }
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
    public $idType insert(@RequestBody @NotNull $insertInput input) throws AuthorizeException {
        return sqlClient.insert(input).getModifiedEntity().${idName}();
    }
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
    public $idType update(@RequestBody @NotNull $updateInput input) throws AuthorizeException {
        return sqlClient.update(input, AssociatedSaveMode.REPLACE).getModifiedEntity().${idName}();
    }
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
    public int delete(@RequestParam @NotNull List<${
            typeStrToJavaType(
                idProperty.type,
                false
            )
        }> ids) throws AuthorizeException {
        return sqlClient.deleteByIds(${name}.class, ids).getAffectedRowCount(${name}.class);
    }
""")).trim() + existValidItemWithNames.joinToString("") { (name, validItem) ->
            """

    /**
     * 根据${validItem.properties.joinToString(", ") { it.comment }}校验${comment}是否存在。
     *
     * @param spec ${comment}校验规格对象。
     * @return ${comment}是否存在。
     */
    @PostMapping("/${validItem.functionName}")
    @SaCheckPermission("${permissionPrefix}:list")
""" + stringifyExistValidQueryMethod(tableProxy, name, validItem)
        } + "\n}"
    }

    private fun stringifyExistValidQueryMethod(
        tableProxy: String,
        name: String,
        validItem: ExistValidItem,
    ) = """
    public boolean ${validItem.functionName}(@RequestBody @NotNull $name spec) throws AuthorizeException {
        return sqlClient.createQuery($tableProxy)
                .where(spec)
                .select($tableProxy)
                .exists();
    }
        """.trimBlankLine()
}