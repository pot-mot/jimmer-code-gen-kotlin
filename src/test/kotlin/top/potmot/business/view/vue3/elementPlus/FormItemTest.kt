package top.potmot.business.view.vue3.elementPlus

import java.time.LocalDateTime
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import top.potmot.core.business.view.generate.builder.vue3.Vue3ComponentBuilder
import top.potmot.core.business.view.generate.impl.vue3elementPlus.formItem.FormItem
import top.potmot.entity.dto.GenEntityBusinessView
import top.potmot.entity.dto.GenEntityBusinessView.TargetOf_properties.TargetOf_column
import top.potmot.entity.dto.GenEntityBusinessView.TargetOf_properties.TargetOf_enum
import top.potmot.entity.dto.GenEntityBusinessView.TargetOf_properties.TargetOf_typeEntity
import top.potmot.enumeration.AssociationType

class FormItemTest : FormItem {
    private val formData = "formData"

    private val disabled = false

    private val baseProperty = GenEntityBusinessView.TargetOf_properties(
        id = 0,
        createdTime = LocalDateTime.now(),
        modifiedTime = LocalDateTime.now(),
        name = "name",
        comment = "comment",
        type = "type",
        typeNotNull = true,
        orderKey = 0,
        remark = "remark",
        entityId = 0,
    )

    private val builder = Vue3ComponentBuilder()

