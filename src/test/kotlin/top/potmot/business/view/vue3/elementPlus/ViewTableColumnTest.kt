package top.potmot.business.view.vue3.elementPlus

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import top.potmot.business.baseProperty
import top.potmot.business.testEntityBusiness
import top.potmot.business.testEnum
import top.potmot.business.testEnumBusiness
import top.potmot.core.business.meta.CommonProperty
import top.potmot.core.business.meta.EnumProperty
import top.potmot.core.business.meta.PropertyBusiness
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Vue3ElementPlusViewGenerator
import top.potmot.core.business.view.generate.impl.vue3elementPlus.view.ViewTableColumn
import top.potmot.core.common.typescript.stringify
import top.potmot.core.common.vue3.TagElement
import top.potmot.utils.string.appendBlock
import top.potmot.utils.string.appendLines

class ViewTableColumnTest : ViewTableColumn {
    private val builder = Vue3ElementPlusViewGenerator.componentBuilder

    private val PropertyBusiness.result: String
        get() = viewTableColumns(withDateTimeFormat = true)
            .joinToString("\n") {
                buildString {
                    builder.apply {
                        appendLines(it.imports.stringify(builder.indent, builder.wrapThreshold))
                        appendBlock(
                            listOf(
                                TagElement(
                                    "el-table-item",
                                    props = it.props,
                                    children = it.elements
                                )
                            ).stringifyElements()
                        )
                    }
                }.trim()
            }

    @Test
    fun `test base table column`() {
        assertEquals(
            "<el-table-item/>",
            CommonProperty(testEntityBusiness, baseProperty).result,
        )
    }

    @Test
    fun `test enum table column`() {
        assertEquals(
            """
import EnumView from "@/components/enums/enum/EnumView.vue"
<el-table-item>
    <EnumView :value="scope.row.property"/>
</el-table-item>
            """.trimIndent(),
            EnumProperty(
                testEntityBusiness,
                baseProperty.copy(
                    enumId = testEnum.id
                ),
                testEnumBusiness
            )
                .result,
        )
    }

    @Test
    fun `test date table column`() {
        listOf(
            baseProperty.copy(
                type = "java.time.LocalDate"
            )
        ).forEach {
            assertEquals(
                """
import {formatDate} from "@/utils/timeFormat"
<el-table-item>
    {{ formatDate(scope.row.property) }}
</el-table-item>
                """.trimIndent(),
                CommonProperty(testEntityBusiness, it).result,
            )
        }
    }

    @Test
    fun `test time table column`() {
        listOf(
            baseProperty.copy(
                type = "java.time.LocalTime"
            )
        ).forEach {
            assertEquals(
                """
import {formatTime} from "@/utils/timeFormat"
<el-table-item>
    {{ formatTime(scope.row.property) }}
</el-table-item>
                """.trimIndent(),
                CommonProperty(testEntityBusiness, it).result,
            )
        }
    }

    @Test
    fun `test datetime table column`() {
        listOf(
            baseProperty.copy(
                type = "java.time.LocalDateTime"
            )
        ).forEach {
            assertEquals(
                """
import {formatDateTime} from "@/utils/timeFormat"
<el-table-item>
    {{ formatDateTime(scope.row.property) }}
</el-table-item>
                """.trimIndent(),
                CommonProperty(testEntityBusiness, it).result,
            )
        }
    }
}