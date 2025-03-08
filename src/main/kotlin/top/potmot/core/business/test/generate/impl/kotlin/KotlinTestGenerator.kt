package top.potmot.core.business.test.generate.impl.kotlin

import top.potmot.core.business.meta.AssociationProperty
import top.potmot.core.business.meta.EntityBusiness
import top.potmot.core.business.meta.EnumProperty
import top.potmot.core.business.meta.ForceIdViewProperty
import top.potmot.core.business.meta.PropertyBusiness
import top.potmot.core.business.meta.RootEntityBusiness
import top.potmot.core.business.meta.SubEntityBusiness
import top.potmot.core.business.test.generate.TestGenerator
import top.potmot.core.business.test.generate.meta.LazyInsertId
import top.potmot.core.business.test.generate.meta.PropertyValueResult
import top.potmot.core.business.test.generate.meta.ValueWithLazyInsertIds
import top.potmot.core.common.booleanType
import top.potmot.core.common.intType
import top.potmot.core.common.numericType
import top.potmot.core.common.stringType
import top.potmot.enumeration.GenLanguage
import top.potmot.utils.string.buildScopeString

private const val sqlClient = "sqlClient"

object KotlinTestGenerator : TestGenerator {
    override val suffix = GenLanguage.KOTLIN.suffix

    override fun stringifyTest(entity: RootEntityBusiness): String {
        val imports = mutableListOf<String>()
        val lazyInsertIds = mutableListOf<LazyInsertId>()

        val idPropertyShortType = entity.idProperty.type.substringAfterLast(".")

        val currentInsertData = buildScopeString {
            if (entity.canAdd) {
                append("${entity.serviceLowerName}.insert(")
                val (value, subLazyIds) = entity.insertDto()
                lazyInsertIds += subLazyIds
                blockNotEndLine(value)
                append(")")
            } else {
                append("$sqlClient.insert(")
                val (value, subLazyIds) = entity.insertEntity()
                lazyInsertIds += subLazyIds
                blockNotEndLine(value)
                append(").modifiedEntity.id")
            }
        }

        val updateId = "updateId"

        val currentUpdateData = buildScopeString {
            if (entity.canEdit) {
                append("${entity.serviceLowerName}.update(")
                val (value, subLazyIds) = entity.updateDto(updateId)
                lazyInsertIds += subLazyIds
                blockNotEndLine(value)
                append(")")
            }
        }

        val lazyInsertIdActionPairs = lazyInsertIds.flatToValuePairs().distinctBy { it.first.propertyId }
        val allLazyInsertIds = lazyInsertIdActionPairs.map { it.first }
        val allLazyInsertActions = lazyInsertIdActionPairs.map { it.second }

        val needSqlClient =
            !entity.canAdd || allLazyInsertIds.any { !it.insertByService }

        if (needSqlClient) {
            imports += "org.babyfish.jimmer.sql.kt.KSqlClient"
        }

        val serviceEntities = allLazyInsertIds
            .filter { it.insertByService }
            .map { it.entity }
            .let {
                if (entity.canAdd) {
                    it + entity
                } else {
                    it
                }
            }
            .distinctBy { it.id }

        serviceEntities.forEach {
            imports += "${it.packages.service}.${it.serviceName}}"
        }

        return buildScopeString {
            imports.forEach {
                line("import $it")
            }

            line("@SpringBootTest")
            line("@ActiveProfiles(\"test\")")

            line("class ${entity.name}Test(")
            scope {
                if (needSqlClient) {
                    line("private val $sqlClient: KSqlClient")
                }
                serviceEntities.forEach {
                    line("private val ${it.serviceLowerName}: ${it.serviceName},")
                }
            }
            scope(") {", "}") {
                scope("fun insertAndReturnId(): $idPropertyShortType {", "}") {
                    allLazyInsertActions.forEach {
                        block(it)
                        line()
                    }
                    append("return ")
                    block(currentInsertData)
                }

                line()
                scope("fun updateAndReturnId($updateId: $idPropertyShortType): $idPropertyShortType {", "}") {
                    allLazyInsertActions.forEach {
                        block(it)
                        line()
                    }
                    append("return ")
                    block(currentUpdateData)
                }

                line()
                scope("fun testInsert() {", "}") {

                }

                line()
                scope("fun testUpdate() {", "}") {

                }

                line()
                scope("fun testDelete() {", "}") {

                }
            }
        }
    }

