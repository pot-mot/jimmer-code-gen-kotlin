package top.potmot.business.view.vue3.elementPlus

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import top.potmot.business.baseEntity
import top.potmot.business.enumIdMap
import top.potmot.business.testEnum
import top.potmot.business.testEnumBusiness
import top.potmot.core.business.meta.AssociationPath
import top.potmot.core.business.meta.AssociationPathItem
import top.potmot.core.business.meta.AssociationPathItemType
import top.potmot.core.business.meta.AssociationProperty
import top.potmot.core.business.meta.CommonProperty
import top.potmot.core.business.meta.EnumProperty
import top.potmot.core.business.meta.PropertySpecialFormType
import top.potmot.core.business.meta.RootEntityBusiness
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Vue3ElementPlusViewGenerator
import top.potmot.core.business.view.generate.impl.vue3elementPlus.edit.EditFormItem
import top.potmot.core.business.view.generate.meta.typescript.stringify
import top.potmot.entity.dto.GenEntityBusinessView
import top.potmot.entity.dto.GenEntityBusinessView.TargetOf_properties.TargetOf_column
import top.potmot.enumeration.AssociationType
import java.sql.Types
import java.time.LocalDateTime

class EditFormItemTest : EditFormItem {
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
        get() = CommonProperty(mockEntityBusiness, this).toEditFormItem(formData, disabled).let {
            var result: String
            builder.apply {
                result =
                    (it.imports.stringify(
                        builder.indent,
                        builder.wrapThreshold
                    ) + it.elements.stringifyElements()).joinToString("\n")
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
    :value-on-clear="'min'"
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
    :value-on-clear="'min'"
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
    :value-on-clear="'min'"
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
    :value-on-clear="'min'"
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
    :value-on-clear="'min'"
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
    :value-on-clear="'min'"
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
    :value-on-clear="'min'"
/>
            """.trimIndent(),
            baseProperty.copy(
                type = "java.math.BigDecimal",
                column = TargetOf_column(dataSize = 10, numericPrecision = 2, typeCode = Types.DECIMAL)
            ).result,
        )
    }

    private val GenEntityBusinessView.TargetOf_properties.enumResult: String
        get() = EnumProperty(mockEntityBusiness, this, testEnumBusiness).toEditFormItem(formData, disabled).let {
            var result: String
            builder.apply {
                result =
                    (it.imports.stringify(
                        builder.indent,
                        builder.wrapThreshold
                    ) + it.elements.stringifyElements()).joinToString("\n")
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
        get() = AssociationProperty(
            path = AssociationPath(
                mockEntityBusiness,
                listOf(AssociationPathItem(mockEntityBusiness, this, AssociationPathItemType.PROPERTY))
            ),
            entityBusiness = mockEntityBusiness,
            property = this,
            idView = null,
            typeEntity = baseEntity,
            associationType = associationType!!
        ).toEditFormItem(formData, disabled).let {
            var result: String
            builder.apply {
                result =
                    (it.imports.stringify(
                        builder.indent,
                        builder.wrapThreshold
                    ) + it.elements.stringifyElements()).joinToString("\n")
            }
            result
        }

    @Test
    fun `test file`() {
        val fileProperty = baseProperty.copy(
            type = "kotlin.String",
            specialFormType = PropertySpecialFormType.FILE
        )

        assertEquals(
            """
import FileUpload from "@/components/common/FileUpload"
<FileUpload v-model="formData.name"/>
            """.trimIndent(),
            fileProperty.result
        )

        val fileNullableProperty = baseProperty.copy(
            type = "kotlin.String",
            typeNotNull = false,
            specialFormType = PropertySpecialFormType.FILE,
        )

        assertEquals(
            """
import FileUpload from "@/components/common/FileUpload"
<FileUpload v-model="formData.name" clearable/>
            """.trimIndent(),
            fileNullableProperty.result
        )

        val fileListProperty = baseProperty.copy(
            type = "kotlin.String",
            listType = true,
            specialFormType = PropertySpecialFormType.FILE_LIST,
        )

        assertEquals(
            """
import FilesUpload from "@/components/common/FilesUpload"
<FilesUpload v-model="formData.name"/>
            """.trimIndent(),
            fileListProperty.result
        )
    }

    @Test
    fun `test image`() {
        val imageProperty = baseProperty.copy(
            type = "kotlin.String",
            specialFormType = PropertySpecialFormType.IMAGE
        )

        assertEquals(
            """
import ImageUpload from "@/components/common/ImageUpload"
<ImageUpload v-model="formData.name"/>
            """.trimIndent(),
            imageProperty.result
        )

        val imageNullableProperty = baseProperty.copy(
            type = "kotlin.String",
            typeNotNull = false,
            specialFormType = PropertySpecialFormType.IMAGE,
        )

        assertEquals(
            """
import ImageUpload from "@/components/common/ImageUpload"
<ImageUpload v-model="formData.name" clearable/>
            """.trimIndent(),
            imageNullableProperty.result
        )

        val imageListProperty = baseProperty.copy(
            type = "kotlin.String",
            listType = true,
            specialFormType = PropertySpecialFormType.IMAGE_LIST,
        )

        assertEquals(
            """
import ImagesUpload from "@/components/common/ImagesUpload"
<ImagesUpload v-model="formData.name"/>
            """.trimIndent(),
            imageListProperty.result
        )
    }

    @Test
    fun `test to one id`() {
        val expect = """
import EntityIdSelect from "@/components/entity/EntityIdSelect.vue"
<EntityIdSelect
    v-model="formData.name"
    :options="nameIdOptions"
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
    fun `test to many id`() {
        val expect = """
import EntityIdMultiSelect from "@/components/entity/EntityIdMultiSelect.vue"
<EntityIdMultiSelect
    v-model="formData.name"
    :options="nameIdsOptions"
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

        val oneToManyProperty = manyToManyProperty.copy(
            associationType = AssociationType.ONE_TO_MANY,
        )

        assertEquals(
            expect,
            oneToManyProperty.associationResult,
        )
    }
}
