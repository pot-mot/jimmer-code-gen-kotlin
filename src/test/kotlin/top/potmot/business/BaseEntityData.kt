package top.potmot.business

import java.time.LocalDateTime
import top.potmot.entity.dto.GenEntityBusinessView
import top.potmot.entity.dto.GenEntityBusinessView.TargetOf_idProperties
import top.potmot.entity.dto.GenEntityBusinessView.TargetOf_properties
import top.potmot.entity.dto.GenEntityBusinessView.TargetOf_properties.TargetOf_typeEntity
import top.potmot.entity.dto.GenEntityBusinessView.TargetOf_properties.TargetOf_enum
import top.potmot.enumeration.AssociationType

val baseProperty = TargetOf_properties(
    id = 0,
    name = "property",
    comment = "comment",
    remark = "remark",
    type = "kotlin.String",
    listType = false,
    typeNotNull = true,
    idProperty = false,
    idGenerationAnnotation = null,
    keyProperty = false,
    keyGroup = null,
    logicalDelete = false,
    idView = false,
    idViewTarget = null,
    associationType = null,
    mappedBy = null,
    inputNotNull = null,
    joinColumnMetas = null,
    joinTableMeta = null,
    dissociateAnnotation = null,
    longAssociation = false,
    otherAnnotation = null,
    orderKey = 0,
    entityId = 0,
    column = null,
    enum = null,
    typeEntity = null,
    createdTime = LocalDateTime.now(),
    modifiedTime = LocalDateTime.now(),
)

val idProperty = baseProperty.copy(
    id = 1,
    idProperty = true,
    name = "id",
    comment = "id",
    type = "kotlin.Int",
)

val enumProperty = baseProperty.copy(
    id = 2,
    name = "enumProperty",
    type = "Enum",
    comment = "enumProperty",
    enum = TargetOf_enum(
        id = 0,
        name = "Enum",
        packagePath = "EnumPackagePath",
        comment = "enum",
        items = listOf(
            TargetOf_enum.TargetOf_items(
                id = 0,
                name = "item1",
                comment = "comment1",
                orderKey = 0,
            )
        )
    )
)

val enumNullableProperty = enumProperty.copy(
    id = 3,
    name = "enumNullableProperty",
    typeNotNull = false,
    comment = "enumNullableProperty",
)

val toOneProperty = baseProperty.copy(
    id = 4,
    name = "toOneProperty",
    type = "ToOneEntity",
    comment = "toOneProperty",
    associationType = AssociationType.MANY_TO_ONE,
    typeEntity = TargetOf_typeEntity(
        id = 0,
        name = "ToOneEntity",
        packagePath = "ToOneEntityPackagePath",
        comment = "comment",
        idProperties = listOf(
            TargetOf_typeEntity.TargetOf_idProperties(
                id = 0,
                name = "id",
                type = "kotlin.Long",
                typeNotNull = true,
            )
        )
    )
)

val toOneNullableProperty = toOneProperty.copy(
    id = 5,
    name = "toOneNullableProperty",
    typeNotNull = false,
    comment = "toOneNullableProperty",
)

val toManyProperty = baseProperty.copy(
    id = 6,
    name = "toManyProperties",
    type = "ToManyEntity",
    comment = "toManyProperty",
    listType = true,
    associationType = AssociationType.MANY_TO_MANY,
    typeEntity = TargetOf_typeEntity(
        id = 0,
        name = "ToManyEntity",
        packagePath = "ToManyEntityPackagePath",
        comment = "comment",
        idProperties = listOf(
            TargetOf_typeEntity.TargetOf_idProperties(
                id = 0,
                name = "id",
                type = "kotlin.Long",
                typeNotNull = true,
            )
        )
    )
)

val testEntity = GenEntityBusinessView(
    id = 0,
    name = "Entity",
    comment = "comment",
    author = "author",
    remark = "remark",
    packagePath = "EntityPackage",
    createdTime = LocalDateTime.now(),
    modifiedTime = LocalDateTime.now(),
    idProperties = listOf(
        TargetOf_idProperties(idProperty.toEntity())
    ),
    properties = listOf(
        idProperty,
        enumProperty,
        enumNullableProperty,
        toOneProperty,
        toOneNullableProperty,
        toManyProperty,
    ),
    indexes = emptyList()
)