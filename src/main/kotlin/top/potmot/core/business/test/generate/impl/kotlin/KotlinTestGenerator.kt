package top.potmot.core.business.test.generate.impl.kotlin

import top.potmot.core.business.meta.AssociationProperty
import top.potmot.core.business.meta.EntityBusiness
import top.potmot.core.business.meta.EnumProperty
import top.potmot.core.business.meta.ForceIdViewProperty
import top.potmot.core.business.meta.PropertyBusiness
import top.potmot.core.business.meta.RootEntityBusiness
import top.potmot.core.business.meta.SubEntityBusiness
import top.potmot.core.business.test.generate.TestGenerator
import top.potmot.core.business.test.generate.meta.ActionPropertyValueResult
import top.potmot.core.business.test.generate.meta.BuilderAction
import top.potmot.core.business.test.generate.meta.BuilderAction_WithLazyInsertIds
import top.potmot.core.business.test.generate.meta.LazyInsertId
import top.potmot.core.business.test.generate.meta.PropertyValueResult
import top.potmot.core.business.test.generate.meta.RawPropertyValueResult
import top.potmot.core.business.test.generate.meta.append
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
                lazyInsertIds += append(entity.insertDto())
                append(")")
            } else {
                append("$sqlClient.insert(")
                lazyInsertIds += append(entity.insertEntity())
                append(").modifiedEntity.id")
            }
        }

        val updateId = "updateId"

        val currentUpdateData = buildScopeString {
            if (entity.canEdit) {
                append("${entity.serviceLowerName}.update(")
                lazyInsertIds += append(entity.updateDto(updateId))
                append(")")
            }
        }

        val lazyInsertIdActionMap = lazyInsertIds.flatToActionMap()
        val allLazyInsertIds = lazyInsertIdActionMap.keys
        val allLazyInsertActions = lazyInsertIdActionMap.values

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
                        append(it)
                        line()
                    }
                    append("return ")
                    block(currentInsertData)
                }

                line()
                scope("fun updateAndReturnId(): $idPropertyShortType {", "}") {
                    allLazyInsertActions.forEach {
                        append(it)
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
            RawPropertyValueResult("emptyList()")
        } else if (!typeNotNull) {
            RawPropertyValueResult("null")
        } else if (this@entityPropertyValue is EnumProperty) {
            RawPropertyValueResult("${enum.name}.${enum.defaultItem.name}")
        } else if (this@entityPropertyValue is AssociationProperty) {
            if (isTargetOne) {
                if (isLongAssociation) {
                    val lazyInsertIds = mutableListOf<LazyInsertId>()

                    val builderAction: BuilderAction = {
                        scopeEndNoLine("${typeEntity.name} {", "}") {
                            typeEntityBusiness.subEditNoIdProperties.forEach {
                                append(it.name)
                                append(" = ")
                                lazyInsertIds += append(it.entityPropertyValue())
                                line()
                            }
                        }
                    }

                    ActionPropertyValueResult(
                        lazyInsertIds,
                        builderAction
                    )
                } else {
                    val variableName = "${entityBusiness.lowerName}${upperName}Id"
                    ActionPropertyValueResult(
                        lazyInsertIds = listOf(
                            LazyInsertId(
                                propertyId = id,
                                entity = typeEntityBusiness,
                                name = variableName,
                            )
                        )
                    ) {
                        append(variableName)
                    }
                }
            } else {
                RawPropertyValueResult("emptyList()")
            }
        } else if (this@entityPropertyValue is ForceIdViewProperty) {
            if (isTargetOne) {
                val variableName = "${entityBusiness.lowerName}${upperName}"
                ActionPropertyValueResult(
                    lazyInsertIds = listOf(
                        LazyInsertId(
                            propertyId = id,
                            entity = typeEntityBusiness,
                            name = variableName,
                        )
                    )
                ) {
                    append(variableName)
                }
            } else {
                RawPropertyValueResult("emptyList()")
            }
        } else when (type) {
            in stringType -> RawPropertyValueResult("\"\"")
            in booleanType -> RawPropertyValueResult("false")
            in intType, in numericType -> RawPropertyValueResult("0")
            else -> RawPropertyValueResult("${type.substringAfterLast(".")}()")
        }

    private fun PropertyBusiness.dtoPropertyValue(parentTypeName: String): PropertyValueResult =
        if (listType) {
            RawPropertyValueResult("emptyList()")
        } else if (!typeNotNull) {
            RawPropertyValueResult("null")
        } else if (this@dtoPropertyValue is EnumProperty) {
            RawPropertyValueResult("${enum.name}.${enum.defaultItem.name}")
        } else if (this@dtoPropertyValue is AssociationProperty) {
            val currentType = "${parentTypeName}.TargetOf_$name"

            if (isTargetOne) {
                if (isLongAssociation) {
                    val lazyInsertIds = mutableListOf<LazyInsertId>()

                    val builderAction: BuilderAction = {
                        append(currentType)
                        line("(")
                        scope {
                            typeEntityBusiness.subEditNoIdProperties.forEach {
                                append(it.name)
                                append(" = ")
                                lazyInsertIds += append(it.dtoPropertyValue(currentType))
                                line(",")
                            }
                        }
                        append(")")
                    }

                    ActionPropertyValueResult(
                        lazyInsertIds,
                        builderAction,
                    )
                } else {
                    val variableName = "${entityBusiness.lowerName}${upperName}Id"
                    ActionPropertyValueResult(
                        lazyInsertIds = listOf(
                            LazyInsertId(
                                propertyId = id,
                                entity = typeEntityBusiness,
                                name = variableName,
                            )
                        )
                    ) {
                        append(variableName)
                    }
                }
            } else {
                RawPropertyValueResult("emptyList()")
            }
        } else if (this@dtoPropertyValue is ForceIdViewProperty) {
            if (isTargetOne) {
                val variableName = "${entityBusiness.lowerName}${upperName}"
                ActionPropertyValueResult(
                    lazyInsertIds = listOf(
                        LazyInsertId(
                            propertyId = id,
                            entity = typeEntityBusiness,
                            name = variableName,
                        )
                    )
                ) {
                    append(variableName)
                }
            } else {
                RawPropertyValueResult("emptyList()")
            }
        } else when (type) {
            in stringType -> RawPropertyValueResult("\"\"")
            in booleanType -> RawPropertyValueResult("false")
            in intType, in numericType -> RawPropertyValueResult("0")
            else -> RawPropertyValueResult("${type.substringAfterLast(".")}()")
        }

    private fun EntityBusiness.insertEntity(): BuilderAction_WithLazyInsertIds {
        val lazyInsertIds = mutableListOf<LazyInsertId>()

        val builderAction: BuilderAction = {
            scopeEndNoLine("$name {", "}") {
                properties
                    .filterNot { it.property.idProperty && it.property.generatedId }
                    .forEach {
                        append(it.name)
                        append(" = ")
                        if (it.property.idProperty) {
                            append("TODO(\"mock id\")")
                        } else {
                            lazyInsertIds += append(it.entityPropertyValue())
                        }
                        line()
                    }
            }
        }

        return BuilderAction_WithLazyInsertIds(
            lazyInsertIds,
            builderAction,
        )
    }

    private fun RootEntityBusiness.insertDto(): BuilderAction_WithLazyInsertIds {
        val lazyInsertIds = mutableListOf<LazyInsertId>()

        val builderAction: BuilderAction = {
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
                            lazyInsertIds += append(it.dtoPropertyValue(typeName))
                        }
                        line(",")
                    }
            }
        }

        return BuilderAction_WithLazyInsertIds(
            lazyInsertIds,
            builderAction,
        )
    }

    private fun SubEntityBusiness.insertDto() = asRoot.insertDto()

    private fun RootEntityBusiness.updateDto(idPropertyValue: String): BuilderAction_WithLazyInsertIds {
        val lazyInsertIds = mutableListOf<LazyInsertId>()

        val builderAction: BuilderAction = {
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
                    lazyInsertIds += append(it.dtoPropertyValue(typeName))
                    line(",")
                }
            }
        }

        return BuilderAction_WithLazyInsertIds(
            lazyInsertIds,
            builderAction,
        )
    }

    private fun LazyInsertId.insertAndReturnId(): BuilderAction_WithLazyInsertIds {
        val lazyInsertIds = mutableListOf<LazyInsertId>()

        val builderAction: BuilderAction = {
            if (entity.canAdd) {
                append("val $name = ${entity.serviceLowerName}.insert(")
                lazyInsertIds += append(entity.insertDto())
                append(")")
            } else {
                append("val $name = $sqlClient.insert(")
                lazyInsertIds += append(entity.insertEntity())
                append(").modifiedEntity.id")
            }
        }

        return BuilderAction_WithLazyInsertIds(
            lazyInsertIds,
            builderAction,
        )
    }

    private fun Iterable<LazyInsertId>.flatToActionMap(): Map<LazyInsertId, BuilderAction> {
        val map = mutableMapOf<LazyInsertId, BuilderAction>()
        forEach {
            val (subLazyIds, builderAction) = it.insertAndReturnId()
            map += subLazyIds.flatToActionMap()
            map[it] = builderAction
        }
        return map
    }
}