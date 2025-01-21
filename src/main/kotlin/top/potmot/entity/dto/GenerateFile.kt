package top.potmot.entity.dto

import top.potmot.core.business.meta.EntityBusiness
import top.potmot.core.business.meta.EnumBusiness
import top.potmot.entity.extension.allSuperTables
import top.potmot.enumeration.GenerateTag

enum class MainType {
    Table,
    Entity,
    Enum,
}

data class MainIdName(
    val mainType: MainType,
    val idName: IdName,
)

data class TableEntityNotNullPair(
    val table: IdName,
    val entity: IdName,
)

data class TableEntityPair(
    var table: IdName? = null,
    var entity: IdName? = null,
)

data class GenerateFile(
    val path: String,
    val content: String,
    val tags: List<GenerateTag>,
    val main: MainIdName? = null,
    val tableEntities: List<TableEntityPair> = emptyList(),
    val enums: List<IdName> = emptyList(),
    val associations: List<IdName> = emptyList(),
)

data class GenerateResult(
    val files: List<GenerateFile>,
    val tableEntityPairs: List<TableEntityNotNullPair>,
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
        main = MainIdName(MainType.Table, table.idName),
        tableEntities = allSuperTables
            .map { TableEntityPair(table = it.idName) },
        associations = listOf(
            table.outAssociations.map { it.idName },
            allSuperTables.flatMap { it.outAssociations.map { a -> a.idName } },
            table.inAssociations.map { it.idName },
            allSuperTables.flatMap { it.inAssociations.map { a -> a.idName } },
        ).flatten().distinctBy { it.id }
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
        tableEntities = listOf(
            tables.map { it.idName },
            allSuperTables.map { it.idName }
        ).flatten()
            .distinctBy { it.id }
            .map { TableEntityPair(table = it) },
        associations = listOf(
            tables.flatMap { it.outAssociations.map { a -> a.idName } },
            allSuperTables.flatMap { it.outAssociations.map { a -> a.idName } },
            tables.flatMap { it.inAssociations.map { a -> a.idName } },
            allSuperTables.flatMap { it.inAssociations.map { a -> a.idName } },
        ).flatten().distinctBy { it.id }
    )
}

private val GenEnumGenerateView.idName
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
    main = MainIdName(MainType.Enum, enum.idName),
    enums = listOf(enum.idName),
)

private val EnumBusiness.idName
    get() = IdName(id, name)

fun GenerateFile(
    enum: EnumBusiness,
    path: String,
    content: String,
    tags: List<GenerateTag>,
) = GenerateFile(
    path = path,
    content = content,
    tags = tags,
    main = MainIdName(MainType.Enum, enum.idName),
    enums = listOf(enum.idName),
)

private val GenEntityGenerateView.idName
    get() = IdName(id, name)

private val GenEntityGenerateView.TargetOf_properties.TargetOf_typeTable.TargetOf_entity.idName
    get() = IdName(id, name)

private val GenEntityGenerateView.TargetOf_properties.TargetOf_enum.idName
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
    main = MainIdName(MainType.Entity, entity.idName),
    tableEntities = entity.properties
        .mapNotNull { it.typeTable?.entity?.idName }
        .distinctBy { it.id }
        .map { TableEntityPair(entity = it) },
    enums = entity.properties
        .mapNotNull { it.enum?.idName }
        .distinctBy { it.id }
)

private val GenEntityBusinessView.idName
    get() = IdName(id, name)

fun GenerateFile(
    entityBusiness: EntityBusiness,
    path: String,
    content: String,
    tags: List<GenerateTag>,
) = GenerateFile(
    path = path,
    content = content,
    tags = tags,
    main = MainIdName(MainType.Entity, entityBusiness.entity.idName),
    tableEntities = entityBusiness.associationProperty
        .map { it.typeEntity.idName }
        .distinctBy { it.id }
        .map { TableEntityPair(entity = it) },
    enums = entityBusiness.enums
        .map { it.idName }
        .distinctBy { it.id }
)

fun createGenerateFileByEntities(
    entities: Iterable<EntityBusiness>,
    path: String,
    content: String,
    tags: List<GenerateTag>,
) = GenerateFile(
    path = path,
    content = content,
    tags = tags,
    tableEntities = entities
        .distinctBy { it.id }
        .map { TableEntityPair(entity = IdName(it.id, it.name)) },
)