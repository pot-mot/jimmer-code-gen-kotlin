package top.potmot.service

import org.babyfish.jimmer.sql.ast.mutation.AssociatedSaveMode
import org.babyfish.jimmer.sql.ast.mutation.SaveMode
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.babyfish.jimmer.sql.kt.ast.expression.valueNotIn
import org.babyfish.jimmer.sql.kt.exists
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.support.TransactionTemplate
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import top.potmot.entity.database.DatabaseType
import top.potmot.entity.database.DbDatabase
import top.potmot.entity.database.DbTable
import top.potmot.entity.database.databaseId
import top.potmot.entity.database.dto.DatabaseConnectionView
import top.potmot.entity.database.dto.DatabaseInsertInput
import top.potmot.entity.database.dto.DatabaseSpec
import top.potmot.entity.database.dto.DatabaseUpdateInput
import top.potmot.entity.database.dto.DatabaseView
import top.potmot.entity.database.dto.TableView
import top.potmot.entity.database.id
import top.potmot.error.DatabaseException
import top.potmot.error.DeleteException
import top.potmot.error.UpdateException
import top.potmot.utils.database.metadata.fetchMetadata
import top.potmot.utils.database.metadata.getTypeFromConnection
import top.potmot.utils.transaction.executeNotNull
import java.sql.DriverManager
import java.util.UUID
import kotlin.use

@RestController
@RequestMapping("/database")
class DatabaseService(
    @Autowired
    private val sqlClient: KSqlClient,
    @Autowired
    private val transactionTemplate: TransactionTemplate
) {
    @PostMapping("/list")
    fun list(@RequestBody spec: DatabaseSpec): List<DatabaseView> {
        return sqlClient
            .createQuery(DbDatabase::class) {
                where(spec)
                select(table.fetch(DatabaseView::class))
            }.execute()
    }

    @PostMapping("/get")
    fun get(databaseId: UUID): DatabaseView? {
        return sqlClient
            .createQuery(DbDatabase::class) {
                where(table.id eq databaseId)
                select(table.fetch(DatabaseView::class))
            }.fetchOneOrNull()
    }

    @PostMapping("/insert")
    @Throws(
        DatabaseException.ConnectFail::class,
        DatabaseException.DatabaseTypeNotMatch::class,
    )
    fun insert(@RequestBody input: DatabaseInsertInput): DatabaseView {
        return transactionTemplate.executeNotNull {
            testConnection(input.type, input.url, input.username, input.password)
            sqlClient
                .saveCommand(input) {
                    setMode(SaveMode.INSERT_ONLY)
                }.execute(DatabaseView::class)
                .modifiedView
        }
    }

    @PostMapping("/update")
    @Throws(
        UpdateException::class,
        DatabaseException.ConnectFail::class,
        DatabaseException.DatabaseTypeNotMatch::class,
    )
    fun update(@RequestBody input: DatabaseUpdateInput): DatabaseView {
        return transactionTemplate.executeNotNull {
            sqlClient
                .exists(DbDatabase::class) {
                    where(table.id eq input.id)
                }.let { if (!it) throw UpdateException.notExisted() }
            testConnection(input.type, input.url, input.username, input.password)
            sqlClient
                .saveCommand(input) {
                    setMode(SaveMode.UPDATE_ONLY)
                }.execute(DatabaseView::class)
                .modifiedView
        }
    }

    fun getConnectionView(databaseId: UUID): DatabaseConnectionView {
        return sqlClient
            .createQuery(DbDatabase::class) {
                where(table.id eq databaseId)
                select(table.fetch(DatabaseConnectionView::class))
            }.fetchOneOrNull() ?: throw DatabaseException.DatabaseNotFound()
    }

    fun testConnection(type: DatabaseType, url: String, username: String, password: String) =
        DriverManager.getConnection(
            url,
            username,
            password
        ).use { connection ->
            if (connection.isClosed) {
                throw DatabaseException.connectFail()
            }
            if (getTypeFromConnection(connection) != type) {
                throw DatabaseException.databaseTypeNotMatch()
            }
        }


    @PostMapping("/test")
    @Throws(DatabaseException.DatabaseNotFound::class)
    fun test(databaseId: UUID): Boolean {
        val database = getConnectionView(databaseId)
        return try {
            testConnection(database.type, database.url, database.username, database.password)
            true
        } catch (_: Throwable) {
            false
        }
    }

    @PostMapping("/fetchTables")
    fun fetchTables(databaseId: UUID): List<TableView> {
        return sqlClient
            .createQuery(DbTable::class) {
                where(table.databaseId eq databaseId)
                select(table.fetch(TableView::class))
            }.execute()
    }

    @PostMapping("/refreshTables")
    @Throws(
        DatabaseException.DatabaseNotFound::class,
        DatabaseException.ConnectFail::class,
        DatabaseException.DatabaseTypeNotMatch::class,
    )
    fun refreshTables(databaseId: UUID): List<TableView> {
        return transactionTemplate.executeNotNull {
            val database = getConnectionView(databaseId)
            testConnection(database.type, database.url, database.username, database.password)
            DriverManager.getConnection(
                database.url,
                database.username,
                database.password
            ).use { connection ->
                val tableInputs = fetchMetadata(connection)
                val tableViews = sqlClient
                    .saveEntitiesCommand(tableInputs.map {
                        it.toEntity {
                            this.databaseId = databaseId
                        }
                    }) {
                        setMode(SaveMode.UPSERT)
                        setAssociatedModeAll(AssociatedSaveMode.MERGE)
                    }.execute(TableView::class)
                    .viewItems.map { it.modifiedView }
                val tableViewIds = tableViews.map { it.id }
                sqlClient.executeDelete(DbTable::class) {
                    where(table.databaseId eq databaseId)
                    where(table.id valueNotIn tableViewIds)
                }
                tableViews
            }
        }
    }

    @PostMapping("/delete")
    @Throws(DeleteException::class)
    fun delete(databaseId: UUID): Int {
        return transactionTemplate.executeNotNull {
            sqlClient
                .exists(DbDatabase::class) {
                    where(table.id eq databaseId)
                }.let { if (!it) throw DeleteException.notExisted() }
            sqlClient
                .createDelete(DbDatabase::class) {
                    where(table.id eq databaseId)
                }.execute()
        }
    }
}