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
            assert(testUserTable.columns.size == 1)
            assert(testUserTable.columns[0].name == "id")
            assert(testUserTable.indexes.size == 1)
            assert(testUserTable.foreignKeys.isEmpty())
        }

        val testGroupCategoriesTable = result.find { it.name == "test_group_categories" }
        assert(testGroupCategoriesTable != null)
        testGroupCategoriesTable?.apply {
            assert(testGroupCategoriesTable.columns.size == 2)
            assert(testGroupCategoriesTable.columns[0].name == "group_id")
            assert(testGroupCategoriesTable.columns[1].name == "category_id")
            assert(testGroupCategoriesTable.indexes.size == 1)
            assert(testGroupCategoriesTable.foreignKeys.isEmpty())
        }

        val testTable = result.find { it.name == "test_table" }
        assert(testTable != null)
        testTable?.apply {
            assert(testTable.comment == "测试表")
            assert(testTable.columns.size == 23)
            Assertions.assertLinesMatch(
                """
id 自增主键 int identity 10,null PRIMARY AUTO_INCREMENT
user_id 用户ID int 10,null
group_id 组ID int 10,null
category_id 分类ID int 10,null
nullable_user_id 可空用户ID int 10,null NULL
name 名称 nvarchar 50,null NULL
email 邮箱 nvarchar 100,null NULL
status 状态 smallint 5,null NULL DEFAULT ((1)) 
type_int 整数类型 int 10,null NULL
type_bigint 大整数类型 bigint 19,null NULL
type_smallint 小整数类型 smallint 5,null NULL
type_decimal 精确小数类型 decimal 10,2 NULL
type_float 单精度浮点数 real 24,null NULL
type_double 双精度浮点数 float 53,null NULL
type_boolean 布尔类型 bit 1,null NULL
type_date 日期类型 date 10,null NULL
type_datetime 日期时间类型 datetime2 27,7 NULL
type_timestamp 时间戳类型 datetime2 27,7 NULL DEFAULT (getdate()) 
type_timestamp_tz 时区时间戳类型 datetimeoffset 34,7 NULL
type_text 文本类型 nvarchar 2147483647,null NULL
type_check_enum 枚举类型检查 nvarchar 20,null NULL
type_blob 二进制大对象类型 varbinary 2147483647,null NULL
type_bit 位类型 binary 8,null NULL
                """.trim().split("\n"),
                testTable.columns.map { it.stringify() }
            )

            assert(testTable.indexes.size == 3)
            Assertions.assertLinesMatch(
                """
pk_test_table true id
uk_email true email
idx_name_status false name,status
                """.trim().split("\n"),
                testTable.indexes.map { it.stringify() }
            )

            assert(testTable.foreignKeys.size == 3)
            Assertions.assertLinesMatch(
                """
fk_group_category dbo.test_table group_id -> group_id NO ACTION NO ACTION
fk_nullable_user dbo.test_table nullable_user_id -> id CASCADE SET NULL
fk_user dbo.test_table user_id -> id NO ACTION NO ACTION
                """.trim().split("\n"),
                testTable.foreignKeys.sortedBy { it.name }.map { it.stringify() }
            )

            assert(testTable.checks.size == 1)
            Assertions.assertLinesMatch(
                """
chk_type_check_enum ([type_check_enum]='value3' OR [type_check_enum]='value2' OR [type_check_enum]='value1')
                """.trim().split("\n"),
                testTable.checks.map { it.stringify() }
            )
        }
    }
}