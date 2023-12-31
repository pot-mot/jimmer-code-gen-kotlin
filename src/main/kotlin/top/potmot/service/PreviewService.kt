package top.potmot.service

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
import top.potmot.core.entity.generate.generateEntitiesCode
import top.potmot.core.entity.generate.generateEnumsCode
import top.potmot.enumeration.DataSourceType
import top.potmot.enumeration.GenLanguage
import top.potmot.model.GenEntity
import top.potmot.model.GenEnum
import top.potmot.model.GenModel
import top.potmot.model.GenTable
import top.potmot.model.by
import top.potmot.model.dto.GenEntityPropertiesView
import top.potmot.model.dto.GenTableAssociationsView
import top.potmot.model.id
import top.potmot.model.modelId
import top.potmot.model.tableId
import top.potmot.model.dto.GenEntityPropertiesView.TargetOf_properties.TargetOf_enum_2 as PropertyEnum

@RestController
@RequestMapping("/preview")
class PreviewService(
    @Autowired val sqlClient: KSqlClient,
    @Autowired val convertService: ConvertService
) {
    @GetMapping("/entity")
    fun previewEntity(
        @RequestParam entityIds: List<Long>,
        @RequestParam(required = false) dataSourceType: DataSourceType?,
        @RequestParam(required = false) language: GenLanguage?,
        @RequestParam(required = false) withPath: Boolean?
    ): List<Pair<String, String>> =
        sqlClient.createQuery(GenEntity::class) {
            where(table.id valueIn entityIds)
            select(table.fetch(GenEntityPropertiesView::class))
        }.execute().let {
            generateEntitiesCode(it, dataSourceType, language, withPath)
        }

    @GetMapping("/enum")
    fun previewEnums(
        @RequestParam enumIds: List<Long>,
        @RequestParam(required = false) dataSourceType: DataSourceType?,
        @RequestParam(required = false) language: GenLanguage?,
        @RequestParam(required = false) withPath: Boolean?
    ): List<Pair<String, String>> =
        sqlClient.createQuery(GenEnum::class) {
            where(table.id valueIn enumIds)
            select(table.fetch(PropertyEnum::class))
        }.execute().let {
            generateEnumsCode(it, dataSourceType, language, withPath)
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
        return previewEntity(entityIds.toList(), dataSourceType, language, withPath)
    }


    fun getTableIdsByModelId(modelId: Long): List<Long> =
        sqlClient.createQuery(GenTable::class) {
            where(table.modelId eq modelId)
            select(table.id)
        }.execute()

    fun getEntityByModelId(modelId: Long): List<GenEntityPropertiesView> =
        sqlClient.createQuery(GenEntity::class) {
            where(
                table.tableId valueIn subQuery(GenTable::class) {
                    where(table.modelId eq modelId)
                    select(table.id)
                }
            )
            select(table.fetch(GenEntityPropertiesView::class))
        }.execute()

    private final val baseInfoFetcher = newFetcher(GenModel::class).by {
        syncConvertEntity()
        packagePath()
        dataSourceType()
        language()
    }

    private final val enumsFetcher = newFetcher(GenModel::class).by(baseInfoFetcher) {
        enumIds()
    }

    fun getModel(id: Long, fetcher: Fetcher<GenModel> = baseInfoFetcher): GenModel? {
        return sqlClient.createQuery(GenModel::class) {
            where(table.id eq id)
            select(table.fetch(fetcher))
        }.execute().firstOrNull()
    }

    @PostMapping("/model/sql")
    fun previewModelSql(
        @RequestParam id: Long,
    ): List<Pair<String, String>> {
        val model = getModel(id) ?: return emptyList()

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
        val model = getModel(id, enumsFetcher) ?: return emptyList()

        val entityCodes = if (model.syncConvertEntity) {
            val tableIds = getTableIdsByModelId(id)
            previewEntityByTable(tableIds, model.id, model.dataSourceType, model.language, model.packagePath, withPath)
        } else {
            val entities = getEntityByModelId(id)
            generateEntitiesCode(entities, model.dataSourceType, model.language, withPath)
        }

        val enumCodes = previewEnums(model.enumIds, model.dataSourceType, model.language, withPath)


        return (entityCodes + enumCodes).distinct()
    }
}
