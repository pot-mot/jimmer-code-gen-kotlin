package top.potmot.entity.model.dto

fun ModelInsertInput.toHistory(
    savedModel: ModelNoJsonView
): ModelHistoryInput {
    return ModelHistoryInput(
        modelId = savedModel.id,
        name = name,
        description = description,
        modifiedTime = savedModel.modifiedTime,
        databaseType = databaseType,
        databaseNameStrategy = databaseNameStrategy,
        defaultForeignKeyType = defaultForeignKeyType,
        jvmLanguage = jvmLanguage,
        defaultEnumerationStrategy = defaultEnumerationStrategy,
        jsonData = jsonData
    )
}

fun ModelUpdateInput.toHistory(
    savedModel: ModelNoJsonView
): ModelHistoryInput {
    return ModelHistoryInput(
        modelId = savedModel.id,
        name = name,
        description = description,
        modifiedTime = savedModel.modifiedTime,
        databaseType = databaseType,
        databaseNameStrategy = databaseNameStrategy,
        defaultForeignKeyType = defaultForeignKeyType,
        jvmLanguage = jvmLanguage,
        defaultEnumerationStrategy = defaultEnumerationStrategy,
        jsonData = jsonData
    )
}
