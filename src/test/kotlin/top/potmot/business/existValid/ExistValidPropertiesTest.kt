package top.potmot.business.existValid

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import top.potmot.business.enumNullableProperty
import top.potmot.business.enumProperty
import top.potmot.business.toOneIdView
import top.potmot.business.toOneNullableIdView
import top.potmot.business.toOneNullableProperty
import top.potmot.business.toOneProperty
import top.potmot.core.business.utils.existValidItems
import top.potmot.error.ModelException

class ExistValidPropertiesTest {
    @Test
    fun `test existValidItems`() {
        listOf(
            toOneAndEnumEntity,
            toOneDuplicateAndEnumEntity,
        ).forEach { testEntity ->
            val existValidItems = testEntity.existValidItems

            assertEquals(
                2,
                existValidItems.size
            )

            val toOneExistByValidItem = existValidItems[0]
            assertEquals(
                2,
                toOneExistByValidItem.properties.size
            )
            assertEquals(
                "${toOneProperty.name}, ${toOneNullableProperty.name}",
                toOneExistByValidItem.properties.joinToString(", ") { it.name }
            )

            val enumExistByValidItem = existValidItems[1]
            assertEquals(
                2,
                enumExistByValidItem.properties.size
            )
            assertEquals(
                "${enumProperty.name}, ${enumNullableProperty.name}",
                enumExistByValidItem.properties.joinToString(", ") { it.name }
            )
        }
    }

    @Test
    fun `test toMany existValidItems as error`() {
        listOf(
            toManyEntity,
            toManyIdViewEntity,
        ).forEach { testEntity ->
            assertThrows<ModelException.IndexRefPropertyCannotBeList> {
                testEntity.existValidItems
            }
        }
    }

    @Test
    fun `test idView existValidItems`() {
        listOf(
            toOneIdViewAndEnumEntity,
            toOneIdViewDuplicateAndEntity,
        ).forEach { testEntity ->
            val existValidItems = testEntity.existValidItems

            assertEquals(
                2,
                existValidItems.size
            )

            val toOneExistByValidItem = existValidItems[0]
            assertEquals(
                2,
                toOneExistByValidItem.properties.size
            )
            assertEquals(
                "${toOneIdView.name}, ${toOneNullableIdView.name}",
                toOneExistByValidItem.properties.joinToString(", ") { it.name }
            )

            val enumExistByValidItem = existValidItems[1]
            assertEquals(
                2,
                enumExistByValidItem.properties.size
            )
            assertEquals(
                "${enumProperty.name}, ${enumNullableProperty.name}",
                enumExistByValidItem.properties.joinToString(", ") { it.name }
            )
        }
    }
}