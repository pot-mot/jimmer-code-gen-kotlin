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
                .getResourceAsStream("/database/sqlite.sql")
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
            Assertions.assertEquals(1, testGroupCategoriesTable.indexes.size)
            Assertions.assertEquals(0, testGroupCategoriesTable.foreignKeys.size)
        }

        val testTable = result.find { it.name == "test_table" }
        assert(testTable != null)
        testTable?.apply {
            Assertions.assertEquals("", testTable.comment)
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
type_real  REAL 2000000000,10 NULL
type_timestamp  TEXT 2000000000,null NULL DEFAULT datetime('now') 
type_text  TEXT 2000000000,null NULL
type_check_enum  TEXT 2000000000,null NULL
type_blob  BLOB 2000000000,null NULL
                """.trim().split("\n"),
                testTable.columns.map { it.stringify() }
            )

            Assertions.assertEquals(2, testTable.indexes.size)
            Assertions.assertLinesMatch(
                """
idx_name_status false name,status
sqlite_autoindex_test_table_1 true email
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

            Assertions.assertEquals(1, testTable.checks.size)
            Assertions.assertLinesMatch(
                listOf(" CHECK (type_check_enum IN ('value1', 'value2', 'value3')"),
                testTable.checks.map { it.stringify() }
            )
        }
    }
}