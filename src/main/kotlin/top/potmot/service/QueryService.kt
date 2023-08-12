package top.potmot.service

import org.babyfish.jimmer.sql.fetcher.Fetcher
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import top.potmot.model.GenEntity
import top.potmot.model.GenTable
import java.util.*

interface QueryService {
    fun getTable(tableId: Long, tableFetcher: Fetcher<GenTable>? = null): Optional<GenTable>

    fun getTables(page: Pageable, tableFetcher: Fetcher<GenTable>? = null): Page<GenTable>

    fun getTables(tableIds: Iterable<Long>, tableFetcher: Fetcher<GenTable>? = null): List<Optional<GenTable>>

    fun getEntity(entityId: Long, entityFetcher: Fetcher<GenEntity>? = null): Optional<GenEntity>

    fun getEntities(page: Pageable, entityFetcher: Fetcher<GenEntity>? = null): Page<GenEntity>

    fun getEntities(entityIds: Iterable<Long>, entityFetcher: Fetcher<GenEntity>? = null): List<Optional<GenEntity>>
}
