package top.potmot.business.enum

import top.potmot.business.AbstractEnumTest
import top.potmot.enumeration.ViewType

class TestEnum : AbstractEnumTest() {
    override fun getTestEnumJson() = enumJson

    override fun getViewResult(viewType: ViewType) =
        when(viewType) {
            ViewType.VUE3_ELEMENT_PLUS -> vue3ElementPlusResult
        }
}