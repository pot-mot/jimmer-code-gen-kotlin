package top.potmot.entity.model.dto

import top.potmot.entity.model.Model

fun ModelInsertInput.toHistory(savedModel: Model): ModelHistoryInput {
    return ModelHistoryInput(
        modelId = savedModel.id,
        name = name,
        description = description,
        modifiedTime = modifiedTime,
        databaseType = databaseType,
        databaseNameStrategy = databaseNameStrategy,
        defaultForeignKeyType = defaultForeignKeyType,
        jvmLanguage = jvmLanguage,
        defaultEnumerationStrategy = defaultEnumerationStrategy,
        jsonData = jsonData
    )
}

fun ModelUpdateInput.toHistory(savedModel: Model): ModelHistoryInput {
    return ModelHistoryInput(
        modelId = savedModel.id,
        name = name,
        description = description,
        modifiedTime = modifiedTime,
        databaseType = databaseType,
        databaseNameStrategy = databaseNameStrategy,
        defaultForeignKeyType = defaultForeignKeyType,
        jvmLanguage = jvmLanguage,
        defaultEnumerationStrategy = defaultEnumerationStrategy,
        jsonData = jsonData
    )
}
