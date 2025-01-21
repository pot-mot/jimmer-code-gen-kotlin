package top.potmot.business

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import top.potmot.core.business.meta.EnumBusiness
import top.potmot.core.business.view.generate.getViewGenerator
import top.potmot.entity.dto.GenEnumGenerateView
import top.potmot.enumeration.ViewType


abstract class AbstractEnumTest {
    abstract fun getTestEnum(): GenEnumGenerateView

    abstract fun getViewResult(viewType: ViewType): String

    @ParameterizedTest
    @MethodSource("viewTypes")
    fun generateView(viewType: ViewType) {
        val enum = getTestEnum()

        assertEquals(
            getViewResult(viewType).trim(),
            viewType.getViewGenerator().generateEnum(EnumBusiness(enum)).map { it.path to it.content }.toString()
        )
    }

    companion object {
        @JvmStatic
        fun viewTypes() = ViewType.entries
    }
}