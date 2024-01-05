package top.potmot.core.database.generate

import top.potmot.core.entity.convert.clearColumnName
import top.potmot.core.entity.convert.clearTableName
import top.potmot.core.database.meta.ForeignKeyMeta
import top.potmot.core.database.meta.MappingTableMeta
import top.potmot.core.database.meta.ColumnTypeMeta
import top.potmot.core.database.meta.getTypeMeta
import top.potmot.core.database.meta.getAssociations
import top.potmot.core.database.meta.reversed
import top.potmot.core.database.meta.toFkMeta
import top.potmot.core.database.meta.toMappingTableMeta
import top.potmot.enumeration.AssociationType.MANY_TO_MANY
import top.potmot.enumeration.AssociationType.MANY_TO_ONE
import top.potmot.enumeration.AssociationType.ONE_TO_MANY
import top.potmot.enumeration.AssociationType.ONE_TO_ONE
import top.potmot.error.GenerateTableDefineException
import top.potmot.model.dto.GenTableAssociationsView
import top.potmot.model.extension.pkColumns
import top.potmot.utils.template.TemplateBuilder

abstract class TableDefineBuilder : TemplateBuilder() {
    abstract fun String.escape(): String

    abstract fun getColumnTypeDefiner(): ColumnTypeDefiner

    open fun getColumnTypeDefine(typeMeta: ColumnTypeMeta): String =
        getColumnTypeDefiner().getTypeDefine(typeMeta)

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

    open fun columnStringify(column: GenTableAssociationsView.TargetOf_columns): String =
        buildString {
            append(column.name.escape())
            append(' ')
            append(getColumnTypeDefine(column.getTypeMeta()))

            if (column.typeNotNull) {
                append(" NOT NULL")
            }

            if (!column.partOfPk) {
                if (!column.defaultValue.isNullOrBlank()) {
                    append(" DEFAULT ").append(column.defaultValue)
                } else if (!column.typeNotNull) {
                    append(" DEFAULT NULL")
                }
            }
        }

    open fun createTable(
        table: GenTableAssociationsView,
        otherLines: List<String> = emptyList(),
        append: String = "",
        withIndex: Boolean = true,
    ): String {
        val lines = mutableListOf<String>()

        lines.addAll(table.columns.map { columnStringify(it) })

        if (table.pkColumns().isNotEmpty()) {
            lines.add(createPkLine(table))
        }

        lines.addAll(otherLines)

        val tableDefine = createTable(table.name, lines, append)

        val indexes = table.indexes.map { index ->
            val columnNames = table.columns.filter { it.id in index.columnIds }.map { it.name }

            if (columnNames.size != index.columnIds.size) {
                throw GenerateTableDefineException.index(
                    "Index [${index.name}] column not match: " +
                        " table: ${table.name} " +
                        " current columns: ${table.columns.map { it.id to it.name }} " +
                        " need column ids: ${index.columnIds}"
                )
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
        meta: ForeignKeyMeta
    ): String =
        buildString {
            appendLine("    FOREIGN KEY (${meta.sourceColumnNames.joinToString(", ") { it.escape() }})")
            appendLine("  REFERENCES ${meta.targetTableName.escape()} (${meta.targetColumnNames.joinToString(", ") { it.escape() }})")
            append("  ${meta.onDelete} ${meta.onUpdate}")
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

    open fun createFkConstraint(
        meta: ForeignKeyMeta,
        fake: Boolean = false,
    ): String =
        if (!fake) {
            "${addConstraint(meta.sourceTableName, meta.name)}\n${fkDefine(meta)};"
        } else {
            "-- fake association ${meta.name}"
        }

    open fun createMappingTable(
        meta: MappingTableMeta,
        otherLines: List<String> = emptyList(),
        append: String = "",
    ): String {
        val mappingTable = createTable(
            meta.name,
            meta.columnLines + otherLines,
            append
        )

        val mappingTablePk = createPkConstraint(
            meta.name,
            meta.mappingSourceColumnNames + meta.mappingTargetColumnNames
        )

        val sourceFk = createFkConstraint(
            meta.sourceFk,
        )

        val targetFk = createFkConstraint(
            meta.targetFk,
        )

        return listOf(
            dropTable(meta.name),
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

        val (outAssociations) = getAssociations()

        outAssociations.forEach { association ->
            when (association.associationType) {
                ONE_TO_ONE, MANY_TO_ONE -> {
                    createFkConstraint(
                        association.toFkMeta(),
                        fake = association.fake,
                    ).let {
                        list += it
                    }
                }

                ONE_TO_MANY -> {
                    createFkConstraint(
                        association.toFkMeta().reversed(),
                        fake = association.fake
                    ).let {
                        list += it
                    }
                }

                MANY_TO_MANY -> {
                    createMappingTable(
                        association.toMappingTableMeta()
                    ).let {
                        list += it
                    }
                }
            }
        }

        return list
    }
}
