package top.potmot.dataSource

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import top.potmot.enumeration.DataSourceType
import top.potmot.enumeration.TableType
import top.potmot.model.dto.GenDataSourceInput
import top.potmot.model.dto.GenTableModelInput
import top.potmot.model.extension.execute
import top.potmot.utils.liquibase.createSql
import java.sql.Types

@SpringBootTest
@ActiveProfiles("test-kotlin", "h2")
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class TestLiquibaseCreateSql {
    private val column1 = GenTableModelInput.TargetOf_columns(
        remark = "remark",
        orderKey = 2,
        name = "Name",
        typeCode = Types.INTEGER,
        overwriteByType = false,
        type = "Type",
        displaySize = 5,
        numericPrecision = 4,
        defaultValue = "Default Value",
        comment = "comment",
        partOfPk = true,
        autoIncrement = true,
        typeNotNull = true,
        businessKey = false,
        logicalDelete = false,
    )

    private val column2 = GenTableModelInput.TargetOf_columns(
        remark = "Another remark",
        orderKey = 3,
        name = "Another Name",
        typeCode = Types.DECIMAL,
        overwriteByType = false,
        type = "Another Type",
        displaySize = 5,
        numericPrecision = 4,
        defaultValue = null,
        comment = "Another comment",
        partOfPk = false,
        autoIncrement = false,
        typeNotNull = true,
        businessKey = false,
        logicalDelete = false,
    )

    private val table = GenTableModelInput(
        remark = "",
        name = "table",
        comment = "comment",
        orderKey = 1,
        type = TableType.TABLE,
        schema = GenTableModelInput.TargetOf_schema(
            name = "jimmer_code_gen",
        ),
        columns = listOf(
            column1,
            column2
        )
    )

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

        val sql = dataSource.createSql(
            listOf(table),
            emptyList()
        )
        assertEquals(
            """CREATE TABLE jimmer_code_gen.`table` (
    Name INT AUTO_INCREMENT NOT NULL COMMENT 'comment',
    `Another Name` DECIMAL(5,
    4) NOT NULL COMMENT 'Another comment',
    CONSTRAINT PK_TABLE PRIMARY KEY (Name)
) COMMENT='comment';
""",
            sql,
        )

        val createResults = dataSource.execute("jimmer_code_gen", sql, true)

        assertEquals(1, createResults.size)
        assertEquals(0, createResults.filter { !it.success }.size)

        val deleteResults = dataSource.execute("jimmer_code_gen", "DROP TABLE jimmer_code_gen.`table`", true)

        assertEquals(1, deleteResults.size)
        assertEquals(0, deleteResults.filter { !it.success }.size)
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

        val sql = dataSource.createSql(
            listOf(table),
            emptyList()
        )
        assertEquals(
            """CREATE TABLE jimmer_code_gen."table" (
    "Name" INTEGER GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    "Another Name" numeric(5,
    4) NOT NULL,
    CONSTRAINT table_pkey PRIMARY KEY ("Name")
);
COMMENT ON TABLE jimmer_code_gen."table" IS 'comment';
COMMENT ON COLUMN jimmer_code_gen."table"."Name" IS 'comment';
COMMENT ON COLUMN jimmer_code_gen."table"."Another Name" IS 'Another comment';
""",
            sql
        )

        val createResults = dataSource.execute("jimmer_code_gen", sql, true)

        assertEquals(4, createResults.size)
        assertEquals(0, createResults.filter { !it.success }.size)

        val deleteResults = dataSource.execute("jimmer_code_gen", "DROP TABLE jimmer_code_gen.\"table\"", true)

        assertEquals(1, deleteResults.size)
        assertEquals(0, deleteResults.filter { !it.success }.size)
    }
}
