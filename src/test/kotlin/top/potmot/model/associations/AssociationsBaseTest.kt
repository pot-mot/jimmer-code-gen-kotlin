package top.potmot.model.associations

import top.potmot.context.multiple
import top.potmot.model.BaseTest
import top.potmot.model.dto.GenConfigProperties
import top.potmot.model.languageProperties
import top.potmot.model.realFkProperties
import top.potmot.service.ConvertService
import top.potmot.service.ModelService
import top.potmot.service.PreviewService

/**
 * 属性基础测试关联，覆盖 entity 生成测试 properties 为语言和外键真伪
 */
abstract class AssociationsBaseTest(
    modelService: ModelService,
    convertService: ConvertService,
    previewService: PreviewService,
): BaseTest(
    modelService,
    convertService,
    previewService
) {
    override val entityTestProperties: List<GenConfigProperties>
        get() = languageProperties.multiple(realFkProperties)
}
