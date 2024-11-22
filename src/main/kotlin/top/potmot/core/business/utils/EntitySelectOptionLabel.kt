package top.potmot.core.business.utils

import top.potmot.entity.dto.GenEntityBusinessView

val GenEntityBusinessView.selectOptionLabel: String?
    get() = properties
        .firstOrNull { it.inOptionView }
        ?.name