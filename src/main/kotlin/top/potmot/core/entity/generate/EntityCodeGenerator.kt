package top.potmot.core.entity.generate

import top.potmot.entity.dto.GenEntityGenerateView
import top.potmot.entity.dto.GenEnumGenerateView
import top.potmot.entity.dto.GenerateFile
import top.potmot.enumeration.GenerateTag
import top.potmot.error.GenerateException

abstract class EntityCodeGenerator {
    abstract fun getFileSuffix(): String

    protected abstract fun stringify(entity: GenEntityGenerateView): String

    protected abstract fun stringify(enum: GenEnumGenerateView): String

    @Throws(GenerateException::class)
    fun generateEntity(
        entity: GenEntityGenerateView,
    ) = GenerateFile(
        entity,
        "${entity.packagePath.replace(".", "/")}/${entity.name}${getFileSuffix()}",
        stringify(entity),
        listOf(GenerateTag.BackEnd, GenerateTag.Entity)
    )

    @Throws(GenerateException::class)
    fun generateEntity(
        entities: Iterable<GenEntityGenerateView>,
    ): List<GenerateFile> =
        entities
            .map { generateEntity(it) }
            .sortedBy { it.path }

    fun generateEnum(
        enum: GenEnumGenerateView,
    ) = GenerateFile(
        enum,
        "${enum.packagePath.replace(".", "/")}/${enum.name}${getFileSuffix()}",
        stringify(enum),
        listOf(GenerateTag.BackEnd, GenerateTag.Enum)
    )

    fun generateEnum(
        enums: Iterable<GenEnumGenerateView>,
    ): List<GenerateFile> =
        enums
            .map { generateEnum(it) }
            .sortedBy { it.path }
}
