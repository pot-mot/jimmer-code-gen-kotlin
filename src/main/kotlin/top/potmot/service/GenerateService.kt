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
import top.potmot.core.business.route.generate.DynamicRouteGenerator
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
import top.potmot.entity.dto.GenEntityGenerateFileFillView
import top.potmot.entity.dto.GenEntityGenerateView
import top.potmot.entity.dto.GenEnumGenerateView
import top.potmot.entity.dto.GenTableGenerateView
import top.potmot.entity.dto.GenerateFile
import top.potmot.entity.dto.IdName
import top.potmot.entity.dto.TableEntityNotNullPair
import top.potmot.entity.extension.merge
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
    ): List<GenerateFile> =
        useContext(
            sqlClient.getModelProperties(id)?.merge(properties)
                ?: throw GenerateException.modelNotFound("ModelId $id Not Found", modelId = id)
        ) { context ->
            val result = mutableListOf<GenerateFile>()

            val languageDir by lazy { context.language.name.lowercase() }

            val tables by lazy {
                sqlClient.listTable(id)
            }
            val lazyEntityGenerateView = lazy {
                sqlClient.listEntity<GenEntityGenerateView>(id)
            }
            val entityGenerateViews by lazyEntityGenerateView
            val entityBusinessViews by lazy {
                sqlClient.listEntity<GenEntityBusinessView>(id) {
                    where(table.table.type ne TableType.SUPER_TABLE)
                }
            }
            val enums by lazy {
                sqlClient.listEnum(id)
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
                result += tableDefineGenerator.generate(tables)
            }
            if (containsAll || containsBackEnd || GenerateType.Enum in typeSet) {
                entityGenerator.generateEnum(enums).forEach {
                    result += it.copy(path = "${languageDir}/${it.path}")
                }
            }
            if (containsAll || containsBackEnd || GenerateType.Entity in typeSet) {
                entityGenerator.generateEntity(entityGenerateViews).forEach {
                    result += it.copy(path = "${languageDir}/${it.path}")
                }
            }
            if (containsAll || containsBackEnd || GenerateType.Service in typeSet) {
                serviceGenerator.generateService(entityBusinessViews).forEach {
                    result += it.copy(path = "${languageDir}/${it.path}")
                }
            }
            if (containsAll || containsBackEnd || GenerateType.DTO in typeSet) {
                result += generateDto(entityBusinessViews)
            }
            if (containsAll || containsBackEnd || GenerateType.Permission in typeSet) {
                result += PermissionGenerator.generate(entityBusinessViews)
            }
            if (containsAll || containsBackEnd || GenerateType.Route in typeSet) {
                result += DynamicRouteGenerator.generate(entityBusinessViews)
            }
            if (containsAll || containsFrontEnd || GenerateType.EnumComponent in typeSet) {
                result += viewGenerator.generateEnum(enums)
            }
            if (containsAll || containsFrontEnd || GenerateType.View in typeSet) {
                result += viewGenerator.generateView(entityBusinessViews)
            }



            result.apply {
                val tableEntityPairs =
                    if (lazyEntityGenerateView.isInitialized()) {
                        entityGenerateViews.map {
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

                fillGenerateFilesData(
                    this,
                    tableEntityPairs
                )
            }
        }

    fun fillGenerateFilesData(
        generateFiles: List<GenerateFile>,
        tableEntityPairs: List<TableEntityNotNullPair>,
    ) {
        val tableIdEntityMap = tableEntityPairs.associate { it.table.id to it.entity }
        val entityIdTableMap = tableEntityPairs.associate { it.entity.id to it.table }

        generateFiles.forEach { generateFile ->
            generateFile.tableEntities.forEach {
                val (table, entity) = it

                if (table != null && entity == null) {
                    it.entity = tableIdEntityMap[table.id]
                } else if (entity != null && table == null) {
                    it.table = entityIdTableMap[entity.id]
                }
            }
        }
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
