package top.potmot.core.business.service.generate.impl.java

import kotlin.jvm.Throws
import top.potmot.core.business.service.generate.ServiceGenerator
import top.potmot.core.business.utils.dtoNames
import top.potmot.core.business.utils.packages
import top.potmot.core.business.utils.permissionPrefix
import top.potmot.core.business.utils.requestPath
import top.potmot.core.business.utils.serviceName
import top.potmot.core.business.utils.typeStrToJavaType
import top.potmot.entity.dto.GenEntityBusinessView
import top.potmot.error.GenerateException
import top.potmot.utils.string.entityNameToTableName

object JavaServiceGenerator : ServiceGenerator() {
    override fun getFileSuffix() = ".java"

    @Throws(GenerateException::class)
    override fun stringifyService(
        entity: GenEntityBusinessView,
    ): String {
        val serviceName = entity.serviceName

        val (_, servicePackage,  _, exceptionPackage, dtoPackage) = entity.packages
        val (_, listView, detailView, insertInput, updateInput, spec) = entity.dtoNames

        val table = entityNameToTableName(entity.name) + "_TABLE"

        val idProperty = entity.idProperty
            ?: throw GenerateException.idPropertyNotFound("entityName: ${entity.name}")
        val idName = idProperty.name
        val idType = typeStrToJavaType(idProperty.type, idProperty.typeNotNull)

        return """
package ${servicePackage};

import cn.dev33.satoken.annotation.SaCheckPermission;
import org.babyfish.jimmer.View;
import org.babyfish.jimmer.Page;
import org.babyfish.jimmer.sql.kt.KSqlClient;
import org.babyfish.jimmer.sql.ast.mutation.AssociatedSaveMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ${entity.packagePath}.${entity.name};
import ${entity.packagePath}.Tables;
import ${dtoPackage}.${listView};
import ${dtoPackage}.${detailView};
import ${dtoPackage}.${insertInput};
import ${dtoPackage}.${updateInput};
import ${dtoPackage}.${spec};
import ${entity.packagePath}.query.PageQuery;
import ${exceptionPackage}.AuthorizeException;
import org.jetbrains.annotations.NotNull;
import jakarta.annotation.Nullable;
import java.util.List;

@RestController
@RequestMapping("/${entity.requestPath}")
class $serviceName implements Tables {
    private final JSqlClient sqlClient;

    public $serviceName(JSqlClient sqlClient) {
        this.sqlClient = sqlClient;
    }

    /**
     * 根据ID获取${entity.comment}。
     *
     * @param id ${entity.comment}的ID。
     * @return ${entity.comment}的详细信息。
     */
    @GetMapping("/{id}")
    @SaCheckPermission("${entity.permissionPrefix}:get")
    @Nullable
    public $detailView get(@PathVariable $idType id) throws AuthorizeException { 
        return sqlClient.findById(${detailView}.class, id);
    }
    
    /**
     * 根据提供的查询参数列出${entity.comment}。
     *
     * @param spec 查询参数。
     * @return ${entity.comment}列表数据。
     */
    @PostMapping("/list")
    @SaCheckPermission("${entity.permissionPrefix}:list")
    @NotNull
    public List<@NotNull ${listView}> list(@RequestBody @NotNull $spec spec) throws AuthorizeException {
        return sqlClient.createQuery(${table})
                .where(spec)
                .select(${table}.fetch(${listView}.class))
                .execute();
    }
    
    /**
     * 根据提供的查询参数列出${entity.comment}。
     *
     * @param query 分页查询参数。
     * @return ${entity.comment}分页数据。
     */
    @PostMapping("/page")
    @SaCheckPermission("${entity.permissionPrefix}:list")
    @NotNull
    public Page<@NotNull ${listView}> page(@RequestBody @NotNull PageQuery<${spec}> query) throws AuthorizeException {
        sqlClient.createQuery(${table})
                .where(query.getSpec())
                .select(${table}.fetch(${listView}.class))
                .fetchPage(query.getPageIndex(), query.getPageSize());
    }

    /**
     * 插入新的${entity.comment}。
     *
     * @param input ${entity.comment}插入输入对象。
     * @return 插入的${entity.comment}的ID。
     */
    @PostMapping
    @SaCheckPermission("${entity.permissionPrefix}:insert")
    @Transactional
    public $idType insert(@RequestBody @NotNull $insertInput input) throws AuthorizeException {
        return sqlClient.insert(input).modifiedEntity.${idName};
    }

    /**
     * 更新${entity.comment}。
     *
     * @param input ${entity.comment}更新输入对象。
     * @return 更新的${entity.comment}的ID。
     */
    @PutMapping
    @SaCheckPermission("${entity.permissionPrefix}:update")
    @Transactional
    public $idType update(@RequestBody @NotNull $updateInput input) throws AuthorizeException {
        return sqlClient.update(input, AssociatedSaveMode.REPLACE).modifiedEntity.${idName};
    }

    /**
     * 删除指定ID的${entity.comment}。
     *
     * @param ids 要删除的${entity.comment}ID列表。
     * @return 删除的${entity.comment}的行数。
     */
    @DeleteMapping
    @SaCheckPermission("${entity.permissionPrefix}:delete")
    @Transactional
    public int delete(@RequestParam @NotNull List<${typeStrToJavaType(idProperty.type, false)}> ids) throws AuthorizeException {
        return sqlClient.deleteByIds(${entity.name}.class, ids).affectedRowCount(${entity.name}.class);
    }
}
    """.trim()
    }
}