package top.potmot.business.single

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

ConditionMatchOptionView {
    id
}

input ConditionMatchInsertInput {
    #allScalars
    -id
    userId
    conditionId
}

input ConditionMatchUpdateInput {
    #allScalars
    id!
    userId
    conditionId
}

specification ConditionMatchSpec {
    eq(id)
    associatedIdEq(user)
    associatedIdEq(condition)
    eq(status)
    le(date)
    ge(date)
    le(money)
    ge(money)
    like/i(description)
})
"""