package top.potmot.service

import org.babyfish.jimmer.View
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import top.potmot.context.useContext
import top.potmot.core.business.dto.generate.DtoGenerator
import top.potmot.core.business.service.generate.getServiceGenerator
import top.potmot.core.business.view.generate.getViewGenerator
import top.potmot.core.database.generate.getTableDefineGenerator
import top.potmot.core.entity.generate.getEntityGenerator
import top.potmot.entity.GenEntity
import top.potmot.entity.GenEnum
import top.potmot.entity.GenModel
import top.potmot.entity.GenTable
import top.potmot.entity.dto.GenConfigProperties
import top.potmot.entity.dto.GenEntityBusinessView
import top.potmot.entity.dto.GenEntityGenerateView
import top.potmot.entity.dto.GenEnumGenerateView
import top.potmot.entity.dto.GenTableGenerateView
import top.potmot.entity.extension.merge
import top.potmot.entity.id
import top.potmot.entity.modelId
import top.potmot.enumeration.DataSourceType
import top.potmot.enumeration.GenLanguage
import top.potmot.enumeration.GenerateType
import top.potmot.enumeration.ViewType
import top.potmot.error.ColumnTypeException
import top.potmot.error.GenerateEntityException
import top.potmot.error.GenerateException
import top.potmot.error.GenerateTableDefineException

@RestController
@RequestMapping("/preview")
class GenerateService(
    @Autowired val sqlClient: KSqlClient
) {
    @PostMapping("/model")
    @Throws(GenerateException::class, GenerateEntityException::class, ColumnTypeException::class)
    fun generateModel(
        @RequestParam id: Long,
        @RequestParam types: List<GenerateType>,
        @RequestParam(required = false) properties: GenConfigProperties? = null,
    ): List<Pair<String, String>> =
        useContext(
            sqlClient.getModelProperties(id)?.merge(properties)
                ?: throw GenerateException.modelNotFound("modelId $id")
        ) { context ->
            val result = mutableListOf<Pair<String, String>>()

            val languageDir by lazy { context.language.name.lowercase() }

            val tables by lazy { sqlClient.listTable(id) }
            val entityGenerateViews by lazy { sqlClient.listEntity<GenEntityGenerateView>(id) }
            val entityBusinessViews by lazy { sqlClient.listEntity<GenEntityBusinessView>(id) }
            val enums by lazy { sqlClient.listEnum(id) }

            val typeSet = types.toSet()
            val containsAll = GenerateType.ALL in typeSet

            if (containsAll || GenerateType.DDL in typeSet) {
                result += generateTableDefine(tables, context.dataSourceType)
                    .map { "ddl/${it.first}" to it.second }
            }
            if (containsAll || GenerateType.Enum in typeSet) {
                result += generateEnumCode(enums, context.language)
                    .map { "${languageDir}/${it.first}" to it.second }
            }
            if (containsAll || GenerateType.Entity in typeSet) {
                result += generateEntityCode(entityGenerateViews, context.language)
                    .map { "${languageDir}/${it.first}" to it.second }
            }
            if (containsAll || GenerateType.Service in typeSet) {
                result += generateServiceCode(entityBusinessViews, context.language)
                    .map { "${languageDir}/${it.first}" to it.second }
            }
            if (containsAll || GenerateType.DTO in typeSet) {
                result += generateDto(entityBusinessViews)
                    .map { "dto/${it.first}" to it.second }
            }
            if (containsAll || GenerateType.EnumComponent in typeSet) {
                result += generateEnumComponent(enums)
                    .map { "view/src/${it.first}" to it.second }
            }
            if (containsAll || GenerateType.View in typeSet) {
                result += generateView(entityBusinessViews)
                    .map { "view/src/${it.first}" to it.second }
            }

            result
        }


    /**
     * 批量生成的基本函数
     */

    @Throws(GenerateEntityException::class)
    fun generateEntityCode(
        entities: Iterable<GenEntityGenerateView>,
        language: GenLanguage,
    ): List<Pair<String, String>> =
        language.getEntityGenerator().generateEntity(entities)

    fun generateEnumCode(
        enums: Iterable<GenEnumGenerateView>,
        language: GenLanguage,
    ): List<Pair<String, String>> =
        language.getEntityGenerator().generateEnum(enums)

    @Throws(GenerateTableDefineException::class, ColumnTypeException::class)
    fun generateTableDefine(
        tables: Iterable<GenTableGenerateView>,
        dataSourceType: DataSourceType,
    ): List<Pair<String, String>> =
        dataSourceType.getTableDefineGenerator().generate(tables)

    fun generateServiceCode(
        entities: Iterable<GenEntityBusinessView>,
        language: GenLanguage,
    ): List<Pair<String, String>> =
        language.getServiceGenerator().generateService(entities)

    fun generateView(
        entities: Iterable<GenEntityBusinessView>,
        viewType: ViewType = ViewType.VUE3_ELEMENT_PLUS
    ): List<Pair<String, String>> =
        viewType.getViewGenerator().generateView(entities)

    fun generateEnumComponent(
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

    private fun KSqlClient.getModelProperties(id: Long) =
        createQuery(GenModel::class) {
            where(table.id eq id)
            select(table.fetch(GenConfigProperties::class))
        }.fetchOneOrNull()

    private fun KSqlClient.listTable(modelId: Long) =
        createQuery(GenTable::class) {
            where(table.modelId eq modelId)
            select(table.fetch(GenTableGenerateView::class))
        }.execute()

    private inline fun <reified V : View<GenEntity>> KSqlClient.listEntity(modelId: Long) =
        createQuery(GenEntity::class) {
            where(table.modelId eq modelId)
            select(table.fetch(V::class))
        }.execute()

    private fun KSqlClient.listEnum(modelId: Long) =
        createQuery(GenEnum::class) {
            where(table.modelId eq modelId)
            select(table.fetch(GenEnumGenerateView::class))
        }.execute()
}
