package top.potmot.service

import org.babyfish.jimmer.View
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
import top.potmot.context.getContextOrGlobal
import top.potmot.context.useContext
import top.potmot.core.business.dto.generate.DtoGenerator
import top.potmot.core.business.service.generate.getServiceGenerator
import top.potmot.core.business.view.generate.getViewGenerator
import top.potmot.core.database.generate.getTableDefineGenerator
import top.potmot.core.entity.generate.getEntityGenerator
import top.potmot.enumeration.DataSourceType
import top.potmot.enumeration.GenLanguage
import top.potmot.error.ColumnTypeException
import top.potmot.error.ConvertEntityException
import top.potmot.error.GenerateEntityException
import top.potmot.error.GenerateTableDefineException
import top.potmot.entity.GenEntity
import top.potmot.entity.GenEnum
import top.potmot.entity.GenModel
import top.potmot.entity.GenModelFetcherDsl
import top.potmot.entity.GenTable
import top.potmot.entity.by
import top.potmot.entity.dto.GenConfig
import top.potmot.entity.dto.GenConfigProperties
import top.potmot.entity.dto.GenEntityBusinessView
import top.potmot.entity.dto.GenEntityGenerateView
import top.potmot.entity.dto.GenEnumGenerateView
import top.potmot.entity.dto.GenTableGenerateView
import top.potmot.entity.id
import top.potmot.enumeration.ViewType
import top.potmot.utils.list.flatListOf

