package top.potmot.service

import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import top.potmot.core.business.dto.generate.DtoGenerator.generateDto
import top.potmot.core.business.permission.generate.PermissionGenerator
import top.potmot.core.business.route.generate.DynamicRouteGenerator
import top.potmot.core.business.service.generate.getServiceGenerator
import top.potmot.core.business.test.generate.getTestGenerator
import top.potmot.core.business.view.generate.getViewGenerator
import top.potmot.core.config.merge
import top.potmot.core.config.useContext
import top.potmot.core.database.generate.getDDLGenerator
import top.potmot.core.entity.generate.getEntityGenerator
import top.potmot.core.plugin.PluginStore
import top.potmot.core.common.GenerateContext
import top.potmot.entity.GenModel
import top.potmot.entity.dto.GenConfigProperties
import top.potmot.entity.dto.GenerateFile
import top.potmot.entity.dto.GenerateResult
import top.potmot.entity.id
import top.potmot.enumeration.GenerateType
import top.potmot.error.ColumnTypeException
import top.potmot.error.GenerateException
import top.potmot.error.ModelException

@RestController
@RequestMapping("/generate")
class GenerateService(
    @Autowired
    private val sqlClient: KSqlClient,
    @Autowired
    private val pluginStore: PluginStore,
) {
    @GetMapping("/types")
    fun listCustomGenerateType(): Set<String> =
        pluginStore.generatePlugins.keys

    /**
     * 生成模型
     * @param id 模型ID
     * @param types 生成类型，null 默认全部
     * @param customTypes 自定义生成类型，null 默认全部
     * @param properties 生成配置
     */
    @PostMapping("/model")
    @Throws(ModelException::class, GenerateException::class, ColumnTypeException::class)
    fun generateModel(
        @RequestParam id: Long,
        @RequestParam(required = false) types: List<GenerateType>? = null,
        @RequestParam(required = false) customTypes: List<String>? = null,
        @RequestParam(required = false) properties: GenConfigProperties? = null,
    ): GenerateResult =
        useContext(
            sqlClient.getModelProperties(id)?.merge(properties)
                ?: throw GenerateException.modelNotFound("Model [$id] Not Found", modelId = id)
        ) { context ->
            val files = mutableListOf<GenerateFile>()

            val languageDir by lazy { context.language.dir }
            val dtoDir = "dto"
            val viewDir by lazy { context.viewType.dir }

            val typeSet = types?.toSet() ?: setOf(GenerateType.ALL)
            val containsAll = GenerateType.ALL in typeSet
            val containsBackEnd = GenerateType.BackEnd in typeSet
            val containsFrontEnd = GenerateType.FrontEnd in typeSet

            val generateContext = GenerateContext(
                typeSet = typeSet,
                sqlClient = sqlClient,
                modelId = id,
            )

            val tableDefineGenerator by lazy {
                context.dataSourceType.getDDLGenerator()
            }
            val entityGenerator by lazy {
                context.language.getEntityGenerator()
            }
            val serviceGenerator by lazy {
                context.language.getServiceGenerator()
            }
            val testGenerator by lazy {
                context.language.getTestGenerator()
            }
            val viewGenerator by lazy {
                context.viewType.getViewGenerator()
            }

            if (containsAll || containsBackEnd || GenerateType.DDL in typeSet) {
                files += tableDefineGenerator.generate(generateContext.tables)
            }
            if (containsAll || containsBackEnd || GenerateType.Enum in typeSet) {
                entityGenerator.generateEnum(generateContext.enums).forEach {
                    files += it.copy(path = "main/${languageDir}/${it.path}")
                }
            }
            if (containsAll || containsBackEnd || GenerateType.Entity in typeSet) {
                entityGenerator.generateEntity(generateContext.entities).forEach {
                    files += it.copy(path = "main/${languageDir}/${it.path}")
                }
            }
            if (containsAll || containsBackEnd || GenerateType.Service in typeSet) {
                serviceGenerator.generateService(generateContext.entityBusinesses).forEach {
                    files += it.copy(path = "main/${languageDir}/${it.path}")
                }
            }
            if (containsAll || containsBackEnd || GenerateType.Test in typeSet) {
                testGenerator.generateTest(generateContext.entityBusinesses).forEach {
                    files += it.copy(path = "test/${languageDir}/${it.path}")
                }
            }
            if (containsAll || containsBackEnd || GenerateType.DTO in typeSet) {
                files += generateDto(generateContext.entityBusinesses).map {
                    it.copy(path = "main/${dtoDir}/${it.path}")
                }
            }
            if (containsAll || containsBackEnd || GenerateType.Permission in typeSet) {
                files += PermissionGenerator.generate(generateContext.entityBusinesses)
            }
            if (containsAll || containsBackEnd || GenerateType.Route in typeSet) {
                files += DynamicRouteGenerator.generate(generateContext.entityBusinesses)
            }
            if (containsAll || containsFrontEnd || GenerateType.View in typeSet) {
                viewGenerator.generateEntity(generateContext.entityBusinesses).forEach {
                    files += it.copy(path = "${viewDir}/${it.path}")
                }
            }

            val customTypeSet = customTypes?.toSet() ?: pluginStore.generatePlugins.keys
            pluginStore.generatePlugins.forEach {
                if (containsAll || it.key in customTypeSet) {
                    files += it.value.generate(generateContext)
                }
            }

            GenerateResult(
                files.sortedBy { it.path },
                generateContext.tableEntityPairsForInfo,
                generateContext.enumsForInfo,
            )
        }

    private fun KSqlClient.getModelProperties(id: Long) =
        createQuery(GenModel::class) {
            where(table.id eq id)
            select(table.fetch(GenConfigProperties::class))
        }.fetchOneOrNull()
}
