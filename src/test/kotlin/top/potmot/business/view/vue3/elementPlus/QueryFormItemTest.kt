package top.potmot.business.view.vue3.elementPlus

import java.sql.Types
import java.time.LocalDateTime
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import top.potmot.business.baseEntity
import top.potmot.core.business.meta.AssociationProperty
import top.potmot.core.business.meta.CommonProperty
import top.potmot.core.business.view.generate.builder.vue3.Vue3ComponentBuilder
import top.potmot.core.business.view.generate.impl.vue3elementPlus.queryFormItem.QueryFormItem
import top.potmot.entity.dto.GenEntityBusinessView
import top.potmot.entity.dto.GenEntityBusinessView.TargetOf_properties.TargetOf_column
import top.potmot.entity.dto.GenEntityBusinessView.TargetOf_properties.TargetOf_enum
import top.potmot.enumeration.AssociationType

class QueryFormItemTest : QueryFormItem {
    private val spec = "spec"

    private val baseProperty = GenEntityBusinessView.TargetOf_properties(
        id = 0,
        createdTime = LocalDateTime.now(),
        modifiedTime = LocalDateTime.now(),
        name = "name",
        comment = "comment",
        type = "type",
        remark = "remark",
        typeNotNull = true,
        orderKey = 0,
        entityId = 0,
    )

    private val builder = Vue3ComponentBuilder()

