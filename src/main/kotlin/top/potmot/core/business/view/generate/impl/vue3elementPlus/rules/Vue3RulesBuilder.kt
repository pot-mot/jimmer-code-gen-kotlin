package top.potmot.core.business.view.generate.impl.vue3elementPlus.rules

import top.potmot.core.business.view.generate.builder.typescript.TypeScriptBuilder
import top.potmot.core.business.view.generate.meta.rules.Rule
import top.potmot.core.business.view.generate.meta.typescript.CodeBlock
import top.potmot.core.business.view.generate.meta.typescript.Function
import top.potmot.core.business.view.generate.meta.typescript.FunctionArg
import top.potmot.core.business.view.generate.meta.typescript.ImportType
import top.potmot.core.business.view.generate.staticPath
import top.potmot.utils.string.trimBlankLine

class Vue3RulesBuilder(
    override val indent: String,
    override val wrapThreshold: Int,
) : TypeScriptBuilder {
    fun createFormRules(
        functionName: String,
        formData: String,
        formDataType: String,
        propertyRules: Map<String, Iterable<Rule>>,
        isArray: Boolean = false,
    ): String {
        val imports = listOf(
            ImportType("vue", listOf("Ref")),
            ImportType("element-plus", listOf("FormRules")),
            ImportType(staticPath, listOf(formDataType)),
        ).stringifyImports()

        val body = buildString {
            appendLine("return {")
            propertyRules.forEach { (property, rules) ->
                appendLine("$indent$property: [")
                rules.forEach { rule ->
                    appendLine("$indent$indent${rule.stringify()},")
                }
                appendLine("$indent],")
            }
            append("}")
        }

        val withFormDataProp = body.contains(formData)

        val codes = listOf(
            Function(
                name = functionName,
                args = listOf(
                    FunctionArg(
                        name = if (withFormDataProp) formData else "_",
                        type = "Ref<${if (isArray) "Array<${formDataType}>" else formDataType}>"
                    ),
                ),
                returnType = "FormRules<${formDataType}>",
                body = listOf(CodeBlock(body))
            )
        ).stringifyCodes()

        return """
${imports.joinToString("\n")}

export $codes
        """.trimBlankLine()
    }
}
