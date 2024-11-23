package top.potmot.core.business.utils

import top.potmot.entity.dto.GenEntityBusinessView

val GenEntityBusinessView.selectOptionLabels: List<String>
    get() = properties
        .filter { it.inOptionView }
        .map { it.name }