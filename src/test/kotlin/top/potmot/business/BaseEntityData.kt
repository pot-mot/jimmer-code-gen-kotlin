package top.potmot.business

import java.sql.Types
import java.time.LocalDateTime
import top.potmot.core.business.meta.EntityBusiness
import top.potmot.core.business.meta.EnumBusiness
import top.potmot.entity.dto.toFlat
import top.potmot.entity.dto.GenEntityBusinessView
import top.potmot.entity.dto.GenEntityBusinessView.TargetOf_properties
import top.potmot.entity.dto.GenEntityBusinessView.TargetOf_properties.TargetOf_column
import top.potmot.entity.dto.GenEnumGenerateView
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
    column = TargetOf_column(typeCode = Types.VARCHAR, dataSize = 0, numericPrecision = 0),
    enumId = null,
    typeEntityId = null,
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
    column = TargetOf_column(typeCode = Types.INTEGER, dataSize = 0, numericPrecision = 0),
)

val baseEntity = GenEntityBusinessView(
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
    properties = listOf(idProperty),
    indexes = emptyList(),
)

val testEnum = GenEnumGenerateView(
    id = testId++,
    name = "Enum",
    packagePath = "EnumPackagePath",
    comment = "enum",
    remark = "remark",
    items = listOf(
        GenEnumGenerateView.TargetOf_items(
            id = testId++,
            name = "item1",
            comment = "comment1",
            mappedValue = "item1",
            orderKey = 0,
            remark = "remark",
            defaultItem = true
        ),
    )
)

val testEnumBusiness = EnumBusiness(testEnum)

val enumProperty = baseProperty.copy(
    id = testId++,
    name = "enumProperty",
    type = testEnum.name,
    comment = "enumProperty",
    enumId = testEnum.id
)

val enumNullableProperty = enumProperty.copy(
    id = testId++,
    name = "enumNullableProperty",
    typeNotNull = false,
    comment = "enumNullableProperty",
)

val toOneEntity = baseEntity.copy(
    id = testId++,
    name = "ToOneEntity",
    packagePath = "ToOneEntityPackagePath",
    comment = "comment",
    properties = listOf(
        idProperty.copy(
            id = testId++,
        ),
    ),
)

val toOneProperty = baseProperty.copy(
    id = testId++,
    name = "toOneProperty",
    type = "ToOneEntity",
    comment = "toOneProperty",
    associationType = AssociationType.MANY_TO_ONE,
    column = TargetOf_column(typeCode = Types.INTEGER, dataSize = 0, numericPrecision = 0),
    typeEntityId = toOneEntity.id
)

val toOneNullableProperty = toOneProperty.copy(
    id = testId++,
    name = "toOneNullableProperty",
    typeNotNull = false,
    comment = "toOneNullableProperty",
)


val toManyEntity = baseEntity.copy(
    id = testId++,
    name = "ToManyEntity",
    packagePath = "ToManyEntityPackagePath",
    comment = "comment",
    properties = listOf(
        idProperty.copy(
            id = testId++,
        ),
    ),
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
    column = TargetOf_column(typeCode = Types.INTEGER, dataSize = 0, numericPrecision = 0),
    typeEntityId = toManyEntity.id
)

val testEntity = baseEntity.copy(
    id = testId++,
    properties = listOf(
        idProperty,
        enumProperty,
        enumNullableProperty,
        toOneProperty,
        toOneNullableProperty,
        toManyProperty,
    ),
)

val toOneIdView = toOneProperty.copy(
    id = testId++,
    name = toOneProperty.name + "Id",
    idView = true,
    idViewTarget = toOneProperty.name,
    typeEntityId = null,
    type = idProperty.type,
)

val toOneNullableIdView = toOneNullableProperty.copy(
    id = testId++,
    name = toOneNullableProperty.name + "Id",
    idView = true,
    idViewTarget = toOneNullableProperty.name,
    typeEntityId = null,
    type = idProperty.type,
)

val toManyIdView = toManyProperty.copy(
    id = testId++,
    name = toManyProperty.name.toSingular() + "Ids",
    idView = true,
    idViewTarget = toManyProperty.name,
    typeEntityId = null,
    type = idProperty.type,
)

val idViewTestEntity = baseEntity.copy(
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


private val superTestEntity = baseEntity.copy(
    id = testId++,
    name = "BASE_ENTITY",
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

val entityIdMap = mapOf(
    testEntity.id to testEntity,
    toOneEntity.id to toOneEntity,
    toManyEntity.id to toManyEntity,
    idViewTestEntity.id to idViewTestEntity,
    withSuperTestEntity.id to withSuperTestEntity,
)

val enumIdMap = mapOf(
    testEnum.id to testEnumBusiness,
)

val testEntityBusiness = EntityBusiness(testEntity, entityIdMap, enumIdMap)

val idViewTestEntityBusiness = EntityBusiness(idViewTestEntity, entityIdMap, enumIdMap)

val withSuperTestEntityBusiness = EntityBusiness(withSuperTestEntity.toFlat(), entityIdMap, enumIdMap)
