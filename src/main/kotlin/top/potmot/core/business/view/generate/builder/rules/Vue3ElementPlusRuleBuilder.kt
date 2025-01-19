package top.potmot.core.business.view.generate.builder.rules

import top.potmot.core.business.property.AssociationProperty
import top.potmot.core.business.property.PropertyBusiness
import top.potmot.core.business.view.generate.builder.typescript.TypeScriptBuilder
import top.potmot.core.business.view.generate.enumPath
import top.potmot.core.business.view.generate.meta.rules.ExistValidRule
import top.potmot.core.business.view.generate.meta.rules.Rule
import top.potmot.core.business.view.generate.meta.typescript.CodeBlock
import top.potmot.core.business.view.generate.meta.typescript.Function
import top.potmot.core.business.view.generate.meta.typescript.FunctionArg
import top.potmot.core.business.view.generate.meta.typescript.ImportItem
import top.potmot.core.business.view.generate.meta.typescript.ImportType
import top.potmot.core.business.view.generate.staticPath
import top.potmot.error.ModelException
import top.potmot.utils.string.buildScopeString
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
        formDataTypePath: String = staticPath,
        ruleDataType: String = formDataType,
        ruleDataTypePath: String = formDataTypePath,
        propertyRules: Map<PropertyBusiness, Iterable<Rule>>,
        isArray: Boolean = false,
    ): String {
        val imports = mutableListOf<ImportItem>(
            ImportType("vue", "Ref"),
            ImportType("element-plus", "FormRules"),
            ImportType(formDataTypePath, formDataType),
            ImportType(ruleDataTypePath, ruleDataType),
        )

        var hasExistValidRule = false

        val body = buildScopeString(indent) {
            line("return {")
            scope {
                propertyRules.forEach { (propertyBusiness, rules) ->
                    val name = if (propertyBusiness is AssociationProperty) {
                        propertyBusiness.nameOrWithId
                    } else {
                        propertyBusiness.property.name
                    }

                    line("${name}: [")
                    rules.forEach { rule ->
                        if (!hasExistValidRule) hasExistValidRule = rule is ExistValidRule
                        scope {
                            block(rule.stringify() + ",")
                        }
                    }
                    line("],")
                }
            }
            append("}")
        }

        if (hasExistValidRule) {
            imports += existValidRuleImport
        }
        propertyRules.forEach { (propertyBusiness, rules) ->
            val property = propertyBusiness.property
            if (property.enum != null) {
                val enumName = property.enum.name
                if (rules.map { it.stringify() }.toString().contains(enumName)) {
                    imports += ImportType(enumPath, enumName)
                }
            }
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
                returnType = "FormRules<${ruleDataType}>",
                body = listOf(CodeBlock(body))
            )
        ).stringifyCodes()

        return """
${imports.stringifyImports().joinToString("\n")}

export $codes
        """.trimBlankLine()
    }
}
