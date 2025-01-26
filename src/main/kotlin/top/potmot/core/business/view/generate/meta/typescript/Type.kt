package top.potmot.core.business.view.generate.meta.typescript

import top.potmot.utils.string.StringIndentScopeBuilder
import top.potmot.utils.string.buildScopeString

sealed interface TsType {
    val canUndefined: Boolean
}

data class TsTypeProperty(
    val name: String,
    val type: TsType,
)

data class TsRawType(
    val value: String,
    override val canUndefined: Boolean
): TsType {
    fun stringify(builder: StringIndentScopeBuilder) {
        builder.append(value)
    }
}

data class TsWithGenericType(
    val name: String,
    val genericTypes: Collection<TsType>,
    override val canUndefined: Boolean,
): TsType {
    fun stringify(builder: StringIndentScopeBuilder) {
        builder.append("$name<")
        genericTypes.forEachIndexed { index, it ->
            when(it) {
                is TsRawType -> builder.append(it.value)
                is TsWithGenericType -> it.stringify(builder)
                is TsComplexType -> it.stringify(builder)
            }
            if (index != genericTypes.size - 1) {
                builder.line(", ")
            }
        }
        builder.append(">")
        if (canUndefined) builder.append(" | undefined")
    }

    fun stringify(indent: String) = buildScopeString(indent) {
        stringify(this)
    }
}

fun TsWithGenericType(
    name: String, vararg genericTypes: TsType, canUndefined: Boolean
) = TsWithGenericType(
    name, genericTypes.asList(), canUndefined
)

data class TsComplexType(
    val properties: Collection<TsTypeProperty>,
    override val canUndefined: Boolean,
): TsType {
    fun stringify(builder: StringIndentScopeBuilder) {
        builder.line("{")
        builder.scope {
            properties.forEach {
                builder.append(it.name)
                if (it.type.canUndefined) builder.append("?")
                builder.append(": ")

                when(it.type) {
                    is TsRawType -> it.type.stringify(builder)
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
}
