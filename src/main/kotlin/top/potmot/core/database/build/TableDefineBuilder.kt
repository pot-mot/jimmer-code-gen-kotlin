package top.potmot.core.database.build

import org.babyfish.jimmer.sql.DissociateAction
import top.potmot.core.entity.convert.clearColumnName
import top.potmot.core.entity.convert.clearTableComment
import top.potmot.core.entity.convert.clearTableName
import top.potmot.core.meta.getAssociationMeta
import top.potmot.core.meta.getFkName
import top.potmot.core.meta.getMappingTableName
import top.potmot.core.meta.getMeta
import top.potmot.utils.template.TemplateBuilder
import top.potmot.enumeration.AssociationType.*
import top.potmot.model.GenColumn
import top.potmot.model.dto.GenTableAssociationsView
import top.potmot.model.extension.pkColumns

abstract class TableDefineBuilder : TemplateBuilder() {
    abstract fun String.escape(): String

    abstract fun GenTableAssociationsView.TargetOf_columns.columnStringify(): String

    abstract fun getDatabaseTypeString(
        column: GenColumn,
        mappingTable: Boolean = false,
    ): String

    open fun createTable(
        name: String,
        lines: List<String>,
        append: String = ""
    ): String =
        buildString {
            append("CREATE TABLE ${name.escape()} (\n")
            append("    ${lines.joinToString(",\n    ")}\n")
            append(")${append};")
        }

    open fun dropTable(
        name: String,
        append: String = ""
    ): String =
        "DROP TABLE IF EXISTS ${name.escape()}${if (append.isBlank()) "" else " $append"};"

    fun alterTable(
        name: String
    ): String =
        "ALTER TABLE ${name.escape()} "

    open fun createPkLine(
        table: GenTableAssociationsView,
    ): String =
        pkDefine(table.pkColumns().map { it.name })

    open fun createTable(
        table: GenTableAssociationsView,
        otherLines: List<String> = emptyList(),
        append: String = "",
        withIndex: Boolean = true,
    ): String {
        val lines = mutableListOf<String>()

        lines.addAll(table.columns.map { it.columnStringify() })

        lines.add(createPkLine(table))

        lines.addAll(otherLines)

        val tableDefine = createTable(table.name, lines, append)

        val indexes = table.indexes.map {index ->
            val columnNames = table.columns.filter { it.id in index.columnIds }.map { it.name }

            if (columnNames.size != index.columnIds.size) {
                throw RuntimeException("Unique index column count not match: \n table: ${table.name}, unique index: ${index.name}")
            }

            createIndex(
                table.name,
                columnNames,
                index.uniqueIndex,
                index.name,
            )
        }

        return tableDefine + (if (withIndex) "\n${indexes.joinToString("\n")}" else "")
    }

    open fun addConstraint(
        tableName: String,
        constraintName: String
    ): String =
        "${alterTable(tableName)}ADD CONSTRAINT ${constraintName.escape()} "

    open fun pkDefine(
        columnNames: List<String>
    ): String =
        "PRIMARY KEY (${columnNames.joinToString(",") { it.escape() }})"

    open fun fkDefine(
        sourceTableName: String,
        sourceColumnNames: List<String>,

        targetTableName: String,
        targetColumnNames: List<String>,

        dissociateAction: DissociateAction?
    ): String =
        buildString {
            append("FOREIGN KEY (${sourceColumnNames.joinToString(", ") { it.escape() }}) REFERENCES ${targetTableName.escape()} (${targetColumnNames.joinToString(", ") { it.escape() }})")
            append(" ${dissociateAction?.toOnDeleteAction() ?: ""} ON UPDATE RESTRICT")
        }


    open fun createPkName(
        tableName: String,
        columnNames: List<String>
    ): String =
        "pk_${tableName}"

    open fun createPkConstraint(
        tableName: String,
        columnNames: List<String>,

        constraintName: String =
            createPkName(tableName, columnNames)
    ): String =
        "${addConstraint(tableName, constraintName)}${pkDefine(columnNames)};"

    open fun createFkName(
        sourceTableName: String,
        sourceColumnNames: List<String>,

        targetTableName: String,
        targetColumnNames: List<String>,
    ): String =
        getFkName(sourceTableName, sourceColumnNames, targetTableName, targetColumnNames)

