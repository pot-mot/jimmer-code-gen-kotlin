package top.potmot.core.business.test.generate

import top.potmot.core.business.meta.RootEntityBusiness
import top.potmot.entity.dto.GenerateFile
import top.potmot.enumeration.GenerateTag

interface TestGenerator {
    val suffix: String

    fun stringifyTest(entity: RootEntityBusiness): String

    fun generateTest(entity: RootEntityBusiness): GenerateFile = GenerateFile(
        entity,
        "${entity.testFilePath}/${entity.testName}.$suffix",
        stringifyTest(entity),
        tags = listOf(GenerateTag.BackEnd, GenerateTag.Entity, GenerateTag.CrudTest)
    )

    fun generateTest(entities: Iterable<RootEntityBusiness>): List<GenerateFile> =
        entities
            .map { generateTest(it) }
            .distinctBy { it.path }
            .sortedBy { it.path }
}
