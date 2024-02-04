package top.potmot.model

import org.babyfish.jimmer.kt.merge
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import top.potmot.model.dto.GenConfig
import top.potmot.model.dto.GenModelInput
import top.potmot.service.ModelService
import top.potmot.service.PreviewService
import top.potmot.util.replaceSinceTimeComment

abstract class BaseTest(
    private val modelService: ModelService,
    private val previewService: PreviewService,
) {
    val logger = LoggerFactory.getLogger(BaseTest::class.java)

    abstract fun getBaseModel(): GenModelInput

    abstract fun getEntityResult(config: GenConfig): String

    abstract fun getTableDefineResult(config: GenConfig): String

    open val entityTestProperties = languageProperties

    private fun testEntity(
        model: GenModelInput,
        config: GenConfig,
    ) {
        val id = modelService.save(model)
        val entityCodes = previewService.previewModelEntity(id, true)

        assertEquals(
            getEntityResult(config).replaceSinceTimeComment().trim(),
            entityCodes.toString().replaceSinceTimeComment().trim()
        )
    }

    @Test
    open fun testEntity() {
        entityTestProperties.forEach {
            val entity = merge(getBaseModel().toEntity(), it.toEntity())
            val model = GenModelInput(entity)
            val config = GenConfig(entity)
            logger.debug(it.toEntity().toString())
            testEntity(model, config)
        }
    }

    open val tableDefineTestProperties = dataSourceTypeProperties

    private fun testTableDefine(
        model: GenModelInput,
        config: GenConfig,
    ) {
        val id = modelService.save(model)
        val tableDefineCodes = previewService.previewModelSql(id)

        assertEquals(
            getTableDefineResult(config).trim(),
            tableDefineCodes.toString().trim()
        )
    }

    @Test
    open fun testTableDefine() {
        tableDefineTestProperties.forEach {
            val entity = merge(getBaseModel().toEntity(), it.toEntity())
            val model = GenModelInput(entity)
            val config = GenConfig(entity)
            logger.debug(it.toEntity().toString())
            testTableDefine(model, config)
        }
    }
}
