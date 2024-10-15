package top.potmot.business

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import top.potmot.core.business.dto.generate.DtoGenerator
import top.potmot.core.business.service.generate.getServiceGenerator
import top.potmot.core.business.view.generate.getViewGenerator
import top.potmot.entity.dto.GenEntityPropertiesView
import top.potmot.enumeration.GenLanguage
import top.potmot.enumeration.ViewType


abstract class BaseTest {
    abstract fun getTestEntity(): GenEntityPropertiesView

    abstract fun getDtoResult(): String

    abstract fun getViewResult(viewType: ViewType): String

    abstract fun getServiceResult(language: GenLanguage): String

    @Test
    fun generateDto() {
        val entity = getTestEntity()

        assertEquals(
            getDtoResult().trim(),
            DtoGenerator.generateDto(entity).toString()
        )
    }

    @Test
    fun generateView() {
        val entity = getTestEntity()

        ViewType.entries.forEach {
            assertEquals(
                getViewResult(it).trim(),
                it.getViewGenerator().generateView(entity).toString()
            )
        }
    }

    @Test
    fun generateService() {
        val entity = getTestEntity()

        GenLanguage.entries.forEach {
            assertEquals(
                getServiceResult(it).trim(),
                it.getServiceGenerator().generateService(entity).toString()
            )
        }
    }
}