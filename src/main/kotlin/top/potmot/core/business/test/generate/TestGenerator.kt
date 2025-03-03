package top.potmot.core.business.test.generate

import top.potmot.core.business.meta.RootEntityBusiness
import top.potmot.core.business.meta.SubEntityBusiness
import top.potmot.entity.dto.GenerateFile
import top.potmot.enumeration.GenerateTag

abstract class TestGenerator {
    abstract val suffix: String

    protected abstract fun stringifyTest(entity: RootEntityBusiness): String

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

data class LazyInsertId(
    val entity: SubEntityBusiness,
    val name: String,
    val insertByService: Boolean = entity.canAdd,
)