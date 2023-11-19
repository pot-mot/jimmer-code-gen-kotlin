package top.potmot.core.template.table

import org.babyfish.jimmer.sql.DissociateAction
import top.potmot.config.GenConfig
import top.potmot.core.convert.clearColumnName
import top.potmot.core.convert.clearTableComment
import top.potmot.core.convert.clearTableName
import top.potmot.core.template.TemplateBuilder
import top.potmot.enumeration.AssociationType.*
import top.potmot.model.extension.outColumns
import top.potmot.model.dto.GenTableAssociationsView

abstract class TableContext : TemplateBuilder() {
    abstract fun String.escape(): String

    abstract fun GenTableAssociationsView.TargetOf_columns.columnStringify(): String

    abstract fun typeStringify(
        type: String,
        typeCode: Int,
        displaySize: Long,
        numericPrecision: Long,
        fullType: String,
        partOfPk: Boolean,
        partOfUniqueIdx: Boolean,
        partOfFk: Boolean,
        autoIncrement: Boolean,
        mappingTable: Boolean = false,
    ): String

    open fun GenTableAssociationsView.TargetOf_columns.typeStringify(
        mappingTable: Boolean = false,
    ): String =
        typeStringify(
            type, typeCode, displaySize, numericPrecision, fullType, partOfPk, partOfFk, partOfUniqueIdx, autoIncrement,
            mappingTable
        )

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

    open fun alterTable(
        name: String
    ): String =
        "ALTER TABLE ${name.escape()} "

    open fun createTable(
        table: GenTableAssociationsView,
        content: List<String> = emptyList(),
        append: String = "",
    ): String {
        val list = mutableListOf<String>()

        list.addAll(table.columns.map { it.columnStringify() })

        list.addAll(content)

        return createTable(table.name, list, append) +
                "\n\n" +
                table.columns
                    .mapNotNull { column ->
                        if (column.partOfUniqueIdx || column.partOfFk) {
                            createIndex(table.name, column.name, column.partOfPk, column.partOfUniqueIdx)
                        } else {
                            null
                        }
                    }
                    .joinToString { "\n\n$it" }
    }
    open fun createPkConstraint(
        tableName: String,
        columnNames: List<String>
    ): String =
        "${alterTable(tableName)}ADD CONSTRAINT ${"pk_${tableName}".escape()} PRIMARY KEY (${columnNames.joinToString(",")});"

    open fun createFkConstraint(
        sourceTableName: String,
        sourceColumnName: String,

        targetTableName: String,
        targetColumnName: String,

        associationName: String = "fk__${sourceTableName.clearTableName()}_${sourceColumnName.clearColumnName()}_" +
                "_${targetTableName.clearTableName()}_${targetColumnName.clearColumnName()}",

        dissociateAction: DissociateAction?
    ): String =
        buildString {
            if (GenConfig.tableDefineWithFk) {
                appendLine(alterTable(sourceTableName.escape()))
                appendLine("ADD CONSTRAINT ${associationName.escape()}")
                appendLine(" FOREIGN KEY (${sourceColumnName.escape()}) REFERENCES ${targetTableName.escape()} (${targetColumnName.escape()})")
                append(" ${dissociateAction?.toOnDeleteAction() ?: ""} ON UPDATE RESTRICT;")
            } else {
                append("-- fake association $associationName")
            }
        }

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

        mappingTableName: String = "${sourceTableName.clearTableName()}_${targetTableName.clearTableName()}_mapping",
        mappingTableComment: String = "${sourceTableComment.clearTableComment()}与${targetTableComment.clearTableComment()}的映射关系表",
        mappingSourceColumnName: String = "${sourceTableName.clearTableName()}_id",
        mappingTargetColumnName: String = "${targetTableName.clearTableName()}_id"
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

        val sourceFk = createFkConstraint(
            sourceTableName = mappingTableName,
            sourceColumnName = mappingSourceColumnName,
            targetTableName = sourceTableName,
            targetColumnName = sourceColumnName,
            dissociateAction = DissociateAction.DELETE,
        )

        val targetFk = createFkConstraint(
            sourceTableName = mappingTableName,
            sourceColumnName = mappingTargetColumnName,
            targetTableName = targetTableName,
            targetColumnName = targetColumnName,
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
        columnName: String,
        partOfPk: Boolean = false,
        partOfUniqueIdx: Boolean = false,
    ): String? {
        if (partOfPk) return null

        val indexName =
            "${if (partOfUniqueIdx) "u" else ""}idx_${tableName.clearTableName()}_${columnName.clearColumnName()}"

        return "CREATE ${if (partOfUniqueIdx) "UNIQUE " else ""}INDEX ${indexName.escape()} ON ${
            tableName.escape()
        } (${columnName.escape()});"
    }

    /**
     * 根据 out association 生成外键约束、唯一性约束和关联表
     */
    open fun GenTableAssociationsView.associationsStringify(): List<String> {
        val list = mutableListOf<String>()

        for (column in outColumns()) {
            column.outAssociations.forEach { association ->
                when (association.associationType) {
                    ONE_TO_ONE, MANY_TO_ONE -> {
                        createFkConstraint(
                            dissociateAction = association.dissociateAction,
                            sourceColumnName = column.name,
                            sourceTableName = name,
                            targetColumnName = association.targetColumn.name,
                            targetTableName = association.targetColumn.table.name,
                        ).let {
                            list += it
                        }
                    }

                    ONE_TO_MANY -> {
                        createFkConstraint(
                            sourceTableName = association.targetColumn.table.name,
                            sourceColumnName = association.targetColumn.name,
                            targetTableName = name,
                            targetColumnName = column.name,
                            dissociateAction = association.dissociateAction,
                        ).let {
                            list += it
                        }
                    }

                    MANY_TO_MANY -> {
                        createMappingTable(
                            sourceColumnName = column.name,
                            sourceTableName = name,
                            sourceTableComment = comment,
                            targetColumnName = association.targetColumn.name,
                            targetTableName = association.targetColumn.table.name,
                            targetTableComment = association.targetColumn.table.comment,
                            columnType = column.typeStringify(mappingTable = true),
                        ).let {
                            list += it
                        }
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
