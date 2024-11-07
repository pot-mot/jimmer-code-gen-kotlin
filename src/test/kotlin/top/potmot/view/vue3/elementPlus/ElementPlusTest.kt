package top.potmot.view.vue3.elementPlus

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import top.potmot.core.business.view.generate.builder.vue3.Vue3ComponentBuilder
import top.potmot.core.business.view.generate.builder.vue3.PropBind
import top.potmot.core.business.view.generate.builder.vue3.Element
import top.potmot.core.business.view.generate.builder.vue3.componentLib.ElementPlus
import top.potmot.utils.string.trimBlankLine

class ElementPlusTest : ElementPlus {
    private val builder = Vue3ComponentBuilder()
    
    @Test
    fun testInput() {
        val result = input(
            modelValue = "testModel",
            comment = "testComment",
            placeholder = { "testPlaceholder" },
            clearable = false
        )

        builder.apply {
            assertEquals(
                """
<el-input
    v-model="testModel"
    placeholder="testPlaceholder"
/>
                """.trimBlankLine(),
                listOf(result).stringifyElements()
            )
        }
    }

    @Test
    fun testInputNumber() {
        val result = inputNumber(
            modelValue = "testModel",
            comment = "testComment",
            placeholder = { "testPlaceholder" },
            precision = 2,
            min = 0.0,
            max = 100.0,
            valueOnClear = "null"
        )

        builder.apply {
            assertEquals(
                """
<el-input-number
    v-model.number="testModel"
    placeholder="testPlaceholder"
    :precision="2"
    :min="0.0"
    :max="100.0"
    :value-on-clear="null"
/>
                """.trimBlankLine(),
                listOf(result).stringifyElements()
            )
        }
    }

    @Test
    fun testSwitch() {
        val result = switch(modelValue = "testModel")

        builder.apply {
            assertEquals(
                "<el-switch v-model=\"testModel\"/>",
                listOf(result).stringifyElements()
            )
        }
    }

    @Test
    fun testSelect() {
        val result = select(
            modelValue = "testModel",
            comment = "testComment",
            placeholder = { "testPlaceholder" },
            clearable = true
        )

        builder.apply {
            assertEquals(
                """
<el-select
    v-model="testModel"
    placeholder="testPlaceholder"
    clearable
/>
                """.trimBlankLine(),
                listOf(result).stringifyElements()
            )
        }
    }

    @Test
    fun testOption() {
        val result = options(
            options = "testOptions",
            option = "testItem",
            key = { "$it.key" },
            value = { "$it.value" },
            label = { "$it.label" }
        )

        builder.apply {
            assertEquals(
                """
<el-option
    v-for="testItem in testOptions"
    :key="testItem.key"
    :value="testItem.value"
    :label="testItem.label"
/>
                """.trimBlankLine(),
                listOf(result).stringifyElements()
            )
        }
    }

    @Test
    fun testTimePicker() {
        val result = timePicker(
            modelValue = "testModel",
            valueFormat = "HH:mm:ss",
            comment = "testComment",
            placeholder = { "testPlaceholder" },
            clearable = false
        )

        builder.apply {
            assertEquals(
                """
<el-time-picker
    v-model="testModel"
    value-format="HH:mm:ss"
    placeholder="testPlaceholder"
/>
                """.trimBlankLine(),
                listOf(result).stringifyElements()
            )
        }
    }

    @Test
    fun testDatePicker() {
        val result = datePicker(
            modelValue = "testModel",
            valueFormat = "YYYY-MM-DD",
            comment = "testComment",
            placeholder = { "testPlaceholder" },
            clearable = true
        )

        builder.apply {
            assertEquals(
                """
<el-date-picker
    v-model="testModel"
    type="date"
    value-format="YYYY-MM-DD"
    placeholder="testPlaceholder"
    clearable
/>
                """.trimBlankLine(),
                listOf(result).stringifyElements()
            )
        }
    }

    @Test
    fun testDateTimePicker() {
        val result = dateTimePicker(
            modelValue = "testModel",
            valueFormat = "YYYY-MM-DDTHH:mm:ss",
            comment = "testComment",
            placeholder = { "testPlaceholder" },
            clearable = false
        )

        builder.apply {
            assertEquals(
                """
<el-date-picker
    v-model="testModel"
    type="datetime"
    value-format="YYYY-MM-DDTHH:mm:ss"
    placeholder="testPlaceholder"
/>
                """.trimBlankLine(),
                listOf(result).stringifyElements()
            )
        }
    }

