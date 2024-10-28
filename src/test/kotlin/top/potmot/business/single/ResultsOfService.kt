package top.potmot.business.single

const val kotlinResult = """
(com/example/service/ConditionMatchService.kt, package com.example.service

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
import com.example.entity.ConditionMatch
import com.example.entity.dto.ConditionMatchListView
import com.example.entity.dto.ConditionMatchDetailView
import com.example.entity.dto.ConditionMatchInsertInput
import com.example.entity.dto.ConditionMatchUpdateInput
import com.example.entity.dto.ConditionMatchSpec
import com.example.entity.query.PageQuery
import com.example.exception.AuthorizeException
import com.example.utils.sqlClient.query
import com.example.utils.sqlClient.queryPage

@RestController
@RequestMapping("/conditionMatch")
class ConditionMatchService(
    @Autowired
    private val sqlClient: KSqlClient,
) {
    /**
     * 根据ID获取条件匹配。
     *
     * @param id 条件匹配的ID。
     * @return 条件匹配的详细信息。
     */
    @GetMapping("/{id}")
    @SaCheckPermission("conditionMatch:get")
    @Throws(AuthorizeException::class)
    fun get(@PathVariable id: Int) = 
        sqlClient.findById(ConditionMatchDetailView::class, id)
    
    /**
     * 根据提供的查询参数列出条件匹配。
     *
     * @param spec 查询参数。
     * @return 条件匹配列表数据。
     */
    @PostMapping("/list")
    @SaCheckPermission("conditionMatch:list")
    @Throws(AuthorizeException::class)
    fun list(@RequestBody spec: ConditionMatchSpec) = 
        sqlClient.query(ConditionMatchListView::class, spec)

    /**
     * 根据提供的查询参数分页查询条件匹配。
     *
     * @param query 分页查询参数。
     * @return 条件匹配分页数据。
     */
    @PostMapping("/page")
    @SaCheckPermission("conditionMatch:list")
    @Throws(AuthorizeException::class)
    fun page(@RequestBody query: PageQuery<ConditionMatchSpec>) = 
        sqlClient.queryPage(ConditionMatchListView::class, query)

    /**
     * 插入新的条件匹配。
     *
     * @param input 条件匹配插入输入对象。
     * @return 插入的条件匹配的ID。
     */
    @PostMapping
    @SaCheckPermission("conditionMatch:insert")
    @Transactional
    @Throws(AuthorizeException::class)
    fun insert(@RequestBody input: ConditionMatchInsertInput) = 
        sqlClient.insert(input).modifiedEntity.id

    /**
     * 更新条件匹配。
     *
     * @param input 条件匹配更新输入对象。
     * @return 更新的条件匹配的ID。
     */
    @PutMapping
    @SaCheckPermission("conditionMatch:update")
    @Transactional
    @Throws(AuthorizeException::class)
    fun update(@RequestBody input: ConditionMatchUpdateInput) = 
        sqlClient.update(input, AssociatedSaveMode.REPLACE).modifiedEntity.id

    /**
     * 删除指定ID的条件匹配。
     *
     * @param ids 要删除的条件匹配ID列表。
     * @return 删除的条件匹配的行数。
     */
    @DeleteMapping
    @SaCheckPermission("conditionMatch:delete")
    @Transactional
    @Throws(AuthorizeException::class)
    fun delete(@RequestParam ids: List<Int>) = 
        sqlClient.deleteByIds(ConditionMatch::class, ids).affectedRowCount(ConditionMatch::class)
})
"""