    private val GenEntityBusinessView.TargetOf_properties.result: String
        get() = createFormItem(formData, disabled).let {
            var result: String
            builder.apply {
                result =
                    (it.imports.stringifyImports() + it.elements.stringifyElements()).joinToString("\n")
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
    fun `test date`() {
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
    fun `test datetime`() {
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
    fun `test switch`() {
        assertEquals(
            """
<el-switch v-model="formData.name"/>
            """.trimIndent(),
            baseProperty.copy(type = "kotlin.Boolean").result,
        )

        assertEquals(
            """
<el-select
    v-model="formData.name"
    placeholder="请选择comment"
    clearable
    :value-on-clear="undefined"
>
    <el-option :value="true" label="是"/>
    <el-option :value="false" label="否"/>
</el-select>
            """.trimIndent(),
            baseProperty.copy(type = "kotlin.Boolean", typeNotNull = false).result,
        )
    }

    @Test
    fun `test int`() {
        assertEquals(
            """
<el-input-number
    v-model.number="formData.name"
    placeholder="请输入comment"
    :precision="0"
/>
            """.trimIndent(),
            baseProperty.copy(type = "kotlin.Int").result,
        )

        assertEquals(
            """
<el-input-number
    v-model.number="formData.name"
    placeholder="请输入comment"
    :precision="0"
    :value-on-clear="undefined"
/>
            """.trimIndent(),
            baseProperty.copy(type = "kotlin.Int", typeNotNull = false).result,
        )

        assertEquals(
            """
<el-input-number
    v-model.number="formData.name"
    placeholder="请输入comment"
    :precision="0"
    :min="0"
    :max="999999999"
    :value-on-clear="0"
/>
            """.trimIndent(),
            baseProperty.copy(type = "kotlin.Int", column = TargetOf_column(dataSize = 9, numericPrecision = 0)).result,
        )

        assertEquals(
            """
<el-input-number
    v-model.number="formData.name"
    placeholder="请输入comment"
    :precision="0"
    :min="0"
    :max="999999999"
    :value-on-clear="undefined"
/>
            """.trimIndent(),
            baseProperty.copy(type = "kotlin.Int", typeNotNull = false, column = TargetOf_column(dataSize = 9, numericPrecision = 0)).result,
        )

        assertEquals(
            """
<el-input-number
    v-model.number="formData.name"
    placeholder="请输入comment"
    :precision="0"
    :min="0"
    :max="9999999999"
    :value-on-clear="0"
/>
            """.trimIndent(),
            baseProperty.copy(type = "kotlin.Int", column = TargetOf_column(dataSize = 10, numericPrecision = 0)).result,
        )

        assertEquals(
            """
<el-input-number
    v-model.number="formData.name"
    placeholder="请输入comment"
    :precision="0"
    :min="0"
    :max="999999999999"
    :value-on-clear="0"
/>
            """.trimIndent(),
            baseProperty.copy(type = "kotlin.Int", column = TargetOf_column(dataSize = 12, numericPrecision = 0)).result,
        )

        assertEquals(
            """
<el-input-number
    v-model.number="formData.name"
    placeholder="请输入comment"
    :precision="0"
    :min="0"
    :max="999999999999999"
    :value-on-clear="0"
/>
            """.trimIndent(),
            baseProperty.copy(type = "kotlin.Int", column = TargetOf_column(dataSize = 20, numericPrecision = 0)).result,
        )
    }

    @Test
    fun `test float`() {
        assertEquals(
            """
<el-input-number
    v-model.number="formData.name"
    placeholder="请输入comment"
/>
            """.trimIndent(),
            baseProperty.copy(type = "kotlin.Float").result,
        )

        assertEquals(
            """
<el-input-number
    v-model.number="formData.name"
    placeholder="请输入comment"
    :precision="2"
    :min="0.00"
    :max="99999999.99"
    :value-on-clear="0.00"
/>
            """.trimIndent(),
            baseProperty.copy(
                type = "kotlin.Float",
                column = TargetOf_column(dataSize = 10, numericPrecision = 2)
            ).result,
        )

        assertEquals(
            """
<el-input-number
    v-model.number="formData.name"
    placeholder="请输入comment"
    :precision="2"
    :min="0.00"
    :max="99999999.99"
    :value-on-clear="undefined"
/>
            """.trimIndent(),
            baseProperty.copy(
                type = "kotlin.Float",
                typeNotNull = false,
                column = TargetOf_column(dataSize = 10, numericPrecision = 2)
            ).result,
        )
    }

    @Test
    fun `test double`() {
        assertEquals(
            """
<el-input-number
    v-model.number="formData.name"
    placeholder="请输入comment"
/>
            """.trimIndent(),
            baseProperty.copy(type = "kotlin.Double").result,
        )

        assertEquals(
            """
<el-input-number
    v-model.number="formData.name"
    placeholder="请输入comment"
    :precision="2"
    :min="0.00"
    :max="99999999.99"
    :value-on-clear="0.00"
/>
            """.trimIndent(),
            baseProperty.copy(
                type = "kotlin.Double",
                column = TargetOf_column(dataSize = 10, numericPrecision = 2)
            ).result,
        )
    }

    @Test
    fun `test decimal`() {
        assertEquals(
            """
<el-input-number
    v-model.number="formData.name"
    placeholder="请输入comment"
/>
            """.trimIndent(),
            baseProperty.copy(type = "java.math.BigDecimal").result,
        )

        assertEquals(
            """
<el-input-number
    v-model.number="formData.name"
    placeholder="请输入comment"
    :precision="2"
    :min="0.00"
    :max="99999999.99"
    :value-on-clear="0.00"
/>
            """.trimIndent(),
            baseProperty.copy(
                type = "java.math.BigDecimal",
                column = TargetOf_column(dataSize = 10, numericPrecision = 2)
            ).result,
        )
    }

    @Test
    fun `test enum`() {
        assertEquals(
            """
import EnumSelect from "@/components/enums/enum/EnumSelect.vue"
<EnumSelect v-model="formData.name"/>
            """.trimIndent(),
            baseProperty.copy(
                type = "kotlin.String", enum = TargetOf_enum(
                    id = 0,
                    packagePath = "",
                    name = "Enum",
                    comment = "comment",
                    items = emptyList()
                )
            ).result,
        )

    }

    @Test
    fun `test to one association`() {
        val expect = """
import EntityIdSelect from "@/components/entity/EntityIdSelect.vue"
<EntityIdSelect
    v-model="formData.name"
    :options="nameOptions"
/>
        """.trimIndent()

        val manyToOneProperty = baseProperty.copy(
            type = "kotlin.Int",
            associationType = AssociationType.MANY_TO_ONE,
            typeEntity = TargetOf_typeEntity(
                id = 0,
                packagePath = "",
                name = "Entity",
                comment = "comment",
                idProperties = emptyList(),
                shortViewProperties = emptyList(),
            )
        )

        assertEquals(
            expect,
            manyToOneProperty.result,
        )

        val oneToOneProperty = manyToOneProperty.copy(
            associationType = AssociationType.ONE_TO_ONE,
        )

        assertEquals(
            expect,
            oneToOneProperty.result,
        )
    }

    @Test
    fun `test to many association`() {
        val expect = """
import EntityIdMultiSelect from "@/components/entity/EntityIdMultiSelect.vue"
<EntityIdMultiSelect
    v-model="formData.name"
    :options="nameOptions"
/>
        """.trimIndent()

        val manyToManyProperty = baseProperty.copy(
            type = "kotlin.Int",
            associationType = AssociationType.MANY_TO_MANY,
            listType = true,
            typeEntity = TargetOf_typeEntity(
                id = 0,
                packagePath = "",
                name = "Entity",
                comment = "comment",
                idProperties = emptyList(),
                shortViewProperties = emptyList(),
            )
        )

        assertEquals(
            expect,
            manyToManyProperty.result,
        )

        val oneToOneProperty = manyToManyProperty.copy(
            associationType = AssociationType.ONE_TO_MANY,
        )

        assertEquals(
            expect,
            oneToOneProperty.result,
        )
    }
}
