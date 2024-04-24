package top.potmot.model

import org.babyfish.jimmer.kt.merge
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import top.potmot.model.dto.GenConfig
import top.potmot.model.dto.GenConfigProperties
import top.potmot.model.dto.GenModelInput
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
abstract class BaseTest {
    val logger = LoggerFactory.getLogger(BaseTest::class.java)

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
    open fun testEntities(properties: GenConfigProperties) {
        val propertiesEntity = properties.toEntity()
        logger.debug(propertiesEntity.toString())

        val entity = merge(getBaseModel().toEntity(), propertiesEntity)
        val model = GenModelInput(entity)
        val config = GenConfig(entity)

        val id = modelService.save(model)
        convertService.convertModel(id, null)
        val entityCodes = generateService.generateModelEntity(id, true)

        assertEquals(
            getEntityResult(config).replaceSinceTimeComment().trim(),
            entityCodes.toString().replaceSinceTimeComment().trim()
        )
    }

    @ParameterizedTest
    @MethodSource("tableDefineProperties")
    open fun testTableDefines(properties: GenConfigProperties) {
        val propertiesEntity = properties.toEntity()
        logger.debug(propertiesEntity.toString())

        val entity = merge(getBaseModel().toEntity(), properties.toEntity())
        val model = GenModelInput(entity)
        val config = GenConfig(entity)

        val id = modelService.save(model)
        val tableDefineCodes = generateService.generateModelSql(id)

        assertEquals(
            getTableDefineResult(config).trim(),
            tableDefineCodes.toString().trim()
        )
    }

    companion object {
        @JvmStatic
        fun entityProperties(): List<GenConfigProperties> = languageProperties

        @JvmStatic
        fun tableDefineProperties(): List<GenConfigProperties> = dataSourceTypeProperties
    }
}
