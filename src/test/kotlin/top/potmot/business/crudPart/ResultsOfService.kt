package top.potmot.business.crudPart

const val addOnlyKotlinResult = """
(EntityPackage/EntityService.kt, package EntityPackage

import cn.dev33.satoken.annotation.SaCheckPermission
import org.babyfish.jimmer.View
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import EntityPackage.Entity
import EntityPackage.dto.EntityListView
import EntityPackage.dto.EntityDetailView
import EntityPackage.dto.EntityInsertInput
import EntityPackage.dto.EntitySpec
import EntityPackage.dto.EntityOptionView
import EntityPackage.query.PageQuery
import EntityPackage.AuthorizeException
import EntityPackage.sqlClient.query
import EntityPackage.sqlClient.queryPage

@RestController
@RequestMapping("/entity")
class EntityService(
    @Autowired
    private val sqlClient: KSqlClient,
) {
    /**
     * 根据ID获取comment。
     *
     * @param id comment的ID。
     * @return comment的详细信息。
     */
    @GetMapping("/{id}")
    @SaCheckPermission("entity:get")
    @Throws(AuthorizeException::class)
    fun get(@PathVariable id: Int) = 
        sqlClient.findById(EntityDetailView::class, id)

    /**
     * 根据提供的查询参数列出comment。
     *
     * @param spec 查询参数。
     * @return comment列表数据。
     */
    @PostMapping("/list")
    @SaCheckPermission("entity:list")
    @Throws(AuthorizeException::class)
    fun list(@RequestBody spec: EntitySpec) = 
        sqlClient.query(EntityListView::class, spec)

    /**
     * 根据提供的查询参数分页查询comment。
     *
     * @param query 分页查询参数。
     * @return comment分页数据。
     */
    @PostMapping("/page")
    @SaCheckPermission("entity:list")
    @Throws(AuthorizeException::class)
    fun page(@RequestBody query: PageQuery<EntitySpec>) = 
        sqlClient.queryPage(EntityListView::class, query)

    /**
     * 根据提供的查询参数列出comment选项。
     *
     * @param spec 查询参数。
     * @return comment列表数据。
     */
    @PostMapping("/list/options")
    @SaCheckPermission("entity:select")
    @Throws(AuthorizeException::class)
    fun listOptions(@RequestBody spec: EntitySpec) = 
        sqlClient.query(EntityOptionView::class, spec)

    /**
     * 插入新的comment。
     *
     * @param input comment插入输入对象。
     * @return 插入的comment的ID。
     */
    @PostMapping
    @SaCheckPermission("entity:insert")
    @Transactional
    @Throws(AuthorizeException::class)
    fun insert(@RequestBody input: EntityInsertInput) = 
        sqlClient.insert(input).modifiedEntity.id
})
"""