    open fun createFkConstraint(
        sourceTableName: String,
        sourceColumnNames: List<String>,

        targetTableName: String,
        targetColumnNames: List<String>,

        constraintName: String = createFkName(
            sourceTableName,
            sourceColumnNames,
            targetTableName,
            targetColumnNames
        ),

        fake: Boolean = false,
        dissociateAction: DissociateAction?
    ): String =
        if (!fake) {
            "${addConstraint(sourceTableName, constraintName)}\n${
                fkDefine(
                    sourceTableName,
                    sourceColumnNames,

                    targetTableName,
                    targetColumnNames,
                    
                    dissociateAction
                )
            };"
        } else {
            "-- fk placeholder for $constraintName"
        }

    open fun createMappingTableName(
        sourceTableName: String,
        targetTableName: String,
        sourceColumnName: String,
        targetColumnName: String,
    ): String =
        getMappingTableName(sourceTableName, targetTableName, sourceColumnName, targetColumnName)

    open fun createMappingTableComment(
        sourceTableComment: String,
        targetTableComment: String,
    ): String =
        "${sourceTableComment.clearTableComment()}与${targetTableComment.clearTableComment()}的映射关系表"

    open fun createMappingSourceColumnName(
        sourceTableName: String,
        sourceTableComment: String,
        sourceColumnName: String,
    ): String =
        "${sourceTableName.clearTableName()}_${sourceColumnName.clearColumnName()}"

    open fun createMappingTargetColumnName(
        targetTableName: String,
        targetTableComment: String,
        targetColumnName: String,
    ): String =
        "${targetTableName.clearTableName()}_${targetColumnName.clearColumnName()}"

    open fun createMappingTable(
        sourceTableName: String,
        sourceTableComment: String,
        sourceColumnName: String,

        targetTableName: String,
        targetTableComment: String,
        targetColumnName: String,

        columnType: String,

        lines: List<String> = emptyList(),
        append: String = "",

        mappingTableName: String = createMappingTableName(sourceTableName, targetTableName, sourceColumnName, targetColumnName),
        mappingTableComment: String = createMappingTableComment(sourceTableComment, targetTableComment),
        mappingSourceColumnName: String = createMappingSourceColumnName(sourceTableName, sourceTableComment, sourceColumnName),
        mappingTargetColumnName: String = createMappingTargetColumnName(targetTableName, targetTableComment, targetColumnName)
    ): String {
        val mappingTable = createTable(
            mappingTableName,
            listOf(
                "$mappingSourceColumnName $columnType NOT NULL",
                "$mappingTargetColumnName $columnType NOT NULL",
            ) + lines,
            append
        )

        val mappingTablePk = createPkConstraint(
            mappingTableName,
            listOf(mappingSourceColumnName, mappingTargetColumnName)
        )

        val commonFkName = createFkName(
            sourceTableName,
            listOf(sourceColumnName),
            targetTableName,
            listOf(targetColumnName)
        )

        val sourceFk = createFkConstraint(
            sourceTableName = mappingTableName,
            sourceColumnNames = listOf(mappingSourceColumnName),
            targetTableName = sourceTableName,
            targetColumnNames = listOf(sourceColumnName),
            constraintName = "${commonFkName}_SOURCE",
            dissociateAction = DissociateAction.DELETE,
        )

        val targetFk = createFkConstraint(
            sourceTableName = mappingTableName,
            sourceColumnNames = listOf(mappingTargetColumnName),
            targetTableName = targetTableName,
            targetColumnNames = listOf(targetColumnName),
            constraintName = "${commonFkName}_TARGET",
            dissociateAction = DissociateAction.DELETE,
        )

        return listOf(
            dropTable(mappingTableName),
            mappingTable,
            mappingTablePk,
            sourceFk,
            targetFk
        ).joinToString("\n\n")
    }

    open fun createIndex(
        tableName: String,
        columnNames: List<String>,
        unique: Boolean = false,
        name: String = "${if (unique) "u" else ""}idx_${tableName.clearTableName()}_${columnNames.joinToString("_") { it.clearColumnName() }}"
    ): String {
        return "CREATE ${if (unique) "UNIQUE " else ""}INDEX ${name.escape()} ON ${
            tableName.escape()
        } (${columnNames.joinToString(", ") { it.escape() }});"
    }

    /**
     * 根据 out association 生成外键约束、唯一性约束和关联表
     */
    open fun GenTableAssociationsView.associationsStringify(): List<String> {
        val list = mutableListOf<String>()

        val (outAssociations) = getAssociationMeta()

        outAssociations.forEach { association ->
            val (
                name,
                sourceTable,
                sourceColumns,
                targetTable,
                targetColumns
            ) = association.getMeta()

            when (association.associationType) {
                ONE_TO_ONE, MANY_TO_ONE -> {
                    createFkConstraint(
                        constraintName = name,
                        sourceTableName = sourceTable.name,
                        sourceColumnNames = sourceColumns.map { it.name },
                        targetTableName = targetTable.name,
                        targetColumnNames = targetColumns.map { it.name },
                        fake = association.fake,
                        dissociateAction = association.dissociateAction,
                    ).let {
                        list += it
                    }
                }

                ONE_TO_MANY -> {
                    createFkConstraint(
                        constraintName = name,
                        sourceTableName = targetTable.name,
                        sourceColumnNames = targetColumns.map { it.name },
                        targetTableName = sourceTable.name,
                        targetColumnNames = sourceColumns.map { it.name },
                        dissociateAction = association.dissociateAction,
                        fake = association.fake
                    ).let {
                        list += it
                    }
                }

                MANY_TO_MANY -> {
                    if (association.columnReferences.size > 1) {
                        throw RuntimeException("Create mapping table fail: \nMANY_TO_MANY does not support more than one ColumnReference")                    }

                    createMappingTable(
                        sourceTableName = sourceTable.name,
                        sourceColumnName = sourceColumns[0].name,
                        sourceTableComment = sourceTable.comment,
                        targetColumnName = targetTable.name,
                        targetTableName = targetColumns[0].name,
                        targetTableComment = targetTable.comment,
                        columnType = getDatabaseTypeString(sourceColumns[0], mappingTable = true),
                    ).let {
                        list += it
                    }
                }
            }
        }

        return list
    }
}

private fun DissociateAction.toOnDeleteAction(): String =
    when (this) {
        DissociateAction.NONE -> ""
        DissociateAction.SET_NULL -> "ON DELETE SET NULL"
        DissociateAction.DELETE -> "ON DELETE CASCADE"
        DissociateAction.LAX -> ""
        DissociateAction.CHECK -> ""
    }