@RestController
@RequestMapping("/preview")
class GenerateService(
    @Autowired val sqlClient: KSqlClient
) {
    @GetMapping("/tableDefine")
    @Throws(GenerateTableDefineException::class, ColumnTypeException::class)
    fun generateTableDefine(
        @RequestParam tableIds: List<Long>,
        @RequestParam(required = false) properties: GenConfigProperties?,
    ): List<Pair<String, String>> =
        useContext(properties) {
            val tables = sqlClient.getTables(tableIds)
            generateTableDefines(tables)
        }

    @GetMapping("/entity")
    @Throws(GenerateEntityException::class)
    fun generateEntity(
        @RequestParam entityIds: List<Long>,
        @RequestParam(required = false) withPath: Boolean?,
        @RequestParam(required = false) properties: GenConfigProperties?,
    ): List<Pair<String, String>> =
        useContext(properties) {
            val entities = sqlClient.getEntities<GenEntityGenerateView>(entityIds)
            generateEntitiesCode(entities, withPath)
        }

    @GetMapping("/enum")
    fun generateEnums(
        @RequestParam enumIds: List<Long>,
        @RequestParam(required = false) withPath: Boolean?,
        @RequestParam(required = false) properties: GenConfigProperties?,
    ): List<Pair<String, String>> =
        useContext(properties) {
            val enums = sqlClient.getEnums(enumIds)
            generateEnumsCode(enums, withPath)
        }

    @PostMapping("/model/sql")
    @Throws(GenerateTableDefineException::class, ColumnTypeException::class)
    fun generateModelSql(
        @RequestParam id: Long,
    ): List<Pair<String, String>> {
        val model = sqlClient.getModel(id) {
            allScalarFields()
            tableIds()
        } ?: return emptyList()

        val properties = GenConfigProperties(model)

        return useContext(properties) {
            val tables = sqlClient.getTables(model.tableIds)
            generateTableDefines(tables, dataSourceType = model.dataSourceType)
        }
    }

    @GetMapping("/model/entity")
    @Throws(ConvertEntityException::class, ColumnTypeException::class, GenerateEntityException::class)
    fun generateModelEntity(
        @RequestParam id: Long,
        @RequestParam(required = false) withPath: Boolean?
    ): List<Pair<String, String>> {
        val model = sqlClient.getModel(id) {
            allScalarFields()
            entities()
            enumIds()
        } ?: return emptyList()

        val properties = GenConfigProperties(model)

        return useContext(properties) {
            val entities = sqlClient.getEntities<GenEntityGenerateView>(model.entityIds)
            val enums = sqlClient.getEnums(model.enumIds)

            flatListOf(
                generateEntitiesCode(entities, withPath),
                generateEnumsCode(enums, withPath)
            )
                .distinct().sortedBy { it.first }
        }
    }

    @GetMapping("/model/business")
    @Throws(ConvertEntityException::class, ColumnTypeException::class, GenerateEntityException::class)
    fun generateModelBusiness(
        @RequestParam id: Long,
    ): List<Pair<String, String>> {
        val model = sqlClient.getModel(id) {
            allScalarFields()
            entities()
            enumIds()
        } ?: return emptyList()

        val properties = GenConfigProperties(model)

        return useContext(properties) {
            val entities = sqlClient.getEntities<GenEntityBusinessView>(model.entityIds)
            val enums = sqlClient.getEnums(model.enumIds)

            flatListOf(
                generateServiceCode(entities, true).map { "${getContextOrGlobal().language.name.lowercase()}/${it.first}" to it.second },
                generateDto(entities).map { "dto/${it.first}" to it.second },
                generateView(entities).map { "view/src/${it.first}" to it.second},
                generateEnumView(enums).map { "view/src/${it.first}" to it.second}
            )
                .distinct().sortedBy { it.first }
        }
    }


    /**
     * 批量生成的基本函数
     */

    @Throws(GenerateEntityException::class)
    fun generateEntitiesCode(
        entities: Iterable<GenEntityGenerateView>,
        withPath: Boolean?,
        context: GenConfig = getContextOrGlobal(),
        language: GenLanguage = context.language,
    ): List<Pair<String, String>> =
        language.getEntityGenerator().generateEntity(entities, withPath ?: false)

    fun generateEnumsCode(
        enums: Iterable<GenEnumGenerateView>,
        withPath: Boolean?,
        context: GenConfig = getContextOrGlobal(),
        language: GenLanguage = context.language,
    ): List<Pair<String, String>> =
        language.getEntityGenerator().generateEnum(enums, withPath ?: false)

    @Throws(GenerateTableDefineException::class, ColumnTypeException::class)
    fun generateTableDefines(
        tables: Iterable<GenTableGenerateView>,
        context: GenConfig = getContextOrGlobal(),
        dataSourceType: DataSourceType = context.dataSourceType,
    ): List<Pair<String, String>> =
        dataSourceType.getTableDefineGenerator().generate(tables)

    fun generateServiceCode(
        entities: Iterable<GenEntityBusinessView>,
        withPath: Boolean?,
        context: GenConfig = getContextOrGlobal(),
        language: GenLanguage = context.language,
    ): List<Pair<String, String>> =
        language.getServiceGenerator().generateService(entities, withPath ?: false)

    fun generateView(
        entities: Iterable<GenEntityBusinessView>,
        viewType: ViewType = ViewType.VUE3_ELEMENT_PLUS
    ): List<Pair<String, String>> =
        viewType.getViewGenerator().generateView(entities)

    fun generateEnumView(
        enums: Iterable<GenEnumGenerateView>,
        viewType: ViewType = ViewType.VUE3_ELEMENT_PLUS
    ): List<Pair<String, String>> =
        viewType.getViewGenerator().generateEnum(enums)

    fun generateDto(
        entities: Iterable<GenEntityBusinessView>
    ): List<Pair<String, String>> =
       DtoGenerator.generateDto(entities)

    /**
     * 基本查询
     */

    private fun KSqlClient.getModel(id: Long, block: GenModelFetcherDsl.() -> Unit): GenModel? =
        createQuery(GenModel::class) {
            where(table.id eq id)
            select(table.fetch(newFetcher(GenModel::class).by(block)))
        }.execute().firstOrNull()

    private fun KSqlClient.getTables(ids: List<Long>) =
        if (ids.isEmpty()) emptyList()
        else createQuery(GenTable::class) {
            where(table.id valueIn ids)
            select(table.fetch(GenTableGenerateView::class))
        }.execute()

    private inline fun <reified V : View<GenEntity>> KSqlClient.getEntities(ids: List<Long>) =
        if (ids.isEmpty()) emptyList()
        else createQuery(GenEntity::class) {
            where(table.id valueIn ids)
            select(table.fetch(V::class))
        }.execute()

    private fun KSqlClient.getEnums(ids: List<Long>) =
        if (ids.isEmpty()) emptyList()
        else createQuery(GenEnum::class) {
            where(table.id valueIn ids)
            select(table.fetch(GenEnumGenerateView::class))
        }.execute()
}
