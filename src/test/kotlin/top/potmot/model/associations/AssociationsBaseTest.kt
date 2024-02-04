package top.potmot.model.associations

import top.potmot.context.multiple
import top.potmot.model.BaseTest
import top.potmot.model.dto.GenConfigProperties
import top.potmot.model.realFkProperties
import top.potmot.service.ModelService
import top.potmot.service.PreviewService

abstract class AssociationsBaseTest(
    modelService: ModelService,
    previewService: PreviewService,
): BaseTest(
    modelService,
    previewService
) {
    override val entityTestProperties: List<GenConfigProperties>
        get() = super.entityTestProperties.multiple(realFkProperties)
}
