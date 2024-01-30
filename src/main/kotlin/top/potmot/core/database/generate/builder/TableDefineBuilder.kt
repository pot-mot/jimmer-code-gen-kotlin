package top.potmot.core.database.generate.builder

import top.potmot.core.database.generate.columnTypeDefiner.ColumnTypeDefiner
import top.potmot.core.database.meta.ForeignKeyMeta
import top.potmot.core.database.meta.MappingTableMeta
import top.potmot.core.database.meta.getAssociations
import top.potmot.core.database.meta.getTypeMeta
import top.potmot.core.database.meta.reversed
import top.potmot.core.database.meta.toFkMeta
import top.potmot.core.database.meta.toMappingTableMeta
import top.potmot.enumeration.AssociationType.MANY_TO_MANY
import top.potmot.enumeration.AssociationType.MANY_TO_ONE
import top.potmot.enumeration.AssociationType.ONE_TO_MANY
import top.potmot.enumeration.AssociationType.ONE_TO_ONE
import top.potmot.error.ColumnTypeException
import top.potmot.error.ConvertEntityException
import top.potmot.error.GenerateTableDefineException
import top.potmot.model.dto.ColumnTypeMeta
import top.potmot.model.dto.GenTableAssociationsView
import top.potmot.model.extension.pkColumns
import top.potmot.utils.identifier.IdentifierFilter
import top.potmot.utils.string.changeCase

abstract class TableDefineBuilder(
    private val identifierFilter: IdentifierFilter,
    private val columnTypeDefiner: ColumnTypeDefiner
) {
    abstract fun String.escape(): String

    @Throws(ColumnTypeException::class)
    open fun getColumnTypeDefine(typeMeta: ColumnTypeMeta) =
        columnTypeDefiner.getTypeDefine(typeMeta)

    fun produceIdentifier(identifier: String) =
        identifierFilter.getIdentifier(identifier.trim()).escape().changeCase()

    open fun createTable(
        name: String,
        lines: List<String>,
        append: String = ""
    ) =
        buildString {
            append("CREATE TABLE ${produceIdentifier(name)} (\n")
            append("    ${lines.joinToString(",\n    ")}\n")
            append(")${append}")
        }

    open fun dropTable(
        name: String,
        append: String = ""
    ) =
        "DROP TABLE IF EXISTS ${produceIdentifier(name)}${if (append.isBlank()) "" else " $append"}"

    open fun alterTable(
        name: String
    ) =
        "ALTER TABLE ${produceIdentifier(name)}"

    open fun createPkLine(
        table: GenTableAssociationsView,
    ) =
        pkDefine(table.pkColumns().map { it.name })

    @Throws(ColumnTypeException::class)
    open fun columnStringify(column: GenTableAssociationsView.TargetOf_columns) =
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

    @Throws(ColumnTypeException::class)
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

    @Throws(GenerateTableDefineException::class)
    open fun indexLines(
        table: GenTableAssociationsView,
    ) =
        table.indexes.map { index ->
            val columnNames = table.columns.filter { it.id in index.columnIds }.map { it.name }

            if (columnNames.size != index.columnIds.size) {
                throw GenerateTableDefineException.index(
                    "Index [${index.name}] generate fail: " +
                            " table ${table.name} columns [${table.columns.map { it.name }} not match index column ids [${index.columnIds}]"
                )
            }

            createIndex(
                index.name,
                table.name,
                columnNames,
                index.uniqueIndex,
            )
        }

    open fun addConstraint(
        constraintName: String
    ) =
        "ADD CONSTRAINT ${produceIdentifier(constraintName)}"

    open fun pkDefine(
        columnNames: List<String>
    ) =
        "PRIMARY KEY (${columnNames.joinToString(",") { produceIdentifier(it) }})"

    open fun fkDefine(
        meta: ForeignKeyMeta,
        indentCount: Int = 2,
    ) =
        buildString {
            val indent = "    "
            var temp = indentCount

            append(indent.repeat(temp++))
            appendLine("FOREIGN KEY (${meta.sourceColumnNames.joinToString(", ") { produceIdentifier(it) }})")

            append(indent.repeat(temp++))
            append(
                "REFERENCES ${produceIdentifier(meta.targetTableName)} (${
                    meta.targetColumnNames.joinToString(
                        ", "
                    ) { produceIdentifier(it) }
                })"
            )

            meta.onUpdate.takeIf { it.isNotBlank() }?.let {
                append("\n${indent.repeat(temp)}ON UPDATE ${it.changeCase()}")
            }
            meta.onDelete.takeIf { it.isNotBlank() }?.let {
                append("\n${indent.repeat(temp)}ON DELETE ${it.changeCase()}")
            }
        }


    open fun createPkName(
        tableName: String,
        columnNames: List<String>
    ) =
        "pk_${tableName}"

    open fun createPkConstraint(
        tableName: String,
        columnNames: List<String>,

        constraintName: String =
            createPkName(tableName, columnNames)
    ) =
        "${alterTable(tableName)} ${addConstraint(constraintName)} ${pkDefine(columnNames)}"

    open fun createFkConstraint(
        meta: ForeignKeyMeta,
        fake: Boolean = false,
    ) =
        listOf(
            alterTable(meta.sourceTableName),
            "\n    ",
            addConstraint(meta.name),
            "\n",
            fkDefine(meta),
        ).joinToString("")
            .let {
                if (fake) {
                    it.split("\n").joinToString("\n") {line -> "-- $line" }
                } else {
                    it
                }
            }

    open fun createMappingTable(
        meta: MappingTableMeta,
        fake: Boolean = false,
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
            fake,
        )

        val targetFk = createFkConstraint(
            meta.targetFk,
            fake,
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
        name: String,
        tableName: String,
        columnNames: List<String>,
        unique: Boolean = false,
    ): String {
        return "CREATE ${if (unique) "UNIQUE " else ""}INDEX ${produceIdentifier(name)} ON ${
            produceIdentifier(tableName)
        } (${columnNames.joinToString(", ") { produceIdentifier(it) }})"
    }

    /**
     * 根据 out association 生成外键约束、唯一性约束和关联表
     */
    @Throws(ConvertEntityException::class)
    open fun associationsStringify(
        table: GenTableAssociationsView
    ): List<String> {
        val list = mutableListOf<String>()

        val (outAssociations) = table.getAssociations()

        outAssociations.forEach { association ->
            when (association.type) {
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
                        association.toMappingTableMeta(),
                        fake = association.fake
                    ).let {
                        list += it
                    }
                }
            }
        }

        return list
    }
}
