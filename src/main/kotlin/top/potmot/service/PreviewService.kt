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
import top.potmot.context.cleanContextGenConfig
import top.potmot.context.getContextGenConfig
import top.potmot.context.merge
import top.potmot.core.database.generate.getTableDefineGenerator
import top.potmot.core.entity.generate.getEntityGenerator
import top.potmot.enumeration.DataSourceType
import top.potmot.enumeration.GenLanguage
import top.potmot.error.ColumnTypeException
import top.potmot.error.ConvertEntityException
import top.potmot.error.GenerateEntityException
import top.potmot.error.GenerateTableDefineException
import top.potmot.model.GenEntity
import top.potmot.model.GenEnum
import top.potmot.model.GenModel
import top.potmot.model.GenTable
import top.potmot.model.by
import top.potmot.model.dto.GenConfigProperties
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
    @GetMapping("/tableDefine")
    @Throws(GenerateTableDefineException::class, ColumnTypeException::class)
    fun previewTableDefine(
        @RequestParam tableIds: List<Long>,
        @RequestParam(required = false) properties: GenConfigProperties?,
    ): List<Pair<String, String>> {
        val context = getContextGenConfig()
        properties?.let { context.merge(it) }

        val tableDefines = sqlClient.createQuery(GenTable::class) {
            where(table.id valueIn tableIds)
            select(table.fetch(GenTableAssociationsView::class))
        }.execute().let {
            generateTableDefines(it)
        }

        cleanContextGenConfig()

        return tableDefines
    }

    @GetMapping("/entity")
    @Throws(GenerateEntityException::class)
    fun previewEntity(
        @RequestParam entityIds: List<Long>,
        @RequestParam(required = false) withPath: Boolean?,
        @RequestParam(required = false) properties: GenConfigProperties?,
    ): List<Pair<String, String>> {
        val context = getContextGenConfig()
        properties?.let { context.merge(it) }

        val entities = sqlClient.createQuery(GenEntity::class) {
            where(table.id valueIn entityIds)
            select(table.fetch(GenEntityPropertiesView::class))
        }.execute().let {
            generateEntitiesCode(it, withPath)
        }

        cleanContextGenConfig()

        return entities
    }

    @GetMapping("/enum")
    fun previewEnums(
        @RequestParam enumIds: List<Long>,
        @RequestParam(required = false) withPath: Boolean?,
        @RequestParam(required = false) properties: GenConfigProperties?,
    ): List<Pair<String, String>> {
        val context = getContextGenConfig()
        properties?.let { context.merge(it) }

        val enums = sqlClient.createQuery(GenEnum::class) {
            where(table.id valueIn enumIds)
            select(table.fetch(PropertyEnum::class))
        }.execute().let {
            generateEnumsCode(it, withPath)
        }

        cleanContextGenConfig()

        return enums
    }


    @GetMapping("/entity/byTable")
    @Throws(ConvertEntityException::class, ColumnTypeException::class, GenerateEntityException::class)
    fun previewEntityByTable(
        @RequestParam tableIds: List<Long>,
        @RequestParam(required = false) modelId: Long?,
        @RequestParam(required = false) withPath: Boolean?,
        @RequestParam(required = false) properties: GenConfigProperties?,
    ): List<Pair<String, String>> {
        val entityIds = convertService.convert(tableIds, modelId, properties)
        return previewEntity(entityIds.toList(), withPath, properties)
    }

    @PostMapping("/model/sql")
    @Throws(GenerateTableDefineException::class, ColumnTypeException::class)
    fun previewModelSql(
        @RequestParam id: Long,
    ): List<Pair<String, String>> {
        val model = getModel(id, propertiesFetcher) ?: return emptyList()

        val properties = GenConfigProperties(model)

        val context = getContextGenConfig()
        context.merge(properties)

        val tables = sqlClient.createQuery(GenTable::class) {
            where(table.modelId eq id)
            select(table.fetch(GenTableAssociationsView::class))
        }.execute()

        val tableDefines = generateTableDefines(tables, model.dataSourceType)

        cleanContextGenConfig()

        return tableDefines
    }

    @GetMapping("/model/entity")
    @Throws(ConvertEntityException::class, ColumnTypeException::class, GenerateEntityException::class)
    fun previewModelEntity(
        @RequestParam id: Long,
        @RequestParam(required = false) withPath: Boolean?
    ): List<Pair<String, String>> {
        val model = getModel(id, enumsFetcher) ?: return emptyList()

        val properties = GenConfigProperties(model)

        val context = getContextGenConfig()
        context.merge(properties)

        val entityCodes = if (model.syncConvertEntity) {
            val tableIds = getTableIdsByModelId(id)
            previewEntityByTable(tableIds, model.id, withPath, properties)
        } else {
            val entities = getEntityByModelId(id)
            generateEntitiesCode(entities, withPath)
        }

        val enumCodes =
            previewEnums(model.enumIds, withPath, properties)

        cleanContextGenConfig()

        return (entityCodes + enumCodes).distinct()
    }


    /**
     * 批量生成的基本函数
     */

    @Throws(GenerateEntityException::class)
    fun generateEntitiesCode(
        entities: Collection<GenEntityPropertiesView>,
        withPath: Boolean?,
        language: GenLanguage = getContextGenConfig().language,
    ): List<Pair<String, String>> =
        language.getEntityGenerator().let {
            entities.flatMap { entity ->
                it.generateWithEnums(entity, withPath ?: false)
            }.distinct().sortedBy { it.first }
        }

    fun generateEnumsCode(
        enums: Collection<PropertyEnum>,
        withPath: Boolean?,
        language: GenLanguage = getContextGenConfig().language,
    ): List<Pair<String, String>> =
        language.getEntityGenerator().let {
            enums.map {enum ->
                it.generate(enum, withPath ?: false)
            }.distinct().sortedBy { it.first }
        }


    @Throws(GenerateTableDefineException::class, ColumnTypeException::class)
    fun generateTableDefines(
        tables: Collection<GenTableAssociationsView>,
        dataSourceType: DataSourceType = getContextGenConfig().dataSourceType
    ): List<Pair<String, String>> =
        dataSourceType.getTableDefineGenerator()
            .generate(tables)
            .distinct().sortedBy { it.first }


    /**
     * 基本查询
     */

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

    private final val propertiesFetcher = GenConfigProperties.METADATA.fetcher

    private final val enumsFetcher = newFetcher(GenModel::class).by(propertiesFetcher) {
        enumIds()
    }

    fun getModel(id: Long, fetcher: Fetcher<GenModel>): GenModel? {
        return sqlClient.createQuery(GenModel::class) {
            where(table.id eq id)
            select(table.fetch(fetcher))
        }.execute().firstOrNull()
    }
}
