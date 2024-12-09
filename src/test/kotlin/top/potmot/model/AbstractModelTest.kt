package top.potmot.model

import org.babyfish.jimmer.kt.merge
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ActiveProfiles
import top.potmot.entity.GenModel
import top.potmot.entity.dto.GenConfig
import top.potmot.entity.dto.GenConfigProperties
import top.potmot.entity.dto.GenModelInput
import top.potmot.enumeration.GenerateType
import top.potmot.service.ConvertService
import top.potmot.service.GenerateService
import top.potmot.service.ModelService
import top.potmot.util.replaceSinceTimeComment

/**
 * 测试 model 生成 entity 和 sql 是否符合 Results 的约定
 *
 * 可按照需要调整 entityProperties 和 tableDefineProperties 两组测试属性集
 * 并实现 EntityResult、TableDefineResult 匹配特定属性集的输出
 */
@ActiveProfiles("test", "h2", "hide-sql")
abstract class AbstractModelTest {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @Autowired
    lateinit var sqlClient: KSqlClient

    @Autowired
    lateinit var modelService: ModelService

    @Autowired
    lateinit var convertService: ConvertService

    @Autowired
    lateinit var generateService: GenerateService

    abstract fun getBaseModel(): GenModelInput

    abstract fun getEntityResult(config: GenConfig): String

    abstract fun getTableDefineResult(config: GenConfig): String

    @ParameterizedTest
    @MethodSource("entityProperties")
    fun testEntities(properties: GenConfigProperties) {
        val propertiesEntity = properties.toEntity()
        logger.debug(propertiesEntity.toString())

        val entity = merge(getBaseModel().toEntity(), propertiesEntity)
        val model = GenModelInput(entity)
        val config = GenConfig(entity)

        val id = modelService.save(model)
        convertService.convertModel(id, null)
        val entityCodes = generateService.generateModel(id, listOf(GenerateType.Entity, GenerateType.Enum))
            .files
            .map {it.path to it.content}
        val deleteResult = sqlClient.deleteById(GenModel::class, id)

        assertEquals(
            getEntityResult(config).replaceSinceTimeComment().trim(),
            entityCodes.toString().replaceSinceTimeComment().trim()
        )
        assertEquals(
            1,
            deleteResult.affectedRowCount(GenModel::class)
        )
    }

    @ParameterizedTest
    @MethodSource("tableDefineProperties")
    fun testTableDefines(properties: GenConfigProperties) {
        val propertiesEntity = properties.toEntity()
        logger.debug(propertiesEntity.toString())

        val entity = merge(getBaseModel().toEntity(), properties.toEntity())
        val model = GenModelInput(entity)
        val config = GenConfig(entity)

        val id = modelService.save(model)
        val tableDefineCodes = generateService.generateModel(id, listOf(GenerateType.DDL))
            .files
            .map {it.path to it.content}
        val deleteResult = sqlClient.deleteById(GenModel::class, id)

        assertEquals(
            getTableDefineResult(config).trim(),
            tableDefineCodes.toString().trim()
        )
        assertEquals(
            1,
            deleteResult.affectedRowCount(GenModel::class)
        )
    }

    companion object {
        @JvmStatic
        fun entityProperties(): List<GenConfigProperties> = languageProperties

        @JvmStatic
        fun tableDefineProperties(): List<GenConfigProperties> = dataSourceTypeProperties
    }
}
