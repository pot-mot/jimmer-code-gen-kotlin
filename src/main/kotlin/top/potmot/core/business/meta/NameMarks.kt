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
    val optionView: String,
)

data class NamePath(
    val name: String,
    val suffix: String,
    val path: String,
) {
    val fullPath = "$path/$name.$suffix"

    val fullPathNoSuffix = "$path/$name"
}

sealed interface EntityComponentFiles

data class RootEntityComponentFiles(
    val table: NamePath,
    val addForm: NamePath,
    val addFormType: NamePath,
    val addFormDefault: NamePath,
    val editForm: NamePath,
    val editFormType: NamePath,
    val queryForm: NamePath,
    val page: NamePath,
) : EntityComponentFiles

data class SubEntityComponentFiles(
    val subForm: NamePath,
    val subFormType: NamePath,
    val subFormDefault: NamePath,
    val editTable: NamePath,
    val editTableType: NamePath,
    val idSelect: NamePath,
    val idMultiSelect: NamePath,
) : EntityComponentFiles

sealed interface EntityRuleFiles

data class RootEntityRuleFiles(
    val addFormRules: NamePath,
    val editFormRules: NamePath,
) : EntityRuleFiles

data class SubEntityRuleFiles(
    val subFormRules: NamePath,
    val editTableRules: NamePath,
) : EntityRuleFiles

data class EntityPermissions(
    val get: String,
    val list: String,
    val select: String,
    val insert: String,
    val update: String,
    val delete: String,
    val menu: String,
)

data class EnumComponentFiles(
    val view: NamePath,
    val select: NamePath,
    val nullableSelect: NamePath,
)
