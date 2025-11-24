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
type_integer  int 10,null NULL
type_tinyint  tinyint 3,null NULL
type_smallint 小整数类型 smallint 5,null NULL
type_bigint 大整数类型 bigint 19,null NULL
type_numeric  numeric(18) 18,null NULL
type_numeric_10  numeric(10) 10,null NULL
type_numeric_10_2  numeric(10,2) 10,2 NULL
type_decimal 精确小数类型 decimal(18) 18,null NULL
type_decimal_10  decimal(10) 10,null NULL
type_decimal_10_2  decimal(10,2) 10,2 NULL
type_dec  decimal(18) 18,null NULL
type_real  real 24,null NULL
type_float 单精度浮点数 float 53,null NULL
type_double_precision  float 53,null NULL
type_date  date 10,null NULL
type_time  time(7) 16,7 NULL
type_time_3  time(3) 12,3 NULL
type_datetime  datetime2(7) 27,7 NULL
type_datetime_3  datetime2(3) 23,3 NULL
type_datetime_default  datetime2(7) 27,7 NULL DEFAULT (getdate()) 
type_timestamp  timestamp 8,null
type_datetimeoffset  datetimeoffset(7) 34,7 NULL
type_datetimeoffset_3  datetimeoffset(3) 30,3 NULL
type_text  text 2147483647,null NULL
type_ntext  ntext 1073741823,null NULL
type_guid  uniqueidentifier 36,null NULL
type_check_enum  varchar(20) 20,null NULL
type_char  char(20) 20,null NULL
type_nchar  nchar(20) 20,null NULL
type_nvarchar  nvarchar(20) 20,null NULL
type_bit  bit 1,null NULL
type_binary  binary(1) 1,null NULL
type_binary_8  binary(8) 8,null NULL
type_varbinary  varbinary(1) 1,null NULL
type_varbinary_8  varbinary(8) 8,null NULL
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
fk_group_category dbo.test_group_categories group_id -> group_id,category_id -> category_id NO ACTION NO ACTION
fk_nullable_user dbo.test_user nullable_user_id -> id CASCADE SET NULL
fk_user dbo.test_user user_id -> id NO ACTION NO ACTION
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