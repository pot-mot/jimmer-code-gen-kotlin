package top.potmot.utils.database.metadata

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import top.potmot.entity.database.dto.TableInput
import top.potmot.utils.sql.execute
import java.sql.DriverManager

class SqliteMetadataFetchTest {
    @Test
    fun testMetadata() {
        DriverManager.getConnection(
            "jdbc:sqlite::memory:",
            "",
            ""
        ).use { connection ->
            val initScript = this::class.java
                .getResourceAsStream("/sql/metadata-test-sqlite.sql")
                ?.bufferedReader()
                ?.readText()
                ?: throw Exception("no sqlite init script find")
            connection.execute(initScript)

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
            assert(testUserTable.indexes.size == 0)
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
            assert(testTable.columns.size == 22)
            Assertions.assertLinesMatch(
                """
id  INTEGER 2000000000,null NULL PRIMARY AUTO_INCREMENT
user_id  INTEGER 2000000000,null
group_id  INTEGER 2000000000,null
category_id  INTEGER 2000000000,null
nullable_user_id  INTEGER 2000000000,null NULL
name  TEXT 2000000000,null NULL
email  TEXT 2000000000,null NULL
status  INTEGER 2000000000,null NULL DEFAULT 1 
type_int  INTEGER 2000000000,null NULL
type_bigint  INTEGER 2000000000,null NULL
type_smallint  INTEGER 2000000000,null NULL
type_decimal  REAL 2000000000,10 NULL
type_float  REAL 2000000000,10 NULL
type_double  REAL 2000000000,10 NULL
type_boolean  INTEGER 2000000000,null NULL
type_date  TEXT 2000000000,null NULL
type_datetime  TEXT 2000000000,null NULL
type_timestamp  TEXT 2000000000,null NULL DEFAULT datetime('now') 
type_timestamp_tz  TEXT 2000000000,null NULL
type_text  TEXT 2000000000,null NULL
type_check_enum  TEXT 2000000000,null NULL
type_blob  BLOB 2000000000,null NULL
                """.trim().split("\n"),
                testTable.columns.map { it.stringify() }
            )

            assert(testTable.indexes.size == 2)
            Assertions.assertLinesMatch(
                """
idx_name_status false name,status
sqlite_autoindex_test_table_1 true email
                """.trim().split("\n"),
                testTable.indexes.map { it.stringify() }
            )

            assert(testTable.foreignKeys.size == 3)
            Assertions.assertLinesMatch(
                """
fk_group_category .test_table group_id -> group_id RESTRICT RESTRICT
fk_nullable_user .test_table nullable_user_id -> id SET NULL SET NULL
fk_user .test_table user_id -> id CASCADE CASCADE
                """.trim().split("\n"),
                testTable.foreignKeys.sortedBy { it.name }.map { it.stringify() }
            )

            assert(testTable.checks.size == 1)
            Assertions.assertLinesMatch(
                listOf(" CHECK (type_check_enum IN ('value1', 'value2', 'value3')"),
                testTable.checks.map { it.stringify() }
            )
        }
    }
}