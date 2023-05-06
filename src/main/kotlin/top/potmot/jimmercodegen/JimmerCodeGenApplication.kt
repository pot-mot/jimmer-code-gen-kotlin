package top.potmot.jimmercodegen

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class JimmerCodeGenApplication

fun main(args: Array<String>) {
    runApplication<JimmerCodeGenApplication>(*args)
}
