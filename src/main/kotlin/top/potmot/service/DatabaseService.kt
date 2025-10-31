package top.potmot.service

import org.babyfish.jimmer.sql.ast.mutation.AssociatedSaveMode
import org.babyfish.jimmer.sql.ast.mutation.SaveMode
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.support.TransactionTemplate
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
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
import top.potmot.utils.database.metadata.fetchMetadata
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
    @Throws(DatabaseException.DataSourceNotFound::class)
    fun get(databaseId: UUID): DatabaseView {
        return sqlClient
            .createQuery(DbDatabase::class) {
                where(table.id eq databaseId)
                select(table.fetch(DatabaseView::class))
            }.fetchOneOrNull() ?: throw DatabaseException.dataSourceNotFound()
    }

    @PostMapping("/insert")
    fun insert(@RequestBody input: DatabaseInsertInput): DatabaseView {
        return transactionTemplate.executeNotNull {
            sqlClient
                .saveCommand(input) {
                    setMode(SaveMode.INSERT_ONLY)
                }.execute(DatabaseView::class)
                .modifiedView
        }
    }

    @PostMapping("/update")
    fun update(@RequestBody input: DatabaseUpdateInput): DatabaseView {
        return transactionTemplate.executeNotNull {
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
            }.fetchOneOrNull() ?: throw DatabaseException.dataSourceNotFound()
    }

    @PostMapping("/test")
    @Throws(DatabaseException.DataSourceNotFound::class)
    fun test(databaseId: UUID): Boolean {
        val database = getConnectionView(databaseId)
        return DriverManager.getConnection(
            database.url,
            database.username,
            database.password
        ).use { connection ->
            !connection.isClosed
        }
    }

    @PostMapping("/fetchTables")
    @Throws(DatabaseException.DataSourceNotFound::class)
    fun fetchTables(databaseId: UUID): List<TableView> {
        return sqlClient
            .createQuery(DbTable::class) {
                where(table.databaseId eq databaseId)
                select(table.fetch(TableView::class))
            }.execute()
    }

    @PostMapping("/refreshTables")
    @Throws(DatabaseException.DataSourceNotFound::class)
    fun refreshTables(databaseId: UUID): List<TableView> {
        return transactionTemplate.executeNotNull {
            val database = getConnectionView(databaseId)
            DriverManager.getConnection(
                database.url,
                database.username,
                database.password
            ).use { connection ->
                val tableInputs = fetchMetadata(connection)
                sqlClient
                    .saveEntitiesCommand(tableInputs.map { it.toEntity {
                        this.databaseId = databaseId
                    } }) {
                        setMode(SaveMode.UPSERT)
                        setAssociatedModeAll(AssociatedSaveMode.MERGE)
                    }.execute(TableView::class)
                    .viewItems.map { it.modifiedView }
            }
        }
    }

    @PostMapping("/delete")
    fun delete(databaseId: UUID): Int {
        return transactionTemplate.executeNotNull {
            sqlClient
                .createDelete(DbDatabase::class) {
                    where(table.id eq databaseId)
                }.execute()
        }
    }
}