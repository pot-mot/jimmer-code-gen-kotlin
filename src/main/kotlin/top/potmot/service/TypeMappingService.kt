package top.potmot.service

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
import top.potmot.entity.typeMapping.dto.JvmTypeInput
import top.potmot.entity.typeMapping.dto.SqlTypeInput
import top.potmot.entity.typeMapping.dto.TsTypeInput
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
    fun listCrossType(): List<CrossTypeInput> {
        return sqlClient
            .createQuery(CrossType::class) {
                select(table.fetch(CrossTypeInput::class))
            }.execute()
    }

    @PostMapping("/saveCrossType")
    fun saveCrossType(@RequestBody inputs: List<CrossTypeInput>) {
        transactionTemplate.executeNotNull {
            sqlClient.saveInputsCommand(inputs).execute()
        }
    }

    @PostMapping("/listJvmType")
    fun listJvmType(): List<JvmTypeInput> {
        return sqlClient
            .createQuery(JvmType::class) {
                select(table.fetch(JvmTypeInput::class))
            }.execute()
    }

    @PostMapping("/saveJvmType")
    fun saveJvmType(@RequestBody inputs: List<JvmTypeInput>) {
        transactionTemplate.executeNotNull {
            sqlClient.saveInputsCommand(inputs).execute()
        }
    }

    @PostMapping("/listSqlType")
    fun listSqlType(): List<SqlTypeInput> {
        return sqlClient
            .createQuery(SqlType::class) {
                select(table.fetch(SqlTypeInput::class))
            }.execute()
    }

    @PostMapping("/saveSqlType")
    fun saveSqlType(@RequestBody inputs: List<SqlTypeInput>) {
        transactionTemplate.executeNotNull {
            sqlClient.saveInputsCommand(inputs).execute()
        }
    }

    @PostMapping("/listTsType")
    fun listTsType(): List<TsTypeInput> {
        return sqlClient
            .createQuery(TsType::class) {
                select(table.fetch(TsTypeInput::class))
            }.execute()
    }

    @PostMapping("/saveTsType")
    fun saveTsType(@RequestBody inputs: List<TsTypeInput>) {
        transactionTemplate.executeNotNull {
            sqlClient.saveInputsCommand(inputs).execute()
        }
    }
}
