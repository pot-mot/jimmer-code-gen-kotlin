package top.potmot.service

import org.babyfish.jimmer.sql.ast.mutation.SaveMode
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.asc
import org.babyfish.jimmer.sql.kt.ast.expression.desc
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.babyfish.jimmer.sql.kt.exists
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.support.TransactionTemplate
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import top.potmot.entity.model.Model
import top.potmot.entity.model.ModelHistory
import top.potmot.entity.model.createdTime
import top.potmot.entity.model.dto.ModelHistoryNoJsonView
import top.potmot.entity.model.dto.ModelHistoryView
import top.potmot.entity.model.dto.ModelInsertInput
import top.potmot.entity.model.dto.ModelNoJsonView
import top.potmot.entity.model.dto.ModelSpec
import top.potmot.entity.model.dto.ModelUpdateInput
import top.potmot.entity.model.dto.ModelView
import top.potmot.entity.model.dto.toHistory
import top.potmot.entity.model.id
import top.potmot.entity.model.modelId
import top.potmot.entity.model.modifiedTime
import top.potmot.error.DeleteException
import top.potmot.error.UpdateException
import top.potmot.utils.transaction.executeNotNull
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.UUID

@RestController
@RequestMapping("/model")
class ModelService(
    @Autowired
    private val sqlClient: KSqlClient,
    @Autowired
    private val transactionTemplate: TransactionTemplate
) {
    enum class ModelOrder {
        CREATE_TIME_ASC,
        CREATE_TIME_DESC,
        MODIFIED_TIME_ASC,
        MODIFIED_TIME_DESC,
    }

    @PostMapping("/list")
    fun list(@RequestBody spec: ModelSpec, modelOrder: ModelOrder): List<ModelNoJsonView> {
        return sqlClient
            .createQuery(Model::class) {
                where(spec)
                orderBy(
                    when (modelOrder) {
                        ModelOrder.CREATE_TIME_ASC -> table.createdTime.asc()
                        ModelOrder.CREATE_TIME_DESC -> table.createdTime.desc()
                        ModelOrder.MODIFIED_TIME_ASC -> table.modifiedTime.asc()
                        ModelOrder.MODIFIED_TIME_DESC -> table.modifiedTime.desc()
                    }
                )
                select(table.fetch(ModelNoJsonView::class))
            }.execute()
    }

    @PostMapping("/get")
    fun get(modelId: UUID): ModelView? {
        return sqlClient
            .createQuery(Model::class) {
                where(table.id eq modelId)
                select(table.fetch(ModelView::class))
            }.fetchOneOrNull()
    }

    @PostMapping("/insert")
    fun insert(@RequestBody input: ModelInsertInput): ModelNoJsonView {
        return transactionTemplate.executeNotNull {
            val savedModel = sqlClient
                .saveCommand(input.toEntity {
                    val now = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS)
                    createdTime = now
                    modifiedTime = now
                }) {
                    setMode(SaveMode.INSERT_ONLY)
                }.execute(ModelNoJsonView::class)
                .modifiedView

            val modelHistoryInput = input.toHistory(savedModel)

            sqlClient
                .saveCommand(modelHistoryInput) {
                    setMode(SaveMode.INSERT_ONLY)
                }.execute()

            savedModel
        }
    }

    @PostMapping("/update")
    @Throws(UpdateException::class)
    fun update(@RequestBody input: ModelUpdateInput): ModelNoJsonView {
        return transactionTemplate.executeNotNull {
            sqlClient
                .exists(Model::class) {
                    where(table.id eq input.id)
                }.let { if (!it) throw UpdateException.notExisted() }
            val savedModel = sqlClient
                .saveCommand(input.toEntity {
                    modifiedTime = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS)
                }) {
                    setMode(SaveMode.UPDATE_ONLY)
                }.execute(ModelNoJsonView::class)
                .modifiedView

            val modelHistoryInput = input.toHistory(savedModel)

            sqlClient
                .saveCommand(modelHistoryInput) {
                    setMode(SaveMode.INSERT_ONLY)
                }.execute()

            savedModel
        }
    }

    @PostMapping("/fetchHistories")
    fun fetchHistories(modelId: UUID): List<ModelHistoryNoJsonView> {
        return sqlClient
            .createQuery(ModelHistory::class) {
                where(table.modelId eq modelId)
                orderBy(table.modifiedTime.asc())
                select(table.fetch(ModelHistoryNoJsonView::class))
            }.execute()
    }

    @PostMapping("/getHistory")
    fun getHistory(modelHistoryId: UUID): ModelHistoryView? {
        return sqlClient
            .createQuery(ModelHistory::class) {
                where(table.id eq modelHistoryId)
                select(table.fetch(ModelHistoryView::class))
            }.fetchOneOrNull()
    }

    @PostMapping("/delete")
    @Throws(DeleteException::class)
    fun delete(modelId: UUID): Int {
        sqlClient
            .exists(Model::class) {
                where(table.id eq modelId)
            }.let { if (!it) throw DeleteException.notExisted() }
        return transactionTemplate.executeNotNull {
            sqlClient
                .createDelete(Model::class) {
                    where(table.id eq modelId)
                }.execute()
        }
    }

    @PostMapping("/deleteHistory")
    @Throws(DeleteException::class)
    fun deleteHistory(modelHistoryId: UUID): Int {
        sqlClient
            .exists(ModelHistory::class) {
                where(table.id eq modelHistoryId)
            }.let { if (!it) throw DeleteException.notExisted() }
        return transactionTemplate.executeNotNull {
            sqlClient
                .createDelete(ModelHistory::class) {
                    where(table.id eq modelHistoryId)
                }.execute()
        }
    }
}