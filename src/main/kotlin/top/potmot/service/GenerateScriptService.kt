package top.potmot.service

import org.babyfish.jimmer.sql.ast.mutation.SaveMode
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.babyfish.jimmer.sql.kt.ast.expression.valueIn
import org.babyfish.jimmer.sql.kt.exists
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.support.TransactionTemplate
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import top.potmot.entity.script.id
import top.potmot.entity.script.GenerateScript
import top.potmot.entity.script.dto.GenerateScriptInsertInput
import top.potmot.entity.script.dto.GenerateScriptUpdateInput
import top.potmot.entity.script.dto.GenerateScriptView
import top.potmot.error.UpdateException
import top.potmot.utils.transaction.executeNotNull
import java.util.UUID

@RestController
@RequestMapping("/generateScript")
class GenerateScriptService(
    @Autowired
    private val sqlClient: KSqlClient,
    @Autowired
    private val transactionTemplate: TransactionTemplate
) {
    @PostMapping("/list")
    fun list(): List<GenerateScriptView> {
        return sqlClient
            .createQuery(GenerateScript::class) {
                select(table.fetch(GenerateScriptView::class))
            }.execute()
    }

    @PostMapping("/get")
    fun get(scriptId: UUID): GenerateScriptView? {
        return sqlClient
            .createQuery(GenerateScript::class) {
                where(table.id eq scriptId)
                select(table.fetch(GenerateScriptView::class))
            }.fetchOneOrNull()
    }


    @PostMapping("/insert")
    fun insert(@RequestBody input: GenerateScriptInsertInput): GenerateScriptView {
        return transactionTemplate.executeNotNull {
            sqlClient
                .saveCommand(input) {
                    setMode(SaveMode.INSERT_ONLY)
                }.execute(GenerateScriptView::class)
                .modifiedView
        }
    }

    @PostMapping("/update")
    @Throws(UpdateException::class)
    fun update(@RequestBody input: GenerateScriptUpdateInput): GenerateScriptView {
        return transactionTemplate.executeNotNull {
            sqlClient
                .exists(GenerateScript::class) {
                    where(table.id eq input.id)
                }.let { if (!it) throw UpdateException.notExisted() }
            sqlClient
                .saveCommand(input) {
                    setMode(SaveMode.UPDATE_ONLY)
                }.execute(GenerateScriptView::class)
                .modifiedView
        }
    }

    @PostMapping("/delete")
    fun delete(@RequestParam scriptIds: List<UUID>): Int {
        return transactionTemplate.executeNotNull {
            sqlClient
                .createDelete(GenerateScript::class) {
                    where(table.id valueIn scriptIds)
                }.execute()
        }
    }
}
