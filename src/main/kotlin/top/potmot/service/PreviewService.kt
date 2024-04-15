package top.potmot.service

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
import top.potmot.model.GenModelFetcherDsl
import top.potmot.model.GenTable
import top.potmot.model.by
import top.potmot.model.dto.GenConfig
import top.potmot.model.dto.GenConfigProperties
import top.potmot.model.dto.GenEntityPropertiesView
import top.potmot.model.dto.GenPropertyEnum
import top.potmot.model.dto.GenTableAssociationsView
import top.potmot.model.id

@RestController
@RequestMapping("/preview")
class PreviewService(
    @Autowired val sqlClient: KSqlClient
) {
    @GetMapping("/tableDefine")
    @Throws(GenerateTableDefineException::class, ColumnTypeException::class)
    fun previewTableDefine(
        @RequestParam tableIds: List<Long>,
        @RequestParam(required = false) properties: GenConfigProperties?,
    ): List<Pair<String, String>> =
        useContext(properties) {
            val tables = sqlClient.getTables(tableIds)
            generateTableDefines(tables)
        }

    @GetMapping("/entity")
    @Throws(GenerateEntityException::class)
    fun previewEntity(
        @RequestParam entityIds: List<Long>,
        @RequestParam(required = false) withPath: Boolean?,
        @RequestParam(required = false) properties: GenConfigProperties?,
    ): List<Pair<String, String>> =
        useContext(properties) {
            val entities = sqlClient.getEntities(entityIds)
            generateEntitiesCode(entities, withPath)
        }

    @GetMapping("/enum")
    fun previewEnums(
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
    fun previewModelSql(
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
    fun previewModelEntity(
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
            val entities = sqlClient.getEntities(model.entityIds)
            val enums = sqlClient.getEnums(model.enumIds)

            (generateEntitiesCode(entities, withPath) + generateEnumsCode(enums, withPath))
                .distinct().sortedBy { it.first }
        }
    }


    /**
     * 批量生成的基本函数
     */

    @Throws(GenerateEntityException::class)
    private fun generateEntitiesCode(
        entities: Collection<GenEntityPropertiesView>,
        withPath: Boolean?,
        context: GenConfig = getContextOrGlobal(),
        language: GenLanguage = context.language,
    ): List<Pair<String, String>> =
        language.getEntityGenerator().generateEntities(entities, withPath ?: false)

    private fun generateEnumsCode(
        enums: Collection<GenPropertyEnum>,
        withPath: Boolean?,
        context: GenConfig = getContextOrGlobal(),
        language: GenLanguage = context.language,
    ): List<Pair<String, String>> =
        language.getEntityGenerator().generateEnums(enums, withPath ?: false)

    @Throws(GenerateTableDefineException::class, ColumnTypeException::class)
    private fun generateTableDefines(
        tables: Collection<GenTableAssociationsView>,
        context: GenConfig = getContextOrGlobal(),
        dataSourceType: DataSourceType = context.dataSourceType,
    ): List<Pair<String, String>> =
        dataSourceType.getTableDefineGenerator().generate(tables)

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
            select(table.fetch(GenTableAssociationsView::class))
        }.execute()

    private fun KSqlClient.getEntities(ids: List<Long>) =
        if (ids.isEmpty()) emptyList()
        else createQuery(GenEntity::class) {
            where(table.id valueIn ids)
            select(table.fetch(GenEntityPropertiesView::class))
        }.execute()

    private fun KSqlClient.getEnums(ids: List<Long>) =
        if (ids.isEmpty()) emptyList()
        else createQuery(GenEnum::class) {
            where(table.id valueIn ids)
            select(table.fetch(GenPropertyEnum::class))
        }.execute()
}
