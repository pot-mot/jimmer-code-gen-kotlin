package top.potmot.business.single

import top.potmot.business.AbstractEntityTest
import top.potmot.business.testEntityBusiness
import top.potmot.enumeration.GenLanguage
import top.potmot.enumeration.ViewType

class TestSingle : AbstractEntityTest() {
    override fun getTestEntity() = testEntityBusiness

    override fun getDtoResult() = dtoResult

    override fun getViewResult(viewType: ViewType) =
        when (viewType) {
            ViewType.VUE3_ELEMENT_PLUS -> vue3ElementPlusResult
        }

    override fun getServiceResult(language: GenLanguage) =
        when (language) {
            GenLanguage.JAVA -> javaResult
            GenLanguage.KOTLIN -> kotlinResult
        }
}