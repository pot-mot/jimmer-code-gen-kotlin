package top.potmot.service

import org.babyfish.jimmer.client.ThrowsAll
import org.babyfish.jimmer.sql.fetcher.Fetcher
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.babyfish.jimmer.sql.kt.ast.expression.valueIn
import org.babyfish.jimmer.sql.kt.fetcher.newFetcher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import top.potmot.core.database.generate.generateTableDefines
import top.potmot.core.entity.generate.generateEntityCode
import top.potmot.enumeration.DataSourceType
import top.potmot.enumeration.GenLanguage
import top.potmot.error.DataSourceErrorCode
import top.potmot.model.GenEntity
import top.potmot.model.GenModel
import top.potmot.model.GenTable
import top.potmot.model.by
import top.potmot.model.dto.GenEntityPropertiesView
import top.potmot.model.dto.GenTableAssociationsView
import top.potmot.model.id
import top.potmot.model.modelId
import top.potmot.model.tableId

@RestController
@RequestMapping("/preview")
class PreviewService(
    @Autowired val sqlClient: KSqlClient,
    @Autowired val convertService: ConvertService
) {
    fun getTableIdsByModelId(modelId: Long): List<Long> =
        sqlClient.createQuery(GenTable::class) {
            where(
                table.modelId eq modelId
            )
            select(table.id)
        }.execute()

    fun getEntityIdsByModelId(modelId: Long): List<Long> =
        sqlClient.createQuery(GenEntity::class) {
            where(
                table.tableId valueIn subQuery(GenTable::class) {
                    where(table.modelId eq modelId)
                    select(table.id)
                }
            )
            select(table.id)
        }.execute()

    @GetMapping("/entity")
    fun previewEntity(
        @RequestParam entityIds: List<Long>,
        @RequestParam(required = false) language: GenLanguage?,
        @RequestParam(required = false) withPath: Boolean?
    ): List<Pair<String, String>> =
        sqlClient.createQuery(GenEntity::class) {
            where(table.id valueIn entityIds)
            select(table.fetch(GenEntityPropertiesView::class))
        }.execute().flatMap {
            generateEntityCode(it, language, withPath)
        }

    @GetMapping("/entity/byTable")
    fun previewEntityByTable(
        @RequestParam tableIds: List<Long>,
        @RequestParam(required = false) modelId: Long?,
        @RequestParam(required = false) dataSourceType: DataSourceType?,
        @RequestParam(required = false) language: GenLanguage?,
        @RequestParam(required = false) packagePath: String?,
        @RequestParam(required = false) withPath: Boolean?
    ): List<Pair<String, String>> {
        val entityIds = convertService.convert(tableIds, modelId, dataSourceType, language, packagePath)
        return previewEntity(entityIds.toList(), language, withPath)
    }

    private final val previewFetcher = newFetcher(GenModel::class).by {
        syncConvertEntity()
        packagePath()
        dataSourceType()
        language()
    }

    private final val graphDataFetcher = newFetcher(GenModel::class).by(previewFetcher) {
        graphData()
    }

    fun getPreviewModel(id: Long, fetcher: Fetcher<GenModel> = previewFetcher): GenModel? {
        return sqlClient.createQuery(GenModel::class) {
            where(table.id eq id)
            select(table.fetch(fetcher))
        }.execute().firstOrNull()
    }

    @PostMapping("/model/sql")
    @ThrowsAll(DataSourceErrorCode::class)
    fun previewModelSql(
        @RequestParam id: Long,
    ): List<Pair<String, String>> {
        val model = getPreviewModel(id) ?: return emptyList()

        val tables = sqlClient.createQuery(GenTable::class) {
            where(table.modelId eq id)
            select(table.fetch(GenTableAssociationsView::class))
        }.execute()

        return generateTableDefines(tables, listOf(model.dataSourceType))
    }

    @GetMapping("/model/entity")
    fun previewModelEntity(
        @RequestParam id: Long,
        @RequestParam(required = false) withPath: Boolean?
    ): List<Pair<String, String>> {
        val model = getPreviewModel(id) ?: return emptyList()

        return if (model.syncConvertEntity) {
            val tableIds = getTableIdsByModelId(id)
            previewEntityByTable(tableIds, model.id, model.dataSourceType, model.language, model.packagePath, withPath)
        } else {
            val entityIds = getEntityIdsByModelId(id)
            previewEntity(entityIds, model.language, withPath)
        }
    }

    @GetMapping("/model")
    fun previewModel(
        @RequestParam id: Long,
    ): List<Pair<String, String>> {
        val model = getPreviewModel(id, graphDataFetcher) ?: return emptyList()

        val modelJson = model.toString()

        val tableCodes = mutableListOf<Pair<String, String>>()

        val tables = sqlClient.createQuery(GenTable::class) {
            where(table.modelId eq id)
            select(table.fetch(GenTableAssociationsView::class))
        }.execute()

        tableCodes += generateTableDefines(tables, listOf(model.dataSourceType))

        val entityCodes = mutableListOf<Pair<String, String>>()

        entityCodes +=
            if (model.syncConvertEntity) {
                previewEntityByTable(tables.map { it.id }, model.id, model.dataSourceType, model.language, model.packagePath, true)
            } else {
                val entityIds = getEntityIdsByModelId(id)
                previewEntity(entityIds, model.language, true)
            }

        val result = mutableListOf(Pair("model.json", modelJson))

        result += tableCodes.map { Pair(model.dataSourceType.name.lowercase() + "/" + it.first, it.second) }

        result += entityCodes.map { Pair(model.language.name.lowercase() + "/" + it.first, it.second) }

        return result
    }
}
