package top.potmot.business.view.vue3.elementPlus

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import top.potmot.business.baseProperty
import top.potmot.core.business.view.generate.builder.vue3.Vue3ComponentBuilder
import top.potmot.core.business.view.generate.impl.vue3elementPlus.tableColumn.TableColumn
import top.potmot.core.business.view.generate.meta.vue3.TagElement
import top.potmot.entity.dto.GenEntityBusinessView
import top.potmot.entity.dto.GenEntityBusinessView.TargetOf_properties.TargetOf_enum
import top.potmot.utils.string.appendBlock
import top.potmot.utils.string.appendLines

class TableColumnTest : TableColumn {
    private val builder = Vue3ComponentBuilder()

    private val GenEntityBusinessView.TargetOf_properties.result: String
        get() = tableColumnDataList().map { it.second }.joinToString("\n") {
            buildString {
                builder.apply {
                    appendLines(it.imports.stringifyImports())
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
            baseProperty.result,
        )
    }

    @Test
    fun `test enum table column`() {
        assertEquals(
            """
import EnumView from "@/components/enum/EnumView.vue"
<el-table-item>
    <EnumView :value="scope.row.property"/>
</el-table-item>
            """.trimIndent(),
            baseProperty.copy(
                enum = TargetOf_enum(
                    id = 0,
                    packagePath = "",
                    name = "Enum",
                    comment = "comment",
                    items = emptyList(),
                )
            ).result,
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
import {formatTableColumnDate} from "@/utils/timeFormat"
<el-table-item :formatter="formatTableColumnDate"/>
                """.trimIndent(),
                it.result,
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
import {formatTableColumnTime} from "@/utils/timeFormat"
<el-table-item :formatter="formatTableColumnTime"/>
                """.trimIndent(),
                it.result,
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
import {formatTableColumnDateTime} from "@/utils/timeFormat"
<el-table-item :formatter="formatTableColumnDateTime"/>
                """.trimIndent(),
                it.result,
            )
        }
    }
}