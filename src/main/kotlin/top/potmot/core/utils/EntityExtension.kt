package top.potmot.core.utils

import top.potmot.entity.dto.GenEntityPropertiesView
import top.potmot.entity.dto.GenPropertyEnum

private fun formatFilePath(packagePath: String): String =
    packagePath.replace(".", "/") + "/"

val GenEntityPropertiesView.filePath
    get() = formatFilePath(packagePath)

val GenPropertyEnum.filePath
    get() = formatFilePath(packagePath)

val GenPropertyEnum.constants
    get() = "${name}_CONSTANTS"

val GenEntityPropertiesView.idProperty
    get() = properties.first { it.idProperty }

val GenEntityPropertiesView.enums
    get() = properties.mapNotNull { it.enum }

val GenEntityPropertiesView.noIdView
    get() = properties.count { it.idView } == 0 && properties.count { it.associationType != null } > 0

data class Packages(
    val entity: GenEntityPropertiesView,
    val servicePackage: String = entity.packagePath.replaceAfterLast(".", "service"),
    val utilsPackage: String = entity.packagePath.replaceAfterLast(".", "utils"),
    val exceptionPackage: String = entity.packagePath.replaceAfterLast(".", "exception"),
    val dtoPackage: String = "${entity.packagePath}.dto"
)

val GenEntityPropertiesView.packages
    get() = Packages(this)

val GenEntityPropertiesView.requestPath
    get() = name.replaceFirstChar { it.lowercase() }

val GenEntityPropertiesView.permissionPrefix
    get() = name.replaceFirstChar { it.lowercase() }

val GenEntityPropertiesView.serviceName
    get() = "${name}Service"

data class DtoNames(
    val entity: GenEntityPropertiesView,
    val listView: String = "${entity.name}ListView",
    val detailView: String = "${entity.name}DetailView",
    val insertInput: String = "${entity.name}InsertInput",
    val updateInput: String = "${entity.name}UpdateInput",
    val spec: String = "${entity.name}Spec"
)

val GenEntityPropertiesView.dto
    get() = DtoNames(this)

data class EntityComponents(
    val entity: GenEntityPropertiesView,
    val dir: String = entity.name.replaceFirstChar { it.lowercase() },
    val table: String = "${entity.name}Table",
    val form: String = "${entity.name}Form",
    val queryForm: String = "${entity.name}QueryForm",
    val page: String = "${entity.name}Page",
)

val GenEntityPropertiesView.component
    get() = EntityComponents(this)

data class EnumComponents(
    val enum: GenPropertyEnum,
    val dir: String = enum.name.replaceFirstChar { it.lowercase() },
    val select: String = "${enum.name}Select",
    val view: String = "${enum.name}View",
)

val GenPropertyEnum.component
    get() = EnumComponents(this)