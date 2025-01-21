package top.potmot.core.business.meta

data class Packages(
    val entity: String,
    val service: String,
    val utils: String,
    val exception: String,
    val dto: String,
)

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

data class EntityRulesNames(
    val addFormRules: String,
    val editFormRules: String,
    val editTableRules: String,
)

data class EntityPermissions(
    val get: String,
    val list: String,
    val select: String,
    val insert: String,
    val update: String,
    val delete: String,
    val menu: String,
)

data class EnumComponentNames(
    val view: String,
    val select: String,
    val nullableSelect: String,
)
