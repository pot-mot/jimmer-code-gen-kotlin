package top.potmot.core.business.view.generate.meta.typescript

sealed interface CodeItem

data class ConstVariable(
    val name: String,
    val type: String? = null,
    val value: String,
) : CodeItem

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
    var async: Boolean = false,
    val name: String,
    val args: Iterable<FunctionArg> = emptyList(),
    val returnType: String? = null,
    val body: Collection<CodeItem>,
) : CodeItem

data class CodeBlock(
    val content: String
) : CodeItem