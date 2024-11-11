package top.potmot.core.business.view.generate.builder.typescript

import top.potmot.core.business.view.generate.meta.typescript.CodeBlock
import top.potmot.core.business.view.generate.meta.typescript.CodeItem
import top.potmot.core.business.view.generate.meta.typescript.ConstVariable
import top.potmot.core.business.view.generate.meta.typescript.Function
import top.potmot.core.business.view.generate.meta.typescript.Import
import top.potmot.core.business.view.generate.meta.typescript.ImportDefault
import top.potmot.core.business.view.generate.meta.typescript.ImportItem
import top.potmot.core.business.view.generate.meta.typescript.ImportType
import top.potmot.core.business.view.generate.meta.typescript.LetVariable
import top.potmot.error.GenerateException

interface TypeScriptBuilder {
    val indent: String
    val wrapThreshold: Int

    fun Iterable<String>.wrapLines(separator: String = ","): String {
        return "\n" + joinToString("$separator\n") { "$indent$it" } + "\n"
    }

    fun Iterable<String>.inlineOrWarpLines(separator: String = ",", currentIndent: String = indent, currentWrapThreshold: Int = wrapThreshold): String {
        val inline = joinToString("$separator ")

        return if (inline.length > currentWrapThreshold) {
            "\n" + joinToString("$separator\n") { "$currentIndent$it" } + "\n"
        } else {
            inline
        }
    }

    fun Iterable<ImportItem>.stringifyImports(): List<String> =
        groupBy { it.path }.map { (path, imports) ->
            val commonImports = mutableSetOf<String>()
            val typeOnlyImports = mutableSetOf<String>()
            val defaultImports = mutableSetOf<String>()

            imports.forEach { importItem ->
                when (importItem) {
                    is Import -> commonImports.addAll(importItem.items)
                    is ImportType -> typeOnlyImports.addAll(importItem.items)
                    is ImportDefault -> defaultImports.add(importItem.item)
                }
            }

            if (defaultImports.size > 1)
                throw GenerateException.canOnlyHaveOneDefaultImportForOnePath("path: ${path}, defaultImports: $defaultImports")

            val importStatements = mutableListOf<String>()

            if (commonImports.isNotEmpty())
                importStatements.add("import {${commonImports.toList().inlineOrWarpLines()}} from \"$path\"")

            if (typeOnlyImports.isNotEmpty())
                importStatements.add("import type {${typeOnlyImports.toList().inlineOrWarpLines()}} from \"$path\"")

            if (defaultImports.isNotEmpty())
                importStatements.add("import ${defaultImports.toList()[0]} from \"$path\"")

            importStatements
        }.flatten()

    fun Iterable<CodeItem>.stringifyCodes(): String =
        joinToString("\n") { item ->
            when (item) {
                is ConstVariable ->
                    "const ${item.name}${if (item.type != null) ": ${item.type}" else ""} = ${item.value}"

                is LetVariable ->
                    "let ${item.name}${if (item.type != null) ": ${item.type}" else ""} = ${item.value}"

                is Function -> {
                    val args = item.args.map { arg ->
                        "${arg.name}${if (arg.required) "" else "?"}: ${arg.type}${if (arg.defaultValue != null) " = ${arg.defaultValue}" else ""}"
                    }.inlineOrWarpLines()

                    val returnType = (item.returnType ?: "void").let {
                        if (item.async) "Promise<$it>" else it
                    }

                    val body = item.body.stringifyCodes().replace("\n", "\n$indent")
                    "const ${item.name} = ${if (item.async) "async " else ""}(${args}): $returnType => {\n$indent$body\n}"
                }

                is CodeBlock ->
                    item.content
            }
        }
}