package top.potmot.business.existValid

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import top.potmot.core.business.view.generate.builder.rules.existValidRules
import top.potmot.core.business.view.generate.builder.vue3.elementPlus.rules.Vue3ElementPlusRuleBuilder
import top.potmot.core.business.view.generate.meta.rules.Rule

class ExistValidRulesTest {
    private val builder = Vue3ElementPlusRuleBuilder()

    private val Map<String, List<Rule>>.result
        get() = builder.createFormRules("useRules", "formData", "EntityDto", this)

    @Test
    fun `test existValidRules`() {
        listOf(
            toOneAndEnumEntity,
            toOneDuplicateAndEnumEntity
        ).forEach { testEntity ->
            val existValidRulesWithoutId = testEntity.existValidRules(withId = false)

            assertEquals(
                """
import type {Ref} from "vue"
import type {FormRules} from "element-plus"
import type {EntityDto} from "@/api/__generated/model/static"
import {api} from "@/api"
import {asyncValidExist} from "@/utils/asyncValidExist"

export const useRules = (formData: Ref<EntityDto>): FormRules<EntityDto> => {
    return {
        toOneProperty: [
            {
                asyncValidator: asyncValidExist("toOneProperty", async (toOneProperty: ToOneEntity) => {
                    return await api.entityService.existByToOnePropertyAndToOneNullableProperty({body: {toOneProperty, toOneNullableProperty: formData.value.toOneNullableProperty}})
                }),
                trigger: "blur"
            },
        ],
        toOneNullableProperty: [
            {
                asyncValidator: asyncValidExist("toOneNullableProperty", async (toOneNullableProperty: ToOneEntity) => {
                    return await api.entityService.existByToOnePropertyAndToOneNullableProperty({body: {toOneNullableProperty, toOneProperty: formData.value.toOneProperty}})
                }),
                trigger: "blur"
            },
        ],
        enumProperty: [
            {
                asyncValidator: asyncValidExist("enumProperty", async (enumProperty: Enum) => {
                    return await api.entityService.existByEnumPropertyAndEnumNullableProperty({body: {enumProperty, enumNullableProperty: formData.value.enumNullableProperty}})
                }),
                trigger: "blur"
            },
        ],
        enumNullableProperty: [
            {
                asyncValidator: asyncValidExist("enumNullableProperty", async (enumNullableProperty: Enum) => {
                    return await api.entityService.existByEnumPropertyAndEnumNullableProperty({body: {enumNullableProperty, enumProperty: formData.value.enumProperty}})
                }),
                trigger: "blur"
            },
        ],
    }
}
            """.trimIndent(),
                existValidRulesWithoutId.result
            )

            val existValidRulesWithId = testEntity.existValidRules(withId = true)

            assertEquals(
                """
import type {Ref} from "vue"
import type {FormRules} from "element-plus"
import type {EntityDto} from "@/api/__generated/model/static"
import {api} from "@/api"
import {asyncValidExist} from "@/utils/asyncValidExist"

export const useRules = (formData: Ref<EntityDto>): FormRules<EntityDto> => {
    return {
        toOneProperty: [
            {
                asyncValidator: asyncValidExist("toOneProperty", async (toOneProperty: ToOneEntity) => {
                    return await api.entityService.existByToOnePropertyAndToOneNullableProperty({body: {toOneProperty, id: formData.value.id, toOneNullableProperty: formData.value.toOneNullableProperty}})
                }),
                trigger: "blur"
            },
        ],
        toOneNullableProperty: [
            {
                asyncValidator: asyncValidExist("toOneNullableProperty", async (toOneNullableProperty: ToOneEntity) => {
                    return await api.entityService.existByToOnePropertyAndToOneNullableProperty({body: {toOneNullableProperty, id: formData.value.id, toOneProperty: formData.value.toOneProperty}})
                }),
                trigger: "blur"
            },
        ],
        enumProperty: [
            {
                asyncValidator: asyncValidExist("enumProperty", async (enumProperty: Enum) => {
                    return await api.entityService.existByEnumPropertyAndEnumNullableProperty({body: {enumProperty, id: formData.value.id, enumNullableProperty: formData.value.enumNullableProperty}})
                }),
                trigger: "blur"
            },
        ],
        enumNullableProperty: [
            {
                asyncValidator: asyncValidExist("enumNullableProperty", async (enumNullableProperty: Enum) => {
                    return await api.entityService.existByEnumPropertyAndEnumNullableProperty({body: {enumNullableProperty, id: formData.value.id, enumProperty: formData.value.enumProperty}})
                }),
                trigger: "blur"
            },
        ],
    }
}
                """.trimIndent(),
                existValidRulesWithId.result
            )
        }
    }

