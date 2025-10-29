package top.potmot.utils.database

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import top.potmot.entity.database.dto.TableInput
import top.potmot.utils.database.metadata.OracleMetadataFetcher
import java.sql.DriverManager
import kotlin.use

class OracleMetadataFetchTest {
    @Test
    fun testXe21Metadata() {
        DriverManager.getConnection(
            "jdbc:oracle:thin:@localhost:39120:XE",
            "test",
            "test"
        ).use { connection ->
            val result = OracleMetadataFetcher(connection).fetch()
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
            val result = OracleMetadataFetcher(connection).fetch()
            assetResult(result)
        }
    }

    fun assetResult(result: List<TableInput>) {
        assert(result.size == 3)

        val testUserTable = result.find { it.name == "TEST_USER" }
        assert(testUserTable != null)
        testUserTable?.apply {
            assert(testUserTable.columns.size == 1)
            assert(testUserTable.columns[0].name == "ID")
            assert(testUserTable.indexes.size == 1)
            assert(testUserTable.foreignKeys.isEmpty())
        }

        val testGroupCategoriesTable = result.find { it.name == "TEST_GROUP_CATEGORIES" }
        assert(testGroupCategoriesTable != null)
        testGroupCategoriesTable?.apply {
            assert(testGroupCategoriesTable.columns.size == 2)
            assert(testGroupCategoriesTable.columns[0].name == "GROUP_ID")
            assert(testGroupCategoriesTable.columns[1].name == "CATEGORY_ID")
            assert(testGroupCategoriesTable.indexes.size == 1)
            assert(testGroupCategoriesTable.foreignKeys.isEmpty())
        }

        val testTable = result.find { it.name == "TEST_TABLE" }
        assert(testTable != null)
        testTable?.apply {
            assert(testTable.columns.size == 24)
            Assertions.assertLinesMatch(
                """
ID  NUMBER 19,null PRIMARY DEFAULT "TEST"."SEQ_TEST_TABLE_ID"."NEXTVAL" 
USER_ID  NUMBER 10,null
GROUP_ID  NUMBER 10,null
CATEGORY_ID  NUMBER 10,null
NULLABLE_USER_ID  NUMBER 10,null NULL
NAME  VARCHAR2 50,null NULL
EMAIL  VARCHAR2 100,null NULL
STATUS  NUMBER 3,null NULL DEFAULT 1 
TYPE_INT  NUMBER 10,null NULL
TYPE_BIGINT  NUMBER 19,null NULL
TYPE_SMALLINT  NUMBER 5,null NULL
TYPE_DECIMAL  NUMBER 10,2 NULL
TYPE_FLOAT  BINARY_FLOAT 4,null NULL
TYPE_DOUBLE  BINARY_DOUBLE 8,null NULL
TYPE_BOOLEAN  NUMBER 1,null NULL
TYPE_DATE  DATE 7,null NULL
TYPE_DATETIME  TIMESTAMP(6) 11,6 NULL
TYPE_TIMESTAMP  TIMESTAMP(6) 11,6 NULL DEFAULT CURRENT_TIMESTAMP 
TYPE_TIMESTAMP_TZ  TIMESTAMP(6) WITH TIME ZONE 13,6 NULL
TYPE_TEXT  CLOB 4000,null NULL
TYPE_CHECK_ENUM  VARCHAR2 20,null NULL
TYPE_JSON  CLOB 4000,null NULL
TYPE_BLOB  BLOB 4000,null NULL
TYPE_BIT  RAW 8,null NULL
                """.trim().split("\n"),
                testTable.columns.map { it.stringify() }
            )

            assert(testTable.indexes.size == 3)
            Assertions.assertLinesMatch(
                """
PK_TEST_TABLE true ID
UK_EMAIL true EMAIL
IDX_NAME_STATUS false NAME,STATUS
                """.trim().split("\n"),
                testTable.indexes.map { it.stringify() }
            )

            assert(testTable.foreignKeys.size == 3)
            Assertions.assertLinesMatch(
                """
FK_GROUP_CATEGORY TEST.TEST_TABLE GROUP_ID -> GROUP_ID CASCADE RESTRICT
FK_NULLABLE_USER TEST.TEST_TABLE NULLABLE_USER_ID -> ID CASCADE SET NULL
FK_USER TEST.TEST_TABLE USER_ID -> ID CASCADE CASCADE
                """.trim().split("\n"),
                testTable.foreignKeys.sortedBy { it.name }.map { it.stringify() }
            )

            assert(testTable.checks.size == 1)
            Assertions.assertLinesMatch(
                """
CHK_TYPE_CHECK_ENUM type_check_enum IN ('value1', 'value2', 'value3')
                """.trim().split("\n"),
                testTable.checks.map { it.stringify() }
            )
        }
    }
}
