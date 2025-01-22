package top.potmot.core.business.view.generate.meta.typescript

import top.potmot.error.GenerateException

sealed class TsImport(
    open val path: String,
)

data class Import(
    override val path: String,
    val items: List<String>,
) : TsImport(path)

fun Import(
    path: String,
    vararg item: String,
) = Import(path, item.toList())

data class ImportType(
    override val path: String,
    val items: List<String>,
) : TsImport(path)

fun ImportType(
    path: String,
    vararg item: String,
) = ImportType(path, item.toList())

data class ImportDefault(
    override val path: String,
    val item: String,
) : TsImport(path)

fun Iterable<TsImport>.stringify(
    indent: String,
    wrapThreshold: Int,
): List<String> =
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
            throw GenerateException.defaultImportMoreThanOne(
                "path: ${path}, defaultImports: $defaultImports",
                path = path,
                importItems = defaultImports.toList()
            )

        val importStatements = mutableListOf<String>()

        if (commonImports.isNotEmpty())
            importStatements.add(
                "import {${
                    commonImports.toList().inlineOrWarpLines(indent, wrapThreshold)
                }} from \"$path\""
            )

        if (typeOnlyImports.isNotEmpty())
            importStatements.add(
                "import type {${
                    typeOnlyImports.toList().inlineOrWarpLines(indent, wrapThreshold)
                }} from \"$path\""
            )

        if (defaultImports.isNotEmpty())
            importStatements.add("import ${defaultImports.toList()[0]} from \"$path\"")

        importStatements
    }.flatten()