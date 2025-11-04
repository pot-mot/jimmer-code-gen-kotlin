package top.potmot.utils.database.metadata

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import top.potmot.entity.database.dto.TableInput
import java.sql.DriverManager

class PostgreSQLMetadataFetchTest {
    @Test
    fun test16Metadata() {
        DriverManager.getConnection(
            "jdbc:postgresql://localhost:39110/test",
            "test",
            "test"
        ).use { connection ->
            val result = fetchMetadata(connection)
            assetResult(result)
        }
    }

    @Test
    fun test16NoDatabaseMetadata() {
        DriverManager.getConnection(
            "jdbc:postgresql://localhost:39110/",
            "test",
            "test"
        ).use { connection ->
            val result = fetchMetadata(connection)
            assetResult(result)
        }
    }

    @Test
    fun test17Metadata() {
        DriverManager.getConnection(
            "jdbc:postgresql://localhost:39111/test",
            "test",
            "test"
        ).use { connection ->
            val result = fetchMetadata(connection)
            assetResult(result)
        }
    }

    @Test
    fun test17NoDatabaseMetadata() {
        DriverManager.getConnection(
            "jdbc:postgresql://localhost:39111/",
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
            Assertions.assertEquals(29, testTable.columns.size)
            Assertions.assertLinesMatch(
                """
id 自增主键 integer 10,null PRIMARY AUTO_INCREMENT DEFAULT nextval('test_table_id_seq'::regclass) 
user_id 用户ID integer 10,null
group_id 组ID integer 10,null
category_id 分类ID integer 10,null
nullable_user_id 可空用户ID integer 10,null NULL
name 名称 character varying(50) 50,null NULL
email 邮箱 character varying(100) 100,null NULL
status 状态 smallint 5,null NULL DEFAULT 1 
type_int 整数类型 integer 10,null NULL
type_int_array 整数数组类型 integer[] 10,null NULL
type_int_array_fixed_size 整数定长数组类型 integer[] 10,null NULL
type_int_matrix22 二维定长整数数组类型 integer[] 10,null NULL
type_bigint 大整数类型 bigint 19,null NULL
type_smallint 小整数类型 smallint 5,null NULL
type_decimal 精确小数类型 numeric(10,2) 10,2 NULL
type_decimal_array 精确小数数组类型 numeric(10,2)[] 10,2 NULL
type_float 单精度浮点数 real 8,8 NULL
type_double 双精度浮点数 double precision 17,17 NULL
type_boolean 布尔类型 boolean 1,null NULL
type_date 日期类型 date 13,null NULL
type_datetime 日期时间类型 timestamp without time zone 29,6 NULL
type_timestamp 时间戳类型 timestamp without time zone 29,6 NULL DEFAULT CURRENT_TIMESTAMP 
type_timestamp_tz 时区时间戳类型 timestamp with time zone 35,6 NULL
type_text 文本类型 text 2147483647,null NULL
type_check_enum 枚举类型检查 character varying(20) 20,null NULL
type_json JSON数据类型 json 2147483647,null NULL
type_jsonb  jsonb 2147483647,null NULL
type_blob 二进制大对象类型 bytea 2147483647,null NULL
type_bit 位类型 bit(8) 8,null NULL
                """.trim().split("\n"),
                testTable.columns.map { it.stringify() }
            )

            Assertions.assertEquals(2, testTable.indexes.size)
            Assertions.assertLinesMatch(
                """
idx_name_status false name,status WHERE (status = 1)
uk_email true email
                """.trim().split("\n"),
                testTable.indexes.map { it.stringify() }
            )

            Assertions.assertEquals(3, testTable.foreignKeys.size)
            Assertions.assertLinesMatch(
                """
fk_group_category public.test_group_categories group_id -> group_id RESTRICT RESTRICT
fk_nullable_user public.test_user nullable_user_id -> id SET NULL SET NULL
fk_user public.test_user user_id -> id CASCADE CASCADE
                """.trim().split("\n"),
                testTable.foreignKeys.sortedBy { it.name }.map { it.stringify() }
            )

            Assertions.assertEquals(1, testTable.checks.size)
            Assertions.assertLinesMatch(
                """
test_table_type_check_enum_check CHECK (((type_check_enum)::text = ANY ((ARRAY['value1'::character varying, 'value2'::character varying, 'value3'::character varying])::text[])))
                """.trim().split("\n"),
                testTable.checks.map { it.stringify() }
            )
        }
    }
}