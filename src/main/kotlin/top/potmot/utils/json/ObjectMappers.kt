package top.potmot.utils.json

import com.fasterxml.jackson.core.util.DefaultIndenter
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.babyfish.jimmer.jackson.ImmutableModule

val indenter = DefaultIndenter("    ", "\n")
val printer = DefaultPrettyPrinter().apply {
    indentObjectsWith(indenter)
    indentArraysWith(indenter)
}

val commonObjectMapper = jacksonObjectMapper()
    .registerModule(ImmutableModule())
    .registerModule(JavaTimeModule())!!

val prettyObjectWriter = commonObjectMapper
    .writer(printer)!!
