package top.potmot.business.existValid

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import top.potmot.core.business.view.generate.builder.rules.existValidRules

class ExistValidRulesTest {
    @Test
    fun `test existValidRules`() {
        listOf(
            toOneAndEnumEntity,
            toOneDuplicateAndEnumEntity
        ).forEach { testEntity ->
            val existValidRulesWithoutId = testEntity.existValidRules(withId = false)

            assertEquals(
                """
{toOneProperty={
    asyncValidator: asyncValidExist('toOneProperty', async (toOneProperty: ToOneEntity) => {
        return await api.entityService.existByToOnePropertyAndToOneNullableProperty({body: {toOneProperty, toOneNullableProperty: formData.value.toOneNullableProperty}})
    }),
    trigger: "blur"
}, toOneNullableProperty={
    asyncValidator: asyncValidExist('toOneNullableProperty', async (toOneNullableProperty: ToOneEntity) => {
        return await api.entityService.existByToOnePropertyAndToOneNullableProperty({body: {toOneNullableProperty, toOneProperty: formData.value.toOneProperty}})
    }),
    trigger: "blur"
}, enumProperty={
    asyncValidator: asyncValidExist('enumProperty', async (enumProperty: Enum) => {
        return await api.entityService.existByEnumPropertyAndEnumNullableProperty({body: {enumProperty, enumNullableProperty: formData.value.enumNullableProperty}})
    }),
    trigger: "blur"
}, enumNullableProperty={
    asyncValidator: asyncValidExist('enumNullableProperty', async (enumNullableProperty: Enum) => {
        return await api.entityService.existByEnumPropertyAndEnumNullableProperty({body: {enumNullableProperty, enumProperty: formData.value.enumProperty}})
    }),
    trigger: "blur"
}}
            """.trimIndent(),
                existValidRulesWithoutId.mapValues { it.value.joinToString { it.stringify() } }.toString()
            )

            val existValidRulesWithId = testEntity.existValidRules(withId = true)

            assertEquals(
                """
{toOneProperty={
    asyncValidator: asyncValidExist('toOneProperty', async (toOneProperty: ToOneEntity) => {
        return await api.entityService.existByToOnePropertyAndToOneNullableProperty({body: {toOneProperty, id: formData.value.id, toOneNullableProperty: formData.value.toOneNullableProperty}})
    }),
    trigger: "blur"
}, toOneNullableProperty={
    asyncValidator: asyncValidExist('toOneNullableProperty', async (toOneNullableProperty: ToOneEntity) => {
        return await api.entityService.existByToOnePropertyAndToOneNullableProperty({body: {toOneNullableProperty, id: formData.value.id, toOneProperty: formData.value.toOneProperty}})
    }),
    trigger: "blur"
}, enumProperty={
    asyncValidator: asyncValidExist('enumProperty', async (enumProperty: Enum) => {
        return await api.entityService.existByEnumPropertyAndEnumNullableProperty({body: {enumProperty, id: formData.value.id, enumNullableProperty: formData.value.enumNullableProperty}})
    }),
    trigger: "blur"
}, enumNullableProperty={
    asyncValidator: asyncValidExist('enumNullableProperty', async (enumNullableProperty: Enum) => {
        return await api.entityService.existByEnumPropertyAndEnumNullableProperty({body: {enumNullableProperty, id: formData.value.id, enumProperty: formData.value.enumProperty}})
    }),
    trigger: "blur"
}}
                """.trimIndent(),
                existValidRulesWithId.mapValues { it.value.joinToString { it.stringify() } }.toString()
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
{toOnePropertyId={
    asyncValidator: asyncValidExist('toOneProperty', async (toOnePropertyId: kotlin.Long) => {
        return await api.entityService.existByToOnePropertyIdAndToOneNullablePropertyId({body: {toOnePropertyId, toOneNullablePropertyId: formData.value.toOneNullablePropertyId}})
    }),
    trigger: "blur"
}, toOneNullablePropertyId={
    asyncValidator: asyncValidExist('toOneNullableProperty', async (toOneNullablePropertyId: kotlin.Long) => {
        return await api.entityService.existByToOnePropertyIdAndToOneNullablePropertyId({body: {toOneNullablePropertyId, toOnePropertyId: formData.value.toOnePropertyId}})
    }),
    trigger: "blur"
}, enumProperty={
    asyncValidator: asyncValidExist('enumProperty', async (enumProperty: Enum) => {
        return await api.entityService.existByEnumPropertyAndEnumNullableProperty({body: {enumProperty, enumNullableProperty: formData.value.enumNullableProperty}})
    }),
    trigger: "blur"
}, enumNullableProperty={
    asyncValidator: asyncValidExist('enumNullableProperty', async (enumNullableProperty: Enum) => {
        return await api.entityService.existByEnumPropertyAndEnumNullableProperty({body: {enumNullableProperty, enumProperty: formData.value.enumProperty}})
    }),
    trigger: "blur"
}}
            """.trimIndent(),
                existValidRulesWithoutId.mapValues { it.value.joinToString { it.stringify() } }.toString()
            )

            val existValidRulesWithId = testEntity.existValidRules(withId = true)

            assertEquals(
                """
{toOnePropertyId={
    asyncValidator: asyncValidExist('toOneProperty', async (toOnePropertyId: kotlin.Long) => {
        return await api.entityService.existByToOnePropertyIdAndToOneNullablePropertyId({body: {toOnePropertyId, id: formData.value.id, toOneNullablePropertyId: formData.value.toOneNullablePropertyId}})
    }),
    trigger: "blur"
}, toOneNullablePropertyId={
    asyncValidator: asyncValidExist('toOneNullableProperty', async (toOneNullablePropertyId: kotlin.Long) => {
        return await api.entityService.existByToOnePropertyIdAndToOneNullablePropertyId({body: {toOneNullablePropertyId, id: formData.value.id, toOnePropertyId: formData.value.toOnePropertyId}})
    }),
    trigger: "blur"
}, enumProperty={
    asyncValidator: asyncValidExist('enumProperty', async (enumProperty: Enum) => {
        return await api.entityService.existByEnumPropertyAndEnumNullableProperty({body: {enumProperty, id: formData.value.id, enumNullableProperty: formData.value.enumNullableProperty}})
    }),
    trigger: "blur"
}, enumNullableProperty={
    asyncValidator: asyncValidExist('enumNullableProperty', async (enumNullableProperty: Enum) => {
        return await api.entityService.existByEnumPropertyAndEnumNullableProperty({body: {enumNullableProperty, id: formData.value.id, enumProperty: formData.value.enumProperty}})
    }),
    trigger: "blur"
}}
                """.trimIndent(),
                existValidRulesWithId.mapValues { it.value.joinToString { it.stringify() } }.toString()
            )
        }
    }
}