    @Test
    fun testTimePickerRange() {
        val result = timePickerRange(
            modelValue = "testModel",
            valueFormat = "HH:mm:ss",
            comment = "testComment",
            startPlaceholder = { "testStartPlaceholder" },
            endPlaceholder = { "testEndPlaceholder" },
            clearable = false
        )

        builder.apply {
            assertEquals(
                """
<el-time-picker
    v-model="testModel"
    value-format="HH:mm:ss"
    is-range
    unlink-panels
    start-placeholder="testStartPlaceholder"
    end-placeholder="testEndPlaceholder"
/>
                """.trimBlankLine(),
                listOf(result).stringifyElements()
            )
        }
    }

    @Test
    fun testDatePickerRange() {
        val result = datePickerRange(
            modelValue = "testModel",
            valueFormat = "YYYY-MM-DD",
            comment = "testComment",
            startPlaceholder = { "testStartPlaceholder" },
            endPlaceholder = { "testEndPlaceholder" },
            clearable = false
        )

        builder.apply {
            assertEquals(
                """
<el-date-picker
    v-model="testModel"
    type="date"
    value-format="YYYY-MM-DD"
    is-range
    unlink-panels
    start-placeholder="testStartPlaceholder"
    end-placeholder="testEndPlaceholder"
/>
                """.trimBlankLine(),
                listOf(result).stringifyElements()
            )
        }
    }

    @Test
    fun testDateTimePickerRange() {
        val result = dateTimePickerRange(
            modelValue = "testModel",
            valueFormat = "YYYY-MM-DDTHH:mm:ss",
            comment = "testComment",
            startPlaceholder = { "testStartPlaceholder" },
            endPlaceholder = { "testEndPlaceholder" },
            clearable = false
        )

        builder.apply {
            assertEquals(
                """
<el-date-picker
    v-model="testModel"
    type="datetime"
    value-format="YYYY-MM-DDTHH:mm:ss"
    is-range
    unlink-panels
    start-placeholder="testStartPlaceholder"
    end-placeholder="testEndPlaceholder"
/>
                """.trimBlankLine(),
                listOf(result).stringifyElements()
            )
        }
    }

    @Test
    fun testFormItem() {
        val result = formItem(
            prop = "testProp",
            label = "testLabel",
            rule = "testRule",
            content = listOf(Element("div", content = "表单项"))
        )

        builder.apply {
            assertEquals(
                """
<el-form-item
    prop="testProp"
    label="testLabel"
    :rule="testRule"
>
    <div>表单项</div>
</el-form-item>
                """.trimBlankLine(),
                listOf(result).stringifyElements()
            )
        }
    }

    @Test
    fun testForm() {
        val items = listOf(
            Element("el-form-item", props = listOf(PropBind("prop", "testProp")))
        )
        val result = form(
            model = "testModel",
            rules = "testRules",
            items = items
        )

        builder.apply {
            assertEquals(
                """
<el-form
    :model="testModel"
    :rules="testRules"
>
    <el-form-item :prop="testProp"/>
</el-form>
                """.trimBlankLine(),
                listOf(result).stringifyElements()
            )
        }
    }

    @Test
    fun testTableColumn() {
        val content = listOf(
            Element("div", props = listOf(PropBind("class", "testClass", isLiteral = true)))
        )
        val result = tableColumn(
            prop = "testProp",
            label = "testLabel",
            content = content
        )

        builder.apply {
            assertEquals(
                """
<el-table-column
    prop="testProp"
    label="testLabel"
>
    <template #default="scope">
        <div class="testClass"/>
    </template>
</el-table-column>
                """.trimBlankLine(),
                listOf(result).stringifyElements()
            )
        }
    }

    @Test
    fun testTable() {
        val columns = listOf(
            Element("el-table-column", props = listOf(PropBind("prop", "testProp")))
        )
        val result = table(
            data = "testData",
            border = true,
            stripe = false,
            columns = columns
        )

        builder.apply {
            assertEquals(
                """
<el-table
    :data="testData"
    border
>
    <el-table-column :prop="testProp"/>
</el-table>
                """.trimBlankLine(),
                listOf(result).stringifyElements()
            )
        }
    }

    @Test
    fun testDialog() {
        val content = listOf(
            Element("div", props = listOf(PropBind("class", "testClass", isLiteral = true)))
        )
        val result = dialog(
            modelValue = "testModel",
            destroyOnClose = true,
            closeOnClickModal = false,
            content = content
        )

        builder.apply {
            assertEquals(
                """
<el-dialog
    v-model="testModel"
    destroy-on-close
    :close-on-click-modal="false"
>
    <div class="testClass"/>
</el-dialog>
                """.trimBlankLine(),
                listOf(result).stringifyElements()
            )
        }
    }
}
