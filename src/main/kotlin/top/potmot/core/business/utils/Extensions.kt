package top.potmot.core.business.utils

import top.potmot.core.utils.filePath
import top.potmot.entity.dto.GenEntityBusinessView
import top.potmot.entity.dto.IdName
import top.potmot.entity.dto.share.GenerateEntity
import top.potmot.entity.dto.share.GenerateEnum
import top.potmot.entity.dto.share.GenerateItem
import top.potmot.error.ModelException

val GenerateItem.dir
    get() = name.replaceFirstChar { it.lowercase() }

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
    get() = name.replaceFirstChar { it.lowercase() }

val GenerateEntity.permissionPrefix
    get() = name.replaceFirstChar { it.lowercase() }

val GenerateEntity.serviceName
    get() = "${name}Service"

data class DtoNames(
    val listView: String,
    val detailView: String,
    val insertInput: String,
    val updateInput: String,
    val spec: String,
    val optionView: String
)

private fun DtoNames(entity: GenerateEntity) = DtoNames(
    listView = "${entity.name}ListView",
    detailView = "${entity.name}DetailView",
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

val GenEntityBusinessView.TargetOf_properties.TargetOf_enum.defaultItem: GenEntityBusinessView.TargetOf_properties.TargetOf_enum.TargetOf_items
    @Throws(ModelException.DefaultItemNotFound::class)
    get() {
        val defaultItem = items.minByOrNull { it.orderKey }

        if (defaultItem == null)
            throw ModelException.defaultItemNotFound("enumName: $name", enum = IdName(id, name))

        return defaultItem
    }

val alternativeSelectOptionLabel = setOf(
    "name",
    "label",
    "title",
)

val GenEntityBusinessView.idProperty
    @Throws(ModelException.IdPropertyNotFound::class)
    get() =
        if (idProperties.size != 1)
            throw ModelException.idPropertyNotFound("entityName: $name", entity = IdName(id, name))
        else
            idProperties[0]

val GenEntityBusinessView.TargetOf_properties.TargetOf_typeEntity.idProperty
    @Throws(ModelException.IdPropertyNotFound::class)
    get() =
        if (idProperties.size != 1)
            throw ModelException.idPropertyNotFound("entityName: $name", entity = IdName(id, name))
        else
            idProperties[0]

val GenEntityBusinessView.selectOptionLabel: String?
    get() {
        val propertyNames = properties
            .filter { !it.idProperty && it.associationType == null && it.entityId == id }
            .map { it.name }.toSet()

        for (item in alternativeSelectOptionLabel) {
            if (item in propertyNames) return item
        }

        return null
    }