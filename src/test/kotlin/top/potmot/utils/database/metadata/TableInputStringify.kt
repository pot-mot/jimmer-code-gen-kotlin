package top.potmot.utils.database.metadata

import top.potmot.entity.database.dto.TableInput

fun TableInput.TargetOf_columns.stringify() = buildString {
    append(name)
    append(" ")
    append(comment)
    append(" ")
    append(type)
    append(" ")
    append(dataSize)
    append(",")
    append(numericPrecision)
    if (nullable) {
        append(" NULL")
    }
    if (partOfPrimaryKey != null && partOfPrimaryKey) {
        append(" PRIMARY")
    }
    if (autoIncrement != null && autoIncrement) {
        append(" AUTO_INCREMENT")
    }
    if (defaultValue != null) {
        append(" DEFAULT $defaultValue ")
    }
}

fun TableInput.TargetOf_indexes.stringify() = buildString {
    append(name)
    append(" ")
    append(uniqueIndex)
    append(" ")
    append(columnNames.joinToString(","))
}

fun TableInput.TargetOf_foreignKeys.stringify() = buildString {
    append(name)
    append(" ")
    append(referencedTableSchema)
    append(".")
    append(referencedTableName)
    append(" ")
    append(columnRefs.joinToString(",") { "${it.columnName} -> ${it.referencedColumnName}" })
    append(" ")
    append(onUpdate)
    append(" ")
    append(onDelete)
}

fun TableInput.TargetOf_checks.stringify() = buildString {
    append(name)
    append(" ")
    append(expression)
}
