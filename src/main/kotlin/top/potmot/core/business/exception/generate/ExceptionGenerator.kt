package top.potmot.core.business.exception.generate

import top.potmot.core.business.meta.RootEntityBusiness
import top.potmot.entity.dto.GenerateFile

interface ExceptionGenerator {
    val suffix: String

    // TODO
    fun generateExceptions(entities: Iterable<RootEntityBusiness>): List<GenerateFile>
}