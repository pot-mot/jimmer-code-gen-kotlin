package top.potmot.jimmercodegen.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service
import top.potmot.jimmercodegen.dao.GenTableAssociationRepository
import top.potmot.jimmercodegen.dao.GenTableColumnRepository
import top.potmot.jimmercodegen.dao.GenTableRepository
import top.potmot.jimmercodegen.model.GenTable
import top.potmot.jimmercodegen.model.input.GenTableColumnInput
import top.potmot.jimmercodegen.model.input.GenTableInput
import top.potmot.jimmercodegen.utils.GenUtils
import javax.sql.DataSource

@Service
class GenService(
    @Autowired val tableRepository: GenTableRepository,
    @Autowired val associationRepository: GenTableAssociationRepository,
    @Autowired val columnRepository: GenTableColumnRepository,
    @Autowired val dataSource: DataSource
) {
    val jdbcTemplate = JdbcTemplate(dataSource)

    fun importGenTable(tableNames: List<String>) {
        for (tableInput in getTables(tableNames)) {
            GenUtils.initTable(tableInput)
            val table = tableRepository.insert(tableInput)
            for (columnInput in getTableColumn(table)) {
                GenUtils.initColumnField(columnInput, table)
                val column = columnRepository.insert(columnInput)
            }
        }
    }

    fun getTables(tableNames: List<String>): List<GenTableInput> {
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

        return jdbcTemplate.query(sql) { rs, _ ->
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
                "",
                "",
            )
        }
    }

    fun getTableColumn(table: GenTable): List<GenTableColumnInput> {
        val sql = "select " +
                "column_name, " +
                "column_comment, " +
                "column_type, " +
                "(IF((is_nullable = 'no' && column_key != 'PRI'), '1', '0')) as required, " +
                "(IF(column_key = 'PRI', '1', '0')) as pk, ordinal_position as sort, " +
                "(IF(extra = 'auto_increment', '1', '0')) as increment " +
                "from information_schema.columns " +
                "where table_schema = (select database()) " +
                "and table_name = '${table.tableName}' " +
                "order by ordinal_position"

        return jdbcTemplate.query(sql) { rs, _ ->
            GenTableColumnInput(
                null,
                table.genTableId,
                rs.getString("column_name"),
                rs.getString("column_comment"),
                rs.getString("column_type"),
                "",
                "",
                rs.getString("pk"),
                rs.getString("increment"),
                rs.getString("required"),
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                rs.getInt("sort")
            )
        }
    }

}