const val addOnlyJavaResult = """
(EntityPackage/EntityService.java, package EntityPackage;

import cn.dev33.satoken.annotation.SaCheckPermission;
import org.babyfish.jimmer.Page;
import org.babyfish.jimmer.sql.JSqlClient;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import EntityPackage.Entity;
import EntityPackage.Tables;
import EntityPackage.dto.EntityListView;
import EntityPackage.dto.EntityDetailView;
import EntityPackage.dto.EntityInsertInput;
import EntityPackage.dto.EntitySpec;
import EntityPackage.dto.EntityOptionView;
import EntityPackage.query.PageQuery;
import EntityPackage.AuthorizeException;
import org.jetbrains.annotations.NotNull;
import jakarta.annotation.Nullable;
import java.util.List;

@RestController
@RequestMapping("/entity")
public class EntityService implements Tables {
    private final JSqlClient sqlClient;

    public EntityService(JSqlClient sqlClient) {
        this.sqlClient = sqlClient;
    }

    /**
     * 根据ID获取comment。
     *
     * @param id comment的ID。
     * @return comment的详细信息。
     */
    @GetMapping("/{id}")
    @SaCheckPermission("entity:get")
    @Nullable
    public EntityDetailView get(@PathVariable int id) throws AuthorizeException { 
        return sqlClient.findById(EntityDetailView.class, id);
    }

    /**
     * 根据提供的查询参数列出comment。
     *
     * @param spec 查询参数。
     * @return comment列表数据。
     */
    @PostMapping("/list")
    @SaCheckPermission("entity:list")
    @NotNull
    public List<@NotNull EntityListView> list(@RequestBody @NotNull EntitySpec spec) throws AuthorizeException {
        return sqlClient.createQuery(ENTITY_TABLE)
                .where(spec)
                .select(ENTITY_TABLE.fetch(EntityListView.class))
                .execute();
    }

    /**
     * 根据提供的查询参数列出comment。
     *
     * @param query 分页查询参数。
     * @return comment分页数据。
     */
    @PostMapping("/page")
    @SaCheckPermission("entity:list")
    @NotNull
    public Page<@NotNull EntityListView> page(@RequestBody @NotNull PageQuery<EntitySpec> query) throws AuthorizeException {
        return sqlClient.createQuery(ENTITY_TABLE)
                .where(query.getSpec())
                .select(ENTITY_TABLE.fetch(EntityListView.class))
                .fetchPage(query.getPageIndex() - 1, query.getPageSize());
    }

    /**
     * 根据提供的查询参数列出comment选项。
     *
     * @param spec 查询参数。
     * @return comment列表数据。
     */
    @PostMapping("/list/options")
    @SaCheckPermission("entity:select")
    @NotNull
    public List<@NotNull EntityOptionView> listOptions(@RequestBody @NotNull EntitySpec spec) throws AuthorizeException {
        return sqlClient.createQuery(ENTITY_TABLE)
                .where(spec)
                .select(ENTITY_TABLE.fetch(EntityOptionView.class))
                .execute();
    }

    /**
     * 插入新的comment。
     *
     * @param input comment插入输入对象。
     * @return 插入的comment的ID。
     */
    @PostMapping
    @SaCheckPermission("entity:insert")
    @Transactional
    public int insert(@RequestBody @NotNull EntityInsertInput input) throws AuthorizeException {
        return sqlClient.insert(input).getModifiedEntity().id();
    }
})
"""

const val editOnlyKotlinResult = """
(EntityPackage/EntityService.kt, package EntityPackage

import cn.dev33.satoken.annotation.SaCheckPermission
import org.babyfish.jimmer.View
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.ast.mutation.AssociatedSaveMode
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import EntityPackage.Entity
import EntityPackage.dto.EntityListView
import EntityPackage.dto.EntityDetailView
import EntityPackage.dto.EntityUpdateInput
import EntityPackage.dto.EntitySpec
import EntityPackage.dto.EntityOptionView
import EntityPackage.query.PageQuery
import EntityPackage.AuthorizeException
import EntityPackage.sqlClient.query
import EntityPackage.sqlClient.queryPage

@RestController
@RequestMapping("/entity")
class EntityService(
    @Autowired
    private val sqlClient: KSqlClient,
) {
    /**
     * 根据ID获取comment。
     *
     * @param id comment的ID。
     * @return comment的详细信息。
     */
    @GetMapping("/{id}")
    @SaCheckPermission("entity:get")
    @Throws(AuthorizeException::class)
    fun get(@PathVariable id: Int) = 
        sqlClient.findById(EntityDetailView::class, id)

    /**
     * 根据提供的查询参数列出comment。
     *
     * @param spec 查询参数。
     * @return comment列表数据。
     */
    @PostMapping("/list")
    @SaCheckPermission("entity:list")
    @Throws(AuthorizeException::class)
    fun list(@RequestBody spec: EntitySpec) = 
        sqlClient.query(EntityListView::class, spec)

    /**
     * 根据提供的查询参数分页查询comment。
     *
     * @param query 分页查询参数。
     * @return comment分页数据。
     */
    @PostMapping("/page")
    @SaCheckPermission("entity:list")
    @Throws(AuthorizeException::class)
    fun page(@RequestBody query: PageQuery<EntitySpec>) = 
        sqlClient.queryPage(EntityListView::class, query)

    /**
     * 根据提供的查询参数列出comment选项。
     *
     * @param spec 查询参数。
     * @return comment列表数据。
     */
    @PostMapping("/list/options")
    @SaCheckPermission("entity:select")
    @Throws(AuthorizeException::class)
    fun listOptions(@RequestBody spec: EntitySpec) = 
        sqlClient.query(EntityOptionView::class, spec)

    /**
     * 更新comment。
     *
     * @param input comment更新输入对象。
     * @return 更新的comment的ID。
     */
    @PutMapping
    @SaCheckPermission("entity:update")
    @Transactional
    @Throws(AuthorizeException::class)
    fun update(@RequestBody input: EntityUpdateInput) = 
        sqlClient.update(input, AssociatedSaveMode.REPLACE).modifiedEntity.id
})
"""

