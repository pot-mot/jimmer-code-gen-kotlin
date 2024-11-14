package top.potmot.error

import org.babyfish.jimmer.error.ErrorFamily
import org.babyfish.jimmer.error.ErrorField
import top.potmot.core.entity.meta.JoinColumnMeta
import top.potmot.core.entity.meta.JoinTableMeta
import top.potmot.entity.dto.IdName
import top.potmot.entity.dto.IdNullableName
import top.potmot.enumeration.AssociationType

@ErrorFamily
enum class ConvertErrorCode {
    @ErrorField(name = "modelId", type = Long::class)
    MODEL_NOT_FOUND,

    @ErrorField(name = "association", type = IdName::class)
    @ErrorField(name = "sourceTable", type = IdName::class)
    @ErrorField(name = "sourceColumn", type = IdNullableName::class)
    @ErrorField(name = "targetTable", type = IdName::class)
    @ErrorField(name = "targetColumn", type = IdName::class)
    OUT_ASSOCIATION_CANNOT_FOUNT_SOURCE_COLUMN,

    @ErrorField(name = "association", type = IdName::class)
    @ErrorField(name = "sourceTable", type = IdName::class)
    @ErrorField(name = "sourceColumn", type = IdName::class)
    @ErrorField(name = "targetTable", type = IdName::class)
    @ErrorField(name = "targetColumn", type = IdNullableName::class)
    IN_ASSOCIATION_CANNOT_FOUNT_TARGET_COLUMN,

    @ErrorField(name = "association", type = IdName::class)
    @ErrorField(name = "sourceTable", type = IdName::class)
    @ErrorField(name = "sourceColumn", type = IdName::class)
    @ErrorField(name = "targetTable", type = IdName::class)
    @ErrorField(name = "targetColumn", type = IdName::class)
    ASSOCIATION_CANNOT_BE_ONE_TO_MANY,

    @ErrorField(name = "association", type = IdName::class)
    @ErrorField(name = "sourceTable", type = IdName::class)
    @ErrorField(name = "sourceColumns", type = IdName::class, list = true)
    @ErrorField(name = "targetTable", type = IdName::class)
    @ErrorField(name = "targetColumns", type = IdName::class, list = true)
    MULTIPLE_COLUMNS_NOT_SUPPORTED,

    @ErrorField(name = "idViewProperty", type = String::class)
    @ErrorField(name = "baseProperty", type = String::class)
    @ErrorField(name = "associationProperty", type = String::class)
    @ErrorField(name = "typeTable", type = IdName::class)
    @ErrorField(name = "typeTablePkColumnIds", type = Long::class, list = true)
    ID_VIEW_MULTIPLE_PK_NOT_SUPPORTED,

    @ErrorField(name = "association", type = IdName::class)
    @ErrorField(name = "sourceTable", type = IdName::class)
    @ErrorField(name = "sourceColumn", type = IdName::class)
    @ErrorField(name = "targetTable", type = IdName::class)
    @ErrorField(name = "targetColumn", type = IdName::class)
    OUT_ASSOCIATION_CANNOT_FOUND_SOURCE_BASE_PROPERTY,

    @ErrorField(name = "association", type = IdName::class)
    @ErrorField(name = "sourceTable", type = IdName::class)
    @ErrorField(name = "sourceColumn", type = IdName::class)
    @ErrorField(name = "targetTable", type = IdName::class)
    @ErrorField(name = "targetColumn", type = IdName::class)
    IN_ASSOCIATION_CANNOT_FOUND_TARGET_BASE_PROPERTY,

    @ErrorField(name = "duplicateName", type = String::class)
    @ErrorField(name = "properties", type = PropertyNameDuplicateData::class, list = true)
    PROPERTY_NAME_DUPLICATE,

    @ErrorField(name = "superTableIds", type = Long::class, list = true)
    @ErrorField(name = "superEntityIds", type = Long::class, list = true)
    SUPER_TABLE_SUPER_ENTITY_NOT_MATCH,
}

data class PropertyNameDuplicateData(
    val columnId: Long?,
    val name: String,
    val comment: String,
    val type: String,
    val listType: Boolean,
    val typeNotNull: Boolean,
    val typeTableId: Long?,
    val enumId: Long?,
    val associationType: AssociationType?,
    val joinColumnMetas: List<JoinColumnMeta>?,
    val joinTableMeta: JoinTableMeta?,
    val idView: Boolean?,
    val idViewTarget: String?,
)