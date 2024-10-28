package top.potmot.core.business.view.generate.impl.vue3elementPuls

import kotlin.math.min
import top.potmot.core.business.utils.PropertyFormType
import top.potmot.core.business.utils.PropertyQueryType
import top.potmot.core.business.utils.associationTargetOneProperties
import top.potmot.core.business.utils.baseProperties
import top.potmot.core.business.utils.componentNames
import top.potmot.core.business.utils.constants
import top.potmot.core.business.utils.dtoNames
import top.potmot.core.business.utils.enums
import top.potmot.core.business.utils.formType
import top.potmot.core.business.utils.queryType
import top.potmot.core.business.utils.ruleNames
import top.potmot.core.business.utils.serviceName
import top.potmot.core.business.utils.typeStrToTypeScriptDefault
import top.potmot.core.business.utils.typeStrToTypeScriptType
import top.potmot.core.business.view.generate.ViewGenerator
import top.potmot.entity.dto.GenEntityBusinessView
import top.potmot.entity.dto.GenEnumGenerateView
import top.potmot.entity.dto.share.GenerateEntity
import top.potmot.error.GenerateException
import top.potmot.utils.string.appendBlock
import top.potmot.utils.string.toSingular
import top.potmot.utils.string.trimBlankLine

object Vue3ElementPlusViewGenerator : ViewGenerator() {
    override fun getFileSuffix() = "vue"

    override fun stringifyEnumSelect(enum: GenEnumGenerateView): String {
        val (_, dir, _, _, view) = enum.componentNames

        return """
<script setup lang="ts">
import {${enum.constants}} from "@/api/__generated/model/enums"
import type {${enum.name}} from "@/api/__generated/model/enums"
import $view from "@/components/$dir/$view.vue"

const data = defineModel<${enum.name}>({
    required: true
})
</script>

<template>
    <el-select
        v-model="data"
        placeholder="请选择${enum.comment}">
        <el-option
            v-for="value in ${enum.constants}" 
            :value="value">
            <$view :value="value"/>
        </el-option>
        <template #label>
            <$view :value="data"/>
        </template>
    </el-select>
</template>
""".trim()
    }

    override fun stringifyEnumNullableSelect(enum: GenEnumGenerateView): String {
        val (_, dir, _, _, view) = enum.componentNames

        return """
<script setup lang="ts">
import {${enum.constants}} from "@/api/__generated/model/enums"
import type {${enum.name}} from "@/api/__generated/model/enums"
import $view from "@/components/$dir/$view.vue"

const data = defineModel<${enum.name} | undefined>({
    required: true
})
</script>

<template>
    <el-select
        v-model="data"
        placeholder="请选择${enum.comment}"
        clearable
        :empty-values="[undefined]"
        :value-on-clear="undefined">
        <el-option
            v-for="value in ${enum.constants}" 
            :value="value">
            <$view :value="value"/>
        </el-option>
        <template #label>
            <$view v-if="data" :value="data"/>
        </template>
    </el-select>
</template>
""".trim()
    }

    override fun stringifyEnumView(enum: GenEnumGenerateView): String {
        val itemTexts = buildString {
            enum.items.forEachIndexed { index, it ->
                if (index == 0) {
                    append("""    <el-text v-if="value === '${it.name}'">${it.comment}</el-text>""")
                } else {
                    append("\n" + """    <el-text v-else-if="value === '${it.name}'">${it.comment}</el-text>""")
                }
            }
        }

        return """
<script setup lang="ts">
import type {${enum.name}} from "@/api/__generated/model/enums"

defineProps<{
    value: ${enum.name}
}>()
</script>

<template>
$itemTexts
</template>
        """.trim()
    }

    @Throws(GenerateException::class)
    override fun stringifyTable(entity: GenEntityBusinessView): String {
        val listView = entity.dtoNames.listView

        val enums = entity.enums
        val enumImports = enums.joinToString("\n") {
            val (_, dir, _, _, view) = it.componentNames
            """import $view from "@/components/$dir/$view.vue""""
        }

        val idProperty =
            if (entity.idProperties.size != 1)
                throw GenerateException.idPropertyNotFound("entityName: ${entity.name}")
            else
                entity.idProperties[0]

        val idName = idProperty.name

        val propertyColumns = buildString {
            entity.properties.filter { !it.idProperty && it.associationType == null }.forEach {
                appendBlock(
                    if (it.enum != null) {
                        """
        <el-table-column label="${it.comment}" prop="${it.name}">
            <template #default="{row}">
                <${it.enum.componentNames.view} :value="row.${it.name}"/>
            </template>
        </el-table-column>
                         """
                    } else {
                        """
        <el-table-column label="${it.comment}" prop="${it.name}"/>
                        """
                    }.trimBlankLine()
                )
            }
        }

        return """
<script setup lang="ts">
import type {${listView}} from "@/api/__generated/model/static"
$enumImports

withDefaults(
    defineProps<{
        rows: Array<$listView>,
        idColumn?: boolean | undefined,
        indexColumn?: boolean | undefined,
        multiSelect?: boolean | undefined,
    }>(),
    {
        idColumn: false,
        indexColumn: true,
        multiSelect: true,
    }
)

const slots = defineSlots<{
	operations(props: {row: ${listView}, index: number}): any,
}>()

const emits = defineEmits<{
    (event: "changeSelection", selection: Array<${listView}>): void,
}>()

const handleChangeSelection = (selection: Array<${listView}>) => {
    emits("changeSelection", selection)
}
</script>

<template>
    <el-table 
        :data="rows" border stripe row-key="$idName"
        @selection-change="handleChangeSelection">
        <el-table-column v-if="idColumn" label="${idProperty.comment}" prop="${idProperty.name}"/>
        <el-table-column v-if="indexColumn" type="index"/>
        <el-table-column v-if="multiSelect" type="selection"/>
$propertyColumns        
        <el-table-column label="操作" width="330px">
            <template #default="scope">
                <slot name="operations" :row="scope.row as $listView" :index="scope.${'$'}index" />
            </template>
        </el-table-column>
    </el-table>
</template>
        """.trim()
    }

