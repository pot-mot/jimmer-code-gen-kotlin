package top.potmot.service

import org.babyfish.jimmer.kt.unload
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import top.potmot.entity.database.DatabaseType
import top.potmot.entity.database.DbDatabaseDraft
import top.potmot.entity.database.dto.*
import top.potmot.error.DatabaseException

@SpringBootTest
@Transactional(rollbackFor = [Throwable::class])
open class DatabaseServiceTest(
    @Autowired
    private val databaseService: DatabaseService
) {
    @Test
    fun testInsert() {
        // 创建插入数据
        val input = DatabaseInsertInput(
            name = "test_database",
            type = DatabaseType.MYSQL,
            url = "jdbc:mysql://localhost:39100/test",
            username = "test",
            password = "test",
        )

        val view = databaseService.insert(input)

        Assertions.assertEquals(input.toEntity {
            unload(this, DbDatabaseDraft::username)
            unload(this, DbDatabaseDraft::password)
        }, view.toEntity {
            unload(this, DbDatabaseDraft::id)
        })
    }

    @Test
    fun testUpdate() {
        val insertInput = DatabaseInsertInput(
            name = "test_database",
            type = DatabaseType.MYSQL,
            url = "jdbc:mysql://localhost:39100/test",
            username = "test",
            password = "test",
        )
        val view = databaseService.insert(insertInput)

        val updateInput = DatabaseUpdateInput(
            id = view.id,
            name = "updated_test_database",
            type = DatabaseType.POSTGRESQL,
            url = "jdbc:postgresql://localhost:39110/test",
            username = "test",
            password = "test",
        )
        val updatedView = databaseService.update(updateInput)

        Assertions.assertEquals(updateInput.toEntity {
            unload(this, DbDatabaseDraft::username)
            unload(this, DbDatabaseDraft::password)
        }, updatedView.toEntity())
    }

    @Test
    fun testInsertTypeNotMath() {
        val input = DatabaseInsertInput(
            name = "test_database",
            type = DatabaseType.POSTGRESQL,
            url = "jdbc:mysql://localhost:39100/test",
            username = "test",
            password = "test",
        )
        Assertions.assertThrows(DatabaseException.DatabaseTypeNotMatch::class.java) {
            databaseService.insert(input)
        }
    }

    @Test
    fun testUpdateTypeNotMath() {
        val insertInput = DatabaseInsertInput(
            name = "test_database",
            type = DatabaseType.MYSQL,
            url = "jdbc:mysql://localhost:39100/test",
            username = "test",
            password = "test",
        )
        val view = databaseService.insert(insertInput)
        val updateInput = DatabaseUpdateInput(
            id = view.id,
            name = "updated_test_database",
            type = DatabaseType.POSTGRESQL,
            url = "jdbc:mysql://localhost:39100/test",
            username = "test",
            password = "test",
        )
        Assertions.assertThrows(DatabaseException.DatabaseTypeNotMatch::class.java) {
            databaseService.update(updateInput)
        }
    }

    @Test
    fun testDelete() {
        // 先插入一条数据
        val input = DatabaseInsertInput(
            name = "test_database",
            type = DatabaseType.MYSQL,
            url = "jdbc:mysql://localhost:39100/test",
            username = "test",
            password = "test",
        )
        val view = databaseService.insert(input)

        val deletedCount = databaseService.delete(view.id)
        Assertions.assertEquals(1, deletedCount)
    }

    private val testDatabaseList = listOf(
        DatabaseInsertInput(
            name = "test-mysql5",
            type = DatabaseType.MYSQL,
            url = "jdbc:mysql://localhost:39100/test",
            username = "test",
            password = "test",
        ),
        DatabaseInsertInput(
            name = "test-mysql8",
            type = DatabaseType.MYSQL,
            url = "jdbc:mysql://localhost:39101/test",
            username = "test",
            password = "test",
        ),
        DatabaseInsertInput(
            name = "test-postgresql16",
            type = DatabaseType.POSTGRESQL,
            url = "jdbc:postgresql://localhost:39110/test",
            username = "test",
            password = "test",
        ),
        DatabaseInsertInput(
            name = "test-postgresql17",
            type = DatabaseType.POSTGRESQL,
            url = "jdbc:postgresql://localhost:39111/test",
            username = "test",
            password = "test",
        ),
        DatabaseInsertInput(
            name = "test-oracleXe21",
            type = DatabaseType.ORACLE,
            url = "jdbc:oracle:thin:@localhost:39120:XE",
            username = "test",
            password = "test",
        ),
        DatabaseInsertInput(
            name = "test-oracleXe18",
            type = DatabaseType.ORACLE,
            url = "jdbc:oracle:thin:@localhost:39121:XE",
            username = "test",
            password = "test",
        ),
        DatabaseInsertInput(
            name = "test-sqlserver2019",
            type = DatabaseType.SQLSERVER,
            url = "jdbc:sqlserver://localhost:39130;databaseName=test;encrypt=false;",
            username = "test_login",
            password = "Test1234!"
        ),
        DatabaseInsertInput(
            name = "test-sqlserver2022",
            type = DatabaseType.SQLSERVER,
            url = "jdbc:sqlserver://localhost:39131;databaseName=test;encrypt=false;",
            username = "test_login",
            password = "Test1234!"
        ),
        DatabaseInsertInput(
            name = "test-h2",
            type = DatabaseType.H2,
            url = "jdbc:h2:mem:test;MODE=LEGACY;INIT=RUNSCRIPT FROM './src/test/resources/database/h2.sql'",
            username = "sa",
            password = ""
        )
    )

    @Test
    fun testFetchTable() {
        for (input in testDatabaseList) {
            val view = databaseService.insert(input)
            val testResult = databaseService.test(view.id)
            Assertions.assertTrue(testResult)

            val firstRefreshTables = databaseService.refreshTables(view.id)
            Assertions.assertEquals(3, firstRefreshTables.size)
            val savedTables = databaseService.fetchTables(view.id)
            Assertions.assertEquals(3, savedTables.size)
            val secondRefreshTables = databaseService.refreshTables(view.id)
            Assertions.assertEquals(3, secondRefreshTables.size)

            for (i in 0 until 3) {
                Assertions.assertEquals(savedTables[i].toEntity(), firstRefreshTables[i].toEntity())
                Assertions.assertEquals(savedTables[i].toEntity(), secondRefreshTables[i].toEntity())
            }

            val deleteCount = databaseService.delete(view.id)
            Assertions.assertEquals(
                1 + firstRefreshTables.size + firstRefreshTables.sumOf { it.columns.size + it.indexes.size + it.foreignKeys.size + it.checks.size },
                deleteCount
            )
        }
    }
}
