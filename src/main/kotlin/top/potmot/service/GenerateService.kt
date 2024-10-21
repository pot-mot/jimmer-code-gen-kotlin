package top.potmot.service

import org.babyfish.jimmer.View
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.babyfish.jimmer.sql.kt.ast.expression.ne
import org.babyfish.jimmer.sql.kt.ast.query.KMutableRootQuery
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
import top.potmot.entity.dto.GenerateFile
import top.potmot.entity.extension.merge
import top.potmot.entity.id
import top.potmot.entity.modelId
import top.potmot.entity.table
import top.potmot.entity.type
import top.potmot.enumeration.DataSourceType
import top.potmot.enumeration.GenLanguage
import top.potmot.enumeration.GenerateTag
import top.potmot.enumeration.GenerateType
import top.potmot.enumeration.TableType
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
        @RequestParam viewType: ViewType = ViewType.VUE3_ELEMENT_PLUS,
        @RequestParam(required = false) properties: GenConfigProperties? = null,
    ): List<GenerateFile> =
        useContext(
            sqlClient.getModelProperties(id)?.merge(properties)
                ?: throw GenerateException.modelNotFound("modelId $id")
        ) { context ->
            val result = mutableListOf<GenerateFile>()

            val languageDir by lazy { context.language.name.lowercase() }

            val tables by lazy { sqlClient.listTable(id) }
            val entityGenerateViews by lazy { sqlClient.listEntity<GenEntityGenerateView>(id) }
            val entityBusinessViews by lazy {
                sqlClient.listEntity<GenEntityBusinessView>(id) {
                    where(table.table.type ne TableType.SUPER_TABLE)
                }
            }
            val enums by lazy { sqlClient.listEnum(id) }

            val typeSet = types.toSet()
            val containsAll = GenerateType.ALL in typeSet
            val containsBackEnd = GenerateType.BackEnd in typeSet
            val containsFrontEnd = GenerateType.FrontEnd in typeSet

            if (containsAll || containsBackEnd || GenerateType.DDL in typeSet) {
                result += generateTableDefine(tables, context.dataSourceType)
                    .map {
                        GenerateFile(
                            "ddl/${it.first}", it.second,
                            listOf(GenerateTag.BackEnd, GenerateTag.Table)
                        )
                    }
            }
            if (containsAll || containsBackEnd || GenerateType.Enum in typeSet) {
                result += generateEnumCode(enums, context.language)
                    .map {
                        GenerateFile(
                            "${languageDir}/${it.first}", it.second,
                            listOf(GenerateTag.BackEnd, GenerateTag.Enum)
                        )
                    }
            }
            if (containsAll || containsBackEnd || GenerateType.Entity in typeSet) {
                result += generateEntityCode(entityGenerateViews, context.language)
                    .map {
                        GenerateFile(
                            "${languageDir}/${it.first}", it.second,
                            listOf(GenerateTag.BackEnd, GenerateTag.Entity)
                        )
                    }
            }
            if (containsAll || containsBackEnd || GenerateType.Service in typeSet) {
                result += generateServiceCode(entityBusinessViews, context.language)
                    .map {
                        GenerateFile(
                            "${languageDir}/${it.first}", it.second,
                            listOf(GenerateTag.BackEnd, GenerateTag.Service)
                        )
                    }
            }
            if (containsAll || containsBackEnd || GenerateType.DTO in typeSet) {
                result += generateDto(entityBusinessViews)
                    .map {
                        GenerateFile(
                            "dto/${it.first}", it.second,
                            listOf(GenerateTag.BackEnd, GenerateTag.DTO)
                        )
                    }
            }
            if (containsAll || containsFrontEnd || GenerateType.EnumComponent in typeSet) {
                result += generateEnumComponent(enums, viewType)
                    .map {
                        GenerateFile(
                            "${viewType.dir}/${it.first}", it.second,
                            listOf(GenerateTag.FrontEnd, GenerateTag.Enum, GenerateTag.Component)
                        )
                    }
            }
            if (containsAll || containsFrontEnd || GenerateType.View in typeSet) {
                result += generateView(entityBusinessViews, viewType)
                    .map {
                        GenerateFile(
                            "${viewType.dir}/${it.first}", it.second,
                            listOf(GenerateTag.FrontEnd, GenerateTag.View, GenerateTag.Component)
                        )
                    }
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

    @Throws(GenerateException::class)
    fun generateServiceCode(
        entities: Iterable<GenEntityBusinessView>,
        language: GenLanguage,
    ): List<Pair<String, String>> =
        language.getServiceGenerator().generateService(entities)

    fun generateView(
        entities: Iterable<GenEntityBusinessView>,
        viewType: ViewType
    ): List<Pair<String, String>> =
        viewType.getViewGenerator().generateView(entities)

    fun generateEnumComponent(
        enums: Iterable<GenEnumGenerateView>,
        viewType: ViewType
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

    private inline fun <reified V : View<GenEntity>> KSqlClient.listEntity(
        modelId: Long,
        crossinline block: KMutableRootQuery<GenEntity>.() -> Unit = {}
    ) =
        createQuery(GenEntity::class) {
            where(table.modelId eq modelId)
            block()
            select(table.fetch(V::class))
        }.execute()

    private fun KSqlClient.listEnum(modelId: Long) =
        createQuery(GenEnum::class) {
            where(table.modelId eq modelId)
            select(table.fetch(GenEnumGenerateView::class))
        }.execute()
}
