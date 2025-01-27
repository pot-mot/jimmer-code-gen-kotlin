package top.potmot.business.view.vue3.elementPlus

import java.sql.Types
import java.time.LocalDateTime
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import top.potmot.business.baseEntity
import top.potmot.business.enumIdMap
import top.potmot.business.testEnum
import top.potmot.business.testEnumBusiness
import top.potmot.core.business.meta.AssociationPath
import top.potmot.core.business.meta.AssociationProperty
import top.potmot.core.business.meta.CommonProperty
import top.potmot.core.business.meta.EnumProperty
import top.potmot.core.business.meta.RootEntityBusiness
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Vue3ElementPlusViewGenerator
import top.potmot.core.business.view.generate.impl.vue3elementPlus.form.FormItem
import top.potmot.core.business.view.generate.meta.typescript.stringify
import top.potmot.entity.dto.GenEntityBusinessView
import top.potmot.entity.dto.GenEntityBusinessView.TargetOf_properties.TargetOf_column
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

    private val builder = Vue3ElementPlusViewGenerator.componentBuilder

    private val GenEntityBusinessView.TargetOf_properties.mockEntityBusiness
        get() = RootEntityBusiness(
            entity = baseEntity.copy(properties = listOf(this)),
            entityIdMap = mapOf(baseEntity.id to baseEntity),
            enumIdMap = enumIdMap,
        )

    private val GenEntityBusinessView.TargetOf_properties.result: String
        get() = CommonProperty(mockEntityBusiness, this).toFormItemData(formData, disabled).let {
            var result: String
            builder.apply {
                result =
                    (it.imports.stringify(builder.indent, builder.wrapThreshold) + it.elements.stringifyElements()).joinToString("\n")
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
            baseProperty.copy(
                type = "kotlin.Int",
                column = TargetOf_column(dataSize = 9, numericPrecision = 0, typeCode = Types.INTEGER)
            ).result,
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
            baseProperty.copy(
                type = "kotlin.Int",
                typeNotNull = false,
                column = TargetOf_column(dataSize = 9, numericPrecision = 0, typeCode = Types.INTEGER)
            ).result,
        )

        assertEquals(
            """
<el-input-number
    v-model.number="formData.name"
    placeholder="请输入comment"
    :precision="0"
    :min="0"
    :max="2147483647"
    :value-on-clear="0"
/>
            """.trimIndent(),
            baseProperty.copy(
                type = "kotlin.Int",
                column = TargetOf_column(dataSize = 10, numericPrecision = 0, typeCode = Types.INTEGER)
            ).result,
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
            baseProperty.copy(
                type = "kotlin.Int",
                column = TargetOf_column(dataSize = 12, numericPrecision = 0, typeCode = Types.BIGINT)
            ).result,
        )

        assertEquals(
            """
<el-input-number
    v-model.number="formData.name"
    placeholder="请输入comment"
    :precision="0"
    :min="0"
    :max="9223372036854775807"
    :value-on-clear="0"
/>
            """.trimIndent(),
            baseProperty.copy(
                type = "kotlin.Long",
                column = TargetOf_column(dataSize = 20, numericPrecision = 0, typeCode = Types.BIGINT)
            ).result,
        )
    }

    @Test
    fun `test int with default`() {
        assertEquals(
            """
<el-input-number
    v-model.number="formData.name"
    placeholder="请输入comment"
    :precision="0"
    :max="2147483647"
/>
            """.trimIndent(),
            baseProperty.copy(
                type = "kotlin.Int",
                column = TargetOf_column(dataSize = 0, numericPrecision = 0, typeCode = Types.INTEGER)
            ).result,
        )

        assertEquals(
            """
<el-input-number
    v-model.number="formData.name"
    placeholder="请输入comment"
    :precision="0"
    :max="9223372036854775807"
/>
            """.trimIndent(),
            baseProperty.copy(
                type = "kotlin.Long",
                column = TargetOf_column(dataSize = 0, numericPrecision = 0, typeCode = Types.BIGINT)
            ).result,
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
                column = TargetOf_column(dataSize = 10, numericPrecision = 2, typeCode = Types.FLOAT)
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
                column = TargetOf_column(dataSize = 10, numericPrecision = 2, typeCode = Types.FLOAT)
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
                column = TargetOf_column(dataSize = 10, numericPrecision = 2, typeCode = Types.DOUBLE)
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
                column = TargetOf_column(dataSize = 10, numericPrecision = 2, typeCode = Types.DECIMAL)
            ).result,
        )
    }

    private val GenEntityBusinessView.TargetOf_properties.enumResult: String
        get() = EnumProperty(mockEntityBusiness, this, testEnumBusiness).toFormItemData(formData, disabled).let {
            var result: String
            builder.apply {
                result =
                    (it.imports.stringify(builder.indent, builder.wrapThreshold) + it.elements.stringifyElements()).joinToString("\n")
            }
            result
        }

    @Test
    fun `test enum`() {
        assertEquals(
            """
import EnumSelect from "@/components/enums/enum/EnumSelect.vue"
<EnumSelect v-model="formData.name"/>
            """.trimIndent(),
            baseProperty.copy(
                type = "kotlin.String", enumId = testEnum.id
            ).enumResult,
        )

    }


    private val GenEntityBusinessView.TargetOf_properties.associationResult: String
        get() = AssociationProperty(AssociationPath(mockEntityBusiness, emptyList()), mockEntityBusiness, this, null, baseEntity, associationType!!).toFormItemData(formData, disabled).let {
            var result: String
            builder.apply {
                result =
                    (it.imports.stringify(builder.indent, builder.wrapThreshold) + it.elements.stringifyElements()).joinToString("\n")
            }
            result
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
    v-model="formData.name"
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
