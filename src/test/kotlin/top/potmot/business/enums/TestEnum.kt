package top.potmot.business.enums

import top.potmot.business.AbstractEnumTest
import top.potmot.enumeration.ViewType

class TestEnum : AbstractEnumTest() {
    override fun getTestEnum() = enumData

    override fun getViewResult(viewType: ViewType) =
        when(viewType) {
            ViewType.VUE3_ELEMENT_PLUS -> vue3ElementPlusResult
        }
}