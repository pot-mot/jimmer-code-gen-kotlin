package top.potmot.business.enums

const val dtoResult = """
(ConditionMatch.dto, export com.example.entity.ConditionMatch

ConditionMatchListView {
    #allScalars
    userId
    conditionId
}

ConditionMatchDetailView {
    #allScalars
    userId
    conditionId
}

input ConditionMatchInsertInput {
    #allScalars(this)
    -id
    userId
    conditionId
}

input ConditionMatchUpdateInput {
    #allScalars(this)
    id!
    userId
    conditionId
}

specification ConditionMatchSpec {
    eq(id)
    associationIdEq(user)
    associationIdEq(condition)
    eq(status)
    le(date)
    ge(date)
    like/i(description)
})
"""