    private fun GenEntityBusinessView.enumSelectImports(forceNullable: Boolean = false) =
        properties.filter { it.enum != null }.joinToString("\n") {
            val (_, dir, select, nullableSelect) = it.enum!!.componentNames
            if (forceNullable || !it.typeNotNull) {
                """import $nullableSelect from "@/components/$dir/$nullableSelect.vue""""
            } else {
                """import $select from "@/components/$dir/$select.vue""""
            }
        }

    private const val MAX_NUMBER_SIZE = 15L

    private val GenEntityBusinessView.TargetOf_properties.inputNumberMax
        get() = buildString {
            if (column == null)
                append('0')
            else {
                for (i in 1..min(column.dataSize - column.numericPrecision, MAX_NUMBER_SIZE)) {
                    append('9')
                }
            }
        }

    private val GenEntityBusinessView.TargetOf_properties.inputMaxLength
        get() = buildString {
            if (column != null) {
                append(" maxlength=\"")
                append(column.dataSize)
                append("\"")
            }
        }

    private fun GenEntityBusinessView.queryFormItems(spec: String = "spec") =
        properties.filter { !it.idProperty && it.associationType == null }.map {
            val vModel = """v-model="${spec}.${it.name}""""
            val rangeVModel = """v-model="${it.name}Range""""

            it to when (it.queryType) {
                PropertyQueryType.ENUM_SELECT ->
                    """
<${it.enum!!.componentNames.nullableSelect}
    $vModel
    @change="emits('query')"
/>
"""

                PropertyQueryType.INT_RANGE ->
                    """
<el-input-number
    v-model.number="${spec}.min${it.name.replaceFirstChar { c -> c.uppercaseChar() }}"
    placeholder="请输入最小${it.comment}"
    :precision="0"
    :min="0"
    :max="${it.inputNumberMax}"
    :value-on-clear="undefined"
    @change="emits('query')"
/>
<el-input-number
    v-model.number="${spec}.max${it.name.replaceFirstChar { c -> c.uppercaseChar() }}"
    placeholder="请输入最大${it.comment}"
    :precision="0"
    :min="0"
    :max="${it.inputNumberMax}"
    :value-on-clear="undefined"
    @change="emits('query')"
/>
"""

                PropertyQueryType.FLOAT_RANGE ->
                    """
<el-input-number
    v-model.number="${spec}.min${it.name.replaceFirstChar { c -> c.uppercaseChar() }}"
    placeholder="请输入最小${it.comment}"
    :precision="${it.column?.numericPrecision ?: 0}" 
    :min="0"
    :max="${it.inputNumberMax}"
    :value-on-clear="undefined"
    @change="emits('query')"
/>
<el-input-number
    v-model.number="${spec}.max${it.name.replaceFirstChar { c -> c.uppercaseChar() }}"
    placeholder="请输入最大${it.comment}"
    :precision="${it.column?.numericPrecision ?: 0}" 
    :min="0"
    :max="${it.inputNumberMax}"
    :value-on-clear="undefined"
    @change="emits('query')"
/>
"""

                PropertyQueryType.TIME_RANGE ->
                    """
<el-time-picker
    $rangeVModel
    is-range
    start-placeholder="初始${it.comment}"
    end-placeholder="结束${it.comment}"
    unlink-panels
    clearable
    @change="emits('query')"
/>
"""

                PropertyQueryType.DATE_RANGE ->
                    """
<el-date-picker
    $rangeVModel
    type="daterange"
    start-placeholder="初始${it.comment}"
    end-placeholder="结束${it.comment}"
    unlink-panels
    clearable
    @change="emits('query')"
/>
"""

                PropertyQueryType.DATETIME_RANGE ->
                    """
<el-date-picker
    $rangeVModel
    type="datetimerange"
    start-placeholder="初始${it.comment}"
    end-placeholder="结束${it.comment}"
    unlink-panels
    clearable
    @change="emits('query')"
/>
"""

                else ->
                    when (it.formType) {
                        PropertyFormType.SWITCH ->
                            """
<el-select 
    $vModel
    placeholder="请选择${it.comment}"
    clearable
    @change="emits('query')">
    <el-option :value="true" label="是"/>
    <el-option :value="false" label="否"/>
</el-select>
"""

                        else ->
                            """
<el-input
    $vModel${it.inputMaxLength}
    placeholder="请输入${it.comment}"
    clearable
    @change="emits('query')"
/>
"""
                    }

            }.trimBlankLine()
        }.joinToString("\n") { (property, component) ->
            buildString {
                appendLine("""            <el-col :span="8">""")
                appendLine("""                <el-form-item prop="${property.name}" label="${property.comment}">""")
                appendBlock(component) { "                    $it" }
                appendLine("                </el-form-item>")
                appendLine("            </el-col>")
            }
        }

