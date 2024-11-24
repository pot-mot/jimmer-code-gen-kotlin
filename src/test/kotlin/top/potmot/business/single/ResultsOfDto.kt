package top.potmot.business.single

const val dtoResult = """
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

input EntityInsertInput {
    #allScalars
    -id
    id(toOneProperty)
    id(toOneNullableProperty)
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