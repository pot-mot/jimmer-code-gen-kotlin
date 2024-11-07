package top.potmot.view.vue3.elementPlus

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import top.potmot.core.business.view.generate.builder.vue3.Element
import top.potmot.core.business.view.generate.builder.vue3.PropBind
import top.potmot.core.business.view.generate.builder.vue3.Vue3ComponentBuilder
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
            content = items
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

    @Test
    fun testText() {
        val element = text("Hello, World!")
        builder.apply {
            assertEquals(
                """
<el-text>Hello, World!</el-text>
                """.trimIndent(),
                listOf(element).stringifyElements()
            )
        }

        val elementWithType = text("Hello, World!", type = ElementPlus.Type.PRIMARY)
        builder.apply {
            assertEquals(
                """
<el-text type="primary">Hello, World!</el-text>
                """.trimIndent(),
                listOf(elementWithType).stringifyElements()
            )
        }
    }

    @Test
    fun testCol() {
        val element = col(content = listOf(text("Content")))
        builder.apply {
            assertEquals(
                """
<el-col :span="24">
    <el-text>Content</el-text>
</el-col>
                """.trimIndent(),
                listOf(element).stringifyElements()
            )
        }

        val elementWithOffset = col(span = 12, offset = 3, content = listOf(text("Content")))
        builder.apply {
            assertEquals(
                """
<el-col
    :span="12"
    :offset="3"
>
    <el-text>Content</el-text>
</el-col>
                """.trimIndent(),
                listOf(elementWithOffset).stringifyElements()
            )
        }
    }

    @Test
    fun testRow() {
        val element = row(content = listOf(col(content = listOf(text("Content")))))
        builder.apply {
            assertEquals(
                """
<el-row>
    <el-col :span="24">
        <el-text>Content</el-text>
    </el-col>
</el-row>
                """.trimIndent(),
                listOf(element).stringifyElements()
            )
        }

        val elementWithGutter = row(gutter = 10, content = listOf(col(content = listOf(text("Content")))))
        builder.apply {
            assertEquals(
                """
<el-row :gutter="10">
    <el-col :span="24">
        <el-text>Content</el-text>
    </el-col>
</el-row>
                """.trimIndent(),
                listOf(elementWithGutter).stringifyElements()
            )
        }
    }

    @Test
    fun testButton() {
        val element = button(content = "Click Me")
        builder.apply {
            assertEquals(
                """
<el-button>Click Me</el-button>
                """.trimIndent(),
                listOf(element).stringifyElements()
            )
        }

        val elementWithDisabled = button(content = "Click Me", disabled = true)
        builder.apply {
            assertEquals(
                """
<el-button disabled>Click Me</el-button>
                """.trimIndent(),
                listOf(elementWithDisabled).stringifyElements()
            )
        }

        val elementWithType = button(content = "Click Me", type = ElementPlus.Type.WARNING)
        builder.apply {
            assertEquals(
                """
<el-button type="warning">Click Me</el-button>
                """.trimIndent(),
                listOf(elementWithType).stringifyElements()
            )
        }

        val elementWithIcon = button(content = "Click Me", icon = "Search")
        builder.apply {
            assertEquals(
                """
<el-button :icon="Search">Click Me</el-button>
                """.trimIndent(),
                listOf(elementWithIcon).stringifyElements()
            )
        }

        val elementWithPlain = button(content = "Click Me", plain = true)
        builder.apply {
            assertEquals(
                """
<el-button plain>Click Me</el-button>
                """.trimIndent(),
                listOf(elementWithPlain).stringifyElements()
            )
        }

        val elementWithLink = button(content = "Click Me", link = true)
        builder.apply {
            assertEquals(
                """
<el-button link>Click Me</el-button>
                """.trimIndent(),
                listOf(elementWithLink).stringifyElements()
            )
        }

        val elementWithRound = button(content = "Click Me", round = true)
        builder.apply {
            assertEquals(
                """
<el-button round>Click Me</el-button>
                """.trimIndent(),
                listOf(elementWithRound).stringifyElements()
            )
        }

        val elementWithCircle = button(content = "Click Me", circle = true)
        builder.apply {
            assertEquals(
                """
<el-button circle>Click Me</el-button>
                """.trimIndent(),
                listOf(elementWithCircle).stringifyElements()
            )
        }

        val elementWithAllProps = button(
            content = "Click Me",
            disabled = true,
            type = ElementPlus.Type.INFO,
            icon = "Search",
            plain = true,
            link = true,
            round = true,
            circle = true
        )
        builder.apply {
            assertEquals(
                """
<el-button
    type="info"
    :icon="Search"
    plain
    link
    round
    circle
    disabled
>Click Me</el-button>
                """.trimIndent(),
                listOf(elementWithAllProps).stringifyElements()
            )
        }
    }
}
