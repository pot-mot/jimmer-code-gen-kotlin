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
import top.potmot.core.business.dto.generate.DtoGenerator.generateDto
import top.potmot.core.business.permission.generate.PermissionGenerator
import top.potmot.core.business.meta.EnumBusiness
import top.potmot.core.business.meta.RootEntityBusiness
import top.potmot.core.business.route.generate.DynamicRouteGenerator
import top.potmot.core.business.service.generate.getServiceGenerator
import top.potmot.core.business.meta.toFlat
import top.potmot.core.business.view.generate.getViewGenerator
import top.potmot.core.database.generate.getTableDefineGenerator
import top.potmot.core.entity.generate.getEntityGenerator
import top.potmot.entity.GenEntity
import top.potmot.entity.GenEnum
import top.potmot.entity.GenModel
import top.potmot.entity.GenTable
import top.potmot.entity.dto.GenConfigProperties
import top.potmot.entity.dto.GenEntityBusinessView
import top.potmot.entity.dto.GenEntityGenerateFileFillView
import top.potmot.entity.dto.GenEntityGenerateView
import top.potmot.entity.dto.GenEnumGenerateView
import top.potmot.entity.dto.GenTableGenerateView
import top.potmot.entity.dto.GenerateFile
import top.potmot.entity.dto.GenerateResult
import top.potmot.entity.dto.IdName
import top.potmot.entity.dto.TableEntityNotNullPair
import top.potmot.core.config.merge
import top.potmot.entity.id
import top.potmot.entity.modelId
import top.potmot.entity.table
import top.potmot.entity.type
import top.potmot.enumeration.GenerateType
import top.potmot.enumeration.TableType
import top.potmot.enumeration.ViewType
import top.potmot.error.ColumnTypeException
import top.potmot.error.GenerateException
import top.potmot.error.ModelException

@RestController
@RequestMapping("/preview")
class GenerateService(
    @Autowired
    private val sqlClient: KSqlClient,
) {
    @PostMapping("/model")
    @Throws(ModelException::class, GenerateException::class, ColumnTypeException::class)
    fun generateModel(
        @RequestParam id: Long,
        @RequestParam types: List<GenerateType>,
        @RequestParam viewType: ViewType = ViewType.VUE3_ELEMENT_PLUS,
        @RequestParam(required = false) properties: GenConfigProperties? = null,
    ): GenerateResult =
        useContext(
            sqlClient.getModelProperties(id)?.merge(properties)
                ?: throw GenerateException.modelNotFound("ModelId $id Not Found", modelId = id)
        ) { context ->
            val files = mutableListOf<GenerateFile>()

            val languageDir by lazy { context.language.name.lowercase() }

            val tables by lazy {
                sqlClient.listTable(id)
            }
            val lazyEntities = lazy {
                sqlClient.listEntity<GenEntityGenerateView>(id)
            }
            val entities by lazyEntities
            val enums by lazy {
                sqlClient.listEnum(id)
            }

            val enumBusinesses by lazy {
                enums.map { EnumBusiness(it) }
            }
            val enumBusinessIdMap by lazy {
                enumBusinesses.associateBy { it.id }
            }

            val entityBusinesses by lazy {
                val entityBusinessViews = sqlClient.listEntity<GenEntityBusinessView>(id) {
                    where(table.table.type ne TableType.SUPER_TABLE)
                }.map { it.toFlat() }
                val entityBusinessIdMap = entityBusinessViews.associateBy { it.id }
                entityBusinessViews.map { RootEntityBusiness(it, entityBusinessIdMap, enumBusinessIdMap) }
            }

            val typeSet = types.toSet()
            val containsAll = GenerateType.ALL in typeSet
            val containsBackEnd = GenerateType.BackEnd in typeSet
            val containsFrontEnd = GenerateType.FrontEnd in typeSet

            val tableDefineGenerator by lazy {
                context.dataSourceType.getTableDefineGenerator()
            }
            val entityGenerator by lazy {
                context.language.getEntityGenerator()
            }
            val serviceGenerator by lazy {
                context.language.getServiceGenerator()
            }
            val viewGenerator by lazy {
                viewType.getViewGenerator()
            }

            if (containsAll || containsBackEnd || GenerateType.DDL in typeSet) {
                files += tableDefineGenerator.generate(tables)
            }
            if (containsAll || containsBackEnd || GenerateType.Enum in typeSet) {
                entityGenerator.generateEnum(enums).forEach {
                    files += it.copy(path = "${languageDir}/${it.path}")
                }
            }
            if (containsAll || containsBackEnd || GenerateType.Entity in typeSet) {
                entityGenerator.generateEntity(entities).forEach {
                    files += it.copy(path = "${languageDir}/${it.path}")
                }
            }
            if (containsAll || containsBackEnd || GenerateType.Service in typeSet) {
                serviceGenerator.generateService(entityBusinesses).forEach {
                    files += it.copy(path = "${languageDir}/${it.path}")
                }
            }
            if (containsAll || containsBackEnd || GenerateType.DTO in typeSet) {
                files += generateDto(entityBusinesses)
            }
            if (containsAll || containsBackEnd || GenerateType.Permission in typeSet) {
                files += PermissionGenerator.generate(entityBusinesses)
            }
            if (containsAll || containsBackEnd || GenerateType.Route in typeSet) {
                files += DynamicRouteGenerator.generate(entityBusinesses)
            }
            if (containsAll || containsFrontEnd || GenerateType.EnumComponent in typeSet) {
                files += viewGenerator.generateEnum(enumBusinesses)
            }
            if (containsAll || containsFrontEnd || GenerateType.View in typeSet) {
                files += viewGenerator.generateView(entityBusinesses)
            }

            val tableEntityPairs =
                if (lazyEntities.isInitialized()) {
                    entities.map {
                        TableEntityNotNullPair(
                            table = IdName(it.table.id, it.table.name),
                            entity = IdName(it.id, it.name)
                        )
                    }
                } else {
                    sqlClient.listEntity<GenEntityGenerateFileFillView>(modelId = id).map {
                        TableEntityNotNullPair(
                            table = IdName(it.table.id, it.table.name),
                            entity = IdName(it.id, it.name)
                        )
                    }
                }

            GenerateResult(
                files.sortedBy { it.path },
                tableEntityPairs
            )
        }

    /**
     * 基本查询
     */

    private fun KSqlClient.getModelProperties(id: Long) =
        createQuery(GenModel::class) {
            where(table.id eq id)
            select(table.fetch(GenConfigProperties::class))
        }.fetchOneOrNull()

    private fun KSqlClient.listTable(modelId: Long) =
        executeQuery(GenTable::class) {
            where(table.modelId eq modelId)
            select(table.fetch(GenTableGenerateView::class))
        }

    private inline fun <reified V : View<GenEntity>> KSqlClient.listEntity(
        modelId: Long,
        crossinline block: KMutableRootQuery<GenEntity>.() -> Unit = {},
    ) =
        executeQuery(GenEntity::class) {
            where(table.modelId eq modelId)
            block()
            select(table.fetch(V::class))
        }

    private fun KSqlClient.listEnum(modelId: Long) =
        executeQuery(GenEnum::class) {
            where(table.modelId eq modelId)
            select(table.fetch(GenEnumGenerateView::class))
        }
}
