package top.potmot.model.associations

import top.potmot.context.multiple
import top.potmot.model.BaseTest
import top.potmot.entity.dto.GenConfigProperties
import top.potmot.model.languageProperties
import top.potmot.model.realFkProperties

/**
 * 属性基础测试关联，覆盖 entity 生成测试 properties 为语言和外键真伪
 */
abstract class AssociationsBaseTest : BaseTest() {
    companion object {
        @JvmStatic
        fun entityProperties(): List<GenConfigProperties> = languageProperties.multiple(realFkProperties)
    }
}