    private fun PropertyBusiness.entityPropertyValue(): PropertyValueResult =
        if (listType) {
            PropertyValueResult("emptyList()")
        } else if (!typeNotNull) {
            PropertyValueResult("null")
        } else if (this@entityPropertyValue is EnumProperty) {
            PropertyValueResult("${enum.name}.${enum.defaultItem.name}")
        } else if (this@entityPropertyValue is AssociationProperty) {
            if (isTargetOne) {
                if (isLongAssociation) {
                    val lazyInsertIds = mutableListOf<LazyInsertId>()

                    val value = buildScopeString {
                        scopeEndNoLine("${typeEntity.name} {", "}") {
                            typeEntityBusiness.subEditNoIdProperties.forEach {
                                append(it.name)
                                append(" = ")
                                val (value, subLazyIds) = it.entityPropertyValue()
                                lazyInsertIds += subLazyIds
                                blockNotEndLine(value)
                                line()
                            }
                        }
                    }

                    PropertyValueResult(
                        value,
                        lazyInsertIds,
                    )
                } else {
                    val variableName = "${entityBusiness.lowerName}${upperName}Id"
                    PropertyValueResult(
                        value = variableName,
                        lazyInsertIds = listOf(
                            LazyInsertId(
                                propertyId = id,
                                entity = typeEntityBusiness,
                                name = variableName,
                            )
                        )
                    )
                }
            } else {
                PropertyValueResult("emptyList()")
            }
        } else if (this@entityPropertyValue is ForceIdViewProperty) {
            if (isTargetOne) {
                val variableName = "${entityBusiness.lowerName}${upperName}"
                PropertyValueResult(
                    value = variableName,
                    lazyInsertIds = listOf(
                        LazyInsertId(
                            propertyId = id,
                            entity = typeEntityBusiness,
                            name = variableName,
                        )
                    )
                )
            } else {
                PropertyValueResult("emptyList()")
            }
        } else when (type) {
            in stringType -> PropertyValueResult("\"\"")
            in booleanType -> PropertyValueResult("false")
            in intType, in numericType -> PropertyValueResult("0")
            else -> PropertyValueResult("${type.substringAfterLast(".")}()")
        }

    private fun PropertyBusiness.dtoPropertyValue(parentTypeName: String): PropertyValueResult =
        if (listType) {
            PropertyValueResult("emptyList()")
        } else if (!typeNotNull) {
            PropertyValueResult("null")
        } else if (this@dtoPropertyValue is EnumProperty) {
            PropertyValueResult("${enum.name}.${enum.defaultItem.name}")
        } else if (this@dtoPropertyValue is AssociationProperty) {
            val currentType = "${parentTypeName}.TargetOf_$name"

            if (isTargetOne) {
                if (isLongAssociation) {
                    val lazyInsertIds = mutableListOf<LazyInsertId>()

                    val value = buildScopeString {
                        append(currentType)
                        line("(")
                        scope {
                            typeEntityBusiness.subEditNoIdProperties.forEach {
                                append(it.name)
                                append(" = ")
                                val (value, subLazyIds) = it.dtoPropertyValue(currentType)
                                lazyInsertIds += subLazyIds
                                blockNotEndLine(value)
                                line(",")
                            }
                        }
                        append(")")
                    }

                    PropertyValueResult(
                        value,
                        lazyInsertIds,
                    )
                } else {
                    val variableName = "${entityBusiness.lowerName}${upperName}Id"
                    PropertyValueResult(
                        value = variableName,
                        lazyInsertIds = listOf(
                            LazyInsertId(
                                propertyId = id,
                                entity = typeEntityBusiness,
                                name = variableName,
                            )
                        )
                    )
                }
            } else {
                PropertyValueResult("emptyList()")
            }
        } else if (this@dtoPropertyValue is ForceIdViewProperty) {
            if (isTargetOne) {
                val variableName = "${entityBusiness.lowerName}${upperName}"
                PropertyValueResult(
                    value = variableName,
                    lazyInsertIds = listOf(
                        LazyInsertId(
                            propertyId = id,
                            entity = typeEntityBusiness,
                            name = variableName,
                        )
                    )
                )
            } else {
                PropertyValueResult("emptyList()")
            }
        } else when (type) {
            in stringType -> PropertyValueResult("\"\"")
            in booleanType -> PropertyValueResult("false")
            in intType, in numericType -> PropertyValueResult("0")
            else -> PropertyValueResult("${type.substringAfterLast(".")}()")
        }

