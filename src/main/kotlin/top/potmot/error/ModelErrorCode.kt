package top.potmot.error

import org.babyfish.jimmer.error.ErrorFamily
import org.babyfish.jimmer.error.ErrorField
import top.potmot.core.business.meta.AssociationPath
import top.potmot.entity.dto.IdName

@ErrorFamily
enum class ModelErrorCode {
    @ErrorField(name = "enum", type = IdName::class)
    DEFAULT_ITEM_NOT_FOUND,

    @ErrorField(name = "entity", type = IdName::class)
    ID_PROPERTY_NOT_FOUND,

    @ErrorField(name = "entity", type = IdName::class)
    @ErrorField(name = "idProperties", type = IdName::class, list = true)
    ID_PROPERTY_MORE_THAN_ONE,

    @ErrorField(name = "associationPath", type = AssociationPath::class)
    LONG_ASSOCIATION_CIRCULAR_DEPENDENCE,

    @ErrorField(name = "associationPath", type = AssociationPath::class)
    ASSOCIATION_PATH_EXTRACT_NO_ROOT,

    @ErrorField(name = "entity", type = IdName::class)
    @ErrorField(name = "entityProperties", type = IdName::class, list = true)
    @ErrorField(name = "index", type = IdName::class)
    @ErrorField(name = "indexPropertyIds", type = Long::class, list = true)
    @ErrorField(name = "notFoundPropertyId", type = Long::class)
    INDEX_REF_PROPERTY_NOT_FOUND,

    @ErrorField(name = "entity", type = IdName::class)
    @ErrorField(name = "entityProperties", type = IdName::class, list = true)
    @ErrorField(name = "index", type = IdName::class)
    @ErrorField(name = "indexPropertyIds", type = Long::class, list = true)
    @ErrorField(name = "listProperty", type = IdName::class)
    INDEX_REF_PROPERTY_CANNOT_BE_LIST,

    @ErrorField(name = "entity", type = IdName::class)
    @ErrorField(name = "selfAssociationProperties", type = IdName::class, list = true)
    TREE_ENTITY_CANNOT_FOUND_PARENT_PROPERTY,

    @ErrorField(name = "entity", type = IdName::class)
    @ErrorField(name = "selfAssociationProperties", type = IdName::class, list = true)
    TREE_ENTITY_CANNOT_FOUND_CHILDREN_PROPERTY,
}