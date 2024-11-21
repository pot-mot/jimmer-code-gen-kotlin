package top.potmot.core.business.view.generate.builder.vue3.elementPlus.rules

import top.potmot.core.business.view.generate.builder.typescript.TypeScriptBuilder
import top.potmot.core.business.view.generate.meta.rules.ExistValidRule
import top.potmot.core.business.view.generate.meta.rules.Rule
import top.potmot.core.business.view.generate.meta.rules.existValidRuleImport
import top.potmot.core.business.view.generate.meta.typescript.CodeBlock
import top.potmot.core.business.view.generate.meta.typescript.Function
import top.potmot.core.business.view.generate.meta.typescript.FunctionArg
import top.potmot.core.business.view.generate.meta.typescript.ImportItem
import top.potmot.core.business.view.generate.meta.typescript.ImportType
import top.potmot.core.business.view.generate.staticPath
import top.potmot.error.ModelException
import top.potmot.utils.string.appendBlock
import top.potmot.utils.string.trimBlankLine

class Vue3ElementPlusRuleBuilder(
    override val indent: String = "    ",
    override val wrapThreshold: Int = 40,
) : TypeScriptBuilder {
    @Throws(ModelException.IdPropertyNotFound::class)
    fun createFormRules(
        functionName: String,
        formData: String,
        formDataType: String,
        propertyRules: Map<String, Iterable<Rule>>,
        isArray: Boolean = false,
    ): String {
        val imports = mutableListOf<ImportItem>(
            ImportType("vue", "Ref"),
            ImportType("element-plus", "FormRules"),
            ImportType(staticPath, formDataType),
        )

        var hasExistValidRule = false

        val body = buildString {
            appendLine("return {")
            propertyRules.forEach { (property, rules) ->
                appendLine("$indent$property: [")
                rules.forEach { rule ->
                    hasExistValidRule = rule is ExistValidRule
                    appendBlock(rule.stringify() + ",") { "$indent$indent$it" }
                }
                appendLine("$indent],")
            }
            append("}")
        }

        if (hasExistValidRule) {
            imports += existValidRuleImport
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
${imports.stringifyImports().joinToString("\n")}

export $codes
        """.trimBlankLine()
    }
}
