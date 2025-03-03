package top.potmot.core.business.test.generate.impl.kotlin

import top.potmot.core.booleanType
import top.potmot.core.business.meta.AssociationProperty
import top.potmot.core.business.meta.EnumProperty
import top.potmot.core.business.meta.ForceIdViewProperty
import top.potmot.core.business.meta.PropertyBusiness
import top.potmot.core.business.meta.RootEntityBusiness
import top.potmot.core.business.meta.SubEntityBusiness
import top.potmot.core.business.test.generate.LazyInsertId
import top.potmot.core.business.test.generate.TestGenerator
import top.potmot.core.intType
import top.potmot.core.numericType
import top.potmot.core.stringType
import top.potmot.enumeration.GenLanguage
import top.potmot.utils.string.StringIndentScopeBuilder
import top.potmot.utils.string.buildScopeString

object KotlinTestGenerator : TestGenerator() {
    override val suffix = GenLanguage.KOTLIN.suffix

    override fun stringifyTest(entity: RootEntityBusiness): String {
        val frontInsertIdMap = mutableMapOf<Long, LazyInsertId>()

        val currentInsertData = buildScopeString {
            append("val inserted${entity.name}Id = ${entity.serviceName}.insert(")
            entity.insertInputData(this, frontInsertIdMap)
            append(")")
        }

        return buildScopeString {
            frontInsertIdMap.values.toList().forEach {
                it.insertAndReturnId(this, frontInsertIdMap)
            }
            block(currentInsertData)
        }
    }

    private fun LazyInsertId.insertAndReturnId(
        builder: StringIndentScopeBuilder,
        lazyInsertIdMap: MutableMap<Long, LazyInsertId>,
    ) {
        val frontInsertIdMap = mutableMapOf<Long, LazyInsertId>()

        val currentInsertData = buildScopeString {
            append("val $name = $serviceName.insert(")
            entity.insertInputData(this, frontInsertIdMap)
            line(")")
        }

        frontInsertIdMap.forEach { (key, value) ->
            if (key !in lazyInsertIdMap) {
                value.insertAndReturnId(builder, frontInsertIdMap)
                lazyInsertIdMap[key] = value
            }
        }

        builder.block(currentInsertData)
    }

    private fun SubEntityBusiness.insertInputData(
        builder: StringIndentScopeBuilder,
        lazyInsertIdMap: MutableMap<Long, LazyInsertId>,
    ) = builder.apply {
        val insertInputTypeName = asRoot.dto.insertInput

        append(insertInputTypeName)
        line("(")
        scope {
            addFormProperties.forEach {
                append(it.name)
                append(" = ")
                it.insertDefault(insertInputTypeName, this, lazyInsertIdMap)
                line(",")
            }
        }
        append(")")
    }

    private fun RootEntityBusiness.insertInputData(
        builder: StringIndentScopeBuilder,
        lazyInsertIdMap: MutableMap<Long, LazyInsertId>,
    ) = builder.apply {
        val insertInputTypeName = dto.insertInput

        append(insertInputTypeName)
        line("(")
        scope {
            addFormProperties.forEach {
                append(it.name)
                append(" = ")
                it.insertDefault(insertInputTypeName, this, lazyInsertIdMap)
                line(",")
            }
        }
        append(")")
    }

    private fun PropertyBusiness.insertDefault(
        insertInputTypeName: String,
        builder: StringIndentScopeBuilder,
        lazyInsertIdMap: MutableMap<Long, LazyInsertId>,
    ): StringIndentScopeBuilder = builder.apply {
        if (listType) {
            append("emptyList()")
        } else if (!typeNotNull) {
            append("null")
        } else if (this@insertDefault is EnumProperty) {
            append("${enum.name}.${enum.defaultItem.name}")
        } else if (this@insertDefault is AssociationProperty) {
            val currentType = "${insertInputTypeName}.TargetOf_$name"

            if (isLongAssociation) {
                append(currentType)
                line("(")
                scope {
                    typeEntityBusiness.longAssociationInputProperties.forEach {
                        append(it.name)
                        append(" = ")
                        it.insertDefault(currentType, this, lazyInsertIdMap)
                        line(",")
                    }
                }
                append(")")
            } else {
                if (isTargetOne) {
                    if (typeEntity.id !in lazyInsertIdMap) {
                        val variableName = "${entityBusiness.lowerName}${upperName}Id"
                        append(variableName)
                        lazyInsertIdMap[id] = LazyInsertId(
                            entity = typeEntityBusiness,
                            name = variableName,
                            serviceName = typeEntityBusiness.serviceName,
                        )
                    }
                } else {
                    append("emptyList()")
                }
            }
        } else if (this@insertDefault is ForceIdViewProperty) {
            if (isTargetOne) {
                val variableName = "${entityBusiness.lowerName}${upperName}"
                append(variableName)
                lazyInsertIdMap[id] = LazyInsertId(
                    entity = typeEntityBusiness,
                    name = variableName,
                    serviceName = typeEntityBusiness.serviceName,
                )
            } else {
                append("emptyList()")
            }
        } else when (type) {
            in stringType -> append("\"\"")
            in booleanType -> append("false")
            in intType, in numericType -> append("0")
            else -> append("${type.substringAfterLast(".")}()")
        }
    }
}