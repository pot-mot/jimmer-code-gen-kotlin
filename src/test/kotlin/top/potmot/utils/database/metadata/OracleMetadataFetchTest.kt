package top.potmot.utils.database.metadata

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import top.potmot.entity.database.dto.TableInput
import java.sql.DriverManager

class OracleMetadataFetchTest {
    @Test
    fun testXe21Metadata() {
        DriverManager.getConnection(
            "jdbc:oracle:thin:@localhost:39120:XE",
            "test",
            "test"
        ).use { connection ->
            val result = fetchMetadata(connection)
            assetResult(result)
        }
    }

    @Test
    fun testXe18Metadata() {
        DriverManager.getConnection(
            "jdbc:oracle:thin:@localhost:39121:XE",
            "test",
            "test"
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
ID 自增主键 NUMBER(19) 19,null PRIMARY DEFAULT "TEST"."SEQ_TEST_TABLE_ID"."NEXTVAL" 
USER_ID 用户ID NUMBER(10) 10,null
GROUP_ID 组ID NUMBER(10) 10,null
CATEGORY_ID 分类ID NUMBER(10) 10,null
NULLABLE_USER_ID 可空用户ID NUMBER(10) 10,null NULL
NAME 名称 VARCHAR2(50) 50,null NULL
EMAIL 邮箱 VARCHAR2(100) 100,null NULL
STATUS 状态 NUMBER(3) 3,null NULL DEFAULT 1 
TYPE_INT 整数类型 NUMBER(10) 10,null NULL
TYPE_BIGINT 大整数类型 NUMBER(19) 19,null NULL
TYPE_SMALLINT 小整数类型 NUMBER(5) 5,null NULL
TYPE_DECIMAL 精确小数类型 NUMBER(10,2) 10,2 NULL
TYPE_FLOAT 单精度浮点数 BINARY_FLOAT 4,null NULL
TYPE_DOUBLE 双精度浮点数 BINARY_DOUBLE 8,null NULL
TYPE_BOOLEAN 布尔类型 NUMBER(1) 1,null NULL
TYPE_DATE 日期类型 DATE 7,null NULL
TYPE_DATETIME 日期时间类型 TIMESTAMP(6) 11,6 NULL
TYPE_TIMESTAMP 时间戳类型 TIMESTAMP(6) 11,6 NULL DEFAULT CURRENT_TIMESTAMP 
TYPE_TIMESTAMP_TZ 时区时间戳类型 TIMESTAMP(6) WITH TIME ZONE 13,6 NULL
TYPE_TEXT 文本类型 CLOB 4000,null NULL
TYPE_CHECK_ENUM 枚举类型检查 VARCHAR2(20) 20,null NULL
TYPE_JSON JSON数据类型 CLOB 4000,null NULL
TYPE_BLOB 二进制大对象类型 BLOB 4000,null NULL
TYPE_BIT 位类型 RAW(8) 8,null NULL
                """.trim().split("\n"),
                testTable.columns.map { it.stringify() }
            )

            Assertions.assertEquals(2, testTable.indexes.size)
            Assertions.assertLinesMatch(
                """
UK_EMAIL true EMAIL
IDX_NAME_STATUS false NAME,STATUS
                """.trim().split("\n"),
                testTable.indexes.map { it.stringify() }
            )

            Assertions.assertEquals(3, testTable.foreignKeys.size)
            Assertions.assertLinesMatch(
                """
FK_GROUP_CATEGORY TEST.TEST_GROUP_CATEGORIES GROUP_ID -> GROUP_ID,CATEGORY_ID -> CATEGORY_ID CASCADE RESTRICT
FK_NULLABLE_USER TEST.TEST_USER NULLABLE_USER_ID -> ID CASCADE SET NULL
FK_USER TEST.TEST_USER USER_ID -> ID CASCADE CASCADE
                """.trim().split("\n"),
                testTable.foreignKeys.sortedBy { it.name }.map { it.stringify() }
            )

            Assertions.assertEquals(1, testTable.checks.size)
            Assertions.assertLinesMatch(
                """
CHK_TYPE_CHECK_ENUM type_check_enum IN ('value1', 'value2', 'value3')
                """.trim().split("\n"),
                testTable.checks.map { it.stringify() }
            )
        }
    }
}
