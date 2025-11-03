package top.potmot.utils.database.metadata

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import top.potmot.entity.database.dto.TableInput
import java.sql.DriverManager

class SqlServerMetadataFetchTest {
    @Test
    fun test19Metadata() {
        DriverManager.getConnection(
            "jdbc:sqlserver://localhost:39130;databaseName=test;encrypt=false;",
            "test_login",
            "Test1234!"
        ).use { connection ->
            val result = fetchMetadata(connection)
            assetResult(result)
        }
    }

    @Test
    fun test22Metadata() {
        DriverManager.getConnection(
            "jdbc:sqlserver://localhost:39131;databaseName=test;encrypt=false;",
            "test_login",
            "Test1234!"
        ).use { connection ->
            val result = fetchMetadata(connection)
            assetResult(result)
        }
    }

    fun assetResult(result: List<TableInput>) {
        assert(result.size == 3)

        val testUserTable = result.find { it.name == "test_user" }
        assert(testUserTable != null)
        testUserTable?.apply {
            Assertions.assertEquals(1, testUserTable.columns.size)
            Assertions.assertEquals("id", testUserTable.columns[0].name)
            Assertions.assertEquals(0, testUserTable.indexes.size)
            Assertions.assertEquals(0, testUserTable.foreignKeys.size)
        }

        val testGroupCategoriesTable = result.find { it.name == "test_group_categories" }
        assert(testGroupCategoriesTable != null)
        testGroupCategoriesTable?.apply {
            Assertions.assertEquals(2, testGroupCategoriesTable.columns.size)
            Assertions.assertEquals("group_id", testGroupCategoriesTable.columns[0].name)
            Assertions.assertEquals("category_id", testGroupCategoriesTable.columns[1].name)
            Assertions.assertEquals(0, testGroupCategoriesTable.indexes.size)
            Assertions.assertEquals(0, testGroupCategoriesTable.foreignKeys.size)
        }

        val testTable = result.find { it.name == "test_table" }
        assert(testTable != null)
        testTable?.apply {
            Assertions.assertEquals("测试表", testTable.comment)
            Assertions.assertEquals(23, testTable.columns.size)
            Assertions.assertLinesMatch(
                """
id 自增主键 int identity 10,null PRIMARY AUTO_INCREMENT
user_id 用户ID int 10,null
group_id 组ID int 10,null
category_id 分类ID int 10,null
nullable_user_id 可空用户ID int 10,null NULL
name 名称 nvarchar(50) 50,null NULL
email 邮箱 nvarchar(100) 100,null NULL
status 状态 smallint 5,null NULL DEFAULT ((1)) 
type_int 整数类型 int 10,null NULL
type_bigint 大整数类型 bigint 19,null NULL
type_smallint 小整数类型 smallint 5,null NULL
type_decimal 精确小数类型 decimal(10,2) 10,2 NULL
type_float 单精度浮点数 real 24,null NULL
type_double 双精度浮点数 float 53,null NULL
type_boolean 布尔类型 bit 1,null NULL
type_date 日期类型 date 10,null NULL
type_datetime 日期时间类型 datetime2(7) 27,7 NULL
type_timestamp 时间戳类型 datetime2(7) 27,7 NULL DEFAULT (getdate()) 
type_timestamp_tz 时区时间戳类型 datetimeoffset(7) 34,7 NULL
type_text 文本类型 nvarchar(2147483647) 2147483647,null NULL
type_check_enum 枚举类型检查 nvarchar(20) 20,null NULL
type_blob 二进制大对象类型 varbinary(2147483647) 2147483647,null NULL
type_bit 位类型 binary(8) 8,null NULL
                """.trim().split("\n"),
                testTable.columns.map { it.stringify() }
            )

            Assertions.assertEquals(2, testTable.indexes.size)
            Assertions.assertLinesMatch(
                """
idx_name_status false name,status WHERE ([status]=(1))
uk_email true email
                """.trim().split("\n"),
                testTable.indexes.map { it.stringify() }
            )

            Assertions.assertEquals(3, testTable.foreignKeys.size)
            Assertions.assertLinesMatch(
                """
fk_group_category dbo.test_table group_id -> group_id NO ACTION NO ACTION
fk_nullable_user dbo.test_table nullable_user_id -> id CASCADE SET NULL
fk_user dbo.test_table user_id -> id NO ACTION NO ACTION
                """.trim().split("\n"),
                testTable.foreignKeys.sortedBy { it.name }.map { it.stringify() }
            )

            Assertions.assertEquals(1, testTable.checks.size)
            Assertions.assertLinesMatch(
                """
chk_type_check_enum ([type_check_enum]='value3' OR [type_check_enum]='value2' OR [type_check_enum]='value1')
                """.trim().split("\n"),
                testTable.checks.map { it.stringify() }
            )
        }
    }
}