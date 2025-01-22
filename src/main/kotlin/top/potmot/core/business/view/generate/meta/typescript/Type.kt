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
    name, RawType(type)
)

data class ComplexType(
    val properties: Collection<TsTypeProperty>,
): TsType {
    fun stringify(builder: StringIndentScopeBuilder) {
        builder.line("{")
        builder.scope {
            properties.forEachIndexed { index, it ->
                builder.append(it.name)
                builder.append(": ")

                when(it.type) {
                    is RawType -> builder.append(it.type.value)
                    is ComplexType -> it.type.stringify(builder)
                }

                builder.line(if (index != properties.size - 1) "," else "")
            }
        }
        builder.append("}")
    }

    fun stringify(indent: String) = buildScopeString(indent) {
        stringify(this)
    }
}

data class ComplexTypeDeclare(
    val name: String,
    val type: ComplexType,
) {
    fun stringify(indent: String) = "type $name = ${type.stringify(indent)}"
}
