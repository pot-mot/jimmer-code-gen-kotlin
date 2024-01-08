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
import top.potmot.utils.identifier.IdentifierFilter
import top.potmot.utils.string.changeCase
import top.potmot.utils.template.TemplateBuilder

abstract class TableDefineBuilder(
    private val identifierFilter: IdentifierFilter
) : TemplateBuilder() {
    abstract fun String.escape(): String

    abstract fun getColumnTypeDefiner(): ColumnTypeDefiner

    open fun getColumnTypeDefine(typeMeta: ColumnTypeMeta): String =
        getColumnTypeDefiner().getTypeDefine(typeMeta)

    fun produceIdentifier(identifier: String): String =
        identifierFilter.getIdentifier(identifier).changeCase().escape()

    open fun createTable(
        name: String,
        lines: List<String>,
        append: String = ""
    ): String =
        buildString {
            append("CREATE TABLE ${produceIdentifier(name)} (\n")
            append("    ${lines.joinToString(",\n    ")}\n")
            append(")${append}")
        }

    open fun dropTable(
        name: String,
        append: String = ""
    ): String =
        "DROP TABLE IF EXISTS ${produceIdentifier(name)}${if (append.isBlank()) "" else " $append"}"

    fun alterTable(
        name: String
    ): String =
        "ALTER TABLE ${produceIdentifier(name)} "

    open fun createPkLine(
        table: GenTableAssociationsView,
    ): String =
        pkDefine(table.pkColumns().map { it.name })

    open fun columnStringify(column: GenTableAssociationsView.TargetOf_columns): String =
        buildString {
            append(produceIdentifier(column.name))
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
        otherLines: List<String> = emptyList()
    ): String {
        val lines = mutableListOf<String>()

        lines.addAll(table.columns.map { columnStringify(it) })

        if (table.pkColumns().isNotEmpty()) {
            lines.add(createPkLine(table))
        }

        lines.addAll(otherLines)

        return createTable(
            table.name,
            lines,
        )
    }

    open fun createIndexes(
        table: GenTableAssociationsView,
    ) =
        table.indexes.map { index ->
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

    open fun addConstraint(
        tableName: String,
        constraintName: String
    ): String =
        "${alterTable(tableName)}ADD CONSTRAINT ${produceIdentifier(constraintName)} "

    open fun pkDefine(
        columnNames: List<String>
    ): String =
        "PRIMARY KEY (${columnNames.joinToString(",") { produceIdentifier(it) }})"

    open fun fkDefine(
        meta: ForeignKeyMeta
    ): String =
        buildString {
            appendLine("    FOREIGN KEY (${meta.sourceColumnNames.joinToString(", ") { produceIdentifier(it) }})")
            appendLine("  REFERENCES ${produceIdentifier(meta.targetTableName)} (${meta.targetColumnNames.joinToString(", ") { produceIdentifier(it) }})")
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
        "${addConstraint(tableName, constraintName)}${pkDefine(columnNames)}"

    open fun createFkConstraint(
        meta: ForeignKeyMeta,
        fake: Boolean = false,
    ): String =
        if (!fake) {
            "${addConstraint(meta.sourceTableName, meta.name)}\n${fkDefine(meta)}"
        } else {
            "-- fake association ${meta.name}"
        }

    open fun createMappingTable(
        meta: MappingTableMeta,
        otherLines: List<String> = emptyList(),
        append: String = "",
    ): List<String> {
        val mappingTable = createTable(
            meta.name,
            meta.columns.map { columnStringify(it) } + otherLines,
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
        )
    }

    open fun createIndex(
        tableName: String,
        columnNames: List<String>,
        unique: Boolean = false,
        name: String = "${if (unique) "u" else ""}idx_${tableName.clearTableName()}_${columnNames.joinToString("_") { it.clearColumnName() }}"
    ): String {
        return "CREATE ${if (unique) "UNIQUE " else ""}INDEX ${produceIdentifier(name)} ON ${
            produceIdentifier(tableName)
        } (${columnNames.joinToString(", ") { produceIdentifier(it) }})"
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