    override fun stringifyQueryForm(entity: GenEntityBusinessView): String {
        val spec = entity.dtoNames.spec

        val rangeComputed = entity.properties.mapNotNull {
            when (it.queryType) {
                PropertyQueryType.DATE_RANGE,
                PropertyQueryType.TIME_RANGE,
                PropertyQueryType.DATETIME_RANGE,
                ->
                    """
const ${it.name}Range = computed<[string | undefined, string | undefined]>({
    get() {
        return [
            spec.value.min${it.name.replaceFirstChar { c -> c.uppercaseChar() }},
            spec.value.max${it.name.replaceFirstChar { c -> c.uppercaseChar() }},
       ]
    },
    set(range: [string | undefined, string | undefined] | null) {
        spec.value.min${it.name.replaceFirstChar { c -> c.uppercaseChar() }} = range?.[0]
        spec.value.max${it.name.replaceFirstChar { c -> c.uppercaseChar() }} = range?.[1]
    }
})
                    """.trimBlankLine()

                else -> null
            }
        }

        val importComputed = if (rangeComputed.isNotEmpty()) "\nimport {computed} from \"vue\"" else ""

        return """
<script setup lang="ts">${importComputed}
import {Search} from "@element-plus/icons-vue"
import type {${spec}} from "@/api/__generated/model/static"
${entity.enumSelectImports(forceNullable = true)}

const spec = defineModel<${spec}>({
    required: true
})

const emits = defineEmits<{
    (event: "query"): void,
}>()

${rangeComputed.joinToString("\n")}
</script>

<template>
    <el-form>
        <el-row :gutter="20">
${entity.queryFormItems()}
            <el-button :icon="Search" type="primary" @click="emits('query')"/>
        </el-row>
    </el-form>
</template>
        """.trim()
    }

