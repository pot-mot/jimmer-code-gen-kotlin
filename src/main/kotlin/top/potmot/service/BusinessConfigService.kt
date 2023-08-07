package top.potmot.service

import top.potmot.model.GenColumn
import top.potmot.model.GenEntity
import top.potmot.model.input.GenColumnInput
import top.potmot.model.input.GenEntityInput

/**
 * 业务配置服务
 */
interface BusinessConfigService {
    fun configEntity(entity: GenEntityInput): GenEntity

    fun configProperty(column: GenColumnInput): GenColumn
}
