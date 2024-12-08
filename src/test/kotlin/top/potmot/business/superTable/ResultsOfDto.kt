package top.potmot.business.superTable

const val dtoResult = """
(dto/Entity.dto, export EntityPackage.Entity

EntityListView {
    #allScalars
    id(toOneProperty)
    id(toOneNullableProperty)
    id(createdBy)
}

EntityDetailView {
    #allScalars
    id(toOneProperty)
    id(toOneNullableProperty)
    id(toManyProperties) as toManyPropertyIds
    id(createdBy)
}

EntityOptionView {
    id
}

input EntityInsertInput {
    #allScalars
    -id
    id(toOneProperty)
    id(toOneNullableProperty)
    id(createdBy)
}

EntityUpdateFillView {
    #allScalars
    id(toOneProperty)
    id(toOneNullableProperty)
    id(createdBy)
}

input EntityUpdateInput {
    #allScalars
    id!
    id(toOneProperty)
    id(toOneNullableProperty)
    id(createdBy)
}

specification EntitySpec {
    eq(id)
    eq(enumProperty)
    eq(enumNullableProperty)
    associatedIdEq(toOneProperty)
    associatedIdEq(toOneNullableProperty)
    associatedIdIn(toManyProperties) as toManyPropertyIds
    associatedIdEq(createdBy)
})
"""