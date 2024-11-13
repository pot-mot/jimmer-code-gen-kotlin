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
        id = 0,
        name = "name",
        comment = "comment",
        remark = "remark",
        type = "kotlin.String",
        listType = false,
        typeNotNull = false,
        idProperty = false,
        idGenerationAnnotation = null,
        keyProperty = false,
        logicalDelete = false,
        idView = false,
        idViewTarget = null,
        associationType = null,
        mappedBy = null,
        inputNotNull = null,
        joinColumnMetas = null,
        joinTableMeta = null,
        dissociateAnnotation = null,
        otherAnnotation = null,
        orderKey = 0,
        entityId = 0,
        column = null,
        enum = null,
        typeEntity = null,
        createdTime = LocalDateTime.now(),
        modifiedTime = LocalDateTime.now(),
    )

    private val builder = Vue3ComponentBuilder()

    private val GenEntityBusinessView.TargetOf_properties.result: String
        get() = createTableColumn().let {
            var result: String
            builder.apply {
                result = it.elements.stringifyElements()
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
                    id = 0,
                    packagePath = "",
                    name = "Enum",
                    comment = "comment",
                    items = emptyList(),
                )
            ).result,
        )
    }
}