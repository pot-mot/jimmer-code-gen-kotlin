package top.potmot.view.vue3.elementPlus

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import top.potmot.core.business.view.generate.builder.vue3.Vue3ComponentBuilder
import top.potmot.core.business.view.generate.impl.vue3elementPlus.queryFormItem.QueryFormItem
import top.potmot.entity.dto.GenEntityBusinessView
import top.potmot.entity.dto.GenEntityBusinessView.TargetOf_properties.TargetOf_column
import top.potmot.entity.dto.GenEntityBusinessView.TargetOf_properties.TargetOf_enum
import java.time.LocalDateTime

class QueryFormItemTest : QueryFormItem {
    private val spec = "spec"

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
        get() = createQueryFormItem(spec).children.let {
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
    v-model="spec.name"
    placeholder="请输入comment"
    clearable
    @change="emits('query')"
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
    v-model="nameRange"
    value-format="HH:mm:ss"
    is-range
    unlink-panels
    start-placeholder="请选择开始comment"
    end-placeholder="请选择结束comment"
    clearable
    @change="emits('query')"
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
    v-model="nameRange"
    type="date"
    value-format="YYYY-MM-DD"
    is-range
    unlink-panels
    start-placeholder="请选择开始comment"
    end-placeholder="请选择结束comment"
    clearable
    @change="emits('query')"
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
    v-model="nameRange"
    type="datetime"
    value-format="YYYY-MM-DDTHH:mm:ss"
    is-range
    unlink-panels
    start-placeholder="请选择开始comment"
    end-placeholder="请选择结束comment"
    clearable
    @change="emits('query')"
/>
            """.trimIndent(),
            baseProperty.copy(type = "java.time.LocalDateTime").result,
        )
    }

    @Test
    fun `test switch` () {
        assertEquals(
            """
<el-select
    v-model="spec.name"
    placeholder="请选择comment"
    clearable
    @change="emits('query')"
>
    <el-option
        :value="false"
        :label="否"
    />
    <el-option
        :value="true"
        :label="是"
    />
</el-select>
            """.trimIndent(),
            baseProperty.copy(type = "kotlin.Boolean").result,
        )
    }

    @Test
    fun `test int` () {
        assertEquals(
            """
<el-input-number
    v-model.number="spec.minName"
    placeholder="请输入最小comment"
    :precision="0"
    :value-on-clear="undefined"
    @change="emits('query')"
/>
<el-input-number
    v-model.number="spec.maxName"
    placeholder="请输入最大comment"
    :precision="0"
    :value-on-clear="undefined"
    @change="emits('query')"
/>
            """.trimIndent(),
            baseProperty.copy(type = "kotlin.Int").result,
        )

        assertEquals(
            """
<el-input-number
    v-model.number="spec.minName"
    placeholder="请输入最小comment"
    :precision="0"
    :value-on-clear="undefined"
    @change="emits('query')"
/>
<el-input-number
    v-model.number="spec.maxName"
    placeholder="请输入最大comment"
    :precision="0"
    :value-on-clear="undefined"
    @change="emits('query')"
/>
            """.trimIndent(),
            baseProperty.copy(type = "kotlin.Int", typeNotNull = false).result,
        )

        assertEquals(
            """
<el-input-number
    v-model.number="spec.minName"
    placeholder="请输入最小comment"
    :precision="0"
    :min="0"
    :max="999999999"
    :value-on-clear="undefined"
    @change="emits('query')"
/>
<el-input-number
    v-model.number="spec.maxName"
    placeholder="请输入最大comment"
    :precision="0"
    :min="0"
    :max="999999999"
    :value-on-clear="undefined"
    @change="emits('query')"
/>
            """.trimIndent(),
            baseProperty.copy(type = "kotlin.Int", column = TargetOf_column(dataSize = 9)).result,
        )
    }

    @Test
    fun `test float` () {
        assertEquals(
            """
<el-input-number
    v-model.number="spec.minName"
    placeholder="请输入最小comment"
    :precision="0"
    :value-on-clear="undefined"
    @change="emits('query')"
/>
<el-input-number
    v-model.number="spec.maxName"
    placeholder="请输入最大comment"
    :precision="0"
    :value-on-clear="undefined"
    @change="emits('query')"
/>
            """.trimIndent(),
            baseProperty.copy(type = "kotlin.Float").result,
        )

        assertEquals(
            """
<el-input-number
    v-model.number="spec.minName"
    placeholder="请输入最小comment"
    :precision="2"
    :min="0.00"
    :max="99999999.99"
    :value-on-clear="undefined"
    @change="emits('query')"
/>
<el-input-number
    v-model.number="spec.maxName"
    placeholder="请输入最大comment"
    :precision="2"
    :min="0.00"
    :max="99999999.99"
    :value-on-clear="undefined"
    @change="emits('query')"
/>
            """.trimIndent(),
            baseProperty.copy(type = "kotlin.Float", column = TargetOf_column(dataSize = 10, numericPrecision = 2)).result,
        )
    }

    @Test
    fun `test enum` () {
        assertEquals(
            """
<EnumNullableSelect
    v-model="spec.name"
    @change="emits('query')"
/>
            """.trimIndent(),
            baseProperty.copy(type = "kotlin.String", enum = TargetOf_enum(
                packagePath = "",
                name = "Enum",
                comment = "comment"
            )
            ).result,
        )
    }
}
