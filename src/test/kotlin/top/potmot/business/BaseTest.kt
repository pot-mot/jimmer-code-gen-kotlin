package top.potmot.business

import com.fasterxml.jackson.module.kotlin.readValue
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import top.potmot.core.business.dto.generate.DtoGenerator
import top.potmot.core.business.service.generate.getServiceGenerator
import top.potmot.core.business.view.generate.getViewGenerator
import top.potmot.entity.GenEntity
import top.potmot.entity.dto.GenEntityPropertiesView
import top.potmot.enumeration.GenLanguage
import top.potmot.enumeration.ViewType
import top.potmot.utils.json.commonObjectMapper


abstract class BaseTest {
    abstract fun getTestEntityJson(): String

    private fun getTestEntity(): GenEntityPropertiesView =
        GenEntityPropertiesView(
            commonObjectMapper.readValue<GenEntity>(getTestEntityJson())
        )

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

    @ParameterizedTest
    @MethodSource("viewTypes")
    fun generateView(viewType: ViewType) {
        val entity = getTestEntity()

        assertEquals(
            getViewResult(viewType).trim(),
            viewType.getViewGenerator().generateViewAndEnum(entity).toString()
        )
    }

    @ParameterizedTest
    @MethodSource("languages")
    fun generateService(language: GenLanguage) {
        val entity = getTestEntity()

        assertEquals(
            getServiceResult(language).trim(),
            language.getServiceGenerator().generateService(entity).toString()
        )
    }

    companion object {
        @JvmStatic
        fun languages() = GenLanguage.entries

        @JvmStatic
        fun viewTypes() = ViewType.entries
    }
}