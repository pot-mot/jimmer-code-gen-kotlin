package top.potmot.core.business.view.generate.meta.typescript

import top.potmot.utils.string.StringIndentScopeBuilder
import top.potmot.utils.string.buildScopeString

sealed interface TsType

data class TsTypeProperty(
    val name: String,
    val type: TsType,
)

data class RawType(
    val value: String
): TsType

fun TsTypeProperty(
    name: String, type: String
) = TsTypeProperty(
    name,
    RawType(type)
)

data class ComplexType(
    val properties: Iterable<TsTypeProperty>,
): TsType {
    private fun extractBody(builder: StringIndentScopeBuilder) {
        properties.forEach {
            builder.append(it.name)
            builder.append(": ")

            when(it.type) {
                is RawType -> builder.line(it.type.value)
                is ComplexType -> {
                    builder.line("{")
                    builder.scope {
                        it.type.extractBody(builder)
                    }
                    builder.append("}")
                }
            }

            builder.line(",")
        }
    }

    fun stringify(indent: String) = buildScopeString(indent) {
        line("{")
        scope {
            extractBody(this)
        }
        line("}")
    }
}

data class ComplexTypeDeclare(
    val name: String,
    val type: ComplexType,
) {
    fun stringify(indent: String) = "type $name = ${type.stringify(indent)}"
}
