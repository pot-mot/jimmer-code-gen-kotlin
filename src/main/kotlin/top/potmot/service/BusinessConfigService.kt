package top.potmot.service

import top.potmot.model.GenColumn
import top.potmot.model.GenEntity

/**
 * 业务配置服务
 */
interface BusinessConfigService {
    // TODO ConfigEntityInput
    fun configEntity(entity: GenEntity): GenEntity

    // TODO ConfigColumnInput
    fun configProperty(column: GenColumn): GenColumn
}
