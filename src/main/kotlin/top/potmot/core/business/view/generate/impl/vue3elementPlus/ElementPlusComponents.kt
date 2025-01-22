package top.potmot.core.business.view.generate.impl.vue3elementPlus

import top.potmot.core.business.view.generate.meta.vue3.Element
import top.potmot.core.business.view.generate.meta.vue3.PropBind
import top.potmot.core.business.view.generate.meta.vue3.TagElement
import top.potmot.core.business.view.generate.meta.vue3.TextElement
import top.potmot.core.business.view.generate.meta.vue3.VFor
import top.potmot.core.business.view.generate.meta.vue3.VModel
import top.potmot.core.business.view.generate.meta.vue3.slotTemplate
import top.potmot.core.business.view.generate.meta.vue3.toPropBind
import top.potmot.utils.number.formatIfDouble

class ElementPlusComponents {
    enum class Type {
        PRIMARY,
        SUCCESS,
        INFO,
        WARNING,
        DANGER;

        fun toPropBind() = PropBind(
            "type",
            name.lowercase(),
            isLiteral = true
        )
    }

    enum class TableColumnFixed {
        LEFT, RIGHT;

        fun toPropBind() = PropBind(
            "fixed",
            value = if (this == LEFT) null else "right",
            isLiteral = true,
        )
    }

