package top.potmot.external.config

import org.babyfish.jimmer.sql.runtime.*
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import top.potmot.external.util.LogUtils
import java.sql.Connection
import java.sql.PreparedStatement

@Configuration
class JimmerConfig {
    @Value("\${gen.show-sql:false}")
    var showSql: Boolean? = null
    @Bean
    fun executor(): Executor {
        return object : Executor {
            override fun <R> execute(
                con: Connection,
                sql: String,
                variables: List<Any?>,
                purpose: ExecutionPurpose,
                ctx: ExecutorContext?,
                statementFactory: StatementFactory?,
                block: SqlFunction<PreparedStatement, R>
            ): R {
                val start = System.currentTimeMillis()
                val result = DefaultExecutor.INSTANCE
                    .execute(
                        con,
                        sql,
                        variables,
                        purpose,
                        ctx,
                        statementFactory,
                        block
                    )
                if (showSql!!) {
                    val cost = System.currentTimeMillis() - start
                    LogUtils.infoSqlLog(sql, variables, cost, result, LOGGER)
                }
                return result
            }
        }
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(JimmerConfig::class.java)
    }
}
