package top.potmot.service.save

import org.babyfish.jimmer.sql.fetcher.Fetcher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import top.potmot.dao.GenEntityRepository
import top.potmot.dao.GenTableRepository
import top.potmot.model.GenEntity
import top.potmot.model.GenTable
import top.potmot.util.extension.toOptionalList
import java.util.*

@Service
class QueryServiceImpl(
    @Autowired val genTableRepository: GenTableRepository,
    @Autowired val genEntityRepository: GenEntityRepository
) {
    fun getTable(tableId: Long, tableFetcher: Fetcher<GenTable>?): Optional<GenTable> {
        return if (tableFetcher != null) {
            genTableRepository.findById(tableId, tableFetcher)
        } else {
            genTableRepository.findById(tableId)
        }
    }

    fun getTables(page: Pageable, tableFetcher: Fetcher<GenTable>?): Page<GenTable> {
        return genTableRepository.findAll(page, tableFetcher)
    }

    fun getTables(tableIds: Iterable<Long>, tableFetcher: Fetcher<GenTable>?): List<Optional<GenTable>> {
        return genTableRepository.findByIds(tableIds, tableFetcher).toOptionalList(tableIds)
    }

    fun getEntity(entityId: Long, entityFetcher: Fetcher<GenEntity>?): Optional<GenEntity> {
        return if (entityFetcher != null) {
            genEntityRepository.findById(entityId, entityFetcher)
        } else {
            genEntityRepository.findById(entityId)
        }
    }

    fun getEntities(page: Pageable, entityFetcher: Fetcher<GenEntity>?): Page<GenEntity> {
        return genEntityRepository.findAll(page, entityFetcher)
    }

    fun getEntities(entityIds: Iterable<Long>, entityFetcher: Fetcher<GenEntity>?): List<Optional<GenEntity>> {
        return genEntityRepository.findByIds(entityIds, entityFetcher).toOptionalList(entityIds)
    }
}
