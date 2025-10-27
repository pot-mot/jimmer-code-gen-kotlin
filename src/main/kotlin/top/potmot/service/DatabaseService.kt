package top.potmot.service

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
import top.potmot.entity.database.dto.DatabaseInsertInput
import top.potmot.entity.database.dto.DatabaseSpec
import top.potmot.entity.database.dto.DatabaseUpdateInput
import top.potmot.entity.database.dto.DatabaseView
import top.potmot.entity.database.dto.TableView
import top.potmot.entity.database.id
import top.potmot.utils.transaction.executeNotNull
import java.util.UUID

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

    @PostMapping("/insert")
    fun insert(@RequestBody input: DatabaseInsertInput): UUID {
        return transactionTemplate.executeNotNull {
            sqlClient
                .saveCommand(input) {
                    setMode(SaveMode.INSERT_ONLY)
                }.execute()
                .modifiedEntity.id
        }
    }

    @PostMapping("/update")
    fun update(@RequestBody input: DatabaseUpdateInput): UUID {
        return transactionTemplate.executeNotNull {
            sqlClient
                .saveCommand(input) {
                    setMode(SaveMode.UPDATE_ONLY)
                }.execute()
                .modifiedEntity.id
        }
    }

    @PostMapping("/test")
    fun test(databaseId: UUID): Boolean {
        // TODO
        return true
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
    fun refreshTables(databaseId: UUID) {
        return transactionTemplate.executeNotNull {
            // TODO
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