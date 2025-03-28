package top.potmot.core.business.view.generate.impl.vue3elementPlus

import top.potmot.core.business.meta.EnumProperty
import top.potmot.core.business.view.generate.builder.rules.RulesBuilder
import top.potmot.core.business.view.generate.enumPath
import top.potmot.core.business.view.generate.meta.rules.ExistValidRule
import top.potmot.core.business.view.generate.meta.rules.Rules
import top.potmot.core.business.view.generate.meta.rules.existValidRuleImport
import top.potmot.core.common.typescript.CodeBlock
import top.potmot.core.common.typescript.Function
import top.potmot.core.common.typescript.FunctionArg
import top.potmot.core.common.typescript.TsImport
import top.potmot.core.common.typescript.ImportType
import top.potmot.core.common.typescript.stringify
import top.potmot.error.ModelException
import top.potmot.utils.collection.forEachJoinDo
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
            formDataNotNull,
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
            scopeEndNoLine("return {", "}") {
                propertyRules.forEachJoinDo({ _, _ ->
                    line(",")
                }) { property, rules ->
                    line("${property.name}: [")
                    rules.forEach { rule ->
                        scope {
                            if (rule is ExistValidRule) {
                                hasExistValidRule = true
                                block(rule.stringify(formData, formDataNotNull, indent) + ",")
                            } else {
                                block(rule.stringify() + ",")
                            }
                        }
                    }
                    append("]")
                }
                line()
            }
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
                        type = "Ref<${if (isPlural) "Array<${formDataType}>" else formDataType}${if (formDataNotNull) "" else "| undefined"}>"
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
