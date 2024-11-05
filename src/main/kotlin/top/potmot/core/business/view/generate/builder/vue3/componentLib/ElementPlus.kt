package top.potmot.core.business.view.generate.builder.vue3.componentLib

import top.potmot.core.business.view.generate.builder.vue3.VFor
import top.potmot.core.business.view.generate.builder.vue3.VModel
import top.potmot.core.business.view.generate.builder.vue3.Vue3PropBind
import top.potmot.core.business.view.generate.builder.vue3.Vue3TemplateElement
import top.potmot.core.business.view.generate.builder.vue3.toPropBind

interface ElementPlus {
    fun input(
        modelValue: String = "modelValue",
        comment: String = "",
        placeholder: (comment: String) -> String? = { "请输入$it" },
        clearable: Boolean = true,
    ) = Vue3TemplateElement(
        "el-input",
        directives = listOf(
            VModel(modelValue)
        ),
        props = listOfNotNull(
            placeholder(comment).toPropBind("placeholder", true),
            clearable.toPropBind("clearable")
        )
    )

    fun inputNumber(
        modelValue: String = "modelValue",
        comment: String = "",
        placeholder: (comment: String) -> String? = { "请输入$it" },
        precision: Int? = null,
        min: Double? = null,
        max: Double? = null,
        valueOnClear: String? = "undefined",
    ) = Vue3TemplateElement(
        "el-input-number",
        directives = listOf(
            VModel(modelValue, modifier = listOf("number"))
        ),
        props = listOfNotNull(
            placeholder(comment).toPropBind("placeholder", true),
            precision.toPropBind("precision"),
            min.toPropBind("min"),
            max.toPropBind("max"),
            valueOnClear.toPropBind("value-on-clear"),
        )
    )

    fun switch(
        modelValue: String = "modelValue",
    ) = Vue3TemplateElement(
        "el-switch",
        directives = listOf(
            VModel(modelValue)
        ),
    )

    fun select(
        modelValue: String = "modelValue",
        comment: String = "",
        placeholder: (comment: String) -> String? = { "请选择$it" },
        clearable: Boolean = true,
    ) = Vue3TemplateElement(
        "el-select",
        directives = listOf(
            VModel(modelValue)
        ),
        props = listOfNotNull(
            placeholder(comment).toPropBind("placeholder", isLiteral = true),
            clearable.toPropBind("clearable"),
        ),
    )

    fun option(
        options: String,
        item: String,
        withIndex: Boolean = false,
        key: (item: String) -> String = { "$it.value" },
        value: (item: String) -> String = { "$it.value" },
        label: (item: String) -> String = { "$it.label" },
    ) = Vue3TemplateElement(
        "el-option",
        directives = listOf(
            VFor(item, options, withIndex)
        ),
        props = listOf(
            Vue3PropBind("key", key(item)),
            Vue3PropBind("value", value(item)),
            Vue3PropBind("label", label(item))
        )
    )

    fun timePicker(
        modelValue: String = "modelValue",
        valueFormat: String? = "HH:mm:ss",
        comment: String = "时间",
        placeholder: (comment: String) -> String? = { "请选择$it" },
        clearable: Boolean = true,
    ) = Vue3TemplateElement(
        "el-time-picker",
        directives = listOf(
            VModel(modelValue)
        ),
        props = listOfNotNull(
            valueFormat.toPropBind("value-format", true),
            placeholder(comment).toPropBind("placeholder", true),
            clearable.toPropBind("clearable"),
        )
    )

    fun datePicker(
        modelValue: String = "modelValue",
        valueFormat: String? = "YYYY-MM-DD",
        comment: String = "日期",
        placeholder: (comment: String) -> String? = { "请选择$it" },
        clearable: Boolean = true,
    ) = Vue3TemplateElement(
        "el-date-picker",
        directives = listOf(
            VModel(modelValue)
        ),
        props = listOfNotNull(
            Vue3PropBind("type", "date", true),
            valueFormat.toPropBind("value-format", true),
            placeholder(comment).toPropBind("placeholder", true),
            clearable.toPropBind("clearable"),
        )
    )

    fun dateTimePicker(
        modelValue: String = "modelValue",
        valueFormat: String? = "YYYY-MM-DDTHH:mm:ss",
        comment: String = "日期时间",
        placeholder: (comment: String) -> String? = { "请选择$it" },
        clearable: Boolean = true,
    ) = Vue3TemplateElement(
        "el-date-picker",
        directives = listOf(
            VModel(modelValue)
        ),
        props = listOfNotNull(
            Vue3PropBind("type", "datetime", true),
            valueFormat.toPropBind("value-format", true),
            placeholder(comment).toPropBind("placeholder", true),
            clearable.toPropBind("clearable"),
        )
    )

