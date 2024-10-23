package top.potmot.core.database.generate.builder

import top.potmot.core.database.generate.columnType.ColumnTypeDefiner
import top.potmot.core.database.generate.identifier.IdentifierProcessor
import top.potmot.core.database.meta.ForeignKeyMeta
import top.potmot.core.database.meta.MappingTableMeta
import top.potmot.core.database.meta.outAssociations
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
import top.potmot.entity.dto.share.ColumnTypeMeta
import top.potmot.entity.dto.GenTableGenerateView
import top.potmot.entity.extension.pkColumns

/**
 * 表定义构建器
 */
abstract class TableDefineBuilder(
    protected val identifiers: IdentifierProcessor,
    protected val columnTypes: ColumnTypeDefiner,
) {
    @Throws(ColumnTypeException::class)
    open fun getColumnTypeDefine(typeMeta: ColumnTypeMeta) =
        columnTypes.getTypeDefine(typeMeta)

    open fun createTable(
        name: String,
        lines: List<String>,
        append: String = ""
    ) =
        buildString {
            append("CREATE TABLE ${identifiers.tableName(name)} (\n")
            append("    ${lines.joinToString(",\n    ")}\n")
            append(")${append}")
        }

    open fun dropTable(
        name: String,
        append: String = ""
    ) =
        "DROP TABLE IF EXISTS ${identifiers.tableName(name)}${if (append.isBlank()) "" else " $append"}"

    open fun alterTable(
        name: String
    ) =
        "ALTER TABLE ${identifiers.tableName(name)}"

    open fun createPkLine(
        table: GenTableGenerateView,
    ) =
        pkDefine(table.pkColumns().map { it.name })

    @Throws(ColumnTypeException::class)
    open fun columnStringify(column: GenTableGenerateView.TargetOf_columns) =
        buildString {
            append(identifiers.columnName(column.name))
            append(' ')
            append(getColumnTypeDefine(column))

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
        table: GenTableGenerateView,
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
        table: GenTableGenerateView,
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
        "ADD CONSTRAINT ${identifiers.constraintName(constraintName)}"

    open fun pkDefine(
        columnNames: List<String>
    ) =
        "PRIMARY KEY (${columnNames.joinToString(",") { identifiers.primaryKeyName(it) }})"

    open fun fkDefine(
        meta: ForeignKeyMeta,
        indentCount: Int = 2,
    ) =
        buildString {
            val indent = "    "
            var temp = indentCount

            append(indent.repeat(temp++))
            appendLine(
                "FOREIGN KEY (${
                    meta.sourceColumnNames.joinToString(", ") {
                        identifiers.foreignKeyName(it)
                    }
                })")

            append(indent.repeat(temp++))
            append(
                "REFERENCES ${identifiers.tableName(meta.targetTableName)} (${
                    meta.targetColumnNames.joinToString(", ") { identifiers.columnName(it) }
                })"
            )

            meta.onUpdate.takeIf { it.isNotBlank() }?.let {
                append("\n${indent.repeat(temp)}ON UPDATE $it")
            }
            meta.onDelete.takeIf { it.isNotBlank() }?.let {
                append("\n${indent.repeat(temp)}ON DELETE $it")
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
                    it.split("\n").joinToString("\n") { line -> "-- $line" }
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
        return "CREATE ${if (unique) "UNIQUE " else ""}INDEX ${identifiers.indexName(name)} ON ${
            identifiers.tableName(tableName)
        } (${columnNames.joinToString(", ") { identifiers.columnName(it) }})"
    }

    /**
     * 根据 out association 生成外键约束、唯一性约束和关联表
     */
    @Throws(ConvertEntityException::class)
    open fun associationsStringify(
        table: GenTableGenerateView
    ): List<String> {
        val list = mutableListOf<String>()

        val outAssociations = table.outAssociations()

        outAssociations.forEach { outAssociation ->
            val (association) = outAssociation

            val fkMeta = outAssociation.toFkMeta()

            when (association.type) {
                ONE_TO_ONE -> {
                    createFkConstraint(
                        if (outAssociation.sourceColumns.all { it.partOfPk }) fkMeta.reversed() else fkMeta,
                        fake = association.fake,
                    ).let {
                        list += it
                    }
                }

                MANY_TO_ONE -> {
                    createFkConstraint(
                        fkMeta,
                        fake = association.fake,
                    ).let {
                        list += it
                    }
                }

                ONE_TO_MANY -> {
                    createFkConstraint(
                        fkMeta.reversed(),
                        fake = association.fake
                    ).let {
                        list += it
                    }
                }

                MANY_TO_MANY -> {
                    createMappingTable(
                        outAssociation.toMappingTableMeta(),
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
