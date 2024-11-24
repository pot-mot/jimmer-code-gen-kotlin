package top.potmot.entity.dto

import top.potmot.entity.dto.share.GenerateEntity
import top.potmot.entity.dto.share.GenerateEnum
import top.potmot.entity.extension.allSuperTables
import top.potmot.enumeration.GenerateTag

data class GenerateFile(
    val path: String,
    val content: String,
    val tags: List<GenerateTag>,
    val mainTable: IdName? = null,
    val tables: List<IdName> = emptyList(),
    val mainEntity: IdName? = null,
    val entities: List<IdName> = emptyList(),
    val enums: List<IdName> = emptyList(),
    val associations: List<IdName> = emptyList(),
)

private val GenTableGenerateView.idName
    get() = IdName(id, name)

private val GenTableGenerateView.TargetOf_outAssociations.idName
    get() = IdName(id, name)

private val GenTableGenerateView.TargetOf_inAssociations.idName
    get() = IdName(id, name)

fun GenerateFile(
    table: GenTableGenerateView,
    path: String,
    content: String,
    tags: List<GenerateTag>,
): GenerateFile {
    val allSuperTables = table.allSuperTables()

    return GenerateFile(
        path = path,
        content = content,
        tags = tags,
        mainTable = table.idName,
        tables = allSuperTables.map { it.idName },
        associations = listOf(
            table.outAssociations.map { it.idName },
            allSuperTables.flatMap { it.outAssociations.map { a -> a.idName } },
            table.inAssociations.map { it.idName },
            allSuperTables.flatMap { it.inAssociations.map { a -> a.idName } },
        ).flatten()
    )
}

fun createGenerateFileByTables(
    tables: Iterable<GenTableGenerateView>,
    path: String,
    content: String,
    tags: List<GenerateTag>,
): GenerateFile {
    val allSuperTables = tables.flatMap { it.allSuperTables() }.distinctBy { it.id }

    return GenerateFile(
        path = path,
        content = content,
        tags = tags,
        tables = tables.map { it.idName } +
                allSuperTables.map { it.idName },
        associations = listOf(
            tables.flatMap { it.outAssociations.map { a -> a.idName } },
            allSuperTables.flatMap { it.outAssociations.map { a -> a.idName } },
            tables.flatMap { it.inAssociations.map { a -> a.idName } },
            allSuperTables.flatMap { it.inAssociations.map { a -> a.idName } },
        ).flatten()
    )
}

private val GenerateEnum.idName
    get() = IdName(id, name)

fun GenerateFile(
    enum: GenEnumGenerateView,
    path: String,
    content: String,
    tags: List<GenerateTag>,
) = GenerateFile(
    path = path,
    content = content,
    tags = tags,
    enums = listOf(enum.idName),
)

private val GenerateEntity.idName
    get() = IdName(id, name)

private val GenEntityGenerateView.TargetOf_table.idName
    get() = IdName(id, name)

fun GenerateFile(
    entity: GenEntityGenerateView,
    path: String,
    content: String,
    tags: List<GenerateTag>,
) = GenerateFile(
    path = path,
    content = content,
    tags = tags,
    mainTable = entity.table.idName,
    mainEntity = entity.idName,
    entities = entity.properties.mapNotNull { it.typeTable?.entity?.idName },
    enums = entity.properties.mapNotNull { it.enum?.idName }
)

fun GenerateFile(
    entity: GenEntityBusinessView,
    path: String,
    content: String,
    tags: List<GenerateTag>,
) = GenerateFile(
    path = path,
    content = content,
    tags = tags,
    mainEntity = entity.idName,
    entities = entity.properties.mapNotNull { it.typeEntity?.idName },
    enums = entity.properties.mapNotNull { it.enum?.idName }
)

fun GenerateFile(
    entity: GenerateEntity,
    path: String,
    content: String,
    tags: List<GenerateTag>,
) = GenerateFile(
    path = path,
    content = content,
    tags = tags,
    mainEntity = IdName(entity.id, entity.name),
)

fun createGenerateFileByEntities(
    entities: Iterable<GenerateEntity>,
    path: String,
    content: String,
    tags: List<GenerateTag>,
) = GenerateFile(
    path = path,
    content = content,
    tags = tags,
    entities = entities.map { IdName(it.id, it.name) },
)