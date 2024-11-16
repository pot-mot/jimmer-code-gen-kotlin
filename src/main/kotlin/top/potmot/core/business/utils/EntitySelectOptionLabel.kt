package top.potmot.core.business.utils

import top.potmot.entity.dto.GenEntityBusinessView

// 备选 select option label
private val alternativeSelectOptionLabel = setOf(
    "name",
    "label",
    "title",
    "comment",
)

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