    @Throws(GenerateException::class)
    override fun stringifyDefaultAddInput(entity: GenEntityBusinessView) = buildString {
        val insertInput = entity.dtoNames.insertInput

        appendLine("""import type {${insertInput}} from "@/api/__generated/model/static"""")
        appendLine()

        appendLine("export default <${insertInput}> {")

        entity.baseProperties
            .filter { !it.idProperty && it.entityId == entity.id }
            .map {
                if (it.listType) {
                    "${it.name}: [],"
                } else if (it.enum != null) {
                    if (it.enum.defaultItems.size != 1)
                        throw GenerateException.defaultItemNotFound("enumName: ${it.enum.name}")

                    "${it.name}: \"${it.enum.defaultItems[0].name}\","
                } else {
                    "${it.name}: ${typeStrToTypeScriptDefault(it.type, it.typeNotNull)},"
                }
            }.forEach {
                appendLine("    $it")
            }

        entity.associationTargetOneProperties
            .filter { it.entityId == entity.id }
            .map {
                if (it.idView) {
                    if (it.listType) {
                        "${it.name}: [],"
                    } else {
                        "${it.name}: ${typeStrToTypeScriptDefault(it.type, it.typeNotNull)},"
                    }
                } else {
                    if (it.listType) {
                        "${it.name.toSingular()}Ids: [],"
                    } else {
                        val typeIdProperties = it.typeEntity?.idProperties
                            ?: throw GenerateException.entityNotFound("entityName: ${it.typeEntity?.name}")

                        val typeIdProperty =
                            if (typeIdProperties.size != 1)
                                throw GenerateException.idPropertyNotFound("entityName: ${it.typeEntity.name}")
                            else
                                typeIdProperties[0]

                        val type = typeStrToTypeScriptDefault(typeIdProperty.type, typeIdProperty.typeNotNull)
                        "${it.name}Id: $type,"
                    }
                }
            }.forEach {
                appendLine("    $it")
            }

        append("}")
    }

    private fun GenEntityBusinessView.formItems(
        formData: String = "formData",
        block: (property: GenEntityBusinessView.TargetOf_properties, component: String) -> String = { property, component ->
            buildString {
                val required = if (property.typeNotNull) " required" else ""

                appendLine("""        <el-form-item prop="${property.name}" label="${property.comment}"$required>""")
                appendBlock(component) { "            $it" }
                appendLine("        </el-form-item>")
            }
        },
    ) =
        properties
            .filter { !it.idProperty && it.associationType == null && it.entityId == id }
            .map {
                val vModel = """v-model="${formData}.${it.name}""""

                it to when (it.formType) {
                    PropertyFormType.ENUM ->
                        "<${if (it.typeNotNull) it.enum!!.componentNames.select else it.enum!!.componentNames.nullableSelect} $vModel/>"

                    PropertyFormType.SWITCH ->
                        if (it.typeNotNull)
                            "<el-switch $vModel/>"
                        else
                            """
<el-select 
    $vModel
    placeholder="请选择${it.comment}"
    clearable>
    <el-option :value="true" label="是"/>
    <el-option :value="false" label="否"/>
</el-select>
"""

                    PropertyFormType.INT ->
                        if (it.typeNotNull)
                            """
<el-input-number
    v-model.number="${formData}.${it.name}"
    placeholder="请输入${it.comment}"
    :precision="0" 
    :min="0"
    :max="${it.inputNumberMax}"
/>
"""
                        else
                            """
<el-input-number
    v-model.number="${formData}.${it.name}"
    placeholder="请输入${it.comment}"
    :precision="0" 
    :min="0"
    :max="${it.inputNumberMax}"
    :value-on-clear="undefined"
/>
"""

                    PropertyFormType.FLOAT ->
                        if (it.typeNotNull)
                            """
<el-input-number
    v-model.number="${formData}.${it.name}"
    placeholder="请输入${it.comment}"
    :precision="${it.column?.numericPrecision}" 
    :min="0"
    :max="${it.inputNumberMax}"
/>
"""
                        else
                            """
<el-input-number
    v-model.number="${formData}.${it.name}"
    placeholder="请输入${it.comment}"
    :precision="${it.column?.numericPrecision}" 
    :min="0"
    :max="${it.inputNumberMax}"
    :value-on-clear="undefined"
/>
"""


                    PropertyFormType.TIME ->
                        if (it.typeNotNull)
                            """
<el-time-picker
    $vModel
    placeholder="请选择${it.comment}"
/>
"""
                        else
                            """
<el-time-picker
    $vModel
    placeholder="请选择${it.comment}"
    clearable
    :empty-values="[undefined]"
    :value-on-clear="undefined"
/>
"""


                    PropertyFormType.DATE ->
                        if (it.typeNotNull)
                            """
<el-date-picker
    $vModel
    placeholder="请选择${it.comment}"
    type="date"
/>
"""
                        else
                            """
<el-date-picker
    $vModel
    placeholder="请选择${it.comment}"
    type="date"
    clearable
    :empty-values="[undefined]"
    :value-on-clear="undefined"
/>
"""

                    PropertyFormType.DATETIME ->
                        if (it.typeNotNull)
                            """
<el-date-picker
    $vModel
    placeholder="请选择${it.comment}"
    type="datetime"
/>
"""
                        else
                            """
<el-date-picker
    $vModel
    placeholder="请选择${it.comment}"
    type="datetime"
    clearable
    :empty-values="[undefined]"
    :value-on-clear="undefined"
/>
"""

                    else ->
                        """
<el-input
    $vModel${it.inputMaxLength}
    placeholder="请输入${it.comment}"
    clearable
/>
"""
                }.trimBlankLine()
            }
            .joinToString("\n") { block(it.first, it.second) }

    private fun List<GenEntityBusinessView.TargetOf_properties>.toRules() =
        joinToString("") {
            val items = mutableListOf<String>()

            if (it.typeNotNull) {
                items += "{required: true, message: '${it.comment}不能为空', trigger: 'blur'}"
            }

            if (it.listType) {
                items += "{type: 'array', message: '${it.comment}必须是列表', trigger: 'blur'}"
            } else {
                if (it.enum != null) {
                    // TODO 添加枚举型约束
                } else {
                    val formType = it.formType

                    when (formType) {
                        PropertyFormType.INT ->
                            items += "{type: 'integer', message: '${it.comment}必须是整数', trigger: 'blur'}"

                        PropertyFormType.FLOAT ->
                            items += "{type: 'number', message: '${it.comment}必须是数字', trigger: 'blur'}"

                        PropertyFormType.TIME,
                        PropertyFormType.DATE,
                        PropertyFormType.DATETIME,
                        ->
                            items += "{type: 'date', message: '${it.comment}必须是日期', trigger: 'blur'}"

                        PropertyFormType.SWITCH ->
                            items += "{type: 'boolean', message: '${it.comment}必须是对错', trigger: 'blur'"

                        else -> Unit
                    }

                    if (it.column != null) {
                        if (it.column.dataSize != 0L) {
                            when (formType) {
                                PropertyFormType.INT -> {
                                    val max = it.inputNumberMax
                                    items += "{type: 'integer', min: 1, max: ${max}, message: '${it.comment}需要在1-${max}之间', trigger: 'blur'}"
                                }
                                PropertyFormType.FLOAT -> {
                                    val max = it.inputNumberMax
                                    items += "{type: 'number', min: 1, max: ${max}, message: '${it.comment}需要在1-${max}之间', trigger: 'blur'}"
                                }
                                PropertyFormType.INPUT -> {
                                    val max = it.column.dataSize
                                    items += "{type: 'string', min: 1, max: ${max}, message: '${it.comment}长度需要在1-${max}之间', trigger: 'blur'}"
                                }
                                else -> Unit
                            }
                        }
                    }
                }
            }

            buildString {
                append("    ${it.name}: [")
                items.forEach { item ->
                    append("\n        $item,")
                }
                append("\n    ],\n")
            }
        }

    override fun stringifyAddFormRules(entity: GenEntityBusinessView): String {
        val (_, _, _, insertInput) = entity.dtoNames

        val propertyRules = entity.properties
            .filter { !it.idProperty && it.associationType == null && it.entityId == entity.id }
            .toRules()

        return """
import type {${insertInput}} from "@/api/__generated/model/static"
import type {FormRules} from 'element-plus'

export default <FormRules<${insertInput}>> {
$propertyRules
}
        """.trimBlankLine()
    }

    override fun stringifyAddForm(entity: GenEntityBusinessView): String {
        val dir = entity.componentNames.dir
        val defaultAddInput = entity.defaultAddInput()
        val (_, _, _, insertInput) = entity.dtoNames
        val (_, ruleDir, addFormRules) = entity.ruleNames

        return """
<script setup lang="ts">
import {ref} from "vue"
import type { FormInstance } from "element-plus"
import {cloneDeep} from "lodash"
import type {${insertInput}} from "@/api/__generated/model/static"
import defaultInput from "@/components/${dir}/${defaultAddInput}"
import rules from "@/rules/${ruleDir}/${addFormRules}"
${entity.enumSelectImports()}

const formRef = ref<FormInstance>()

const formData = ref<${insertInput}>(cloneDeep(defaultInput))

const emits = defineEmits<{
    (event: "submit", insertInput: ${insertInput}): void,
    (event: "cancel"): void,
}>()

const handleSubmit = () => {
    formRef.value?.validate(valid => {
        if (valid)
            emits('submit', formData.value)
    })
}
</script>

<template>
    <el-form ref="formRef" :rules="rules" :model="formData" inline-message>
${entity.formItems()}
        <div style="text-align: right;">
            <el-button type="info" @click="emits('cancel')" v-text="'取消'"/>
            <el-button type="primary" @click="handleSubmit" v-text="'提交'"/>
        </div>
    </el-form>
</template>
        """.trim()
    }

    override fun stringifyEditFormRules(entity: GenEntityBusinessView): String {
        val (_, _, _, _, updateInput) = entity.dtoNames

        val propertyRules = entity.properties
            .filter { it.associationType == null && it.entityId == entity.id }
            .toRules()

        return """
import type {${updateInput}} from "@/api/__generated/model/static"
import type {FormRules} from 'element-plus'

export default <FormRules<${updateInput}>> {
$propertyRules
}
        """.trimBlankLine()
    }

    override fun stringifyEditForm(entity: GenEntityBusinessView): String {
        val (_, _, _, _, updateInput) = entity.dtoNames
        val (_, ruleDir, _, editFormRules) = entity.ruleNames

        return """
<script setup lang="ts">
import {ref, watch} from "vue"
import type { FormInstance } from "element-plus"
import {cloneDeep} from "lodash"
import type {${updateInput}} from "@/api/__generated/model/static"
import rules from "@/rules/${ruleDir}/${editFormRules}"
${entity.enumSelectImports()}

const props = defineProps<{
    data: $updateInput
}>()

const formRef = ref<FormInstance>()

const formData = ref<${updateInput}>(cloneDeep(props.data))

watch(() => props.data, (data) => {
    formData.value = data
})

const emits = defineEmits<{
    (event: "submit", updateInput: ${updateInput}): void,
    (event: "cancel"): void,
}>()

const handleSubmit = () => {
    formRef.value?.validate(valid => {
        if (valid)
            emits('submit', formData.value)    
    })
}
</script>

<template>
    <el-form ref="formRef" :rules="rules" :model="formData" inline-message>
${entity.formItems()}
        <div style="text-align: right;">
            <el-button type="info" @click="emits('cancel')" v-text="'取消'"/>
            <el-button type="primary" @click="handleSubmit" v-text="'提交'"/>
        </div>
    </el-form>
</template>
        """.trim()
    }

    private val GenerateEntity.editTableType
        get() = dtoNames.insertInput

    override fun stringifyEditTableRules(entity: GenEntityBusinessView): String {
        val editTableType = entity.editTableType

        val propertyRules = entity.properties
            .filter { !it.idProperty && it.associationType == null && it.entityId == entity.id }
            .toRules()

        return """
import type {${editTableType}} from "@/api/__generated/model/static"
import type {FormRules} from 'element-plus'

export default <FormRules<${editTableType}>> {
$propertyRules
}
        """.trimBlankLine()
    }

    private const val ROW_INDEX = "${'$'}index"

    override fun stringifyEditTable(entity: GenEntityBusinessView): String {
        val lowerName = entity.name.replaceFirstChar { c -> c.lowercase() }
        val editTableType = entity.editTableType
        val defaultAddInput = entity.defaultAddInput()
        val rules = entity.ruleNames.editTableRules

        val tableColumns = entity.formItems("row") { property, component ->
            val indent = "            "

            buildString {
                appendLine("""$indent<el-table-column label="${property.comment}" prop="${property.name}">""")
                appendLine("""$indent    <template #default="{$ROW_INDEX, row}">""")
                appendLine("""$indent        <el-form-item :prop="[$ROW_INDEX, '${property.name}']" :rules="rules.${property.name}">""")
                appendBlock(component) { """$indent        $it""" }
                appendLine("""$indent        </el-form-item>""")
                appendLine("""$indent    </template>""")
                appendLine("""$indent</el-table-column>""")
            }
        }

        return """
<script setup lang="ts">
import {ref} from "vue"
import {deleteConfirm} from "@/utils/confirm"
import {cloneDeep} from "lodash"
import $defaultAddInput from "@/components/$lowerName/$defaultAddInput"
import type {$editTableType} from "@/api/__generated/model/static"
import type {FormInstance} from "element-plus"
import {Delete, Plus} from "@element-plus/icons-vue"
import rules from "@/rules/$lowerName/$rules"
import type {EditTableExpose} from "@/components/EditTable/EditTableExpose"

const formRef = ref<FormInstance | undefined>()

defineExpose<EditTableExpose>({formRef})

const rows = defineModel<$editTableType[]>({
    required: true
})

withDefaults(
    defineProps<{
        idColumn?: boolean | undefined,
        indexColumn?: boolean | undefined,
        multiSelect?: boolean | undefined,
    }>(),
    {
        idColumn: false,
        indexColumn: false,
        multiSelect: true,
    }
)

// 多选
const selection = ref<$editTableType[]>([])

const changeSelection = (item: $editTableType[]) => {
    selection.value = item
}

// 新增
const handleAdd = () => {
    rows.value.push(cloneDeep($defaultAddInput))
}

// 删除
const handleBatchDelete = async () => {
    const result = await deleteConfirm('这些${entity.comment}')
    if (!result) return
    rows.value = rows.value.filter(it => !selection.value.includes(it))
}

const handleSingleDelete = async (index: number) => {
    const result = await deleteConfirm('该${entity.comment}')
    if (!result) return
    rows.value = rows.value.filter((_, i) => i !== index)
}
</script>

<template>
    <el-form ref="formRef" :rules="rules" :model="rows" inline-message>
        <el-divider content-position="center">${entity.comment}信息</el-divider> 
        <el-row :gutter="10" class="mb8">
            <el-col :span="1.5">
                <el-button type="primary" :icon="Plus" @click="handleAdd" v-text="'添加'"/>
            </el-col>
            <el-col :span="1.5">
                <el-button type="danger" :icon="Delete" @click="handleBatchDelete" v-text="'删除'" :disabled="selection.length === 0"/>
            </el-col>
        </el-row>
        <el-table :data="rows" border stripe row-key="id" @selection-change="changeSelection">
            <el-table-column v-if="idColumn" label="ID" prop="id" fixed/>
            <el-table-column v-if="indexColumn" type="index" fixed/>
            <el-table-column v-if="multiSelect" type="selection" fixed/>
$tableColumns
            <el-table-column label="操作" fixed="right">
                <template #default="{$ROW_INDEX}">
                    <el-button type="danger" :icon="Delete" @click="handleSingleDelete($ROW_INDEX)"/>
                </template>
            </el-table-column>
        </el-table>
    </el-form>
</template>

        """.trimBlankLine()
    }

    @Throws(GenerateException::class)
    override fun stringifyPage(entity: GenEntityBusinessView): String {
        val (_, dir, table, addForm, editForm, queryForm) = entity.componentNames
        val (_, listView, _, insertInput, updateInput, spec) = entity.dtoNames

        val serviceName = entity.serviceName.replaceFirstChar { it.lowercase() }

        val idProperty =
            if (entity.idProperties.size != 1)
                throw GenerateException.idPropertyNotFound("entityName: ${entity.name}")
            else
                entity.idProperties[0]
        val idName = idProperty.name
        val idType = typeStrToTypeScriptType(idProperty.type, idProperty.typeNotNull)

        return """
<script setup lang="ts">
import {ref} from "vue"
import {Plus, EditPen, Delete} from "@element-plus/icons-vue"
import type {Page, PageQuery, ${spec}, ${listView}, ${insertInput}, ${updateInput}} from "@/api/__generated/model/static"
import {api} from "@/api"
import {sendMessage} from "@/utils/message"
import {deleteConfirm} from "@/utils/confirm"
import {useLoading} from "@/utils/loading"
import {useLegalPage} from "@/utils/legalPage"
import $table from "@/components/$dir/$table.vue"
import $addForm from "@/components/$dir/$addForm.vue"
import $editForm from "@/components/$dir/$editForm.vue"
import $queryForm from "@/components/$dir/$queryForm.vue"

const {isLoading, withLoading} = useLoading()

const pageData = ref<Page<${listView}>>()

// 查询
const queryInfo = ref<PageQuery<${spec}>>({
    spec: {},
    pageIndex: 1,
    pageSize: 5
})

const {queryPage} = useLegalPage(
    pageData,
    queryInfo,
    withLoading(api.${serviceName}.page)
)

const get${entity.name} = withLoading((id: $idType) => api.${serviceName}.get({id}))

const add${entity.name} = withLoading((body: $insertInput) => api.${serviceName}.insert({body}))

const edit${entity.name} = withLoading((body: $updateInput) => api.${serviceName}.update({body}))

const delete${entity.name} = withLoading((ids: Array<$idType>) => api.${serviceName}.delete({ids}))

// 多选
const selection = ref<${listView}[]>([])

const changeSelection = (item: ${listView}[]) => {
    selection.value = item
}

// 新增
const addDialogVisible = ref(false)

const startAdd = () => {
    addDialogVisible.value = true
}

const submitAdd = async (insertInput: ${insertInput}) => {
    try {
        await add${entity.name}(insertInput)
        await queryPage()
        addDialogVisible.value = false
        
        sendMessage('新增${entity.comment}成功', 'success')
    } catch (e) {
        sendMessage('新增${entity.comment}失败', 'error', e)
    }
}

const cancelAdd = () => {
    addDialogVisible.value = false
}

// 修改
const editDialogVisible = ref(false)

const updateInput = ref<${updateInput} | undefined>(undefined)

const startEdit = async (id: ${idType}) => {
    updateInput.value = await get${entity.name}(id)
    if (updateInput.value === undefined) {
        sendMessage('编辑的${entity.comment}不存在', 'error')
        return
    }
    editDialogVisible.value = true
}

const submitEdit = async (updateInput: ${updateInput}) => {
    try {
        await edit${entity.name}(updateInput)
        await queryPage()
        editDialogVisible.value = false
        
        sendMessage('编辑${entity.comment}成功', 'success')
    } catch (e) {
        sendMessage('编辑${entity.comment}失败', 'error', e)
    }
}

const cancelEdit = () => {
    editDialogVisible.value = false
    updateInput.value = undefined
}

// 删除
const handleDelete = async (ids: ${idType}[]) => {
    const result = await deleteConfirm('该${entity.comment}')
    if (!result) return

    try {
        await delete${entity.name}(ids)
        await queryPage()
        
        sendMessage('删除${entity.comment}成功', 'success')
    } catch (e) {
        sendMessage('删除${entity.comment}失败', 'error', e)
    }
}
</script>

<template>
    <el-breadcrumb separator=">">
        <el-breadcrumb-item :to="{ path: '/index' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>${entity.comment}管理</el-breadcrumb-item>
    </el-breadcrumb>

    <el-card v-loading="isLoading">
        <$queryForm v-model="queryInfo.spec" @query="queryPage"/>
        
        <div>
            <el-button type="primary" :icon="Plus" @click="startAdd" v-text="'新增'"/>
            <el-button type="danger" :icon="Delete" @click="handleDelete(selection.map(it => it.${idName}))" 
                       v-text="'删除'" :disabled="selection.length === 0"/>
        </div>

        <template v-if="pageData">
            <$table :rows="pageData.rows" @changeSelection="changeSelection">
                <template #operations="{row}">
                    <el-button type="warning" :icon="EditPen" @click="startEdit(row.${idName})" v-text="'编辑'"/>
                    <el-button type="danger" :icon="Delete" @click="handleDelete([row.${idName}])" v-text="'删除'"/>                
                </template>
            </$table>

            <el-pagination
                v-model:current-page="queryInfo.pageIndex"
                v-model:page-size="queryInfo.pageSize"
                :page-sizes="[5, 10, 20]"
                layout="total, sizes, prev, pager, next, jumper"
                :total="pageData.totalRowCount"
            />
        </template>
    </el-card>
    
    <el-dialog v-model="addDialogVisible" destroy-on-close>
        <$addForm @submit="submitAdd" @cancel="cancelAdd"/>
    </el-dialog>
    
    <el-dialog v-model="editDialogVisible" destroy-on-close>
        <$editForm v-if="updateInput !== undefined" :data="updateInput" @submit="submitEdit" @cancel="cancelEdit"/>
    </el-dialog>
</template>
    """.trim()
    }

    override fun stringifySingleSelect(entity: GenEntityBusinessView): String {
        val (_, dir, table, _, _, queryForm) = entity.componentNames
        val (_, listView, _, _, _, spec) = entity.dtoNames

        val serviceName = entity.serviceName.replaceFirstChar { it.lowercase() }

        return """
<script setup lang="ts">
import {ref} from "vue"
import type {Page, PageQuery, ${spec}, ${listView}} from "@/api/__generated/model/static"
import {api} from "@/api"
import {useLoading} from "@/utils/loading"
import {useLegalPage} from "@/utils/legalPage"
import $table from "@/components/$dir/$table.vue"
import $queryForm from "@/components/$dir/$queryForm.vue"

const {isLoading, withLoading} = useLoading()

const pageData = ref<Page<${listView}>>()

const queryInfo = ref<PageQuery<${spec}>>({
    spec: {},
    pageIndex: 1,
    pageSize: 5
})

const {queryPage} = useLegalPage(
    pageData,
    queryInfo,
    withLoading(api.${serviceName}.page)
)

const emits = defineEmits<{
    (event: "select", item: $listView): void,
}>()

const handleSelect = (item: $listView) => {
    emits("select", item)
}
</script>

<template>
    <el-form v-loading="isLoading">
        <$queryForm v-model="queryInfo.spec" @query="queryPage"/>
    
        <template v-if="pageData">
            <$table :rows="pageData.rows" :multi-select="false">
                <template #operations="{row}">
                    <el-button type="warning" @click="handleSelect(row)" v-text="'选择'"/>
                </template>
            </$table>
            
            <el-pagination
                v-model:current-page="queryInfo.pageIndex"
                v-model:page-size="queryInfo.pageSize"
                :page-sizes="[5, 10, 20]"
                layout="total, sizes, prev, pager, next, jumper"
                :total="pageData.totalRowCount"
            />
        </template>
    </el-form>
</template>
""".trim()
    }

    @Throws(GenerateException::class)
    override fun stringifyMultiSelect(entity: GenEntityBusinessView): String {
        val (_, dir, table, _, _, queryForm) = entity.componentNames
        val (_, listView, _, _, _, spec) = entity.dtoNames

        val serviceName = entity.serviceName.replaceFirstChar { it.lowercase() }

        val idProperty =
            if (entity.idProperties.size != 1)
                throw GenerateException.idPropertyNotFound("entityName: ${entity.name}")
            else
                entity.idProperties[0]
        val idName = idProperty.name
        val idType = typeStrToTypeScriptType(idProperty.type, idProperty.typeNotNull)

        return """
<script setup lang="ts">
import {ref} from "vue"
import type {Page, PageQuery, ${spec}, ${listView}} from "@/api/__generated/model/static"
import {api} from "@/api"
import {useLoading} from "@/utils/loading"
import {useLegalPage} from "@/utils/legalPage"
import $table from "@/components/$dir/$table.vue"
import $queryForm from "@/components/$dir/$queryForm.vue"

const {isLoading, withLoading} = useLoading()

const pageData = ref<Page<${listView}>>()

const queryInfo = ref<PageQuery<${spec}>>({
    spec: {},
    pageIndex: 1,
    pageSize: 5
})

const {queryPage} = useLegalPage(
    pageData,
    queryInfo,
    withLoading(api.${serviceName}.page)
)

const emits = defineEmits<{
    (event: "submit", selection: Array<$listView>): void,
    (event: "cancel"): void,
}>()

const selectMap = ref<Map<${idType}, ${listView}>>(new Map)

const handleSelect = (item: $listView) => {
    selectMap.value.set(item.$idName, item)
}

const handleUnSelect = (item: $listView) => {
    selectMap.value.delete(item.$idName)
}
</script>

<template>
    <el-form v-loading="isLoading">
        <$queryForm v-model="queryInfo.spec" @query="queryPage"/>
    
        <template v-if="pageData">
            <$table :rows="pageData.rows" :multi-select="false">
                <template #operations="{row}">
                    <el-button v-if="!selectMap.has(row.${idName})" type="warning" @click="handleSelect(row)" v-text="'选择'"/>
                    <el-button v-else type="warning" @click="handleUnSelect(row)" v-text="'取消'"/>
                </template>
            </$table>
            
            <el-pagination
                v-model:current-page="queryInfo.pageIndex"
                v-model:page-size="queryInfo.pageSize"
                :page-sizes="[5, 10, 20]"
                layout="total, sizes, prev, pager, next, jumper"
                :total="pageData.totalRowCount"
            />
        </template>
        
        <div style="text-align: right;">
            <el-button type="info" @click="emits('cancel')" v-text="'取消'"/>
            <el-button type="primary" @click="emits('submit', [...selectMap.values()])" v-text="'提交'"/>
        </div>
    </el-form>
</template>
""".trim()
    }

    private val GenEntityBusinessView.selectOptionLabelType: String?
        get() {
            val nameSet = properties
                .filter { !it.idProperty && it.associationType == null && it.entityId == id }
                .map { it.name }.toSet()

            if ("name" in nameSet) {
                return "name"
            } else if ("title" in nameSet) {
                return "title"
            } else if ("label" in nameSet) {
                return "label"
            }

            return null
        }

    override fun stringifyIdSelect(entity: GenEntityBusinessView): String {
        val idProperty =
            if (entity.idProperties.size != 1)
                throw GenerateException.idPropertyNotFound("entityName: ${entity.name}")
            else
                entity.idProperties[0]
        val idName = idProperty.name
        val idType = typeStrToTypeScriptType(idProperty.type, idProperty.typeNotNull)

        val listView = entity.dtoNames.listView

        val label = entity.selectOptionLabelType ?: idName

        return """
<script setup lang="ts">
import {watch} from "vue"
import type {$listView} from "@/api/__generated/model/static"

const modelValue = defineModel<$idType | undefined>({
    required: true
})

const props = defineProps<{
    options: Array<$listView>
}>()

watch(() => [modelValue.value, props.options], () => {
    if (!(props.options.map(it => it.id) as Array<number | undefined>).includes(modelValue.value)) {
        modelValue.value = undefined
    }
}, {immediate: true})
</script>

<template>
    <el-select
        v-model="modelValue"
        filterable
        clearable
        placeholder="请选择${entity.comment}">
        <el-option
            v-for="option in options"
            :key="option.$idName"
            :label="option.$label"
            :value="option.$idName"/>
    </el-select>
</template>
        """.trimBlankLine()
    }

    override fun stringifyIdMultiSelect(entity: GenEntityBusinessView): String {
        val idProperty =
            if (entity.idProperties.size != 1)
                throw GenerateException.idPropertyNotFound("entityName: ${entity.name}")
            else
                entity.idProperties[0]
        val idName = idProperty.name
        val idType = typeStrToTypeScriptType(idProperty.type, idProperty.typeNotNull)

        val listView = entity.dtoNames.listView

        val label = entity.selectOptionLabelType ?: idName

        return """
<script setup lang="ts">
import type {$listView} from "@/api/__generated/model/static"

const modelValue = defineModel<$idType[]>({
    required: true
})

defineProps<{
    options: Array<$listView>
}>()
</script>

<template>
    <el-select
        v-model="modelValue"
        multiple
        filterable
        clearable
        collapse-tags
        collapse-tags-tooltip
        placeholder="请选择${entity.comment}">
        <el-option
            v-for="option in options"
            :key="option.$idName"
            :label="option.$label"
            :value="option.$idName"/>
    </el-select>
</template>
        """.trimBlankLine()
    }
}