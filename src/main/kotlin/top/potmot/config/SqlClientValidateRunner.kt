package top.potmot.config

import org.babyfish.jimmer.sql.kt.KSqlClient
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

@Component
class SqlClientValidateRunner(
    val sqlClient: KSqlClient,
): ApplicationRunner {
    override fun run(args: ApplicationArguments?) {
        val error = sqlClient.validateDatabase()
        if (error != null) {
            throw error
        }
    }
}