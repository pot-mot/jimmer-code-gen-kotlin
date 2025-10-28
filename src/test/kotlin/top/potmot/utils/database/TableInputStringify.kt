package top.potmot.utils.database

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
    append(" ")
    append(nullable)
    append(" ")
    append(partOfPrimaryKey)
    append(" ")
    append(autoIncrement)
    append(" ")
    append(defaultValue)
    append(" ")
    append(otherConstraints?.joinToString(","))
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
