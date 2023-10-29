package top.potmot.liquibase

import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import top.potmot.core.liquibase.createSql
import top.potmot.enumeration.DataSourceType
import top.potmot.enumeration.TableType
import top.potmot.model.dto.GenDataSourceInput
import top.potmot.model.dto.GenTableColumnView
import java.time.LocalDateTime

@SpringBootTest
@ActiveProfiles("test-kotlin", "postgresql")
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class TestLiquibaseCreateSql {
    @Test
    @Order(1)
    fun testCreateMySQLCreateSQL() {
        val dataSource = GenDataSourceInput(
            name = "test",
            host = "127.0.0.1",
            port = "3307",
            urlSuffix = "",
            type = DataSourceType.MySQL,
            username = "root",
            password = "root",
            orderKey = 0L,
            remark = "test"
        ).toEntity()

        val column1 = GenTableColumnView.TargetOf_columns(
            id = 1,
            createdTime = LocalDateTime.now(),
            modifiedTime = LocalDateTime.now(),
            remark = "remark",
            orderKey = 2,
            name = "Name",
            typeCode = 3,
            type = "Type",
            displaySize = 4,
            numericPrecision = 5,
            defaultValue = "Default Value",
            comment = "comment",
            partOfPk = true,
            autoIncrement = true,
            partOfFk = true,
            partOfUniqueIdx = true,
            notNull = true,
            tableId = 1
        )

        val column2 = GenTableColumnView.TargetOf_columns(
            id = 2,
            createdTime = LocalDateTime.now(),
            modifiedTime = LocalDateTime.now(),
            remark = "Another remark",
            orderKey = 3,
            name = "Another Name",
            typeCode = 4,
            type = "Another Type",
            displaySize = 5,
            numericPrecision = 6,
            defaultValue = null,
            comment = "Another comment",
            partOfPk = false,
            autoIncrement = false,
            partOfFk = false,
            partOfUniqueIdx = true,
            notNull = true,
            tableId = 1
        )

        val table = GenTableColumnView(
            id = 1,
            createdTime = LocalDateTime.now(),
            modifiedTime = LocalDateTime.now(),
            remark = "",
            name = "table",
            comment = "comment",
            orderKey = 1,
            type = TableType.TABLE,
            schema = GenTableColumnView.TargetOf_schema(
                id = 1,
                name = "jimmer-code-gen",
                dataSource = GenTableColumnView.TargetOf_schema.TargetOf_dataSource(
                    id = 1,
                    name = "dataSource",
                    type = DataSourceType.MySQL
                )
            ),
            columns = listOf(
                column1,
                column2
            )
        )

        dataSource.createSql(
            listOf(table),
            emptyList()
        ).apply {
            println(this)
        }
    }

    @Test
    @Order(2)
    fun testCreatePostgreCreateSQL() {
        val dataSource = GenDataSourceInput(
            name = "test",
            host = "127.0.0.1",
            port = "5432",
            urlSuffix = "/postgres",
            type = DataSourceType.PostgreSQL,
            username = "postgres",
            password = "root",
            orderKey = 0L,
            remark = "test"
        ).toEntity()

        val column1 = GenTableColumnView.TargetOf_columns(
            id = 1,
            createdTime = LocalDateTime.now(),
            modifiedTime = LocalDateTime.now(),
            remark = "remark",
            orderKey = 2,
            name = "Name",
            typeCode = 3,
            type = "Type",
            displaySize = 4,
            numericPrecision = 5,
            defaultValue = "Default Value",
            comment = "comment",
            partOfPk = true,
            autoIncrement = true,
            partOfFk = true,
            partOfUniqueIdx = true,
            notNull = true,
            tableId = 1
        )

        val column2 = GenTableColumnView.TargetOf_columns(
            id = 2,
            createdTime = LocalDateTime.now(),
            modifiedTime = LocalDateTime.now(),
            remark = "Another remark",
            orderKey = 3,
            name = "Another Name",
            typeCode = 4,
            type = "Another Type",
            displaySize = 5,
            numericPrecision = 6,
            defaultValue = null,
            comment = "Another comment",
            partOfPk = false,
            autoIncrement = false,
            partOfFk = false,
            partOfUniqueIdx = true,
            notNull = true,
            tableId = 1
        )

        val table = GenTableColumnView(
            id = 1,
            createdTime = LocalDateTime.now(),
            modifiedTime = LocalDateTime.now(),
            remark = "",
            name = "table",
            comment = "comment",
            orderKey = 1,
            type = TableType.TABLE,
            schema = GenTableColumnView.TargetOf_schema(
                id = 1,
                name = "jimmer-code-gen",
                dataSource = GenTableColumnView.TargetOf_schema.TargetOf_dataSource(
                    id = 1,
                    name = "dataSource",
                    type = DataSourceType.MySQL
                )
            ),
            columns = listOf(
                column1,
                column2
            )
        )

        dataSource.createSql(
            listOf(table),
            emptyList()
        ).apply {
            println(this)
        }
    }
}
