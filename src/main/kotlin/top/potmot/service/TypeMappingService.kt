package top.potmot.service

import org.babyfish.jimmer.sql.ast.mutation.SaveMode
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.valueNotIn
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
import top.potmot.entity.typeMapping.id
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
            val savedItems = sqlClient.saveEntitiesCommand(inputs.mapIndexed { index, it ->
                it.toEntity { orderKey = index }
            }) { setMode(SaveMode.NON_IDEMPOTENT_UPSERT) }
                .execute(CrossTypeView::class)
                .viewItems.map { it.modifiedView }
            sqlClient.createDelete(CrossType::class) {
                where(table.id valueNotIn savedItems.map { it.id })
            }.execute()
            savedItems
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
            val savedItems = sqlClient.saveEntitiesCommand(inputs.mapIndexed { index, input ->
                input.toEntity {
                    orderKey = index
                    sqlMatchRules = input.sqlMatchRules.mapIndexed { subIndex, rule ->
                        rule.toEntity { orderKey = subIndex }
                    }
                    tsMatchRules = input.tsMatchRules.mapIndexed { subIndex, rule ->
                        rule.toEntity { orderKey = subIndex }
                    }
                }
            }) { setMode(SaveMode.NON_IDEMPOTENT_UPSERT) }
                .execute(JvmTypeView::class)
                .viewItems.map { it.modifiedView }
            sqlClient.createDelete(JvmType::class) {
                where(table.id valueNotIn savedItems.map { it.id })
            }.execute()
            savedItems
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
            val savedItems = sqlClient.saveEntitiesCommand(inputs.mapIndexed { index, input ->
                input.toEntity {
                    orderKey = index
                    jvmMatchRules = input.jvmMatchRules.mapIndexed { subIndex, rule ->
                        rule.toEntity { orderKey = subIndex }
                    }
                    tsMatchRules = input.tsMatchRules.mapIndexed { subIndex, rule ->
                        rule.toEntity { orderKey = subIndex }
                    }
                }
            }) { setMode(SaveMode.NON_IDEMPOTENT_UPSERT) }
                .execute(SqlTypeView::class)
                .viewItems.map { it.modifiedView }
            sqlClient.createDelete(SqlType::class) {
                where(table.id valueNotIn savedItems.map { it.id })
            }.execute()
            savedItems
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
            val savedItems = sqlClient.saveEntitiesCommand(inputs.mapIndexed { index, input ->
                input.toEntity {
                    orderKey = index
                    jvmMatchRules = input.jvmMatchRules.mapIndexed { subIndex, rule ->
                        rule.toEntity { orderKey = subIndex }
                    }
                    sqlMatchRules = input.sqlMatchRules.mapIndexed { subIndex, rule ->
                        rule.toEntity { orderKey = subIndex }
                    }
                }
            }) { setMode(SaveMode.NON_IDEMPOTENT_UPSERT) }
                .execute(TsTypeView::class)
                .viewItems.map { it.modifiedView }
            sqlClient.createDelete(TsType::class) {
                where(table.id valueNotIn savedItems.map { it.id })
            }.execute()
            savedItems
        }
    }
}
