package top.potmot.dataSource.liquibase

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import top.potmot.dataSource.h2DataSource
import top.potmot.dataSource.mysqlDataSource
import top.potmot.dataSource.postgresDataSource
import top.potmot.enumeration.TableType
import top.potmot.model.dto.GenTableModelInput
import top.potmot.model.extension.execute
import top.potmot.utils.liquibase.createSql
import java.sql.Types

@SpringBootTest
@ActiveProfiles("test-kotlin", "h2")
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class TestLiquibaseCreateSql {
    @Test
    @Order(1)
    fun testMysqlCreateSQL() {
        val dataSource = mysqlDataSource.toEntity()

        val sql = dataSource.createSql(
            listOf(table),
            emptyList()
        )
        assertEquals(
            """CREATE TABLE `table` (
    Name INT AUTO_INCREMENT NOT NULL COMMENT 'comment',
    `Another Name` DECIMAL(5,
    4) NOT NULL COMMENT 'Another comment',
    CONSTRAINT PK_TABLE PRIMARY KEY (Name)
) COMMENT='comment';
""",
            sql,
        )

        val dropExistsResults =
            dataSource.execute("jimmer_code_gen", "DROP TABLE IF EXISTS `table`", true)

        assertEquals(2, dropExistsResults.size)
        assertEquals(0, dropExistsResults.filter { !it.success }.size)

        val createResults =
            dataSource.execute("jimmer_code_gen", sql, true)

        assertEquals(2, createResults.size)
        assertEquals(0, createResults.filter { !it.success }.size)

        val deleteResults =
            dataSource.execute("jimmer_code_gen", "DROP TABLE `table`", true)

        assertEquals(2, deleteResults.size)
        assertEquals(0, deleteResults.filter { !it.success }.size)
    }

    @Test
    @Order(2)
    fun testPostgresCreateSQL() {
        val dataSource = postgresDataSource.toEntity()

        val sql = dataSource.createSql(
            listOf(table),
            emptyList()
        )
        assertEquals(
            """CREATE TABLE "table" (
    "Name" INTEGER GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    "Another Name" numeric(5,
    4) NOT NULL,
    CONSTRAINT table_pkey PRIMARY KEY ("Name")
);
COMMENT ON TABLE "table" IS 'comment';
COMMENT ON COLUMN "table"."Name" IS 'comment';
COMMENT ON COLUMN "table"."Another Name" IS 'Another comment';
""",
            sql
        )

        val dropExistsResults =
            dataSource.execute("jimmer_code_gen", "DROP TABLE IF EXISTS \"table\"", true)

        assertEquals(2, dropExistsResults.size)
        assertEquals(0, dropExistsResults.filter { !it.success }.size)

        val createResults =
            dataSource.execute("jimmer_code_gen", sql, true)

        assertEquals(5, createResults.size)
        assertEquals(0, createResults.filter { !it.success }.size)

        val deleteResults =
            dataSource.execute("jimmer_code_gen", "DROP TABLE \"table\"", true)

        assertEquals(2, deleteResults.size)
        assertEquals(0, deleteResults.filter { !it.success }.size)
    }

    @Test
    @Order(3)
    fun testH2CreateSQL() {
        val dataSource = h2DataSource.toEntity()

        val sql = dataSource.createSql(
            listOf(table),
            emptyList()
        )
        assertEquals(
            """CREATE TABLE "table" (
    Name INT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    "Another Name" numeric(5,
    4) NOT NULL,
    CONSTRAINT PK_TABLE PRIMARY KEY (Name)
);
COMMENT ON TABLE "table" IS 'comment';
COMMENT ON COLUMN "table".Name IS 'comment';
COMMENT ON COLUMN "table"."Another Name" IS 'Another comment';
""",
            sql,
        )

        val dropExistsResults =
            dataSource.execute("jimmer_code_gen", "DROP TABLE IF EXISTS `table`", true)

        assertEquals(2, dropExistsResults.size)
        assertEquals(0, dropExistsResults.filter { !it.success }.size)

        val createResults =
            dataSource.execute("jimmer_code_gen", sql, true)

        assertEquals(5, createResults.size)
        assertEquals(0, createResults.filter { !it.success }.size)

        val deleteResults =
            dataSource.execute("jimmer_code_gen", "DROP TABLE `table`", true)

        assertEquals(2, deleteResults.size)
        assertEquals(0, deleteResults.filter { !it.success }.size)
    }

    private val column1 = GenTableModelInput.TargetOf_columns(
        remark = "remark",
        orderKey = 2,
        name = "Name",
        typeCode = Types.INTEGER,
        overwriteByRaw = false,
        rawType = "int",
        dataSize = 5,
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
        overwriteByRaw = false,
        rawType = "decimal",
        dataSize = 5,
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
        type = TableType.TABLE,
        columns = listOf(
            column1,
            column2
        )
    )
}
