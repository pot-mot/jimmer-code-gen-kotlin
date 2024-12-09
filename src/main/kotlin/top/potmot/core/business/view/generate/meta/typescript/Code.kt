package top.potmot.core.business.view.generate.meta.typescript

sealed interface CodeItem

data class ConstVariable(
    val name: String,
    val type: String?,
    val value: String,
) : CodeItem

fun ConstVariable(
    name: String,
    type: String? = null,
    vararg content: String?,
) = ConstVariable(
    name,
    type,
    content.filterNotNull().joinToString("")
)

data class LetVariable(
    val name: String,
    val type: String? = null,
    val value: String,
) : CodeItem

data class FunctionArg(
    val name: String,
    val type: String,
    val required: Boolean = true,
    val defaultValue: String? = null,
)

data class Function(
    val name: String,
    val args: Iterable<FunctionArg> = emptyList(),
    val body: Collection<CodeItem>,
    var async: Boolean = false,
    val returnType: String? = null,
) : CodeItem

fun Function(
    async: Boolean = false,
    name: String,
    args: Iterable<FunctionArg> = emptyList(),
    vararg content: String?,
) = Function(
    name = name,
    args = args,
    body = listOf(CodeBlock(content.filterNotNull().joinToString(""))),
    async,
    null
)

data class CodeBlock(
    val content: String
) : CodeItem

fun CodeBlock(vararg content: String?) =
    CodeBlock(content.filterNotNull().joinToString(""))

val emptyLineCode = CodeBlock("")

fun commentLine(comment: String) = CodeBlock("// $comment")

fun commentBlock(vararg comment: String?) = CodeBlock(buildString {
    appendLine("/**")
    comment.filterNotNull().forEach {
        appendLine(" * $it")
    }
    append(" */")
})