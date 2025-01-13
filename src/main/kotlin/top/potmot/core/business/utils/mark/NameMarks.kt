package top.potmot.core.business.utils.mark

import top.potmot.core.utils.filePath
import top.potmot.entity.dto.GenEntityBusinessView
import top.potmot.entity.dto.share.GenerateEntity
import top.potmot.entity.dto.share.GenerateEnum
import top.potmot.entity.dto.share.GenerateItem

val GenerateItem.lowerName
    get() = name.replaceFirstChar { it.lowercase() }

val GenEntityBusinessView.TargetOf_properties.upperName
    get() = name.replaceFirstChar { it.uppercaseChar() }

val GenEntityBusinessView.TargetOf_idProperties.upperName
    get() = name.replaceFirstChar { it.uppercaseChar() }

val GenerateEnum.dir
    get() = "enums/$lowerName"

val GenerateItem.dir
    get() = lowerName

val GenEntityBusinessView.enums
    get() = properties.mapNotNull { it.enum }

val GenerateEnum.constants
    get() = "${name}_CONSTANTS"

data class Packages(
    val entity: String,
    val service: String,
    val utils: String,
    val exception: String,
    val dto: String,
)

private fun Packages(entity: GenerateEntity) = Packages(
    entity = entity.packagePath,
    service = entity.packagePath.replaceAfterLast(".", "service"),
    utils = entity.packagePath.replaceAfterLast(".", "utils"),
    exception = entity.packagePath.replaceAfterLast(".", "exception"),
    dto = "${entity.packagePath}.dto",
)

val GenerateEntity.packages
    get() = Packages(this)

val GenerateEntity.serviceFilePath
    get() = filePath.replace("/entity/", "/service/")

val GenerateEntity.requestPath
    get() = lowerName

val GenerateEntity.serviceName
    get() = "${name}Service"

val GenerateEntity.apiServiceName
    get() = "${lowerName}Service"

data class DtoNames(
    val listView: String,
    val treeView: String,
    val detailView: String,
    val insertInput: String,
    val updateFillView: String,
    val updateInput: String,
    val spec: String,
    val optionView: String
)

private fun DtoNames(entity: GenerateEntity) = DtoNames(
    listView = "${entity.name}ListView",
    treeView = "${entity.name}TreeView",
    detailView = "${entity.name}DetailView",
    updateFillView = "${entity.name}UpdateFillView",
    updateInput = "${entity.name}UpdateInput",
    insertInput = "${entity.name}InsertInput",
    spec = "${entity.name}Spec",
    optionView = "${entity.name}OptionView",
)

val GenerateEntity.dto
    get() = DtoNames(this)

data class EntityComponentNames(
    val table: String,
    val addForm: String,
    val editForm: String,
    val queryForm: String,
    val page: String,
    val idSelect: String,
    val idMultiSelect: String,
    val editTable: String,
)

private fun EntityComponentNames(entity: GenerateEntity) = EntityComponentNames(
    table = "${entity.name}Table",
    addForm = "${entity.name}AddForm",
    editForm = "${entity.name}EditForm",
    queryForm = "${entity.name}QueryForm",
    page = "${entity.name}Page",
    idSelect = "${entity.name}IdSelect",
    idMultiSelect = "${entity.name}IdMultiSelect",
    editTable = "${entity.name}EditTable",
)

val GenerateEntity.components
    get() = EntityComponentNames(this)

data class EntityRulesNames(
    val addFormRules: String,
    val editFormRules: String,
    val editTableRules: String,
)

private fun EntityRulesNames(entity: GenerateEntity) = EntityRulesNames(
    addFormRules = "${entity.name}AddFormRules",
    editFormRules = "${entity.name}EditFormRules",
    editTableRules = "${entity.name}EditTableRules",
)

val GenerateEntity.rules
    get() = EntityRulesNames(this)


data class EntityPermissions(
    val get: String,
    val list: String,
    val select: String,
    val insert: String,
    val update: String,
    val delete: String,
    val menu: String,
)

private fun EntityPermissions(entity: GenerateEntity): EntityPermissions {
    val permissionPrefix = entity.lowerName
    return EntityPermissions(
        get = "$permissionPrefix:get",
        list = "$permissionPrefix:list",
        select = "$permissionPrefix:select",
        insert = "$permissionPrefix:insert",
        update = "$permissionPrefix:update",
        delete = "$permissionPrefix:delete",
        menu = "$permissionPrefix:menu",
    )
}

val GenerateEntity.permissions
    get() = EntityPermissions(this)

val GenEntityBusinessView.permissionStrList: List<String>
    get() {
        val permissions = this.permissions

        return listOfNotNull(
            permissions.get,
            permissions.list,
            permissions.select,
            if (hasPage) permissions.menu else null,
            if (canAdd) permissions.insert else null,
            if (canEdit) permissions.update else null,
            if (canDelete) permissions.delete else null,
        )
    }

data class EnumComponentNames(
    val view: String,
    val select: String,
    val nullableSelect: String,
)

private fun EnumComponentNames(enum: GenerateEnum) = EnumComponentNames(
    view = "${enum.name}View",
    select = "${enum.name}Select",
    nullableSelect = "${enum.name}NullableSelect",
)

val GenerateEnum.components
    get() = EnumComponentNames(this)