package top.potmot.core.business.test.generate.impl.kotlin

import top.potmot.core.common.booleanType
import top.potmot.core.business.meta.AssociationProperty
import top.potmot.core.business.meta.EntityBusiness
import top.potmot.core.business.meta.EnumProperty
import top.potmot.core.business.meta.ForceIdViewProperty
import top.potmot.core.business.meta.PropertyBusiness
import top.potmot.core.business.meta.RootEntityBusiness
import top.potmot.core.business.meta.SubEntityBusiness
import top.potmot.core.business.test.generate.meta.LazyInsertId
import top.potmot.core.business.test.generate.TestGenerator
import top.potmot.core.common.intType
import top.potmot.core.common.numericType
import top.potmot.core.common.stringType
import top.potmot.enumeration.GenLanguage
import top.potmot.utils.collection.forEachJoinDo
import top.potmot.utils.string.StringIndentScopeBuilder
import top.potmot.utils.string.buildScopeString

private const val sqlClient = "sqlClient"

object KotlinTestGenerator : TestGenerator {
    override val suffix = GenLanguage.KOTLIN.suffix

    override fun stringifyTest(entity: RootEntityBusiness): String {
        val imports = mutableListOf<String>()
        val frontInsertIdMap = mutableMapOf<Long, LazyInsertId>()

        val idPropertyShortType = entity.idProperty.type.substringAfterLast(".")

        val currentInsertData = buildScopeString {
            if (entity.canAdd) {
                append("return ${entity.serviceLowerName}.insert(")
                entity.insertDto(this, frontInsertIdMap)
                append(")")
            } else {
                append("return $sqlClient.insert(")
                entity.insertEntity(this, frontInsertIdMap)
                append(").modifiedEntity.id")
            }
        }

        val frontInsertIdData =
            if (frontInsertIdMap.isEmpty()) ""
            else buildScopeString {
                val items = frontInsertIdMap.values.toList()
                items.forEachJoinDo({
                    line()
                }) {
                    it.insertAndReturnId(this, frontInsertIdMap)
                }
            }

        val needSqlClient =
            !entity.canAdd || frontInsertIdMap.values.any { !it.insertByService }

        if (needSqlClient) {
            imports += "org.babyfish.jimmer.sql.kt.KSqlClient"
        }

        val serviceEntities = frontInsertIdMap.values
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
                    block(frontInsertIdData)
                    block(currentInsertData)
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

    private fun LazyInsertId.insertAndReturnId(
        builder: StringIndentScopeBuilder,
        lazyInsertIdMap: MutableMap<Long, LazyInsertId>,
    ) {
        val frontInsertIdMap = mutableMapOf<Long, LazyInsertId>()

        val currentInsertData = buildScopeString {
            if (entity.canAdd) {
                append("val $name = ${entity.serviceLowerName}.insert(")
                entity.insertDto(this, frontInsertIdMap)
                append(")")
            } else {
                append("val $name = $sqlClient.insert(")
                entity.insertEntity(this, frontInsertIdMap)
                append(").modifiedEntity.id")
            }
        }

        frontInsertIdMap.forEach { (key, value) ->
            if (key !in lazyInsertIdMap) {
                value.insertAndReturnId(builder, frontInsertIdMap)
                builder.line()
                lazyInsertIdMap[key] = value
            }
        }

        builder.block(currentInsertData)
    }

    private fun EntityBusiness.insertEntity(
        builder: StringIndentScopeBuilder,
        lazyInsertIdMap: MutableMap<Long, LazyInsertId>,
    ) = builder.apply {
        scopeEndNoLine("$name {", "}") {
            properties
                .filterNot { it.property.idProperty && it.property.generatedId }
                .forEach {
                    append(it.name)
                    append(" = ")
                    if (it.property.idProperty) {
                        append("TODO(\"mock id\")")
                    } else {
                        it.insertEntityPropertyValue(this, lazyInsertIdMap)
                    }
                    line()
                }
        }
    }

    private fun PropertyBusiness.insertEntityPropertyValue(
        builder: StringIndentScopeBuilder,
        lazyInsertIdMap: MutableMap<Long, LazyInsertId>,
    ): StringIndentScopeBuilder = builder.apply {
        if (listType) {
            append("emptyList()")
        } else if (!typeNotNull) {
            append("null")
        } else if (this@insertEntityPropertyValue is EnumProperty) {
            append("${enum.name}.${enum.defaultItem.name}")
        } else if (this@insertEntityPropertyValue is AssociationProperty) {
            if (isLongAssociation) {
                scopeEndNoLine("${typeEntity.name} {", "}") {
                    typeEntityBusiness.subEditNoIdProperties.forEach {
                        append(it.name)
                        append(" = ")
                        it.insertEntityPropertyValue(this, lazyInsertIdMap)
                    }
                }
            } else {
                if (isTargetOne) {
                    if (typeEntity.id !in lazyInsertIdMap) {
                        val variableName = "${entityBusiness.lowerName}${upperName}Id"
                        append(variableName)
                        lazyInsertIdMap[id] = LazyInsertId(
                            entity = typeEntityBusiness,
                            name = variableName,
                        )
                    }
                } else {
                    append("emptyList()")
                }
            }
        } else if (this@insertEntityPropertyValue is ForceIdViewProperty) {
            if (isTargetOne) {
                val variableName = "${entityBusiness.lowerName}${upperName}"
                append(variableName)
                lazyInsertIdMap[id] = LazyInsertId(
                    entity = typeEntityBusiness,
                    name = variableName,
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

    private fun RootEntityBusiness.insertDto(
        builder: StringIndentScopeBuilder,
        lazyInsertIdMap: MutableMap<Long, LazyInsertId>,
    ) = builder.apply {
        val insertInputTypeName = dto.insertInput

        append(insertInputTypeName)
        line("(")
        scope {
            addFormProperties
                .filterNot { it.property.idProperty && it.property.generatedId }
                .forEach {
                    append(it.name)
                    append(" = ")
                    if (it.property.idProperty) {
                        append("TODO(\"mock id\")")
                    } else {
                        it.insertDtoPropertyValue(insertInputTypeName, this, lazyInsertIdMap)
                    }
                    line(",")
                }
        }
        append(")")
    }

    private fun SubEntityBusiness.insertDto(
        builder: StringIndentScopeBuilder,
        lazyInsertIdMap: MutableMap<Long, LazyInsertId>,
    ) = builder.apply {
        val insertInputTypeName = asRoot.dto.insertInput

        append(insertInputTypeName)
        line("(")
        scope {
            addFormProperties
                .filterNot { it.property.idProperty && it.property.generatedId }
                .forEach {
                    append(it.name)
                    append(" = ")
                    if (it.property.idProperty) {
                        append("TODO(\"mock id\")")
                    } else {
                        it.insertDtoPropertyValue(insertInputTypeName, this, lazyInsertIdMap)
                    }
                    line(",")
                }
        }
        append(")")
    }

    private fun PropertyBusiness.insertDtoPropertyValue(
        insertInputTypeName: String,
        builder: StringIndentScopeBuilder,
        lazyInsertIdMap: MutableMap<Long, LazyInsertId>,
    ): StringIndentScopeBuilder = builder.apply {
        if (listType) {
            append("emptyList()")
        } else if (!typeNotNull) {
            append("null")
        } else if (this@insertDtoPropertyValue is EnumProperty) {
            append("${enum.name}.${enum.defaultItem.name}")
        } else if (this@insertDtoPropertyValue is AssociationProperty) {
            val currentType = "${insertInputTypeName}.TargetOf_$name"

            if (isLongAssociation) {
                append(currentType)
                line("(")
                scope {
                    typeEntityBusiness.subEditNoIdProperties.forEach {
                        append(it.name)
                        append(" = ")
                        it.insertDtoPropertyValue(currentType, this, lazyInsertIdMap)
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
                        )
                    }
                } else {
                    append("emptyList()")
                }
            }
        } else if (this@insertDtoPropertyValue is ForceIdViewProperty) {
            if (isTargetOne) {
                val variableName = "${entityBusiness.lowerName}${upperName}"
                append(variableName)
                lazyInsertIdMap[id] = LazyInsertId(
                    entity = typeEntityBusiness,
                    name = variableName,
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