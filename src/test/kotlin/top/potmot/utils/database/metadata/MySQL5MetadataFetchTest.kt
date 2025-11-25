package top.potmot.utils.database.metadata

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import top.potmot.entity.database.dto.TableInput
import java.sql.DriverManager

class MySQL5MetadataFetchTest {
    @Test
    fun testMetadata() {
        DriverManager.getConnection(
            "jdbc:mysql://localhost:39100/test",
            "test",
            "test"
        ).use { connection ->
            val result = fetchMetadata(connection)
            assetResult(result)
        }
    }

    @Test
    fun testNoDatabaseMetadata() {
        DriverManager.getConnection(
            "jdbc:mysql://localhost:39100",
            "test",
            "test"
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
id 自增主键 int(11) 10,null PRIMARY AUTO_INCREMENT
user_id 用户ID int(11) 10,null
group_id 组ID int(11) 10,null
category_id 分类ID int(11) 10,null
nullable_user_id 可空用户ID int(11) 10,null NULL
name 名称 varchar(50) 50,null NULL
email 邮箱 varchar(100) 100,null NULL
status 状态 tinyint(4) 3,null NULL DEFAULT 1 
type_int 整数类型 int(11) 10,null NULL
type_int1 1位整数类型 tinyint(4) 3,null NULL
type_int2 2位整数类型 smallint(6) 5,null NULL
type_int4 4位整数类型 int(11) 10,null NULL
type_int8 8位整数类型 bigint(20) 19,null NULL
type_int_4  int(4) 10,null NULL
type_int_10  int(10) 10,null NULL
type_int_zerofill  int(10) unsigned zerofill 10,null NULL
type_int_unsigned  int(10) unsigned 10,null NULL
type_int_unsigned_zerofill  int(10) unsigned zerofill 10,null NULL
type_int_10_unsigned  int(10) unsigned 10,null NULL
type_tinyint 微整数类型 tinyint(4) 3,null NULL
type_tinyint_unsigned  tinyint(3) unsigned 3,null NULL
type_smallint 小整数类型 smallint(6) 5,null NULL
type_smallint_unsigned  smallint(5) unsigned 5,null NULL
type_mediumint 中整数类型 mediumint(9) 7,null NULL
type_mediumint_unsigned  mediumint(8) unsigned 8,null NULL
type_bigint 大整数类型 bigint(20) 19,null NULL
type_bigint_unsigned  bigint(20) unsigned 20,null NULL
type_numeric 精确数值类型 decimal(10,0) 10,null NULL
type_numeric_10 精确数值类型(10) decimal(10,0) 10,null NULL
type_numeric_10_2 精确数值类型(10, 2) decimal(10,2) 10,2 NULL
type_decimal 精确数值类型 decimal(10,0) 10,null NULL
type_decimal_10 精确数值类型(10) decimal(10,0) 10,null NULL
type_decimal_10_2 精确数值类型(10, 2) decimal(10,2) 10,2 NULL
type_decimal_unsigned 精确数值类型(无符号) decimal(10,0) unsigned 10,null NULL
type_dec  decimal(10,0) 10,null NULL
type_fixed  decimal(10,0) 10,null NULL
type_real 单精度浮点数 double 22,null NULL
type_real_unsigned  double unsigned 22,null NULL
type_float 单精度浮点数 float 12,null NULL
type_float_unsigned  double unsigned 22,null NULL
type_double 双精度浮点数 double 22,null NULL
type_double_unsigned  double unsigned 22,null NULL
type_double_precision 双精度浮点数 double 22,null NULL
type_float4 单精度浮点数 float 12,null NULL
type_float8 单精度浮点数 double 22,null NULL
type_boolean 布尔类型 tinyint(1) 1,null NULL
type_year 年类型 year(4) 4,null NULL
type_date 日期类型 date 10,null NULL
type_time 时间类型 time 8,null NULL
type_time_30 时间类型(3) time(3) 12,null NULL
type_datetime 日期时间类型 datetime 19,null NULL
type_datetime_3 日期时间类型(3) datetime(3) 23,null NULL
type_timestamp 时间戳类型 timestamp 19,null DEFAULT CURRENT_TIMESTAMP 
type_timestamp_default  timestamp 19,null DEFAULT CURRENT_TIMESTAMP 
type_varchar  varchar(255) 255,null NULL
type_char  char(255) 255,null NULL
type_character  char(255) 255,null NULL
type_character_binary  char(255) 255,null NULL
type_character_varying  varchar(255) 255,null NULL
type_nvarchar  varchar(255) 255,null NULL
type_nchar  char(255) 255,null NULL
type_varchar_binary  varchar(255) 255,null NULL
type_char_binary  char(255) 255,null NULL
type_nvarchar_binary  varchar(255) 255,null NULL
type_nchar_binary  char(255) 255,null NULL
type_text 文本类型 text 65535,null NULL
type_text_binary 二进制文本类型 text 65535,null NULL
type_tinytext 短文本类型 tinytext 255,null NULL
type_mediumtext 中长文本类型 mediumtext 16777215,null NULL
type_longtext 长文本类型 longtext 2147483647,null NULL
type_enum 枚举类型 enum('value1','value2','value3') 6,null NULL
type_set 集合类型 set('option1','option2','option3') 23,null NULL
type_json JSON数据类型 json 1073741824,null NULL
type_bit  bit(1) 1,null NULL
type_bit_8  bit(8) 8,null NULL
type_binary  binary(1) 1,null NULL
type_binary_8  binary(8) 8,null NULL
type_varbinary_8  varbinary(8) 8,null NULL
type_blob  BLOB 65535,null NULL
type_tinyblob  TINYBLOB 255,null NULL
type_mediumblob  MEDIUMBLOB 16777215,null NULL
type_long_blob  LONGBLOB 2147483647,null NULL
                """.trimIndent().trim().split("\n"),
                testTable.columns.map { it.stringify() }
            )

            Assertions.assertEquals(2, testTable.indexes.size)
            Assertions.assertLinesMatch(
                """
uk_email true email
idx_name_status false name,status
                """.trim().split("\n"),
                testTable.indexes.map { it.stringify() }
            )

            Assertions.assertEquals(3, testTable.foreignKeys.size)
            Assertions.assertLinesMatch(
                """
fk_group_category .test_group_categories group_id -> group_id,category_id -> category_id RESTRICT RESTRICT
fk_nullable_user .test_user nullable_user_id -> id SET NULL SET NULL
fk_user .test_user user_id -> id CASCADE CASCADE
                """.trim().split("\n"),
                testTable.foreignKeys.sortedBy { it.name }.map { it.stringify() }
            )

            Assertions.assertEquals(0, testTable.checks.size)
        }
    }
}