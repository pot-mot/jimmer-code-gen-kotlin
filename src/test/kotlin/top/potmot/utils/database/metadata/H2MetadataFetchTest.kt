package top.potmot.utils.database.metadata

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import top.potmot.entity.database.dto.TableInput
import java.sql.DriverManager

class H2MetadataFetchTest {
    @Test
    fun testMetadata() {
        DriverManager.getConnection(
            "jdbc:h2:mem:test;INIT=RUNSCRIPT FROM './src/test/resources/database/h2.sql'",
            "sa",
            ""
        ).use { connection ->
            val result = fetchMetadata(connection)
            assetResult(result)
        }
    }

    fun assetResult(result: List<TableInput>) {
        assert(result.size == 3)

        val testUserTable = result.find { it.name == "TEST_USER" }
        assert(testUserTable != null)
        testUserTable?.apply {
            Assertions.assertEquals(1, testUserTable.columns.size)
            Assertions.assertEquals("ID", testUserTable.columns[0].name)
            Assertions.assertEquals(0, testUserTable.indexes.size)
            Assertions.assertEquals(0, testUserTable.foreignKeys.size)
        }

        val testGroupCategoriesTable = result.find { it.name == "TEST_GROUP_CATEGORIES" }
        assert(testGroupCategoriesTable != null)
        testGroupCategoriesTable?.apply {
            Assertions.assertEquals(2, testGroupCategoriesTable.columns.size)
            Assertions.assertEquals("GROUP_ID", testGroupCategoriesTable.columns[0].name)
            Assertions.assertEquals("CATEGORY_ID", testGroupCategoriesTable.columns[1].name)
            Assertions.assertEquals(0, testGroupCategoriesTable.indexes.size)
            Assertions.assertEquals(0, testGroupCategoriesTable.foreignKeys.size)
        }

        val testTable = result.find { it.name == "TEST_TABLE" }
        assert(testTable != null)
        testTable?.apply {
            Assertions.assertEquals("测试表", testTable.comment)
            Assertions.assertEquals(24, testTable.columns.size)
            Assertions.assertLinesMatch(
                """
ID 自增主键 INTEGER 32,null PRIMARY AUTO_INCREMENT
USER_ID 用户ID INTEGER 32,null
GROUP_ID 组ID INTEGER 32,null
CATEGORY_ID 分类ID INTEGER 32,null
NULLABLE_USER_ID 可空用户ID INTEGER 32,null NULL
NAME 名称 CHARACTER VARYING 50,null NULL
EMAIL 邮箱 CHARACTER VARYING 100,null NULL
STATUS 状态 SMALLINT 16,null NULL DEFAULT 1 
TYPE_INT 整数类型 INTEGER 32,null NULL
TYPE_BIGINT 大整数类型 BIGINT 64,null NULL
TYPE_SMALLINT 小整数类型 SMALLINT 16,null NULL
TYPE_DECIMAL 精确小数类型 NUMERIC 10,2 NULL
TYPE_FLOAT 单精度浮点数 REAL 24,null NULL
TYPE_DOUBLE 双精度浮点数 DOUBLE PRECISION 53,null NULL
TYPE_BOOLEAN 布尔类型 BOOLEAN 1,null NULL
TYPE_DATE 日期类型 DATE 10,null NULL
TYPE_DATETIME 日期时间类型 TIMESTAMP 26,6 NULL
TYPE_TIMESTAMP 时间戳类型 TIMESTAMP 26,6 NULL DEFAULT CURRENT_TIMESTAMP 
TYPE_TIMESTAMP_TZ 时区时间戳类型 TIMESTAMP WITH TIME ZONE 32,6 NULL
TYPE_TEXT 文本类型 CHARACTER VARYING 1000000000,null NULL
TYPE_CHECK_ENUM 枚举类型检查 CHARACTER VARYING 20,null NULL
TYPE_JSON JSON数据类型 JSON 1000000000,null NULL
TYPE_BLOB 二进制大对象类型 BINARY VARYING 1000000000,null NULL
TYPE_BIT 位类型 BINARY 8,null NULL
                """.trim().split("\n"),
                testTable.columns.map { it.stringify() }
            )

            Assertions.assertEquals(2, testTable.indexes.size)
            Assertions.assertLinesMatch(
                """
UK_EMAIL_INDEX_C true EMAIL
IDX_NAME_STATUS false NAME,STATUS
                """.trim().split("\n"),
                testTable.indexes.map { it.stringify() }
            )

            Assertions.assertEquals(3, testTable.foreignKeys.size)
            Assertions.assertLinesMatch(
                """
FK_GROUP_CATEGORY PUBLIC.TEST_TABLE GROUP_ID -> GROUP_ID RESTRICT RESTRICT
FK_NULLABLE_USER PUBLIC.TEST_TABLE NULLABLE_USER_ID -> ID SET NULL SET NULL
FK_USER PUBLIC.TEST_TABLE USER_ID -> ID CASCADE CASCADE
                """.trim().split("\n"),
                testTable.foreignKeys.sortedBy { it.name }.map { it.stringify() }
            )

            Assertions.assertEquals(1, testTable.checks.size)
            Assertions.assertLinesMatch(
                """
CONSTRAINT_CE "TYPE_CHECK_ENUM" IN('value1', 'value2', 'value3')
                """.trim().split("\n"),
                testTable.checks.map { it.stringify() }
            )
        }
    }
}