    fun timePickerRange(
        modelValue: String = "modelValue",
        valueFormat: String? = "HH:mm:ss",
        comment: String = "时间",
        startPlaceholder: (comment: String) -> String? = { "请选择开始$it" },
        endPlaceholder: (comment: String) -> String? = { "请选择结束$it" },
        clearable: Boolean = true,
    ) = Vue3TemplateElement(
        "el-time-picker",
        directives = listOf(
            VModel(modelValue)
        ),
        props = listOfNotNull(
            valueFormat.toPropBind("value-format", true),
            Vue3PropBind("is-range", isLiteral = true),
            Vue3PropBind("unlink-panels", isLiteral = true),
            startPlaceholder(comment).toPropBind("start-placeholder", true),
            endPlaceholder(comment).toPropBind("end-placeholder", true),
            clearable.toPropBind("clearable"),
        )
    )

    fun datePickerRange(
        modelValue: String = "modelValue",
        valueFormat: String? = "YYYY-MM-DD",
        comment: String = "日期",
        startPlaceholder: (comment: String) -> String? = { "请选择开始$it" },
        endPlaceholder: (comment: String) -> String? = { "请选择结束$it" },
        clearable: Boolean = true,
    ) = Vue3TemplateElement(
        "el-date-picker",
        directives = listOf(
            VModel(modelValue)
        ),
        props = listOfNotNull(
            Vue3PropBind("type", "date", true),
            valueFormat.toPropBind("value-format", true),
            Vue3PropBind("is-range", isLiteral = true),
            Vue3PropBind("unlink-panels", isLiteral = true),
            startPlaceholder(comment).toPropBind("start-placeholder", true),
            endPlaceholder(comment).toPropBind("end-placeholder", true),
            clearable.toPropBind("clearable"),
        )
    )

    fun dateTimePickerRange(
        modelValue: String = "modelValue",
        valueFormat: String? = "YYYY-MM-DDTHH:mm:ss",
        comment: String = "日期时间",
        startPlaceholder: (comment: String) -> String? = { "请选择开始$it" },
        endPlaceholder: (comment: String) -> String? = { "请选择结束$it" },
        clearable: Boolean = true,
    ) = Vue3TemplateElement(
        "el-date-picker",
        directives = listOf(
            VModel(modelValue)
        ),
        props = listOfNotNull(
            Vue3PropBind("type", "datetime", true),
            valueFormat.toPropBind("value-format", true),
            Vue3PropBind("is-range", isLiteral = true),
            Vue3PropBind("unlink-panels", isLiteral = true),
            startPlaceholder(comment).toPropBind("start-placeholder", true),
            endPlaceholder(comment).toPropBind("end-placeholder", true),
            clearable.toPropBind("clearable"),
        )
    )

    fun formItem(
        prop: String,
        label: String,
        rule: String? = null,
    ) = Vue3TemplateElement(
        "el-form-item",
        props = listOfNotNull(
            Vue3PropBind("prop", prop, isLiteral = true),
            Vue3PropBind("label", label, isLiteral = true),
            rule.toPropBind("rule"),
        )
    )

    fun form(
        model: String,
        rules: String,
        items: Collection<Vue3TemplateElement>,
    ) = Vue3TemplateElement(
        "el-form",
        props = listOf(
            Vue3PropBind("model", model),
            Vue3PropBind("rules", rules),
        ),
        children = items
    )

    fun tableColumn(
        prop: String,
        label: String,
        content: Collection<Vue3TemplateElement>,
    ) = Vue3TemplateElement(
        "el-table-column",
        props = listOf(
            Vue3PropBind("prop", prop, isLiteral = true),
            Vue3PropBind("label", label, isLiteral = true),
        ),
        children = listOfNotNull(
            if (content.isEmpty())
                null
            else Vue3TemplateElement(
                "template",
                props = listOf(
                    Vue3PropBind("#default", "scope", isLiteral = true),
                ),
                children = content,
            )
        )
    )

    fun table(
        data: String,
        border: Boolean = true,
        stripe: Boolean = true,
        columns: Collection<Vue3TemplateElement>,
    ) = Vue3TemplateElement(
        "el-table",
        props = listOfNotNull(
            Vue3PropBind("data", data),
            border.toPropBind("border"),
            stripe.toPropBind("stripe"),
        ),
        children = columns
    )

    fun dialog(
        modelValue: String,
        destroyOnClose: Boolean = true,
        closeOnClickModal: Boolean = false,
        content: Collection<Vue3TemplateElement>,
    ) = Vue3TemplateElement(
        "el-dialog",
        directives = listOf(
            VModel(modelValue),
        ),
        props = listOfNotNull(
            destroyOnClose.toPropBind("destroy-on-close"),
            closeOnClickModal.toPropBind("close-on-click-modal", default = true),
        ),
        children = content
    )
}