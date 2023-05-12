package top.potmot.external.service

import org.babyfish.jimmer.sql.kt.ast.expression.ilike
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service
import top.potmot.external.dao.GenTableColumnRepository
import top.potmot.external.dao.GenTableRepository
import top.potmot.external.model.*
import top.potmot.external.model.input.GenTableColumnInput
import top.potmot.external.model.input.GenTableInput
import top.potmot.external.utils.GenUtils
import javax.sql.DataSource

@Service
class TableService(
    @Autowired val tableRepository: GenTableRepository,
    @Autowired val columnRepository: GenTableColumnRepository,
    @Autowired @Qualifier("GenDataSource") val genDataSource: DataSource
) {
    val jdbcTemplate = JdbcTemplate(genDataSource)

    fun clearGenTable() {
        tableRepository.deleteAll()
        columnRepository.deleteAll()
    }

    fun deleteColumnsByNameLike(name: String) {
        columnRepository.deleteByIds(
            columnRepository.sql.createQuery(GenTableColumn::class) {
                where(
                    table.columnName ilike name
                )
                select(table.id)
            }.execute()
        )
    }

    fun deleteColumnsByCommentLike(comment: String) {
        columnRepository.deleteByIds(
            columnRepository.sql.createQuery(GenTableColumn::class) {
                where(
                    table.columnComment ilike comment
                )
                select(table.id)
            }.execute()
        )
    }

    /**
     * 引入用于生成的表
     */
    fun importGenTable(tableNames: List<String> = getAllTableName()) {
        for (tableInput in initTables(tableNames)) {
            val table = tableRepository.insert(tableInput)
            for (columnInput in initTableColumn(table)) {
                columnRepository.insert(columnInput)
            }
        }
        deleteColumnsByNameLike("status")
        deleteColumnsByCommentLike("预留字段")
        deleteColumnsByCommentLike("创建者")
        deleteColumnsByCommentLike("创建时间")
        deleteColumnsByCommentLike("更新者")
        deleteColumnsByCommentLike("更新时间")
        deleteColumnsByCommentLike("备注")
        deleteColumnsByCommentLike("序号")
        deleteColumnsByCommentLike("编号")
    }

    /**
     * 获取所有的表
     */
    fun getAllTableName(): List<String> {
        val sql = "select table_name from information_schema.tables\n" +
                "where table_schema = (select database())"

        return jdbcTemplate.query(sql) { rs, _ ->
            rs.getString("table_name")
        }
    }

    fun initTables(tableNames: List<String>): List<GenTableInput> {
        var names = "("
        for (i in tableNames.indices) {
            names += if (i == 0) {
                "'${tableNames[i]}'"
            } else {
                ", '${tableNames[i]}'"
            }
        }
        names += ")"

        val sql = "select table_name, table_comment from information_schema.tables\n" +
                "where table_schema = (select database())\n" +
                "and table_name in " + names

        println(sql)

        val tableInputs = jdbcTemplate.query(sql) { rs, _ ->
            GenTableInput(
                null,
                rs.getString("table_name"),
                rs.getString("table_comment"),
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
            )
        }

        for (tableInput in tableInputs) {
            GenUtils.initTable(tableInput)
        }

        return tableInputs
    }

    fun initTableColumn(table: GenTable): List<GenTableColumnInput> {
        val sql = "select " +
                "column_name, " +
                "column_comment, " +
                "column_type, " +
                "(IF((is_nullable = 'NO' AND column_key != 'PRI' AND EXTRA NOT LIKE '%DEFAULT_GENERATED%'), '1', '0')) as required, " +
                "(IF(column_key = 'PRI', '1', '0') ) as pk, " +
                "ordinal_position AS sort, " +
                "(IF(extra LIKE '%auto_increment%', '1', '0')) as increment " +
                "from information_schema.columns " +
                "where table_schema = (select database()) " +
                "and table_name = '${table.tableName}' " +
                "order by ordinal_position"

        println(sql)

        val columnInputs = jdbcTemplate.query(sql) { rs, _ ->
            GenTableColumnInput(
                null,
                table.id,
                rs.getString("column_name"),
                rs.getString("column_comment"),
                rs.getString("column_type"),
                "",
                "",
                rs.getString("pk"),
                rs.getString("increment"),
                rs.getString("required"),
                "0",
                "0",
                "0",
                "0",
                "0",
                "",
                "",
                "",
                rs.getInt("sort")
            )
        }

        for (columnInput in columnInputs) {
            GenUtils.initColumnField(columnInput, table)
        }

        return columnInputs
    }
}