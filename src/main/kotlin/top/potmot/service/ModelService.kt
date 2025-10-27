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
import top.potmot.entity.model.Model
import top.potmot.entity.model.ModelHistory
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
import top.potmot.utils.transaction.executeNotNull
import java.util.UUID

@RestController
@RequestMapping("/model")
class ModelService(
    @Autowired
    private val sqlClient: KSqlClient,
    @Autowired
    private val transactionTemplate: TransactionTemplate
) {
    @PostMapping("/list")
    fun list(@RequestBody spec: ModelSpec): List<ModelNoJsonView> {
        return sqlClient
            .createQuery(Model::class) {
                where(spec)
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
    fun insert(@RequestBody input: ModelInsertInput): UUID {
        return transactionTemplate.executeNotNull {
            // TODO set createdTime modifiedTime
            val savedModel = sqlClient
                .saveCommand(input) {
                    setMode(SaveMode.INSERT_ONLY)
                }.execute()
                .modifiedEntity

            val modelHistoryInput = input.toHistory(savedModel)

            sqlClient
                .saveCommand(modelHistoryInput) {
                    setMode(SaveMode.INSERT_ONLY)
                }.execute()

            savedModel.id
        }
    }

    @PostMapping("/update")
    fun update(@RequestBody input: ModelUpdateInput): UUID {
        return transactionTemplate.executeNotNull {
            // TODO set createdTime modifiedTime
            val savedModel = sqlClient
                .saveCommand(input) {
                    setMode(SaveMode.UPDATE_ONLY)
                }.execute()
                .modifiedEntity

            val modelHistoryInput = input.toHistory(savedModel)

            sqlClient
                .saveCommand(modelHistoryInput) {
                    setMode(SaveMode.INSERT_ONLY)
                }.execute()

            savedModel.id
        }
    }

    @PostMapping("/fetchHistories")
    fun fetchHistories(modelId: UUID): List<ModelHistoryNoJsonView> {
        return sqlClient
            .createQuery(ModelHistory::class) {
                where(table.modelId eq modelId)
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
    fun delete(modelId: UUID): Int {
        return transactionTemplate.executeNotNull {
            sqlClient
                .createDelete(Model::class) {
                    where(table.id eq modelId)
                }.execute()
        }
    }
}