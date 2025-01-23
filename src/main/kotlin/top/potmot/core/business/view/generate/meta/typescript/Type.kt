package top.potmot.core.business.view.generate.meta.typescript

import top.potmot.utils.string.StringIndentScopeBuilder
import top.potmot.utils.string.buildScopeString

sealed interface TsType

data class TsTypeProperty(
    val name: String,
    val type: TsType,
)

data class TsRawType(
    val value: String
): TsType

fun TsTypeProperty(
    name: String, type: String
) = TsTypeProperty(
    name, TsRawType(type)
)

data class TsWithGenericType(
    val name: String,
    val genericTypes: Collection<TsType>
): TsType {
    fun stringify(builder: StringIndentScopeBuilder) {
        builder.append("$name<")
        builder.scope {
            genericTypes.forEach {
                when(it) {
                    is TsRawType -> builder.append(it.value)
                    is TsWithGenericType -> it.stringify(builder)
                    is TsComplexType -> it.stringify(builder)
                }

                builder.line()
            }
        }
        builder.append(">")
    }

    fun stringify(indent: String) = buildScopeString(indent) {
        stringify(this)
    }
}

fun TsWithGenericType(
    name: String, vararg genericTypes: TsType
) = TsWithGenericType(
    name, genericTypes.asList()
)

data class TsComplexType(
    val properties: Collection<TsTypeProperty>,
    val canUndefined: Boolean,
): TsType {
    fun stringify(builder: StringIndentScopeBuilder) {
        builder.line("{")
        builder.scope {
            properties.forEach {
                builder.append(it.name)
                builder.append(": ")

                when(it.type) {
                    is TsRawType -> builder.append(it.type.value)
                    is TsWithGenericType -> it.type.stringify(builder)
                    is TsComplexType -> it.type.stringify(builder)
                }

                builder.line()
            }
        }
        builder.append("}")

        if (canUndefined) {
            builder.append(" | undefined")
        }
    }

    fun stringify(indent: String) = buildScopeString(indent) {
        stringify(this)
    }
}
