package top.potmot.core.business.utils

import top.potmot.core.utils.filePath
import top.potmot.entity.dto.GenEntityBusinessView
import top.potmot.entity.dto.share.GenerateEntity
import top.potmot.entity.dto.share.GenerateEnum

val GenerateEnum.constants
    get() = "${name}_CONSTANTS"

val GenEntityBusinessView.idProperty
    get() = properties.first { it.idProperty }

val GenEntityBusinessView.enums
    get() = properties.mapNotNull { it.enum }

val GenEntityBusinessView.noIdView
    get() = properties.count { it.idView } == 0 && properties.count { it.associationType != null } > 0

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

val GenerateEntity.dto
    get() = DtoNames(this)

data class EntityComponents(
    val entity: GenerateEntity,
    val dir: String = entity.name.replaceFirstChar { it.lowercase() },
    val table: String = "${entity.name}Table",
    val form: String = "${entity.name}Form",
    val queryForm: String = "${entity.name}QueryForm",
    val page: String = "${entity.name}Page",
)

val GenerateEntity.component
    get() = EntityComponents(this)

data class EnumComponents(
    val enum: GenerateEnum,
    val dir: String = enum.name.replaceFirstChar { it.lowercase() },
    val select: String = "${enum.name}Select",
    val view: String = "${enum.name}View",
)

val GenerateEnum.component
    get() = EnumComponents(this)