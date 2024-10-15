package top.potmot.business.enums

import com.fasterxml.jackson.module.kotlin.readValue
import top.potmot.business.BaseTest
import top.potmot.entity.dto.GenEntityPropertiesView
import top.potmot.enumeration.GenLanguage
import top.potmot.enumeration.ViewType
import top.potmot.utils.json.commonObjectMapper

class TestEnums : BaseTest() {
    override fun getTestEntity() = commonObjectMapper.readValue<GenEntityPropertiesView>(entityData)

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