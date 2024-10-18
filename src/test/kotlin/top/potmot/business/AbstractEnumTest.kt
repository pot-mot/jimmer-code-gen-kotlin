package top.potmot.business

import com.fasterxml.jackson.module.kotlin.readValue
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import top.potmot.core.business.view.generate.getViewGenerator
import top.potmot.entity.GenEnum
import top.potmot.entity.dto.GenEnumGenerateView
import top.potmot.enumeration.ViewType
import top.potmot.utils.json.commonObjectMapper


abstract class AbstractEnumTest {
    abstract fun getTestEnumJson(): String

    private fun getTestEnum(): GenEnumGenerateView =
        GenEnumGenerateView(
            commonObjectMapper.readValue<GenEnum>(getTestEnumJson())
        )

    abstract fun getViewResult(viewType: ViewType): String

    @ParameterizedTest
    @MethodSource("viewTypes")
    fun generateView(viewType: ViewType) {
        val enum = getTestEnum()

        assertEquals(
            getViewResult(viewType).trim(),
            viewType.getViewGenerator().generateEnum(enum).toString()
        )
    }

    companion object {
        @JvmStatic
        fun viewTypes() = ViewType.entries
    }
}