    private fun EntityBusiness.insertEntity(): ValueWithLazyInsertIds {
        val lazyInsertIds = mutableListOf<LazyInsertId>()

        val value = buildScopeString {
            scopeEndNoLine("$name {", "}") {
                properties
                    .filterNot { it.property.idProperty && it.property.generatedId }
                    .forEach {
                        append(it.name)
                        append(" = ")
                        if (it.property.idProperty) {
                            append("TODO(\"mock id\")")
                        } else {
                            val (value, subLazyIds) = it.entityPropertyValue()
                            lazyInsertIds += subLazyIds
                            blockNotEndLine(value)
                        }
                        line()
                    }
            }
        }

        return ValueWithLazyInsertIds(
            value,
            lazyInsertIds,
        )
    }

    private fun RootEntityBusiness.insertDto(): ValueWithLazyInsertIds {
        val lazyInsertIds = mutableListOf<LazyInsertId>()

        val value = buildScopeString {
            val typeName = dto.insertInput
            scopeEndNoLine("$typeName (", ")") {
                addFormProperties
                    .filterNot { it.property.idProperty && it.property.generatedId }
                    .forEach {
                        append(it.name)
                        append(" = ")
                        if (it.property.idProperty) {
                            append("TODO(\"mock id\")")
                        } else {
                            val (value, subLazyIds) = it.dtoPropertyValue(typeName)
                            lazyInsertIds += subLazyIds
                            blockNotEndLine(value)
                        }
                        line(",")
                    }
            }
        }

        return ValueWithLazyInsertIds(
            value,
            lazyInsertIds,
        )
    }

    private fun SubEntityBusiness.insertDto(): ValueWithLazyInsertIds {
        val lazyInsertIds = mutableListOf<LazyInsertId>()

        val value = buildScopeString {
            val typeName = asRoot.dto.insertInput
            scopeEndNoLine("$typeName (", ")") {
                subEditProperties
                    .filterNot { it.property.idProperty && it.property.generatedId }
                    .forEach {
                        append(it.name)
                        append(" = ")
                        if (it.property.idProperty) {
                            append("TODO(\"mock id\")")
                        } else {
                            val (value, subLazyIds) = it.dtoPropertyValue(typeName)
                            lazyInsertIds += subLazyIds
                            blockNotEndLine(value)
                        }
                        line(",")
                    }
            }
        }

        return ValueWithLazyInsertIds(
            value,
            lazyInsertIds,
        )
    }

    private fun RootEntityBusiness.updateDto(idPropertyValue: String): ValueWithLazyInsertIds {
        val lazyInsertIds = mutableListOf<LazyInsertId>()

        val value = buildScopeString {
            val typeName = dto.updateInput
            scopeEndNoLine("$typeName (", ")") {
                idProperty.let {
                    append(it.name)
                    append(" = ")
                    append(idPropertyValue)
                    line(",")
                }
                editFormNoIdProperties.forEach {
                    append(it.name)
                    append(" = ")
                    val (value, subLazyIds) = it.dtoPropertyValue(typeName)
                    lazyInsertIds += subLazyIds
                    blockNotEndLine(value)
                    line(",")
                }
            }
        }

        return ValueWithLazyInsertIds(
            value,
            lazyInsertIds,
        )
    }

    private fun LazyInsertId.insertAndReturnId(): ValueWithLazyInsertIds {
        val lazyInsertIds = mutableListOf<LazyInsertId>()

        val value = buildScopeString {
            if (entity.canAdd) {
                append("val $name = ${entity.serviceLowerName}.insert(")
                val (value, subLazyIds) = entity.insertDto()
                lazyInsertIds += subLazyIds
                blockNotEndLine(value)
                append(")")
            } else {
                append("val $name = $sqlClient.insert(")
                val (value, subLazyIds) = entity.insertEntity()
                lazyInsertIds += subLazyIds
                blockNotEndLine(value)
                append(").modifiedEntity.id")
            }
        }

        return ValueWithLazyInsertIds(
            value,
            lazyInsertIds,
        )
    }

    private fun Iterable<LazyInsertId>.flatToValuePairs(
    ): List<Pair<LazyInsertId, String>> {
        val list = mutableListOf<Pair<LazyInsertId, String>>()
        forEach {
            val (value, subLazyIds) = it.insertAndReturnId()
            list += subLazyIds.flatToValuePairs()
            list += it to value
        }
        return list
    }
}