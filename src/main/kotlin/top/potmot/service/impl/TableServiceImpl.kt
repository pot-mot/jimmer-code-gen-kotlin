package top.potmot.service.impl

import org.babyfish.jimmer.sql.kt.ast.expression.gt
import org.babyfish.jimmer.sql.kt.ast.expression.ilike
import org.babyfish.jimmer.sql.kt.ast.expression.lt
import org.babyfish.jimmer.sql.kt.ast.expression.valueIn
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import top.potmot.dao.GenTableGroupRepository
import top.potmot.model.GenTable
import top.potmot.model.columnName
import top.potmot.model.columns
import top.potmot.model.createdTime
import top.potmot.model.dto.GenColumnCommonView
import top.potmot.model.dto.GenTableColumnsView
import top.potmot.model.dto.GenTableGroupTreeView
import top.potmot.model.groupId
import top.potmot.model.id
import top.potmot.model.query.ColumnQuery
import top.potmot.model.query.TableQuery
import top.potmot.model.schemaId
import top.potmot.model.tableComment
import top.potmot.model.tableName
import top.potmot.service.TableService

@Service
class TableServiceImpl(
    @Autowired val genTableGroupRepository: GenTableGroupRepository
): TableService {
    @Transactional
    override fun moveTables(ids: List<Long>, groupId: Long): Int {
        TODO("Not yet implemented")
    }

    override fun queryTables(query: TableQuery): List<GenTableColumnsView> {
        return genTableGroupRepository.sql.createQuery(GenTable::class) {
            query.groupIds?.takeIf { it.isNotEmpty() }?.let {
                where(table.groupId valueIn it)
            }
            query.schemaIds?.takeIf { it.isNotEmpty() }?.let {
                where(table.schemaId valueIn it)
            }
            query.columnName?.let {
                where(table.asTableEx().columns.columnName ilike it)
            }
            query.keywords?.takeIf { it.isNotEmpty() }?.let {
                query.keywords.forEach {
                    where(table.tableName ilike it)
                    where(table.tableComment ilike it)
                }
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

            select(table.fetch(GenTableColumnsView::class))
        }.execute()
    }

    override fun queryColumns(query: ColumnQuery): List<GenColumnCommonView> {
        TODO("Not yet implemented")
    }

    @Transactional
    override fun deleteTables(ids: List<Long>): Int {
        TODO("Not yet implemented")
    }


    override fun getTableTrees(groupIds: List<Long>?): List<GenTableGroupTreeView> {
        if (groupIds == null) {
            return genTableGroupRepository.findAll(GenTableGroupTreeView.METADATA.fetcher).map {
                GenTableGroupTreeView(it)
            }
        }
        return genTableGroupRepository.findByIds(ids = groupIds, GenTableGroupTreeView.METADATA.fetcher).map {
            GenTableGroupTreeView(it)
        }
    }
}
