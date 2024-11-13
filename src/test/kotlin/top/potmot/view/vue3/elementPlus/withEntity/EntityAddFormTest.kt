package top.potmot.view.vue3.elementPlus.withEntity

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Vue3ElementPlusViewGenerator
import top.potmot.view.testEntity

// TODO 完成测试
class EntityAddFormTest {
    private val generator = Vue3ElementPlusViewGenerator

    @Test
    fun `test addFormType`() {
        assertEquals(
            """
export type EntityAddFormType = {
    enumProperty: Enum
    enumNullableProperty: Enum | undefined
    toOnePropertyId: number | undefined
    toOneNullablePropertyId: number | undefined
}
            """.trimIndent(),
            generator.stringifyAddFormType(testEntity).trim()
        )
    }

    @Test
    fun `test addFormDefault`() {
        assertEquals(
            """
import type {EntityAddFormType} from "./EntityAddFormType"
export const defaultEntity: EntityAddFormType = {
    enumProperty: "item1",
    enumNullableProperty: undefined,
    toOnePropertyId: undefined,
    toOneNullablePropertyId: undefined,
}
            """.trimIndent(),
            generator.stringifyAddFormDefault(testEntity).trim()
        )
    }

    @Test
    fun `test addFormRules`() {
        assertEquals(
            """
[import type {Ref} from "vue", import type {FormRules} from "element-plus", import type {EntityAddFormDataType} from "@/api/__generated/model/static"]

export const useRules = (formData: Ref<EntityAddFormDataType>): FormRules<EntityAddFormDataType> => {
    return {
        enumProperty: [
            {required: }
            {type: "enum", enum: ["item1"], message: "enumProperty必须是item1", trigger: "blur"},
        ],
        enumNullableProperty: [
            {type: "enum", enum: ["item1"], message: "enumNullableProperty必须是item1", trigger: "blur"},
        ],
        toOnePropertyId: [
            {required: }
        ],
        toOneNullablePropertyId: [
        ],
    }
    
}
            """.trimIndent(),
            generator.stringifyAddFormRules(testEntity).trim()
        )
    }

    @Test
    fun `test addForm`() {
        assertEquals(
            "",
            generator.stringifyAddForm(testEntity).trim()
        )
    }
}