const val editOnlyJavaResult = """
(EntityPackage/EntityService.java, package EntityPackage;

import cn.dev33.satoken.annotation.SaCheckPermission;
import org.babyfish.jimmer.Page;
import org.babyfish.jimmer.sql.JSqlClient;
import org.babyfish.jimmer.sql.ast.mutation.AssociatedSaveMode;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import EntityPackage.Entity;
import EntityPackage.Tables;
import EntityPackage.dto.EntityListView;
import EntityPackage.dto.EntityDetailView;
import EntityPackage.dto.EntityUpdateInput;
import EntityPackage.dto.EntitySpec;
import EntityPackage.dto.EntityOptionView;
import EntityPackage.query.PageQuery;
import EntityPackage.AuthorizeException;
import org.jetbrains.annotations.NotNull;
import jakarta.annotation.Nullable;
import java.util.List;

@RestController
@RequestMapping("/entity")
public class EntityService implements Tables {
    private final JSqlClient sqlClient;

    public EntityService(JSqlClient sqlClient) {
        this.sqlClient = sqlClient;
    }

    /**
     * 根据ID获取comment。
     *
     * @param id comment的ID。
     * @return comment的详细信息。
     */
    @GetMapping("/{id}")
    @SaCheckPermission("entity:get")
    @Nullable
    public EntityDetailView get(@PathVariable int id) throws AuthorizeException { 
        return sqlClient.findById(EntityDetailView.class, id);
    }

    /**
     * 根据提供的查询参数列出comment。
     *
     * @param spec 查询参数。
     * @return comment列表数据。
     */
    @PostMapping("/list")
    @SaCheckPermission("entity:list")
    @NotNull
    public List<@NotNull EntityListView> list(@RequestBody @NotNull EntitySpec spec) throws AuthorizeException {
        return sqlClient.createQuery(ENTITY_TABLE)
                .where(spec)
                .select(ENTITY_TABLE.fetch(EntityListView.class))
                .execute();
    }

    /**
     * 根据提供的查询参数列出comment。
     *
     * @param query 分页查询参数。
     * @return comment分页数据。
     */
    @PostMapping("/page")
    @SaCheckPermission("entity:list")
    @NotNull
    public Page<@NotNull EntityListView> page(@RequestBody @NotNull PageQuery<EntitySpec> query) throws AuthorizeException {
        return sqlClient.createQuery(ENTITY_TABLE)
                .where(query.getSpec())
                .select(ENTITY_TABLE.fetch(EntityListView.class))
                .fetchPage(query.getPageIndex() - 1, query.getPageSize());
    }

    /**
     * 根据提供的查询参数列出comment选项。
     *
     * @param spec 查询参数。
     * @return comment列表数据。
     */
    @PostMapping("/list/options")
    @SaCheckPermission("entity:select")
    @NotNull
    public List<@NotNull EntityOptionView> listOptions(@RequestBody @NotNull EntitySpec spec) throws AuthorizeException {
        return sqlClient.createQuery(ENTITY_TABLE)
                .where(spec)
                .select(ENTITY_TABLE.fetch(EntityOptionView.class))
                .execute();
    }

    /**
     * 更新comment。
     *
     * @param input comment更新输入对象。
     * @return 更新的comment的ID。
     */
    @PutMapping
    @SaCheckPermission("entity:update")
    @Transactional
    public int update(@RequestBody @NotNull EntityUpdateInput input) throws AuthorizeException {
        return sqlClient.update(input, AssociatedSaveMode.REPLACE).getModifiedEntity().id();
    }
})
"""