    @Test
    fun `test idView existValidRules`() {
        listOf(
            toOneIdViewAndEnumEntity,
            toOneIdViewDuplicateAndEntity
        ).forEach { testEntity ->
            val existValidRulesWithoutId = testEntity.existValidRules(withId = false)

            assertEquals(
                """
import type {Ref} from "vue"
import type {FormRules} from "element-plus"
import type {EntityDto} from "@/api/__generated/model/static"
import {api} from "@/api"
import {asyncValidExist} from "@/utils/asyncValidExist"

export const useRules = (formData: Ref<EntityDto>): FormRules<EntityDto> => {
    return {
        toOnePropertyId: [
            {
                asyncValidator: asyncValidExist("toOneProperty", async (toOnePropertyId: kotlin.Long) => {
                    return await api.entityService.existByToOnePropertyIdAndToOneNullablePropertyId({body: {toOnePropertyId, toOneNullablePropertyId: formData.value.toOneNullablePropertyId}})
                }),
                trigger: "blur"
            },
        ],
        toOneNullablePropertyId: [
            {
                asyncValidator: asyncValidExist("toOneNullableProperty", async (toOneNullablePropertyId: kotlin.Long) => {
                    return await api.entityService.existByToOnePropertyIdAndToOneNullablePropertyId({body: {toOneNullablePropertyId, toOnePropertyId: formData.value.toOnePropertyId}})
                }),
                trigger: "blur"
            },
        ],
        enumProperty: [
            {
                asyncValidator: asyncValidExist("enumProperty", async (enumProperty: Enum) => {
                    return await api.entityService.existByEnumPropertyAndEnumNullableProperty({body: {enumProperty, enumNullableProperty: formData.value.enumNullableProperty}})
                }),
                trigger: "blur"
            },
        ],
        enumNullableProperty: [
            {
                asyncValidator: asyncValidExist("enumNullableProperty", async (enumNullableProperty: Enum) => {
                    return await api.entityService.existByEnumPropertyAndEnumNullableProperty({body: {enumNullableProperty, enumProperty: formData.value.enumProperty}})
                }),
                trigger: "blur"
            },
        ],
    }
}
            """.trimIndent(),
                existValidRulesWithoutId.result
            )

            val existValidRulesWithId = testEntity.existValidRules(withId = true)

            assertEquals(
                """
import type {Ref} from "vue"
import type {FormRules} from "element-plus"
import type {EntityDto} from "@/api/__generated/model/static"
import {api} from "@/api"
import {asyncValidExist} from "@/utils/asyncValidExist"

export const useRules = (formData: Ref<EntityDto>): FormRules<EntityDto> => {
    return {
        toOnePropertyId: [
            {
                asyncValidator: asyncValidExist("toOneProperty", async (toOnePropertyId: kotlin.Long) => {
                    return await api.entityService.existByToOnePropertyIdAndToOneNullablePropertyId({body: {toOnePropertyId, id: formData.value.id, toOneNullablePropertyId: formData.value.toOneNullablePropertyId}})
                }),
                trigger: "blur"
            },
        ],
        toOneNullablePropertyId: [
            {
                asyncValidator: asyncValidExist("toOneNullableProperty", async (toOneNullablePropertyId: kotlin.Long) => {
                    return await api.entityService.existByToOnePropertyIdAndToOneNullablePropertyId({body: {toOneNullablePropertyId, id: formData.value.id, toOnePropertyId: formData.value.toOnePropertyId}})
                }),
                trigger: "blur"
            },
        ],
        enumProperty: [
            {
                asyncValidator: asyncValidExist("enumProperty", async (enumProperty: Enum) => {
                    return await api.entityService.existByEnumPropertyAndEnumNullableProperty({body: {enumProperty, id: formData.value.id, enumNullableProperty: formData.value.enumNullableProperty}})
                }),
                trigger: "blur"
            },
        ],
        enumNullableProperty: [
            {
                asyncValidator: asyncValidExist("enumNullableProperty", async (enumNullableProperty: Enum) => {
                    return await api.entityService.existByEnumPropertyAndEnumNullableProperty({body: {enumNullableProperty, id: formData.value.id, enumProperty: formData.value.enumProperty}})
                }),
                trigger: "blur"
            },
        ],
    }
}
                """.trimIndent(),
                existValidRulesWithId.result
            )
        }
    }
}