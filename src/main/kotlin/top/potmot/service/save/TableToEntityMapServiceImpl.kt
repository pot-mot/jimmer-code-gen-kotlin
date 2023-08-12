package top.potmot.service.save

import org.babyfish.jimmer.ImmutableObjects
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import top.potmot.dao.GenEntityRepository
import top.potmot.dao.GenTableRepository
import top.potmot.dao.GenTypeMappingRepository
import top.potmot.model.GenEntity
import top.potmot.model.GenTable
import top.potmot.util.convert.tableToEntity
import top.potmot.util.extension.toOptionalList
import java.util.*

@Service
class TableToEntityMapServiceImpl(
    @Autowired val genEntityRepository: GenEntityRepository,
    @Autowired val genTableRepository: GenTableRepository,
    @Autowired val genTypeMappingRepository: GenTypeMappingRepository
) {
    @Transactional
    fun keepTableExist(table: GenTable): GenTable {
        return if (ImmutableObjects.isLoaded(table, "id")) {
            table
        } else {
            genTableRepository.save(table)
        }
    }

    @Transactional
    fun mapEntity(table: GenTable): GenEntity {
        val typeMappings = genTypeMappingRepository.findAll()
        val entity = tableToEntity(keepTableExist(table), typeMappings)
        return genEntityRepository.save(entity)
    }

    @Transactional
    fun mapEntityById(tableId: Long): Optional<GenEntity> {
        val table = genTableRepository.findById(tableId)
        return if (table.isPresent) {
            Optional.of(mapEntity(table.get()))
        } else {
            Optional.empty<GenEntity>()
        }
    }

    @Transactional
    fun mapEntities(tables: Iterable<GenTable>): List<GenEntity> {
        val typeMappings = genTypeMappingRepository.findAll()
        return tables.map {
            val entity = tableToEntity(keepTableExist(it), typeMappings)
            genEntityRepository.save(entity)
        }
    }

    @Transactional
    fun mapEntitiesByIds(tableIds: Iterable<Long>): List<Optional<GenEntity>> {
        return mapEntities(genTableRepository.findByIds(tableIds)).toOptionalList(tableIds)
    }

}
