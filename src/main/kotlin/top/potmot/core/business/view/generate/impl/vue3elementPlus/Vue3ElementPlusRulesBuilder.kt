package top.potmot.core.business.view.generate.impl.vue3elementPlus

import top.potmot.core.business.meta.AssociationProperty
import top.potmot.core.business.meta.EnumProperty
import top.potmot.core.business.view.generate.builder.rules.RulesBuilder
import top.potmot.core.business.view.generate.enumPath
import top.potmot.core.business.view.generate.meta.rules.ExistValidRule
import top.potmot.core.business.view.generate.meta.rules.Rules
import top.potmot.core.business.view.generate.meta.rules.existValidRuleImport
import top.potmot.core.business.view.generate.meta.typescript.CodeBlock
import top.potmot.core.business.view.generate.meta.typescript.Function
import top.potmot.core.business.view.generate.meta.typescript.FunctionArg
import top.potmot.core.business.view.generate.meta.typescript.TsImport
import top.potmot.core.business.view.generate.meta.typescript.ImportType
import top.potmot.core.business.view.generate.meta.typescript.stringify
import top.potmot.error.ModelException
import top.potmot.utils.string.buildScopeString
import top.potmot.utils.string.trimBlankLine

class Vue3ElementPlusRulesBuilder(
    val indent: String,
    val wrapThreshold: Int,
) : RulesBuilder {
    @Throws(ModelException.IdPropertyNotFound::class)
    override fun build(rules: Rules): String {
        val (
            functionName,

            isPlural,
            formData,
            formDataType,
            formDataTypePath,

            ruleDataType,
            ruleDataTypePath,

            propertyRules,
        ) = rules

        val imports = mutableListOf<TsImport>(
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
                        propertyBusiness.nameWithId
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
        propertyRules.forEach { (property, rules) ->
            if (property is EnumProperty) {
                val enumName = property.enum.name
                if (rules.map { it.stringify() }.toString().contains(enumName)) {
                    imports += ImportType(enumPath, enumName)
                }
            }
        }

        val withFormDataProp = body.contains(formData)

        val functionCode =
            Function(
                name = functionName,
                args = listOf(
                    FunctionArg(
                        name = if (withFormDataProp) formData else "_",
                        type = "Ref<${if (isPlural) "Array<${formDataType}>" else formDataType}>"
                    ),
                ),
                returnType = "FormRules<${ruleDataType}>",
                body = listOf(CodeBlock(body))
            )
                .stringify(indent, wrapThreshold)

        return buildScopeString {
            lines(imports.stringify(indent, wrapThreshold))
            line()
            append("export ")
            append(functionCode)
        }.trimBlankLine()
    }
}
