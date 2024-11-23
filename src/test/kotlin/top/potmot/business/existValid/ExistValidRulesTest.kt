package top.potmot.business.existValid

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import top.potmot.core.business.view.generate.builder.rules.existValidRules
import top.potmot.core.business.view.generate.builder.vue3.elementPlus.rules.Vue3ElementPlusRuleBuilder
import top.potmot.core.business.view.generate.meta.rules.Rule
import top.potmot.entity.dto.GenEntityBusinessView

class ExistValidRulesTest {
    private val builder = Vue3ElementPlusRuleBuilder()

    private val Map<GenEntityBusinessView.TargetOf_properties, List<Rule>>.result
        get() = builder.createFormRules("useRules", "formData", "EntityDto", this)

    @ParameterizedTest
    @MethodSource("entities")
    fun `test existValidRules`(testEntity: GenEntityBusinessView) {
        val existValidRulesWithoutId = testEntity.existValidRules(withId = false)

        assertEquals(
            """
import type {Ref} from "vue"
import type {FormRules} from "element-plus"
import type {EntityDto} from "@/api/__generated/model/static"
import {api} from "@/api"
import {asyncValidExist} from "@/utils/asyncValidExist"
import type {Enum} from "@/api/__generated/model/enums"

export const useRules = (formData: Ref<EntityDto>): FormRules<EntityDto> => {
    return {
        toOnePropertyId: [
            {
                asyncValidator: asyncValidExist("toOneProperty", async (toOnePropertyId: number) => {
                    return await api.entityService.existByToOnePropertyAndToOneNullableProperty({
                        body: {
                            toOnePropertyId,
                            toOneNullablePropertyId: formData.value.toOneNullablePropertyId,
                        }
                    })
                }),
                trigger: "blur"
            },
        ],
        toOneNullablePropertyId: [
            {
                asyncValidator: asyncValidExist("toOneNullableProperty", async (toOneNullablePropertyId: number | undefined) => {
                    return await api.entityService.existByToOnePropertyAndToOneNullableProperty({
                        body: {
                            toOneNullablePropertyId,
                            toOnePropertyId: formData.value.toOnePropertyId,
                        }
                    })
                }),
                trigger: "blur"
            },
        ],
        enumProperty: [
            {
                asyncValidator: asyncValidExist("enumProperty", async (enumProperty: Enum) => {
                    return await api.entityService.existByEnumPropertyAndEnumNullableProperty({
                        body: {
                            enumProperty,
                            enumNullableProperty: formData.value.enumNullableProperty,
                        }
                    })
                }),
                trigger: "blur"
            },
        ],
        enumNullableProperty: [
            {
                asyncValidator: asyncValidExist("enumNullableProperty", async (enumNullableProperty: Enum | undefined) => {
                    return await api.entityService.existByEnumPropertyAndEnumNullableProperty({
                        body: {
                            enumNullableProperty,
                            enumProperty: formData.value.enumProperty,
                        }
                    })
                }),
                trigger: "blur"
            },
        ],
    }
}
            """.trimIndent(),
            existValidRulesWithoutId.result
        )
    }

    @ParameterizedTest
    @MethodSource("entities")
    fun `test existValidRules withId`(testEntity: GenEntityBusinessView) {
        val existValidRulesWithoutId = testEntity.existValidRules(withId = true)

        assertEquals(
            """
import type {Ref} from "vue"
import type {FormRules} from "element-plus"
import type {EntityDto} from "@/api/__generated/model/static"
import {api} from "@/api"
import {asyncValidExist} from "@/utils/asyncValidExist"
import type {Enum} from "@/api/__generated/model/enums"

export const useRules = (formData: Ref<EntityDto>): FormRules<EntityDto> => {
    return {
        toOnePropertyId: [
            {
                asyncValidator: asyncValidExist("toOneProperty", async (toOnePropertyId: number) => {
                    return await api.entityService.existByToOnePropertyAndToOneNullableProperty({
                        body: {
                            toOnePropertyId,
                            id: formData.value.id,
                            toOneNullablePropertyId: formData.value.toOneNullablePropertyId,
                        }
                    })
                }),
                trigger: "blur"
            },
        ],
        toOneNullablePropertyId: [
            {
                asyncValidator: asyncValidExist("toOneNullableProperty", async (toOneNullablePropertyId: number | undefined) => {
                    return await api.entityService.existByToOnePropertyAndToOneNullableProperty({
                        body: {
                            toOneNullablePropertyId,
                            id: formData.value.id,
                            toOnePropertyId: formData.value.toOnePropertyId,
                        }
                    })
                }),
                trigger: "blur"
            },
        ],
        enumProperty: [
            {
                asyncValidator: asyncValidExist("enumProperty", async (enumProperty: Enum) => {
                    return await api.entityService.existByEnumPropertyAndEnumNullableProperty({
                        body: {
                            enumProperty,
                            id: formData.value.id,
                            enumNullableProperty: formData.value.enumNullableProperty,
                        }
                    })
                }),
                trigger: "blur"
            },
        ],
        enumNullableProperty: [
            {
                asyncValidator: asyncValidExist("enumNullableProperty", async (enumNullableProperty: Enum | undefined) => {
                    return await api.entityService.existByEnumPropertyAndEnumNullableProperty({
                        body: {
                            enumNullableProperty,
                            id: formData.value.id,
                            enumProperty: formData.value.enumProperty,
                        }
                    })
                }),
                trigger: "blur"
            },
        ],
    }
}
            """.trimIndent(),
            existValidRulesWithoutId.result
        )
    }

    companion object {
        @JvmStatic
        fun entities() = listOf(
            toOneAndEnumEntity,
            toOneDuplicateAndEnumEntity,
            toOneIdViewAndEnumEntity,
            toOneIdViewDuplicateAndEntity
        )
    }
}