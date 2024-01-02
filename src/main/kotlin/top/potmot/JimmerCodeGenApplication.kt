package top.potmot

import org.babyfish.jimmer.client.EnableImplicitApi
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableImplicitApi
class JimmerCodeGenApplication

fun main(args: Array<String>) {
    runApplication<JimmerCodeGenApplication>(*args)
}
