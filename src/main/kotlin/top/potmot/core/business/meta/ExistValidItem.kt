package top.potmot.core.business.meta

data class ExistValidItem(
    val dtoName: String,
    val functionName: String,
    val properties: List<PropertyBusiness>,
)