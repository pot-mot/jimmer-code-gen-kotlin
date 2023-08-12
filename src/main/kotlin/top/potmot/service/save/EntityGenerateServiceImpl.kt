package top.potmot.service.save

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import top.potmot.dao.GenTableRepository
import top.potmot.dao.GenTypeMappingRepository
import top.potmot.model.GenEntity
import top.potmot.model.GenTable
import top.potmot.util.convert.tableToEntity
import top.potmot.util.extension.toOptionalList
import java.util.*

@Service
class EntityGenerateServiceImpl(
    @Autowired val genTableRepository: GenTableRepository,
    @Autowired val genTypeMappingRepository: GenTypeMappingRepository,
) {
    fun previewEntity(table: GenTable): GenEntity {
        val typeMappings = genTypeMappingRepository.findAll()
        return tableToEntity(table, typeMappings)
    }

    fun previewEntityById(tableId: Long): Optional<GenEntity> {
        val table = genTableRepository.findById(tableId)
        return if (table.isPresent) {
            Optional.of(previewEntity(table.get()))
        } else {
            Optional.empty<GenEntity>()
        }
    }

    fun previewEntities(tables: Iterable<GenTable>): List<GenEntity> {
        val typeMappings = genTypeMappingRepository.findAll()
        return tables.map {
            tableToEntity(it, typeMappings)
        }
    }

    fun previewEntitiesByIds(tableIds: Iterable<Long>): List<Optional<GenEntity>> {
        val tables = genTableRepository.findByIds(tableIds).toOptionalList(tableIds)
        val typeMappings = genTypeMappingRepository.findAll()
        return tables.map {
            if (it.isPresent) {
                Optional.of(tableToEntity(it.get(), typeMappings))
            } else {
                Optional.empty<GenEntity>()
            }
        }
    }
}
