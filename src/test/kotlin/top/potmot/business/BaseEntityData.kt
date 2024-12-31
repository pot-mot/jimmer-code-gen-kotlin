package top.potmot.business

import java.time.LocalDateTime
import top.potmot.core.business.utils.entity.idProperty
import top.potmot.entity.dto.GenEntityBusinessView
import top.potmot.entity.dto.GenEntityBusinessView.TargetOf_idProperties
import top.potmot.entity.dto.GenEntityBusinessView.TargetOf_properties
import top.potmot.entity.dto.GenEntityBusinessView.TargetOf_properties.TargetOf_enum
import top.potmot.entity.dto.GenEntityBusinessView.TargetOf_properties.TargetOf_typeEntity
import top.potmot.enumeration.AssociationType
import top.potmot.utils.string.toSingular

private var testId = 1L

val baseProperty = TargetOf_properties(
    id = testId++,
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
    inListView = true,
    inDetailView = true,
    inInsertInput = true,
    inUpdateInput = true,
    inSpecification = true,
    inLongAssociationView = true,
    inLongAssociationInput = true,
    entityId = 0,
    column = null,
    enum = null,
    typeEntity = null,
    createdTime = LocalDateTime.now(),
    modifiedTime = LocalDateTime.now(),
)

val idProperty = baseProperty.copy(
    id = testId++,
    idProperty = true,
    idGenerationAnnotation = "@GeneratedValue(strategy = GenerationType.IDENTITY)",
    name = "id",
    comment = "id",
    type = "kotlin.Int",
    inInsertInput = false,
    inOptionView = true,
)

val enumProperty = baseProperty.copy(
    id = testId++,
    name = "enumProperty",
    type = "Enum",
    comment = "enumProperty",
    enum = TargetOf_enum(
        id = testId++,
        name = "Enum",
        packagePath = "EnumPackagePath",
        comment = "enum",
        items = listOf(
            TargetOf_enum.TargetOf_items(
                id = testId++,
                name = "item1",
                comment = "comment1",
                orderKey = 0,
                defaultItem = true
            ),
        )
    )
)

val enumNullableProperty = enumProperty.copy(
    id = testId++,
    name = "enumNullableProperty",
    typeNotNull = false,
    comment = "enumNullableProperty",
)

val toOneProperty = baseProperty.copy(
    id = testId++,
    name = "toOneProperty",
    type = "ToOneEntity",
    comment = "toOneProperty",
    associationType = AssociationType.MANY_TO_ONE,
    typeEntity = TargetOf_typeEntity(
        id = testId++,
        name = "ToOneEntity",
        packagePath = "ToOneEntityPackagePath",
        comment = "comment",
        idProperties = listOf(
            TargetOf_typeEntity.TargetOf_idProperties(
                id = testId++,
                name = "id",
                type = "kotlin.Long",
                typeNotNull = true,
            )
        ),
        shortViewProperties = emptyList()
    )
)

val toOneNullableProperty = toOneProperty.copy(
    id = testId++,
    name = "toOneNullableProperty",
    typeNotNull = false,
    comment = "toOneNullableProperty",
)

val toManyProperty = baseProperty.copy(
    id = testId++,
    name = "toManyProperties",
    type = "ToManyEntity",
    comment = "toManyProperty",
    listType = true,
    associationType = AssociationType.MANY_TO_MANY,
    inListView = false,
    inDetailView = true,
    inInsertInput = false,
    inUpdateInput = false,
    inSpecification = true,
    inOptionView = false,
    inShortAssociationView = false,
    inLongAssociationView = false,
    inLongAssociationInput = false,
    typeEntity = TargetOf_typeEntity(
        id = testId++,
        name = "ToManyEntity",
        packagePath = "ToManyEntityPackagePath",
        comment = "comment",
        idProperties = listOf(
            TargetOf_typeEntity.TargetOf_idProperties(
                id = testId++,
                name = "id",
                type = "kotlin.Long",
                typeNotNull = true,
            )
        ),
        shortViewProperties = emptyList()
    )
)

val testEntity = GenEntityBusinessView(
    id = testId++,
    name = "Entity",
    comment = "comment",
    author = "author",
    canQuery = true,
    canAdd = true,
    canEdit = true,
    canDelete = true,
    hasPage = true,
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
    indexes = emptyList(),
    longProperties = emptyList()
)

val toOneIdView = toOneProperty.copy(
    id = testId++,
    name = toOneProperty.name + "Id",
    idView = true,
    idViewTarget = toOneProperty.name,
    typeEntity = null,
    type = toOneProperty.typeEntity!!.idProperty.type,
)

val toOneNullableIdView = toOneNullableProperty.copy(
    id = testId++,
    name = toOneNullableProperty.name + "Id",
    idView = true,
    idViewTarget = toOneNullableProperty.name,
    typeEntity = null,
    type = toOneNullableProperty.typeEntity!!.idProperty.type,

    )

val toManyIdView = toManyProperty.copy(
    id = testId++,
    name = toManyProperty.name.toSingular() + "Ids",
    idView = true,
    idViewTarget = toManyProperty.name,
    typeEntity = null,
    type = toManyProperty.typeEntity!!.idProperty.type,
)

val idViewTestEntity = testEntity.copy(
    properties = listOf(
        idProperty,
        enumProperty,
        enumNullableProperty,
        toOneProperty,
        toOneIdView,
        toOneNullableProperty,
        toOneNullableIdView,
        toManyProperty,
        toManyIdView,
    )
)


private val superTestEntity = testEntity.copy(
    id = testId++,
    name = "BASE_ENTITY",
    idProperties = listOf(),
    properties = listOf(
        toOneProperty.copy(
            id = testId++,
            name = "createdBy",
            comment = "createdBy",
        )
    )
)

val withSuperTestEntity = testEntity.copy(
    superEntities = listOf(
        superTestEntity
    )
)