package top.potmot.view.vue3.elementPlus

import java.time.LocalDateTime
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import top.potmot.core.business.view.generate.builder.vue3.Vue3ComponentBuilder
import top.potmot.core.business.view.generate.impl.vue3elementPlus.tableColumn.TableColumn
import top.potmot.entity.dto.GenEntityBusinessView
import top.potmot.entity.dto.GenEntityBusinessView.TargetOf_properties.TargetOf_enum

class TableColumnTest : TableColumn {
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
        get() = createTableColumn().let {
            var result: String
            builder.apply {
                result = it.stringifyElements()
            }
            result
        }

    @Test
    fun `test base table column`() {
        assertEquals(
            "",
            baseProperty.result,
        )
    }

    @Test
    fun `test enum table column`() {
        assertEquals(
            "<EnumView :value=\"scope.row.name\"/>",
            baseProperty.copy(
                enum = TargetOf_enum(
                    packagePath = "",
                    name = "Enum",
                    comment = "comment"
                )
            ).result,
        )
    }
}