package top.potmot

import org.babyfish.jimmer.client.EnableImplicitApi
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableImplicitApi
open class JimmerCodeGenApplication

fun main(args: Array<String>) {
    val context = runApplication<JimmerCodeGenApplication>(*args)

    // when init, validateDatabase
    val sqlClient = context.getBean(KSqlClient::class.java)
    val error = sqlClient.validateDatabase()
    if (error != null) {
        throw error
    }
}
