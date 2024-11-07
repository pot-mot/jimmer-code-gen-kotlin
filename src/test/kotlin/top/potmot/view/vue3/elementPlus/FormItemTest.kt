package top.potmot.view.vue3.elementPlus

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import top.potmot.core.business.view.generate.builder.vue3.Vue3ComponentBuilder
import top.potmot.core.business.view.generate.impl.vue3elementPlus.formItem.FormItem
import top.potmot.entity.dto.GenEntityBusinessView
import java.time.LocalDateTime

class FormItemTest : FormItem {
    private val formData = "formData"

    private val disabled = false

    private val baseProperty = GenEntityBusinessView.TargetOf_properties(
        createdTime = LocalDateTime.now(),
        modifiedTime = LocalDateTime.now(),
        name = "name",
        comment = "comment",
        type = "type",
        remark = "remark",
        typeNotNull = true,
    )

    private val builder = Vue3ComponentBuilder()

    private val GenEntityBusinessView.TargetOf_properties.result: String
        get() = createFormItem(formData, disabled).children.let {
            var result: String
            builder.apply {
                result = it.stringifyElements()
            }
            result
        }

    @Test
    fun `test base input`() {
        assertEquals(
            """
<el-input
    v-model="formData.name"
    placeholder="请输入comment"
    clearable
/>
            """.trimIndent(),
            baseProperty.result,
        )
    }

    @Test
    fun `test time`() {
        assertEquals(
            """
<el-time-picker
    v-model="formData.name"
    value-format="HH:mm:ss"
    placeholder="请选择comment"
    clearable
/>
            """.trimIndent(),
            baseProperty.copy(type = "java.time.LocalTime").result,
        )
    }

    @Test
    fun `test date` () {
        assertEquals(
            """
<el-date-picker
    v-model="formData.name"
    type="date"
    value-format="YYYY-MM-DD"
    placeholder="请选择comment"
    clearable
/>
            """.trimIndent(),
            baseProperty.copy(type = "java.time.LocalDate").result,
        )
    }

    @Test
    fun `test datetime` () {
        assertEquals(
            """
<el-date-picker
    v-model="formData.name"
    type="datetime"
    value-format="YYYY-MM-DDTHH:mm:ss"
    placeholder="请选择comment"
    clearable
/>
            """.trimIndent(),
            baseProperty.copy(type = "java.time.LocalDateTime").result,
        )
    }

    @Test
    fun `test switch` () {
        assertEquals(
            """
<el-switch
    v-model="formData.name"
/>
            """.trimIndent(),
            baseProperty.copy(type = "kotlin.Boolean").result,
        )

        assertEquals(
            """
<el-select
    v-model="formData.name"
    placeholder="请选择"
    clearable
>
    <el-option
        :value="true"
        label="是"
    />
    <el-option
        :value="false"
        label="否"
    />
</el-select>
            """.trimIndent(),
            baseProperty.copy(type = "kotlin.Boolean", typeNotNull = false).result,
        )
    }

}
