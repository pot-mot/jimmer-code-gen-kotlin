package top.potmot.entity.dto

import top.potmot.core.business.meta.EntityBusiness
import top.potmot.core.business.meta.EnumBusiness
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
    val entity: IdNamePackagePath,
)

data class TableEntityPair(
    var table: IdName? = null,
    var entity: IdNamePackagePath? = null,
)

data class GenerateFile(
    val path: String,
    val content: String,
    val tags: List<GenerateTag>,
    val main: MainIdName? = null,
    val tableEntities: List<TableEntityPair> = emptyList(),
    val enums: List<IdNamePackagePath> = emptyList(),
    val associations: List<IdName> = emptyList(),
) {
    val name by lazy {
        path.substringAfterLast("/")
    }
}

data class GenerateResult(
    val files: List<GenerateFile>,
    val tableEntityPairs: List<TableEntityNotNullPair>,
    val enums: List<IdNamePackagePath>,
)

private val GenTableGenerateView.idName
    get() = IdName(id, name)

private val GenTableGenerateView.TargetOf_outAssociations.idName
    get() = IdName(id, name)

private val GenTableGenerateView.TargetOf_inAssociations.idName
    get() = IdName(id, name)

private fun GenTableGenerateView.allSuperTables(): List<GenTableGenerateView> {
    val result = superTables ?: listOf()
    return result + result.flatMap { it.allSuperTables() }
}

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
)

private val GenEntityGenerateView.idName
    get() = IdName(id, name)

private val GenEntityGenerateView.TargetOf_properties.TargetOf_typeTable.TargetOf_entity.idNamePackagePath
    get() = IdNamePackagePath(id, name, packagePath)

private val GenEntityGenerateView.TargetOf_properties.TargetOf_enum.idNamePackagePath
    get() = IdNamePackagePath(id, name, packagePath)

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
        .mapNotNull { it.typeTable?.entity?.idNamePackagePath }
        .distinctBy { it.id }
        .map { TableEntityPair(entity = it) },
    enums = entity.properties
        .mapNotNull { it.enum?.idNamePackagePath }
        .distinctBy { it.id }
)

private val GenEntityBusinessView.idNamePackagePath
    get() = IdNamePackagePath(id, name, packagePath)

private val EntityBusiness.idName
    get() = IdName(id, name)

private val GenEnumGenerateView.idNamePackagePath
    get() = IdNamePackagePath(id, name, packagePath)

fun GenerateFile(
    entityBusiness: EntityBusiness,
    path: String,
    content: String,
    tags: List<GenerateTag>,
) = GenerateFile(
    path = path,
    content = content,
    tags = tags,
    main = MainIdName(MainType.Entity, entityBusiness.idName),
    tableEntities = entityBusiness.associationProperties
        .map { it.typeEntity.idNamePackagePath }
        .distinctBy { it.id }
        .map { TableEntityPair(entity = it) },
    enums = entityBusiness.properties
        .filterIsInstance<EnumBusiness>()
        .map { it.enum.idNamePackagePath }
        .distinctBy { it.id }
)

private val EntityBusiness.idNamePackagePath
    get() = IdNamePackagePath(id, name, packagePath)

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
        .map { TableEntityPair(entity = it.idNamePackagePath) },
)