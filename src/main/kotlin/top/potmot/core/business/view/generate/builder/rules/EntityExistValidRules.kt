package top.potmot.core.business.view.generate.builder.rules

import top.potmot.core.business.utils.entity.ExistValidItem
import top.potmot.core.business.utils.mark.apiServiceName
import top.potmot.core.business.utils.entity.existValidItems
import top.potmot.core.business.utils.entity.idProperty
import top.potmot.core.business.property.nameOrWithId
import top.potmot.core.business.utils.type.typeStrToTypeScriptType
import top.potmot.core.business.view.generate.apiPath
import top.potmot.core.business.view.generate.meta.rules.ExistValidRule
import top.potmot.core.business.view.generate.meta.typescript.Import
import top.potmot.core.business.view.generate.utilPath
import top.potmot.entity.dto.GenEntityBusinessView
import top.potmot.error.ModelException
import top.potmot.utils.string.trimBlankLine

private const val asyncValidExist = "asyncValidExist"

val existValidRuleImport = listOf(
    Import(apiPath, "api"),
    Import("$utilPath/$asyncValidExist", asyncValidExist),
)

private data class EntityExistValidRule(
    val item: ExistValidItem,
    val property: GenEntityBusinessView.TargetOf_properties,
    val entity: GenEntityBusinessView,
    val withId: Boolean,
    val trigger: Collection<String> = listOf("blur"),
) : ExistValidRule() {
    private val Pair<GenEntityBusinessView.TargetOf_properties, GenEntityBusinessView.TargetOf_properties?>.nameOrWithId
        get() = if (second != null) second!!.name else first.nameOrWithId

    @Throws(ModelException.IdPropertyNotFound::class)
    override fun stringify(): String {
        val idProperty = entity.idProperty
        val idName = idProperty.name

        val propertyName = property.nameOrWithId
        val propertyType =
            if (property.typeEntity != null) {
                val typeEntityIdProperty = property.typeEntity.idProperty
                typeStrToTypeScriptType(typeEntityIdProperty.type, property.typeNotNull)
            } else {
                typeStrToTypeScriptType(property.type, property.typeNotNull)
            }

        val properties = listOfNotNull(
            propertyName,
            if (withId) {
                "$idName: formData.value.$idName"
            } else null,
        ) +
                item.scalarProperties
                    .filter {
                        it.name != propertyName && it.name != idName
                    }
                    .map {
                        "${it.name}: formData.value.${it.name},"
                    } +
                item.associationPropertyPairs
                    .mapNotNull {
                        val nameOrWithId = it.nameOrWithId

                        if (nameOrWithId != propertyName && nameOrWithId != idName)
                            "${nameOrWithId}: formData.value.${nameOrWithId},"
                        else
                            null
                    }

        return """
{
    asyncValidator: $asyncValidExist("${property.comment}", async (${propertyName}: ${propertyType}) => {
        return await api.${entity.apiServiceName}.${item.functionName}({
            body: {
${properties.joinToString(",\n") { "    ".repeat(4) + it }}
            }
        })
    }),
    trigger: ${trigger.stringifyTriggers()}
}
""".trimBlankLine()
    }
}

/**
 * 将 existValidItem 转换为 ExistValidRule
 *
 */
@Throws(
    ModelException.IdPropertyNotFound::class,
    ModelException.IndexRefPropertyNotFound::class,
    ModelException.IndexRefPropertyCannotBeList::class
)
fun GenEntityBusinessView.existValidRules(
    withId: Boolean,
    filterProperties: List<GenEntityBusinessView.TargetOf_properties> = properties,
): Map<GenEntityBusinessView.TargetOf_properties, List<ExistValidRule>> {
    val currentPropertyIds = filterProperties.map { it.id }.toSet()

    return existValidItems
        .flatMap { item ->
            item.properties.mapNotNull { property ->
                if (property.id !in currentPropertyIds)
                    null
                else
                    EntityExistValidRule(
                        item,
                        property,
                        this,
                        withId
                    )
            }
        }
        .groupBy { it.property }
}