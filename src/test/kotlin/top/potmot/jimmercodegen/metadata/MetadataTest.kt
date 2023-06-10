package top.potmot.jimmercodegen.metadata

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.SpringBootTest
import java.sql.ResultSet
import javax.sql.DataSource

@SpringBootTest
class MetadataTest (
    @Autowired @Qualifier("GenDataSource") val genDataSource: DataSource
){
    @Test
    fun getAll() {
        val catalog = "ktg"
        val schema = "ktg"
        val table = "cal_%"

        // 获取数据库元数据
        val metaData = genDataSource.connection.metaData
        val columnNamePattern = "%"

        val columns = metaData.getColumns(catalog, schema, table, columnNamePattern)

        // 遍历列信息
        while (columns.next()) {
            val columnName = columns.getString("COLUMN_NAME")
            val dataType = columns.getString("DATA_TYPE")
            val columnSize = columns.getInt("COLUMN_SIZE")
            val nullable = columns.getBoolean("NULLABLE")
            println("Column: $columnName (type: $dataType, size: $columnSize, nullable: $nullable)")
        }

        val foreignKeys: ResultSet = metaData.getExportedKeys(catalog, schema, table)

        // 遍历外键信息
        while (foreignKeys.next()) {
            val fkTableName = foreignKeys.getString("FKTABLE_NAME")
            val fkColumnName = foreignKeys.getString("FKCOLUMN_NAME")
            val pkTableName = foreignKeys.getString("PKTABLE_NAME")
            val pkColumnName = foreignKeys.getString("PKCOLUMN_NAME")
            val fkName = foreignKeys.getString("FK_NAME")
            val pkName = foreignKeys.getString("PK_NAME")
            println(
                "Foreign key: " + fkName + " (" + fkColumnName + " in " + fkTableName + ") references " +
                        pkName + " (" + pkColumnName + " in " + pkTableName + ")"
            )
        }
    }
}