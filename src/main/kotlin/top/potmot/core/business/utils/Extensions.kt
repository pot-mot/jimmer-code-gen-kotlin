package top.potmot.core.business.utils

import top.potmot.core.utils.filePath
import top.potmot.entity.dto.GenEntityBusinessView
import top.potmot.entity.dto.share.GenerateEntity
import top.potmot.entity.dto.share.GenerateEnum
import top.potmot.enumeration.AssociationType

val GenEntityBusinessView.enums
    get() = properties.mapNotNull { it.enum }

private val GenEntityBusinessView.noIdView
    get() = properties.count { it.idView } == 0 && properties.count { it.associationType != null } > 0

val GenEntityBusinessView.associationProperties
    get() =
        if (noIdView)
            properties.filter { it.associationType != null }
        else
            properties.filter { it.idView }

private val targetOneAssociationType =
    setOf(AssociationType.ONE_TO_ONE, AssociationType.MANY_TO_ONE)

val GenEntityBusinessView.associationTargetOneProperties
    get() =
        if (noIdView)
            properties.filter { it.associationType in targetOneAssociationType }
        else
            properties.filter { it.associationType in targetOneAssociationType && it.idView }

val GenerateEnum.constants
    get() = "${name}_CONSTANTS"

data class Packages(
    val entity: GenerateEntity,
    val servicePackage: String = entity.packagePath.replaceAfterLast(".", "service"),
    val utilsPackage: String = entity.packagePath.replaceAfterLast(".", "utils"),
    val exceptionPackage: String = entity.packagePath.replaceAfterLast(".", "exception"),
    val dtoPackage: String = "${entity.packagePath}.dto"
)

val GenerateEntity.packages
    get() = Packages(this)

val GenerateEntity.serviceFilePath
    get() = filePath.replace("/entity/", "/service/")

val GenerateEntity.requestPath
    get() = name.replaceFirstChar { it.lowercase() }

val GenerateEntity.permissionPrefix
    get() = name.replaceFirstChar { it.lowercase() }

val GenerateEntity.serviceName
    get() = "${name}Service"

data class DtoNames(
    val entity: GenerateEntity,
    val listView: String = "${entity.name}ListView",
    val detailView: String = "${entity.name}DetailView",
    val insertInput: String = "${entity.name}InsertInput",
    val updateInput: String = "${entity.name}UpdateInput",
    val spec: String = "${entity.name}Spec"
)

val GenerateEntity.dtoNames
    get() = DtoNames(this)

data class EntityComponentNames(
    val entity: GenerateEntity,
    val dir: String = entity.name.replaceFirstChar { it.lowercase() },
    val table: String = "${entity.name}Table",
    val addForm: String = "${entity.name}AddForm",
    val editForm: String = "${entity.name}EditForm",
    val queryForm: String = "${entity.name}QueryForm",
    val page: String = "${entity.name}Page",
    val singleSelect: String = "${entity.name}SingleSelect",
    val multiSelect: String = "${entity.name}MultiSelect",
    val idSelect: String = "${entity.name}IdSelect",
    val idMultiSelect: String = "${entity.name}IdMultiSelect",
    val editTable: String = "${entity.name}EditTable",
)

val GenerateEntity.componentNames
    get() = EntityComponentNames(this)

data class EntityRulesNames(
    val entity: GenerateEntity,
    val ruleDir: String = entity.name.replaceFirstChar { it.lowercase() },
    val addFormRules: String = "${entity.name}AddFormRules",
    val editFormRules: String = "${entity.name}EditFormRules",
    val editTableRules: String = "${entity.name}EditTableRules",
)

val GenerateEntity.ruleNames
    get() = EntityRulesNames(this)


data class EnumComponentNames(
    val enum: GenerateEnum,
    val dir: String = enum.name.replaceFirstChar { it.lowercase() },
    val select: String = "${enum.name}Select",
    val nullableSelect: String = "${enum.name}NullableSelect",
    val view: String = "${enum.name}View",
)

val GenerateEnum.componentNames
    get() = EnumComponentNames(this)