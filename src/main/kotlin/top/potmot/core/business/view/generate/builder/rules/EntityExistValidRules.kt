package top.potmot.core.business.view.generate.builder.rules

import top.potmot.core.business.meta.AssociationProperty
import top.potmot.core.business.meta.EntityBusiness
import top.potmot.core.business.meta.PropertyBusiness
import top.potmot.core.business.type.typeStrToTypeScriptType
import top.potmot.core.business.view.generate.apiPath
import top.potmot.core.business.view.generate.meta.rules.ExistValidRule
import top.potmot.core.business.view.generate.meta.typescript.Import
import top.potmot.core.business.view.generate.utilPath
import top.potmot.error.ModelException
import top.potmot.utils.string.trimBlankLine

private const val asyncValidExist = "asyncValidExist"

val existValidRuleImport = listOf(
    Import(apiPath, "api"),
    Import("$utilPath/$asyncValidExist", asyncValidExist),
)

private data class EntityExistValidRule(
    val item: ExistValidItem,
    val property: PropertyBusiness,
    val entity: EntityBusiness,
    val withId: Boolean,
    val trigger: Collection<String> = listOf("blur"),
) : ExistValidRule() {
    @Throws(ModelException.IdPropertyNotFound::class)
    override fun stringify(): String {
        val idProperty = entity.idProperty
        val idName = idProperty.name

        val propertyName =
            if (property is AssociationProperty) {
                property.nameWithId
            } else {
                property.name
            }
        val propertyType =
            if (property is AssociationProperty) {
                val typeEntityIdProperty = property.typeEntityBusiness.idProperty
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
                item.associationProperties
                    .mapNotNull {
                        val nameOrWithId = it.nameWithId

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
 * 将 ExistValidItem 转换为 ExistValidRule
 *
 */
@Throws(
    ModelException.IdPropertyNotFound::class,
    ModelException.IndexRefPropertyNotFound::class,
    ModelException.IndexRefPropertyCannotBeList::class
)
fun EntityBusiness.existValidRules(
    withId: Boolean,
    filterProperties: List<PropertyBusiness> = properties,
): Map<PropertyBusiness, List<ExistValidRule>> {
    val filterPropertyIds = filterProperties.map { it.property.id }.toSet()

    return existValidItems
        .flatMap { item ->
            item.properties.mapNotNull { property ->
                if (property.id !in filterPropertyIds)
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