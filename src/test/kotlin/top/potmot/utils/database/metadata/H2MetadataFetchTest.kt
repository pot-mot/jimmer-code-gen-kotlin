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
            Assertions.assertLinesMatch(
                """
ID 自增主键 INTEGER 32,null PRIMARY AUTO_INCREMENT
USER_ID 用户ID INTEGER 32,null
GROUP_ID 组ID INTEGER 32,null
CATEGORY_ID 分类ID INTEGER 32,null
NULLABLE_USER_ID 可空用户ID INTEGER 32,null NULL
NAME 名称 CHARACTER VARYING(50) 50,null NULL
EMAIL 邮箱 CHARACTER VARYING(100) 100,null NULL
STATUS 状态 SMALLINT 16,null NULL DEFAULT 1 
TYPE_INT 整数类型 INTEGER 32,null NULL
TYPE_INT2 2字节整数类型 SMALLINT 16,null NULL
TYPE_INT4 4字节整数类型 INTEGER 32,null NULL
TYPE_INT8 8字节整数类型 BIGINT 64,null NULL
TYPE_INTEGER 整数类型别名 INTEGER 32,null NULL
TYPE_INT_ARRAY 整数数组类型 INTEGER ARRAY[10] 10,null NULL
TYPE_TINYINT 极小整数类型 TINYINT 8,null NULL
TYPE_SMALLINT 小整数类型 SMALLINT 16,null NULL
TYPE_BIGINT 大整数类型 BIGINT 64,null NULL
TYPE_NUMERIC  NUMERIC(100000) 100000,null NULL
TYPE_NUMERIC_10 精确数值类型(10) NUMERIC(10) 10,null NULL
TYPE_NUMERIC_10_2 精确数值类型(10, 2) NUMERIC(10,2) 10,2 NULL
TYPE_NUMERIC_10_ARRAY 精确数值数组类型(10) NUMERIC(10) ARRAY[10] 10,null NULL
TYPE_DECIMAL  DECIMAL(100000) 100000,null NULL
TYPE_DECIMAL_10 精确数值类型(10) DECIMAL(10) 10,null NULL
TYPE_DECIMAL_10_2 精确数值类型(10, 2) DECIMAL(10,2) 10,2 NULL
TYPE_DEC  DECIMAL(100000) 100000,null NULL
TYPE_DEC_FLOAT  DECFLOAT(100000) 100000,null NULL
TYPE_DEC_FLOAT_10  DECFLOAT(10) 10,null NULL
TYPE_FLOAT 单精度浮点数 REAL 24,null NULL
TYPE_DOUBLE 双精度浮点数 DOUBLE PRECISION 53,null NULL
TYPE_BOOLEAN 布尔类型 BOOLEAN 1,null NULL
TYPE_DATE 日期类型 DATE 10,null NULL
TYPE_TIME 时间类型 TIME 8,null NULL
TYPE_TIME_TZ  TIME WITH TIME ZONE 14,null NULL
TYPE_TIME_3  TIME(3) 12,3 NULL
TYPE_TIME_3_TZ  TIME(3) WITH TIME ZONE 18,3 NULL
TYPE_SMALL_DATETIME  TIMESTAMP 19,null NULL
TYPE_DATETIME 日期时间类型 TIMESTAMP(6) 26,6 NULL
TYPE_DATETIME2  TIMESTAMP(6) 26,6 NULL
TYPE_TIMESTAMP 时间戳类型 TIMESTAMP(6) 26,6 NULL DEFAULT CURRENT_TIMESTAMP 
TYPE_TIMESTAMP_3  TIMESTAMP(3) 23,3 NULL
TYPE_TIMESTAMP_TZ 时区时间戳类型 TIMESTAMP(6) WITH TIME ZONE 32,6 NULL
TYPE_TIMESTAMP_3_TZ  TIMESTAMP(3) WITH TIME ZONE 29,3 NULL
TYPE_TIMESTAMP_3_TZ_ARRAY  TIMESTAMP(3) WITH TIME ZONE ARRAY[10] 10,null NULL
TYPE_TEXT 文本类型 CHARACTER VARYING(1000000000) 1000000000,null NULL
TYPE_TINYTEXT 短文本类型 CHARACTER VARYING(1000000000) 1000000000,null NULL
TYPE_MEDIUMTEXT 中等文本类型 CHARACTER VARYING(1000000000) 1000000000,null NULL
TYPE_LONGTEXT 长文本类型 CHARACTER VARYING(1000000000) 1000000000,null NULL
TYPE_UUID  UUID 16,null NULL
TYPE_CHECK_ENUM 枚举类型检查 CHARACTER VARYING(20) 20,null NULL
TYPE_LONG_VARCHAR  CHARACTER VARYING(1000000000) 1000000000,null NULL
TYPE_CHAR 字符类型 CHARACTER(20) 20,null NULL
TYPE_NCHAR 宽字符类型 CHARACTER(20) 20,null NULL
TYPE_NVARCHAR 宽字符文本类型 CHARACTER VARYING(20) 20,null NULL
TYPE_VARCHAR2 字符文本类型 CHARACTER VARYING(20) 20,null NULL
TYPE_JSON JSON数据类型 JSON 1000000000,null NULL
TYPE_BIT 位类型 BOOLEAN 1,null NULL
TYPE_BINARY  BINARY(1) 1,null NULL
TYPE_BINARY_8  BINARY(8) 8,null NULL
TYPE_VARBINARY  BINARY VARYING(1000000000) 1000000000,null NULL
TYPE_VARBINARY_8  BINARY VARYING(8) 8,null NULL
TYPE_BYTEA  BINARY VARYING(1000000000) 1000000000,null NULL
TYPE_BINARY_1000000000  BINARY(1000000000) 1000000000,null NULL
TYPE_BLOB 二进制大对象类型 BINARY LARGE OBJECT 2147483647,null NULL
TYPE_TINYBLOB  BINARY LARGE OBJECT 2147483647,null NULL
TYPE_MEDIUMBLOB  BINARY LARGE OBJECT 2147483647,null NULL
TYPE_LONG_BLOB  BINARY LARGE OBJECT 2147483647,null NULL
TYPE_LONG_VARBINARY  BINARY VARYING(1000000000) 1000000000,null NULL
TYPE_CLOB  CHARACTER LARGE OBJECT 2147483647,null NULL
TYPE_NCLOB  CHARACTER LARGE OBJECT 2147483647,null NULL
                """.trimIndent().trim().split("\n"),
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
FK_GROUP_CATEGORY PUBLIC.TEST_GROUP_CATEGORIES GROUP_ID -> GROUP_ID,CATEGORY_ID -> CATEGORY_ID RESTRICT RESTRICT
FK_NULLABLE_USER PUBLIC.TEST_USER NULLABLE_USER_ID -> ID SET NULL SET NULL
FK_USER PUBLIC.TEST_USER USER_ID -> ID CASCADE CASCADE
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