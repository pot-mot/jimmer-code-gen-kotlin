package top.potmot.core.business.view.generate.impl.vue3elementPlus.editForm

import top.potmot.core.business.meta.PropertyBusiness
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Vue3ElementPlusViewGenerator.form
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Vue3ElementPlusViewGenerator.formItem
import top.potmot.core.business.view.generate.impl.vue3elementPlus.form.SubValidateItem
import top.potmot.core.business.view.generate.impl.vue3elementPlus.form.cancelEvent
import top.potmot.core.business.view.generate.impl.vue3elementPlus.form.exposeValid
import top.potmot.core.business.view.generate.impl.vue3elementPlus.form.formExposeImport
import top.potmot.core.business.view.generate.impl.vue3elementPlus.form.handleCancel
import top.potmot.core.business.view.generate.impl.vue3elementPlus.form.handleSubmit
import top.potmot.core.business.view.generate.impl.vue3elementPlus.form.handleValidate
import top.potmot.core.business.view.generate.impl.vue3elementPlus.form.operationsSlot
import top.potmot.core.business.view.generate.impl.vue3elementPlus.form.operationsSlotElement
import top.potmot.core.business.view.generate.impl.vue3elementPlus.form.submitEvent
import top.potmot.core.business.view.generate.impl.vue3elementPlus.form.submitLoadingProp
import top.potmot.core.business.view.generate.impl.vue3elementPlus.formItem.FormItemData
import top.potmot.core.business.view.generate.impl.vue3elementPlus.selectOptions.SelectOption
import top.potmot.core.business.view.generate.meta.typescript.ConstVariable
import top.potmot.core.business.view.generate.meta.typescript.Import
import top.potmot.core.business.view.generate.meta.typescript.ImportType
import top.potmot.core.business.view.generate.meta.typescript.commentLine
import top.potmot.core.business.view.generate.meta.typescript.emptyLineCode
import top.potmot.core.business.view.generate.meta.vue3.Component
import top.potmot.core.business.view.generate.meta.vue3.ModelProp
import top.potmot.core.business.view.generate.meta.vue3.Prop
import top.potmot.core.business.view.generate.meta.vue3.PropBind
import top.potmot.core.business.view.generate.meta.vue3.VIf
import top.potmot.core.business.view.generate.meta.vue3.emptyLineElement

interface EditForm {
    fun editForm(
        type: String,
        typePath: String,
        useRules: String,
        useRulesPath: String,
        formData: String = "formData",
        formRef: String = "formRef",
        indent: String,
        subValidateItems: Collection<SubValidateItem> = emptyList(),
        selectOptions: Iterable<SelectOption> = emptyList(),
        afterValidCodes: String? = null,
        content: Map<PropertyBusiness, FormItemData>,
    ) = Component(
        imports = listOf(
            Import("vue", "ref"),
            ImportType("element-plus", "FormInstance"),
            formExposeImport,
            ImportType(typePath, type),
            Import(useRulesPath, useRules),
        )
                + content.values.flatMap { it.imports }
                + subValidateItems.map { it.toImport() }
                + selectOptions.map { it.toImport() },
        models = listOf(
            ModelProp(formData, type),
        ),
        props = listOf(
            Prop("withOperations", "boolean", required = false, defaultValue = "true"),
            submitLoadingProp,
            *selectOptions.map { it.toProp() }.toTypedArray(),
        ),
        emits = listOf(
            submitEvent(formData, type),
            cancelEvent
        ),
        slots = listOf(
            operationsSlot
        ),
        script = listOfNotNull(
            ConstVariable(formRef, null, "ref<FormInstance>()"),
            ConstVariable("rules", null, "$useRules($formData)"),
            emptyLineCode,
            *subValidateItems.map { it.toRef() }.toTypedArray(),
            if (subValidateItems.isNotEmpty()) emptyLineCode else null,
            commentLine("校验"),
            handleValidate(formRef, subValidateItems, indent, afterValidCodes),
            emptyLineCode,
            commentLine("提交"),
            handleSubmit(formData, indent),
            emptyLineCode,
            commentLine("取消"),
            handleCancel,
            emptyLineCode,
            exposeValid(indent)
        ),
        template = listOf(
            form(
                model = formData,
                ref = formRef,
                rules = "rules",
                content = content.map { (property, formItemData) ->
                    formItem(
                        prop = property.name,
                        label = property.comment,
                        content = formItemData.elements
                    )
                } + listOf(
                    emptyLineElement,
                    operationsSlotElement.merge {
                        directives += VIf("withOperations")
                    },
                ),
            ).merge {
                props += PropBind("@submit.prevent", isLiteral = true)
            }
        )
    )
}