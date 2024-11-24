package top.potmot.business.crudPart

const val addOnlyDtoResult = """
(dto/Entity.dto, export EntityPackage.Entity

EntityListView {
    #allScalars
    id(toOneProperty)
    id(toOneNullableProperty)
}

EntityOptionView {
    id
}

input EntityInsertInput {
    #allScalars
    -id
    id(toOneProperty)
    id(toOneNullableProperty)
}

specification EntitySpec {
    eq(id)
    eq(enumProperty)
    eq(enumNullableProperty)
    associatedIdEq(toOneProperty)
    associatedIdEq(toOneNullableProperty)
    associatedIdIn(toManyProperties) as toManyPropertyIds
})
"""

const val editOnlyDtoResult = """
(dto/Entity.dto, export EntityPackage.Entity

EntityListView {
    #allScalars
    id(toOneProperty)
    id(toOneNullableProperty)
}

EntityDetailView {
    #allScalars
    id(toOneProperty)
    id(toOneNullableProperty)
}

EntityOptionView {
    id
}

input EntityUpdateInput {
    #allScalars
    id!
    id(toOneProperty)
    id(toOneNullableProperty)
}

specification EntitySpec {
    eq(id)
    eq(enumProperty)
    eq(enumNullableProperty)
    associatedIdEq(toOneProperty)
    associatedIdEq(toOneNullableProperty)
    associatedIdIn(toManyProperties) as toManyPropertyIds
})
"""

const val queryOnlyDtoResult = """
(dto/Entity.dto, export EntityPackage.Entity

EntityListView {
    #allScalars
    id(toOneProperty)
    id(toOneNullableProperty)
}

EntityOptionView {
    id
}

specification EntitySpec {
    eq(id)
    eq(enumProperty)
    eq(enumNullableProperty)
    associatedIdEq(toOneProperty)
    associatedIdEq(toOneNullableProperty)
    associatedIdIn(toManyProperties) as toManyPropertyIds
})
"""

const val deleteOnlyDtoResult = """
(dto/Entity.dto, export EntityPackage.Entity

EntityListView {
    #allScalars
    id(toOneProperty)
    id(toOneNullableProperty)
}

EntityOptionView {
    id
}

specification EntitySpec {
    eq(id)
    eq(enumProperty)
    eq(enumNullableProperty)
    associatedIdEq(toOneProperty)
    associatedIdEq(toOneNullableProperty)
    associatedIdIn(toManyProperties) as toManyPropertyIds
})
"""