const val javaResult = """
(com/example/service/ConditionMatchService.java, package com.example.service;

import cn.dev33.satoken.annotation.SaCheckPermission;
import org.babyfish.jimmer.Page;
import org.babyfish.jimmer.sql.JSqlClient;
import org.babyfish.jimmer.sql.ast.mutation.AssociatedSaveMode;
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
import com.example.entity.ConditionMatch;
import com.example.entity.Tables;
import com.example.entity.dto.ConditionMatchListView;
import com.example.entity.dto.ConditionMatchDetailView;
import com.example.entity.dto.ConditionMatchInsertInput;
import com.example.entity.dto.ConditionMatchUpdateInput;
import com.example.entity.dto.ConditionMatchSpec;
import com.example.entity.query.PageQuery;
import com.example.exception.AuthorizeException;
import org.jetbrains.annotations.NotNull;
import jakarta.annotation.Nullable;
import java.util.List;

@RestController
@RequestMapping("/conditionMatch")
public class ConditionMatchService implements Tables {
    private final JSqlClient sqlClient;

    public ConditionMatchService(JSqlClient sqlClient) {
        this.sqlClient = sqlClient;
    }

    /**
     * 根据ID获取条件匹配。
     *
     * @param id 条件匹配的ID。
     * @return 条件匹配的详细信息。
     */
    @GetMapping("/{id}")
    @SaCheckPermission("conditionMatch:get")
    @Nullable
    public ConditionMatchDetailView get(@PathVariable int id) throws AuthorizeException { 
        return sqlClient.findById(ConditionMatchDetailView.class, id);
    }
    
    /**
     * 根据提供的查询参数列出条件匹配。
     *
     * @param spec 查询参数。
     * @return 条件匹配列表数据。
     */
    @PostMapping("/list")
    @SaCheckPermission("conditionMatch:list")
    @NotNull
    public List<@NotNull ConditionMatchListView> list(@RequestBody @NotNull ConditionMatchSpec spec) throws AuthorizeException {
        return sqlClient.createQuery(CONDITION_MATCH_TABLE)
                .where(spec)
                .select(CONDITION_MATCH_TABLE.fetch(ConditionMatchListView.class))
                .execute();
    }
    
    /**
     * 根据提供的查询参数列出条件匹配。
     *
     * @param query 分页查询参数。
     * @return 条件匹配分页数据。
     */
    @PostMapping("/page")
    @SaCheckPermission("conditionMatch:list")
    @NotNull
    public Page<@NotNull ConditionMatchListView> page(@RequestBody @NotNull PageQuery<ConditionMatchSpec> query) throws AuthorizeException {
        return sqlClient.createQuery(CONDITION_MATCH_TABLE)
                .where(query.getSpec())
                .select(CONDITION_MATCH_TABLE.fetch(ConditionMatchListView.class))
                .fetchPage(query.getPageIndex() - 1, query.getPageSize());
    }

    /**
     * 插入新的条件匹配。
     *
     * @param input 条件匹配插入输入对象。
     * @return 插入的条件匹配的ID。
     */
    @PostMapping
    @SaCheckPermission("conditionMatch:insert")
    @Transactional
    public int insert(@RequestBody @NotNull ConditionMatchInsertInput input) throws AuthorizeException {
        return sqlClient.insert(input).getModifiedEntity().id();
    }

    /**
     * 更新条件匹配。
     *
     * @param input 条件匹配更新输入对象。
     * @return 更新的条件匹配的ID。
     */
    @PutMapping
    @SaCheckPermission("conditionMatch:update")
    @Transactional
    public int update(@RequestBody @NotNull ConditionMatchUpdateInput input) throws AuthorizeException {
        return sqlClient.update(input, AssociatedSaveMode.REPLACE).getModifiedEntity().id();
    }

    /**
     * 删除指定ID的条件匹配。
     *
     * @param ids 要删除的条件匹配ID列表。
     * @return 删除的条件匹配的行数。
     */
    @DeleteMapping
    @SaCheckPermission("conditionMatch:delete")
    @Transactional
    public int delete(@RequestParam @NotNull List<Integer> ids) throws AuthorizeException {
        return sqlClient.deleteByIds(ConditionMatch.class, ids).getAffectedRowCount(ConditionMatch.class);
    }
})
"""