const val queryOnlyKotlinResult = """
(EntityPackage/EntityService.kt, package EntityPackage

import cn.dev33.satoken.annotation.SaCheckPermission
import org.babyfish.jimmer.View
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import EntityPackage.Entity
import EntityPackage.dto.EntityListView
import EntityPackage.dto.EntityDetailView
import EntityPackage.dto.EntitySpec
import EntityPackage.dto.EntityOptionView
import EntityPackage.query.PageQuery
import EntityPackage.AuthorizeException
import EntityPackage.sqlClient.query
import EntityPackage.sqlClient.queryPage

@RestController
@RequestMapping("/entity")
class EntityService(
    @Autowired
    private val sqlClient: KSqlClient,
) {
    /**
     * 根据ID获取comment。
     *
     * @param id comment的ID。
     * @return comment的详细信息。
     */
    @GetMapping("/{id}")
    @SaCheckPermission("entity:get")
    @Throws(AuthorizeException::class)
    fun get(@PathVariable id: Int) = 
        sqlClient.findById(EntityDetailView::class, id)

    /**
     * 根据提供的查询参数列出comment。
     *
     * @param spec 查询参数。
     * @return comment列表数据。
     */
    @PostMapping("/list")
    @SaCheckPermission("entity:list")
    @Throws(AuthorizeException::class)
    fun list(@RequestBody spec: EntitySpec) = 
        sqlClient.query(EntityListView::class, spec)

    /**
     * 根据提供的查询参数分页查询comment。
     *
     * @param query 分页查询参数。
     * @return comment分页数据。
     */
    @PostMapping("/page")
    @SaCheckPermission("entity:list")
    @Throws(AuthorizeException::class)
    fun page(@RequestBody query: PageQuery<EntitySpec>) = 
        sqlClient.queryPage(EntityListView::class, query)

    /**
     * 根据提供的查询参数列出comment选项。
     *
     * @param spec 查询参数。
     * @return comment列表数据。
     */
    @PostMapping("/list/options")
    @SaCheckPermission("entity:select")
    @Throws(AuthorizeException::class)
    fun listOptions(@RequestBody spec: EntitySpec) = 
        sqlClient.query(EntityOptionView::class, spec)
})
"""

