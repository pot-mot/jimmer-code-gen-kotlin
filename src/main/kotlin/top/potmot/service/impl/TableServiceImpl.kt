package top.potmot.service.impl

import org.babyfish.jimmer.View
import org.babyfish.jimmer.sql.kt.ast.expression.gt
import org.babyfish.jimmer.sql.kt.ast.expression.ilike
import org.babyfish.jimmer.sql.kt.ast.expression.lt
import org.babyfish.jimmer.sql.kt.ast.expression.valueIn
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import top.potmot.dao.GenTableGroupRepository
import top.potmot.dao.GenTableRepository
import top.potmot.model.GenColumn
import top.potmot.model.GenTable
import top.potmot.model.name
import top.potmot.model.columns
import top.potmot.model.createdTime
import top.potmot.model.dto.GenColumnCommonView
import top.potmot.model.dto.GenTableCommonView
import top.potmot.model.dto.GenTableGroupTreeView
import top.potmot.model.groupId
import top.potmot.model.id
import top.potmot.model.query.ColumnQuery
import top.potmot.model.query.TableQuery
import top.potmot.model.schemaId
import top.potmot.model.comment
import top.potmot.model.name
import top.potmot.service.TableService
import kotlin.reflect.KClass

@Service
class TableServiceImpl(
    @Autowired val tableRepository: GenTableRepository,
    @Autowired val groupRepository: GenTableGroupRepository
): TableService {
    @Transactional
    override fun moveTables(ids: List<Long>, groupId: Long): Int {
        TODO("Not yet implemented")
    }

    override fun <T : View<GenTable>> queryTables(query: TableQuery, viewCLass: KClass<T>): List<T> {
        return tableRepository.sql.createQuery(GenTable::class) {
            query.keywords?.takeIf { it.isNotEmpty() }?.let {
                query.keywords.forEach {
                    where(table.name ilike it)
                    where(table.comment ilike it)
                }
            }
            query.groupIds?.takeIf { it.isNotEmpty() }?.let {
                where(table.groupId valueIn it)
            }
            query.schemaIds?.takeIf { it.isNotEmpty() }?.let {
                where(table.schemaId valueIn it)
            }
            query.name?.let {
                where(table.asTableEx().columns.name ilike it)
            }

            query.ids?.takeIf { it.isNotEmpty() }?.let {
                where(table.id valueIn it)
            }
            query.createdTime?.let {
                where(table.createdTime gt it.start)
                where(table.createdTime lt it.end)
            }
            query.modifiedTime?.let {
                where(table.createdTime gt it.start)
                where(table.createdTime lt it.end)
            }

            select(table.fetch(viewCLass))
        }.execute()
    }

    override fun <T : View<GenColumn>> queryColumns(query: ColumnQuery, viewCLass: KClass<T>): List<T> {
        TODO("Not yet implemented")
    }

    @Transactional
    override fun deleteTables(ids: List<Long>): Int {
        TODO("Not yet implemented")
    }

    override fun getTableTrees(groupIds: List<Long>?): List<GenTableGroupTreeView> {
        if (groupIds == null) {
            return groupRepository.findAll(GenTableGroupTreeView.METADATA.fetcher).map {
                GenTableGroupTreeView(it)
            }
        }
        return groupRepository.findByIds(ids = groupIds, GenTableGroupTreeView.METADATA.fetcher).map {
            GenTableGroupTreeView(it)
        }
    }
}
