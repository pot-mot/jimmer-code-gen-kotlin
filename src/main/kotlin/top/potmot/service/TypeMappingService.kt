package top.potmot.service

import org.babyfish.jimmer.sql.ast.mutation.SaveMode
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.support.TransactionTemplate
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import top.potmot.entity.typeMapping.CrossType
import top.potmot.entity.typeMapping.JvmType
import top.potmot.entity.typeMapping.SqlType
import top.potmot.entity.typeMapping.TsType
import top.potmot.entity.typeMapping.dto.CrossTypeInput
import top.potmot.entity.typeMapping.dto.CrossTypeView
import top.potmot.entity.typeMapping.dto.JvmTypeInput
import top.potmot.entity.typeMapping.dto.JvmTypeView
import top.potmot.entity.typeMapping.dto.SqlTypeInput
import top.potmot.entity.typeMapping.dto.SqlTypeView
import top.potmot.entity.typeMapping.dto.TsTypeInput
import top.potmot.entity.typeMapping.dto.TsTypeView
import top.potmot.utils.transaction.executeNotNull

@RestController
@RequestMapping("/typeMapping")
class TypeMappingService(
    @Autowired
    private val sqlClient: KSqlClient,
    @Autowired
    private val transactionTemplate: TransactionTemplate
) {
    @PostMapping("/listCrossType")
    fun listCrossType(): List<CrossTypeView> {
        return sqlClient
            .createQuery(CrossType::class) {
                select(table.fetch(CrossTypeView::class))
            }.execute()
    }

    @PostMapping("/saveCrossType")
    fun saveCrossType(@RequestBody inputs: List<CrossTypeInput>): List<CrossTypeView> {
        return transactionTemplate.executeNotNull {
            sqlClient.createDelete(CrossType::class) {}.execute()
            sqlClient.saveInputsCommand(inputs) { setMode(SaveMode.INSERT_ONLY) }
                .execute(CrossTypeView::class)
                .viewItems.map { it.modifiedView }
        }
    }

    @PostMapping("/listJvmType")
    fun listJvmType(): List<JvmTypeView> {
        return sqlClient
            .createQuery(JvmType::class) {
                select(table.fetch(JvmTypeView::class))
            }.execute()
    }

    @PostMapping("/saveJvmType")
    fun saveJvmType(@RequestBody inputs: List<JvmTypeInput>): List<JvmTypeView> {
        return transactionTemplate.executeNotNull {
            sqlClient.createDelete(JvmType::class) {}.execute()
            sqlClient.saveInputsCommand(inputs) { setMode(SaveMode.INSERT_ONLY) }
                .execute(JvmTypeView::class)
                .viewItems.map { it.modifiedView }
        }
    }

    @PostMapping("/listSqlType")
    fun listSqlType(): List<SqlTypeView> {
        return sqlClient
            .createQuery(SqlType::class) {
                select(table.fetch(SqlTypeView::class))
            }.execute()
    }

    @PostMapping("/saveSqlType")
    fun saveSqlType(@RequestBody inputs: List<SqlTypeInput>): List<SqlTypeView> {
        return transactionTemplate.executeNotNull {
            sqlClient.createDelete(SqlType::class) {}.execute()
            sqlClient.saveInputsCommand(inputs) { setMode(SaveMode.INSERT_ONLY) }
                .execute(SqlTypeView::class)
                .viewItems.map { it.modifiedView }
        }
    }

    @PostMapping("/listTsType")
    fun listTsType(): List<TsTypeView> {
        return sqlClient
            .createQuery(TsType::class) {
                select(table.fetch(TsTypeView::class))
            }.execute()
    }

    @PostMapping("/saveTsType")
    fun saveTsType(@RequestBody inputs: List<TsTypeInput>): List<TsTypeView> {
        return transactionTemplate.executeNotNull {
            sqlClient.createDelete(TsType::class) {}.execute()
            sqlClient.saveInputsCommand(inputs) { setMode(SaveMode.INSERT_ONLY) }
                .execute(TsTypeView::class)
                .viewItems.map { it.modifiedView }
        }
    }
}