const val queryOnlyJavaResult = """
(EntityPackage/EntityService.java, package EntityPackage;

import cn.dev33.satoken.annotation.SaCheckPermission;
import org.babyfish.jimmer.Page;
import org.babyfish.jimmer.sql.JSqlClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import EntityPackage.Entity;
import EntityPackage.Tables;
import EntityPackage.dto.EntityListView;
import EntityPackage.dto.EntityDetailView;
import EntityPackage.dto.EntitySpec;
import EntityPackage.dto.EntityOptionView;
import EntityPackage.query.PageQuery;
import EntityPackage.AuthorizeException;
import org.jetbrains.annotations.NotNull;
import jakarta.annotation.Nullable;
import java.util.List;

@RestController
@RequestMapping("/entity")
public class EntityService implements Tables {
    private final JSqlClient sqlClient;

    public EntityService(JSqlClient sqlClient) {
        this.sqlClient = sqlClient;
    }

    /**
     * 根据ID获取comment。
     *
     * @param id comment的ID。
     * @return comment的详细信息。
     */
    @GetMapping("/{id}")
    @SaCheckPermission("entity:get")
    @Nullable
    public EntityDetailView get(@PathVariable int id) throws AuthorizeException { 
        return sqlClient.findById(EntityDetailView.class, id);
    }

    /**
     * 根据提供的查询参数列出comment。
     *
     * @param spec 查询参数。
     * @return comment列表数据。
     */
    @PostMapping("/list")
    @SaCheckPermission("entity:list")
    @NotNull
    public List<@NotNull EntityListView> list(@RequestBody @NotNull EntitySpec spec) throws AuthorizeException {
        return sqlClient.createQuery(ENTITY_TABLE)
                .where(spec)
                .select(ENTITY_TABLE.fetch(EntityListView.class))
                .execute();
    }

    /**
     * 根据提供的查询参数列出comment。
     *
     * @param query 分页查询参数。
     * @return comment分页数据。
     */
    @PostMapping("/page")
    @SaCheckPermission("entity:list")
    @NotNull
    public Page<@NotNull EntityListView> page(@RequestBody @NotNull PageQuery<EntitySpec> query) throws AuthorizeException {
        return sqlClient.createQuery(ENTITY_TABLE)
                .where(query.getSpec())
                .select(ENTITY_TABLE.fetch(EntityListView.class))
                .fetchPage(query.getPageIndex() - 1, query.getPageSize());
    }

    /**
     * 根据提供的查询参数列出comment选项。
     *
     * @param spec 查询参数。
     * @return comment列表数据。
     */
    @PostMapping("/list/options")
    @SaCheckPermission("entity:select")
    @NotNull
    public List<@NotNull EntityOptionView> listOptions(@RequestBody @NotNull EntitySpec spec) throws AuthorizeException {
        return sqlClient.createQuery(ENTITY_TABLE)
                .where(spec)
                .select(ENTITY_TABLE.fetch(EntityOptionView.class))
                .execute();
    }
})
"""

const val deleteOnlyKotlinResult = """
(EntityPackage/EntityService.kt, package EntityPackage

import cn.dev33.satoken.annotation.SaCheckPermission
import org.babyfish.jimmer.View
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import EntityPackage.Entity
import EntityPackage.dto.EntityListView
import EntityPackage.dto.EntityDetailView
import EntityPackage.dto.EntitySpec
import EntityPackage.dto.EntityOptionView
import EntityPackage.query.PageQuery
import EntityPackage.AuthorizeException
import EntityPackage.sqlClient.query
import EntityPackage.sqlClient.queryPage

@RestController
@RequestMapping("/entity")
class EntityService(
    @Autowired
    private val sqlClient: KSqlClient,
) {
    /**
     * 根据ID获取comment。
     *
     * @param id comment的ID。
     * @return comment的详细信息。
     */
    @GetMapping("/{id}")
    @SaCheckPermission("entity:get")
    @Throws(AuthorizeException::class)
    fun get(@PathVariable id: Int) = 
        sqlClient.findById(EntityDetailView::class, id)

    /**
     * 根据提供的查询参数列出comment。
     *
     * @param spec 查询参数。
     * @return comment列表数据。
     */
    @PostMapping("/list")
    @SaCheckPermission("entity:list")
    @Throws(AuthorizeException::class)
    fun list(@RequestBody spec: EntitySpec) = 
        sqlClient.query(EntityListView::class, spec)

    /**
     * 根据提供的查询参数分页查询comment。
     *
     * @param query 分页查询参数。
     * @return comment分页数据。
     */
    @PostMapping("/page")
    @SaCheckPermission("entity:list")
    @Throws(AuthorizeException::class)
    fun page(@RequestBody query: PageQuery<EntitySpec>) = 
        sqlClient.queryPage(EntityListView::class, query)

    /**
     * 根据提供的查询参数列出comment选项。
     *
     * @param spec 查询参数。
     * @return comment列表数据。
     */
    @PostMapping("/list/options")
    @SaCheckPermission("entity:select")
    @Throws(AuthorizeException::class)
    fun listOptions(@RequestBody spec: EntitySpec) = 
        sqlClient.query(EntityOptionView::class, spec)

    /**
     * 删除指定ID的comment。
     *
     * @param ids 要删除的commentID列表。
     * @return 删除的comment的行数。
     */
    @DeleteMapping
    @SaCheckPermission("entity:delete")
    @Transactional
    @Throws(AuthorizeException::class)
    fun delete(@RequestParam ids: List<Int>) = 
        sqlClient.deleteByIds(Entity::class, ids).affectedRowCount(Entity::class)
})
"""