    companion object {
        fun text(
            content: String,
            type: Type? = null,
        ) = text(
            listOf(TextElement(content)),
            type,
        )

        fun text(
            content: Collection<Element>,
            type: Type? = null,
        ) = TagElement(
            "el-text",
            props = listOfNotNull(type?.toPropBind()),
            children = content,
        )

        fun input(
            modelValue: String = "modelValue",
            comment: String = "",
            placeholder: (comment: String) -> String? = { "请输入$it" },
            clearable: Boolean = true,
            disabled: Boolean = false,
        ) = TagElement(
            "el-input",
            directives = listOf(
                VModel(modelValue)
            ),
            props = listOfNotNull(
                placeholder(comment).toPropBind("placeholder", true),
                clearable.toPropBind("clearable"),
                disabled.toPropBind("disabled"),
            )
        )

        fun inputNumber(
            modelValue: String = "modelValue",
            comment: String = "",
            placeholder: (comment: String) -> String? = { "请输入$it" },
            precision: Int? = null,
            min: String? = null,
            max: String? = null,
            valueOnClear: String? = "undefined",
            disabled: Boolean = false,
        ) = TagElement(
            "el-input-number",
            directives = listOf(
                VModel(modelValue, modifier = listOf("number"))
            ),
            props = listOfNotNull(
                placeholder(comment).toPropBind("placeholder", true),
                precision.toPropBind("precision"),
                min.toPropBind("min"),
                max.toPropBind("max"),
                formatIfDouble(valueOnClear, precision).toPropBind("value-on-clear"),
                disabled.toPropBind("disabled"),
            )
        )

        fun switch(
            modelValue: String = "modelValue",
            disabled: Boolean = false,
        ) = TagElement(
            "el-switch",
            directives = listOf(
                VModel(modelValue)
            ),
            props = listOfNotNull(
                disabled.toPropBind("disabled"),
            )
        )

        fun select(
            modelValue: String = "modelValue",
            comment: String = "",
            placeholder: (comment: String) -> String? = { "请选择$it" },
            filterable: Boolean = true,
            clearable: Boolean = true,
            disabled: Boolean = false,
            multiple: Boolean = false,
            valueOnClear: String? = "undefined",
            content: Collection<Element> = listOf(),
        ) = TagElement(
            "el-select",
            directives = listOf(
                VModel(modelValue)
            ),
            props = listOfNotNull(
                placeholder(comment).toPropBind("placeholder", isLiteral = true),
                filterable.toPropBind("filterable"),
                clearable.toPropBind("clearable"),
                valueOnClear.toPropBind("value-on-clear"),
                disabled.toPropBind("disabled"),
                multiple.toPropBind("multiple"),
                multiple.toPropBind("collapse-tags"),
                multiple.toPropBind("collapse-tags-tooltip"),
            ),
            children = content,
        )

        fun option(
            value: String,
            label: String? = null,
            labelIsLiteral: Boolean = false,
            content: Collection<Element> = emptyList(),
        ) = TagElement(
            "el-option",
            props = listOfNotNull(
                PropBind("value", value),
                label.toPropBind("label", isLiteral = labelIsLiteral),
            ),
            children = content
        )

        fun options(
            options: String,
            option: String,
            withIndex: Boolean = false,
            key: (option: String) -> String? = { null },
            value: (option: String) -> String = { "$it.value" },
            label: (option: String) -> String? = { "$it.label" },
            content: Collection<Element> = emptyList(),
        ) = option(
            value(option),
            label(option),
            content = content,
        ).merge {
            directives += VFor(option, options, withIndex)
            key(option).toPropBind("key")?.let { props.add(0, it) }
        }

        fun treeSelect(
            data: String,
            labelProp: String,
            childrenProp: String,
            nodeKey: String,
            modelValue: String = "modelValue",
            comment: String = "",
            placeholder: (comment: String) -> String? = { "请选择$it" },
            filterable: Boolean = true,
            filterNodeMethod: String? = null,
            clearable: Boolean = true,
            disabled: Boolean = false,
            multiple: Boolean = false,
            checkStrictly: Boolean = true,
            valueOnClear: String? = "undefined",
            content: Collection<Element> = listOf(),
        ) = TagElement(
            "el-tree-select",
            directives = listOf(
                VModel(modelValue)
            ),
            props = listOfNotNull(
                placeholder(comment).toPropBind("placeholder", isLiteral = true),
                data.toPropBind("data"),
                PropBind("props", "{label: '${labelProp}', children: '${childrenProp}'}"),
                nodeKey.toPropBind("node-key", isLiteral = true),
                filterable.toPropBind("filterable"),
                filterNodeMethod.toPropBind("filter-node-method"),
                clearable.toPropBind("clearable"),
                valueOnClear.toPropBind("value-on-clear"),
                disabled.toPropBind("disabled"),
                multiple.toPropBind("multiple"),
                checkStrictly.toPropBind("check-strictly"),
            ),
            children = content,
        )

        fun timePicker(
            modelValue: String = "modelValue",
            valueFormat: String? = "HH:mm:ss",
            comment: String = "时间",
            placeholder: (comment: String) -> String? = { "请选择$it" },
            clearable: Boolean = true,
            disabled: Boolean = false,
        ) = TagElement(
            "el-time-picker",
            directives = listOf(
                VModel(modelValue)
            ),
            props = listOfNotNull(
                valueFormat.toPropBind("value-format", true),
                placeholder(comment).toPropBind("placeholder", true),
                clearable.toPropBind("clearable"),
                disabled.toPropBind("disabled"),
            )
        )

        fun datePicker(
            modelValue: String = "modelValue",
            valueFormat: String? = "YYYY-MM-DD",
            comment: String = "日期",
            placeholder: (comment: String) -> String? = { "请选择$it" },
            clearable: Boolean = true,
            disabled: Boolean = false,
        ) = TagElement(
            "el-date-picker",
            directives = listOf(
                VModel(modelValue)
            ),
            props = listOfNotNull(
                PropBind("type", "date", true),
                valueFormat.toPropBind("value-format", true),
                placeholder(comment).toPropBind("placeholder", true),
                clearable.toPropBind("clearable"),
                disabled.toPropBind("disabled"),
            )
        )

        fun dateTimePicker(
            modelValue: String = "modelValue",
            valueFormat: String? = "YYYY-MM-DDTHH:mm:ss",
            comment: String = "日期时间",
            placeholder: (comment: String) -> String? = { "请选择$it" },
            clearable: Boolean = true,
            disabled: Boolean = false,
        ) = TagElement(
            "el-date-picker",
            directives = listOf(
                VModel(modelValue)
            ),
            props = listOfNotNull(
                PropBind("type", "datetime", true),
                valueFormat.toPropBind("value-format", true),
                placeholder(comment).toPropBind("placeholder", true),
                clearable.toPropBind("clearable"),
                disabled.toPropBind("disabled"),
            )
        )

        fun timePickerRange(
            modelValue: String = "modelValue",
            valueFormat: String? = "HH:mm:ss",
            comment: String = "时间",
            startPlaceholder: (comment: String) -> String? = { "请选择开始$it" },
            endPlaceholder: (comment: String) -> String? = { "请选择结束$it" },
            clearable: Boolean = true,
            disabled: Boolean = false,
        ) = TagElement(
            "el-time-picker",
            directives = listOf(
                VModel(modelValue)
            ),
            props = listOfNotNull(
                valueFormat.toPropBind("value-format", true),
                PropBind("is-range", isLiteral = true),
                startPlaceholder(comment).toPropBind("start-placeholder", true),
                endPlaceholder(comment).toPropBind("end-placeholder", true),
                clearable.toPropBind("clearable"),
                disabled.toPropBind("disabled"),
            )
        )

        fun datePickerRange(
            modelValue: String = "modelValue",
            valueFormat: String? = "YYYY-MM-DD",
            comment: String = "日期",
            startPlaceholder: (comment: String) -> String? = { "请选择开始$it" },
            endPlaceholder: (comment: String) -> String? = { "请选择结束$it" },
            clearable: Boolean = true,
            disabled: Boolean = false,
        ) = TagElement(
            "el-date-picker",
            directives = listOf(
                VModel(modelValue)
            ),
            props = listOfNotNull(
                PropBind("type", "daterange", true),
                valueFormat.toPropBind("value-format", true),
                PropBind("unlink-panels", isLiteral = true),
                startPlaceholder(comment).toPropBind("start-placeholder", true),
                endPlaceholder(comment).toPropBind("end-placeholder", true),
                clearable.toPropBind("clearable"),
                disabled.toPropBind("disabled"),
            )
        )

        fun dateTimePickerRange(
            modelValue: String = "modelValue",
            valueFormat: String? = "YYYY-MM-DDTHH:mm:ss",
            comment: String = "日期时间",
            startPlaceholder: (comment: String) -> String? = { "请选择开始$it" },
            endPlaceholder: (comment: String) -> String? = { "请选择结束$it" },
            clearable: Boolean = true,
            disabled: Boolean = false,
        ) = TagElement(
            "el-date-picker",
            directives = listOf(
                VModel(modelValue)
            ),
            props = listOfNotNull(
                PropBind("type", "datetimerange", true),
                valueFormat.toPropBind("value-format", true),
                PropBind("unlink-panels", isLiteral = true),
                startPlaceholder(comment).toPropBind("start-placeholder", true),
                endPlaceholder(comment).toPropBind("end-placeholder", true),
                clearable.toPropBind("clearable"),
                disabled.toPropBind("disabled"),
            )
        )

        fun formItem(
            prop: String,
            propIsLiteral: Boolean = true,
            label: String,
            labelIsLiteral: Boolean = true,
            rule: String? = null,
            content: Collection<Element>,
        ) = TagElement(
            "el-form-item",
            props = listOfNotNull(
                PropBind("prop", prop, isLiteral = propIsLiteral),
                PropBind("label", label, isLiteral = labelIsLiteral),
                rule.toPropBind("rule"),
            ),
            children = content,
        )

        fun form(
            model: String,
            ref: String? = null,
            rules: String? = null,
            content: Collection<Element>,
        ) = TagElement(
            "el-form",
            props = listOfNotNull(
                PropBind("model", model),
                ref.toPropBind("ref", true),
                rules.toPropBind("rules"),
            ),
            children = content
        )

        fun tableColumn(
            prop: String? = null,
            label: String? = null,
            type: String? = null,
            fixed: TableColumnFixed? = null,
            content: Collection<Element> = emptyList(),
        ) = TagElement(
            "el-table-column",
            props = listOfNotNull(
                prop.toPropBind("prop", isLiteral = true),
                label.toPropBind("label", isLiteral = true),
                type.toPropBind("type", isLiteral = true),
                fixed?.toPropBind()
            ),
            children = listOfNotNull(
                if (content.isEmpty())
                    null
                else slotTemplate(
                    propScope = "scope",
                    content = content,
                )
            )
        )

        fun table(
            data: String,
            rowKey: String,
            border: Boolean = true,
            stripe: Boolean = true,
            columns: Collection<Element>,
            childrenProp: String? = null,
        ) = TagElement(
            "el-table",
            props = listOfNotNull(
                PropBind("data", data),
                PropBind("row-key", rowKey, isLiteral = true),
                border.toPropBind("border"),
                stripe.toPropBind("stripe"),
                childrenProp?.let {
                    PropBind("tree-props", "{children: '${childrenProp}'}")
                }
            ),
            children = columns
        )

        fun pagination(
            currentPage: String,
            pageSize: String,
            total: String,
            pageSizes: Iterable<Int> = listOf(5, 10, 20),
            layout: Iterable<String> = listOf("total", "sizes", "prev", "pager", "next", "jumper"),
        ) = TagElement(
            "el-pagination",
            directives = listOf(
                VModel(propName = "current-page", value = currentPage),
                VModel(propName = "page-size", value = pageSize),
            ),
            props = listOfNotNull(
                total.toPropBind("total"),
                PropBind("page-sizes", pageSizes.toString()),
                PropBind("layout", layout.joinToString(", "), isLiteral = true),
            )
        )

        fun dialog(
            modelValue: String,
            destroyOnClose: Boolean = true,
            closeOnClickModal: Boolean = false,
            content: Collection<Element>,
        ) = TagElement(
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

        fun col(
            span: Int = 24,
            xs: Int? = null,
            sm: Int? = null,
            md: Int? = null,
            lg: Int? = null,
            xl: Int? = null,
            offset: Int? = null,
            content: Collection<Element>,
        ) = TagElement(
            "el-col",
            props = listOfNotNull(
                span.toPropBind("span"),
                offset.toPropBind("offset"),
                xs?.toPropBind("xs"),
                sm?.toPropBind("sm"),
                md?.toPropBind("md"),
                lg?.toPropBind("lg"),
                xl?.toPropBind("xl"),
            ),
            children = content
        )

        fun row(
            gutter: Int? = null,
            content: Collection<Element>,
        ) = TagElement(
            "el-row",
            props = listOfNotNull(
                gutter.toPropBind("gutter"),
            ),
            children = content
        )

        fun button(
            content: String,
            disabled: Boolean = false,
            type: Type? = null,
            icon: String? = null,
            plain: Boolean = false,
            link: Boolean = false,
            round: Boolean = false,
            circle: Boolean = false,
        ) = button(
            content = listOf(TextElement(content)),
            disabled = disabled,
            type = type,
            icon = icon,
            plain = plain,
            link = link,
            round = round,
            circle = circle,
        )

        fun button(
            content: Collection<Element> = emptyList(),
            disabled: Boolean = false,
            type: Type? = null,
            icon: String? = null,
            plain: Boolean = false,
            link: Boolean = false,
            round: Boolean = false,
            circle: Boolean = false,
        ) = TagElement(
            "el-button",
            props = listOfNotNull(
                type?.toPropBind(),
                icon.toPropBind("icon"),
                plain.toPropBind("plain"),
                link.toPropBind("link"),
                round.toPropBind("round"),
                circle.toPropBind("circle"),
                disabled.toPropBind("disabled"),
            ),
            children = content
        )
    }
}