    private val GenEntityBusinessView.TargetOf_properties.result: String
        get() = CommonProperty(this).createQueryFormItem(spec).let {
            var result: String
            builder.apply {
                result =
                    (it.imports.stringifyImports() + it.scripts.stringifyCodes() + it.elements.stringifyElements())
                        .joinToString("\n")
                        .trim()
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
/>
            """.trimIndent(),
            baseProperty.result,
        )
    }

    @Test
    fun `test time`() {
        assertEquals(
            """
import {computed} from "vue"
const nameRange = computed<[string | undefined, string | undefined]>({
    get() {
        return [
            spec.value.minName,
            spec.value.maxName,
       ]
    },
    set(range: [string | undefined, string | undefined] | null) {
        spec.value.minName = range?.[0]
        spec.value.maxName = range?.[1]
    }
})
<el-time-picker
    v-model="nameRange"
    value-format="HH:mm:ss"
    is-range
    start-placeholder="请选择开始comment"
    end-placeholder="请选择结束comment"
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
import {computed} from "vue"
const nameRange = computed<[string | undefined, string | undefined]>({
    get() {
        return [
            spec.value.minName,
            spec.value.maxName,
       ]
    },
    set(range: [string | undefined, string | undefined] | null) {
        spec.value.minName = range?.[0]
        spec.value.maxName = range?.[1]
    }
})
<el-date-picker
    v-model="nameRange"
    type="daterange"
    value-format="YYYY-MM-DD"
    unlink-panels
    start-placeholder="请选择开始comment"
    end-placeholder="请选择结束comment"
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
import {computed} from "vue"
const nameRange = computed<[string | undefined, string | undefined]>({
    get() {
        return [
            spec.value.minName,
            spec.value.maxName,
       ]
    },
    set(range: [string | undefined, string | undefined] | null) {
        spec.value.minName = range?.[0]
        spec.value.maxName = range?.[1]
    }
})
<el-date-picker
    v-model="nameRange"
    type="datetimerange"
    value-format="YYYY-MM-DDTHH:mm:ss"
    unlink-panels
    start-placeholder="请选择开始comment"
    end-placeholder="请选择结束comment"
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
<el-select
    v-model="spec.name"
    placeholder="请选择comment"
    clearable
    :value-on-clear="undefined"
>
    <el-option :value="true" label="是"/>
    <el-option :value="false" label="否"/>
</el-select>
            """.trimIndent(),
            baseProperty.copy(type = "kotlin.Boolean").result,
        )
    }

    @Test
    fun `test int`() {
        assertEquals(
            """
<el-input-number
    v-model.number="spec.minName"
    placeholder="最小"
    :precision="0"
    :value-on-clear="undefined"
/>
<el-input-number
    v-model.number="spec.maxName"
    placeholder="最大"
    :precision="0"
    :value-on-clear="undefined"
/>
            """.trimIndent(),
            baseProperty.copy(type = "kotlin.Int").result,
        )

        assertEquals(
            """
<el-input-number
    v-model.number="spec.minName"
    placeholder="最小"
    :precision="0"
    :value-on-clear="undefined"
/>
<el-input-number
    v-model.number="spec.maxName"
    placeholder="最大"
    :precision="0"
    :value-on-clear="undefined"
/>
            """.trimIndent(),
            baseProperty.copy(type = "kotlin.Int", typeNotNull = false).result,
        )

        assertEquals(
            """
<el-input-number
    v-model.number="spec.minName"
    placeholder="最小"
    :precision="0"
    :min="0"
    :max="999999999"
    :value-on-clear="undefined"
/>
<el-input-number
    v-model.number="spec.maxName"
    placeholder="最大"
    :precision="0"
    :min="0"
    :max="999999999"
    :value-on-clear="undefined"
/>
            """.trimIndent(),
            baseProperty.copy(type = "kotlin.Int", column = TargetOf_column(dataSize = 9, numericPrecision = 0, typeCode = Types.INTEGER)).result,
        )
    }

    @Test
    fun `test float`() {
        assertEquals(
            """
<el-input-number
    v-model.number="spec.minName"
    placeholder="最小"
    :precision="0"
    :value-on-clear="undefined"
/>
<el-input-number
    v-model.number="spec.maxName"
    placeholder="最大"
    :precision="0"
    :value-on-clear="undefined"
/>
            """.trimIndent(),
            baseProperty.copy(type = "kotlin.Float").result,
        )

        assertEquals(
            """
<el-input-number
    v-model.number="spec.minName"
    placeholder="最小"
    :precision="2"
    :min="0.00"
    :max="99999999.99"
    :value-on-clear="undefined"
/>
<el-input-number
    v-model.number="spec.maxName"
    placeholder="最大"
    :precision="2"
    :min="0.00"
    :max="99999999.99"
    :value-on-clear="undefined"
/>
            """.trimIndent(),
            baseProperty.copy(
                type = "kotlin.Float",
                column = TargetOf_column(dataSize = 10, numericPrecision = 2, typeCode = Types.DECIMAL)
            ).result,
        )
    }

    @Test
    fun `test enum`() {
        assertEquals(
            """
import EnumNullableSelect from "@/components/enums/enum/EnumNullableSelect.vue"

<EnumNullableSelect v-model="spec.name"/>
            """.trimIndent(),
            baseProperty.copy(
                type = "kotlin.String", enum = TargetOf_enum(
                    id = 0,
                    packagePath = "",
                    name = "Enum",
                    comment = "comment",
                    items = emptyList(),
                )
            ).result,
        )
    }


    private val GenEntityBusinessView.TargetOf_properties.associationResult: String
        get() = AssociationProperty(this, null, baseEntity).createQueryFormItem(spec).let {
            var result: String
            builder.apply {
                result =
                    (it.imports.stringifyImports() + it.elements.stringifyElements()).joinToString("\n")
            }
            result
        }

    @Test
    fun `test to one association`() {
        val expect = """
import EntityIdSelect from "@/components/entity/EntityIdSelect.vue"
<EntityIdSelect
    v-model="spec.name"
    :options="nameOptions"
/>
        """.trimIndent()

        val manyToOneProperty = baseProperty.copy(
            type = "kotlin.Int",
            associationType = AssociationType.MANY_TO_ONE,
            typeEntityId = baseEntity.id
        )

        assertEquals(
            expect,
            manyToOneProperty.associationResult,
        )

        val oneToOneProperty = manyToOneProperty.copy(
            associationType = AssociationType.ONE_TO_ONE,
        )

        assertEquals(
            expect,
            oneToOneProperty.associationResult,
        )
    }

    @Test
    fun `test to many association`() {
        val expect = """
import EntityIdMultiSelect from "@/components/entity/EntityIdMultiSelect.vue"
<EntityIdMultiSelect
    v-model="spec.name"
    :options="nameOptions"
/>
        """.trimIndent()

        val manyToManyProperty = baseProperty.copy(
            type = "kotlin.Int",
            associationType = AssociationType.MANY_TO_MANY,
            listType = true,
            typeEntityId = baseEntity.id
        )

        assertEquals(
            expect,
            manyToManyProperty.associationResult,
        )

        val oneToOneProperty = manyToManyProperty.copy(
            associationType = AssociationType.ONE_TO_MANY,
        )

        assertEquals(
            expect,
            oneToOneProperty.associationResult,
        )
    }
}