const val deleteOnlyJavaResult = """
(EntityPackage/EntityService.java, package EntityPackage;

import cn.dev33.satoken.annotation.SaCheckPermission;
import org.babyfish.jimmer.Page;
import org.babyfish.jimmer.sql.JSqlClient;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import EntityPackage.Entity;
import EntityPackage.Tables;
import EntityPackage.dto.EntityListView;
import EntityPackage.dto.EntityDetailView;
import EntityPackage.dto.EntitySpec;
import EntityPackage.dto.EntityOptionView;
import EntityPackage.query.PageQuery;
import EntityPackage.AuthorizeException;
import org.jetbrains.annotations.NotNull;
import jakarta.annotation.Nullable;
import java.util.List;

@RestController
@RequestMapping("/entity")
public class EntityService implements Tables {
    private final JSqlClient sqlClient;

    public EntityService(JSqlClient sqlClient) {
        this.sqlClient = sqlClient;
    }

    /**
     * 根据ID获取comment。
     *
     * @param id comment的ID。
     * @return comment的详细信息。
     */
    @GetMapping("/{id}")
    @SaCheckPermission("entity:get")
    @Nullable
    public EntityDetailView get(@PathVariable int id) throws AuthorizeException { 
        return sqlClient.findById(EntityDetailView.class, id);
    }

    /**
     * 根据提供的查询参数列出comment。
     *
     * @param spec 查询参数。
     * @return comment列表数据。
     */
    @PostMapping("/list")
    @SaCheckPermission("entity:list")
    @NotNull
    public List<@NotNull EntityListView> list(@RequestBody @NotNull EntitySpec spec) throws AuthorizeException {
        return sqlClient.createQuery(ENTITY_TABLE)
                .where(spec)
                .select(ENTITY_TABLE.fetch(EntityListView.class))
                .execute();
    }

    /**
     * 根据提供的查询参数列出comment。
     *
     * @param query 分页查询参数。
     * @return comment分页数据。
     */
    @PostMapping("/page")
    @SaCheckPermission("entity:list")
    @NotNull
    public Page<@NotNull EntityListView> page(@RequestBody @NotNull PageQuery<EntitySpec> query) throws AuthorizeException {
        return sqlClient.createQuery(ENTITY_TABLE)
                .where(query.getSpec())
                .select(ENTITY_TABLE.fetch(EntityListView.class))
                .fetchPage(query.getPageIndex() - 1, query.getPageSize());
    }

    /**
     * 根据提供的查询参数列出comment选项。
     *
     * @param spec 查询参数。
     * @return comment列表数据。
     */
    @PostMapping("/list/options")
    @SaCheckPermission("entity:select")
    @NotNull
    public List<@NotNull EntityOptionView> listOptions(@RequestBody @NotNull EntitySpec spec) throws AuthorizeException {
        return sqlClient.createQuery(ENTITY_TABLE)
                .where(spec)
                .select(ENTITY_TABLE.fetch(EntityOptionView.class))
                .execute();
    }

    /**
     * 删除指定ID的comment。
     *
     * @param ids 要删除的commentID列表。
     * @return 删除的comment的行数。
     */
    @DeleteMapping
    @SaCheckPermission("entity:delete")
    @Transactional
    public int delete(@RequestParam @NotNull List<Integer> ids) throws AuthorizeException {
        return sqlClient.deleteByIds(Entity.class, ids).getAffectedRowCount(Entity.class);
    }
})
"""