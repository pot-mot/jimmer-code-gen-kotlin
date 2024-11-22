package top.potmot.business.crudPart

import top.potmot.business.AbstractEntityTest
import top.potmot.enumeration.GenLanguage
import top.potmot.enumeration.ViewType

class TestAddOnly : AbstractEntityTest() {
    override fun getTestEntity() = addOnlyEntity

    override fun getDtoResult() = addOnlyDtoResult

    override fun getViewResult(viewType: ViewType) =
        when (viewType) {
            ViewType.VUE3_ELEMENT_PLUS -> vue3ElementPlusResult
        }

    override fun getServiceResult(language: GenLanguage) =
        when (language) {
            GenLanguage.JAVA -> addOnlyJavaResult
            GenLanguage.KOTLIN -> addOnlyKotlinResult
        }
}

class TestEditOnly : AbstractEntityTest() {
    override fun getTestEntity() = editOnlyEntity

    override fun getDtoResult() = editOnlyDtoResult

    override fun getViewResult(viewType: ViewType) =
        when (viewType) {
            ViewType.VUE3_ELEMENT_PLUS -> vue3ElementPlusResult
        }

    override fun getServiceResult(language: GenLanguage) =
        when (language) {
            GenLanguage.JAVA -> editOnlyJavaResult
            GenLanguage.KOTLIN -> editOnlyKotlinResult
        }
}

class TestQueryOnly : AbstractEntityTest() {
    override fun getTestEntity() = queryOnlyEntity

    override fun getDtoResult() = queryOnlyDtoResult

    override fun getViewResult(viewType: ViewType) =
        when (viewType) {
            ViewType.VUE3_ELEMENT_PLUS -> vue3ElementPlusResult
        }

    override fun getServiceResult(language: GenLanguage) =
        when (language) {
            GenLanguage.JAVA -> queryOnlyJavaResult
            GenLanguage.KOTLIN -> queryOnlyKotlinResult
        }
}

class TestDeleteOnly : AbstractEntityTest() {
    override fun getTestEntity() = deleteOnlyEntity

    override fun getDtoResult() = deleteOnlyDtoResult

    override fun getViewResult(viewType: ViewType) =
        when (viewType) {
            ViewType.VUE3_ELEMENT_PLUS -> vue3ElementPlusResult
        }

    override fun getServiceResult(language: GenLanguage) =
        when (language) {
            GenLanguage.JAVA -> deleteOnlyJavaResult
            GenLanguage.KOTLIN -> deleteOnlyKotlinResult
        }
}