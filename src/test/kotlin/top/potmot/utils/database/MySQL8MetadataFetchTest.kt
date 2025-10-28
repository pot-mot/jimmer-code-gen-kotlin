package top.potmot.utils.database

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import top.potmot.entity.database.dto.TableInput
import top.potmot.utils.database.metadata.MySQLMetadataFetcher
import java.sql.DriverManager

class MySQL8MetadataFetchTest {
    @Test
    fun testMetadata() {
        DriverManager.getConnection(
            "jdbc:mysql://localhost:39101/test",
            "test",
            "test"
        ).use { connection ->
            val result = MySQLMetadataFetcher(connection).fetch()
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
            assert(testTable.columns.size == 26)
            Assertions.assertLinesMatch(
                """
id 自增主键 int 10,null PRIMARY AUTO_INCREMENT
user_id 用户ID int 10,null
group_id 组ID int 10,null
category_id 分类ID int 10,null
nullable_user_id 可空用户ID int 10,null NULL
name 名称 varchar(50) 50,null NULL
email 邮箱 varchar(100) 100,null NULL
status 状态 tinyint 3,null NULL DEFAULT 1 
type_int 整数类型 int 10,null NULL
type_bigint 大整数类型 bigint 19,null NULL
type_smallint 小整数类型 smallint 5,null NULL
type_decimal 精确小数类型 decimal(10,2) 10,2 NULL
type_float 单精度浮点数 float 12,null NULL
type_double 双精度浮点数 double 22,null NULL
type_boolean 布尔类型 tinyint(1) 1,null NULL
type_date 日期类型 date 10,null NULL
type_datetime 日期时间类型 datetime 19,null NULL
type_timestamp 时间戳类型 timestamp 19,null NULL DEFAULT CURRENT_TIMESTAMP 
type_text 文本类型 text 65535,null NULL
type_longtext 长文本类型 longtext 2147483647,null NULL
type_enum 枚举类型 enum('value1','value2','value3') 6,null NULL
type_set 集合类型 set('option1','option2','option3') 23,null NULL
type_check_enum 枚举类型检查 varchar(20) 20,null NULL (`type_check_enum` in (_utf8mb4'value1',_utf8mb4'value2',_utf8mb4'value3'))
type_json JSON数据类型 json 1073741824,null NULL
type_blob 二进制大对象类型 blob 65535,null NULL
type_bit 位类型 bit(8) 8,null NULL
                """.trim().split("\n"),
                testTable.columns.map { it.stringify() }
            )

            assert(testTable.foreignKeys.size == 3)
            Assertions.assertLinesMatch(
                """
fk_group_category .test_table group_id -> group_id RESTRICT RESTRICT
fk_user .test_table user_id -> id CASCADE CASCADE
fk_nullable_user .test_table nullable_user_id -> id SET NULL SET NULL
                """.trim().split("\n"),
                testTable.foreignKeys.map { it.stringify() }
            )

            assert(testTable.indexes.size == 6)
            Assertions.assertLinesMatch(
                """
PRIMARY true id
uk_email true email
fk_group_category false group_id,category_id
fk_nullable_user false nullable_user_id
fk_user false user_id
idx_name_status false name,status
                """.trim().split("\n"),
                testTable.indexes.map { it.stringify() }
            )
        }
    }
}