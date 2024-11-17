package top.potmot.business.existValid

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import top.potmot.business.enumNullableProperty
import top.potmot.business.enumProperty
import top.potmot.business.idViewTestEntity
import top.potmot.business.testEntity
import top.potmot.business.toOneIdView
import top.potmot.business.toOneNullableIdView
import top.potmot.business.toOneNullableProperty
import top.potmot.business.toOneProperty
import top.potmot.core.business.utils.ExistByValid
import top.potmot.error.ModelException

class ExistValidPropertiesTest : ExistByValid {
    @Test
    fun `test existByValidItems`() {
        val existByValidItems = testEntity.copy(
            indexes = listOf(toOneUniqueIndex, enumUniqueIndex)
        ).existByValidItems

        assertEquals(
            2,
            existByValidItems.size
        )

        val toOneExistByValidItem = existByValidItems[0]
        assertEquals(
            2,
            toOneExistByValidItem.properties.size
        )
        assertEquals(
            "${toOneProperty.name}, ${toOneNullableProperty.name}",
            toOneExistByValidItem.properties.joinToString(", ") { it.name }
        )

        val enumExistByValidItem = existByValidItems[1]
        assertEquals(
            2,
            enumExistByValidItem.properties.size
        )
        assertEquals(
            "${enumProperty.name}, ${enumNullableProperty.name}",
            enumExistByValidItem.properties.joinToString(", ") { it.name }
        )
    }

    @Test
    fun `test duplicate existByValidItems`() {
        val existByValidItems = testEntity.copy(
            indexes = listOf(toOneUniqueIndex, toOneUniqueIndex, enumUniqueIndex)
        ).existByValidItems

        assertEquals(
            2,
            existByValidItems.size
        )

        val toOneExistByValidItem = existByValidItems[0]
        assertEquals(
            2,
            toOneExistByValidItem.properties.size
        )
        assertEquals(
            "${toOneProperty.name}, ${toOneNullableProperty.name}",
            toOneExistByValidItem.properties.joinToString(", ") { it.name }
        )

        val enumExistByValidItem = existByValidItems[1]
        assertEquals(
            2,
            enumExistByValidItem.properties.size
        )
        assertEquals(
            "${enumProperty.name}, ${enumNullableProperty.name}",
            enumExistByValidItem.properties.joinToString(", ") { it.name }
        )
    }

    @Test
    fun `test toMany existByValidItems as error`() {
        assertThrows<ModelException.IndexRefPropertyCannotBeList> {
            testEntity.copy(
                indexes = listOf(toManyUniqueIndex)
            ).existByValidItems
        }
    }

        @Test
    fun `test idView existByValidItems`() {
        val existByValidItems = idViewTestEntity.copy(
            indexes = listOf(toOneIdViewUniqueIndex, enumUniqueIndex)
        ).existByValidItems

        assertEquals(
            2,
            existByValidItems.size
        )

        val toOneExistByValidItem = existByValidItems[0]
        assertEquals(
            2,
            toOneExistByValidItem.properties.size
        )
        assertEquals(
            "${toOneIdView.name}, ${toOneNullableIdView.name}",
            toOneExistByValidItem.properties.joinToString(", ") { it.name }
        )

        val enumExistByValidItem = existByValidItems[1]
        assertEquals(
            2,
            enumExistByValidItem.properties.size
        )
        assertEquals(
            "${enumProperty.name}, ${enumNullableProperty.name}",
            enumExistByValidItem.properties.joinToString(", ") { it.name }
        )
    }

    @Test
    fun `test idView duplicate existByValidItems`() {
        val existByValidItems = idViewTestEntity.copy(
            indexes = listOf(toOneIdViewUniqueIndex, toOneIdViewUniqueIndex, enumUniqueIndex)
        ).existByValidItems

        assertEquals(
            2,
            existByValidItems.size
        )

        val toOneExistByValidItem = existByValidItems[0]
        assertEquals(
            2,
            toOneExistByValidItem.properties.size
        )
        assertEquals(
            "${toOneIdView.name}, ${toOneNullableIdView.name}",
            toOneExistByValidItem.properties.joinToString(", ") { it.name }
        )

        val enumExistByValidItem = existByValidItems[1]
        assertEquals(
            2,
            enumExistByValidItem.properties.size
        )
        assertEquals(
            "${enumProperty.name}, ${enumNullableProperty.name}",
            enumExistByValidItem.properties.joinToString(", ") { it.name }
        )
    }

    @Test
    fun `test idView toMany existByValidItems as error`() {
        assertThrows<ModelException.IndexRefPropertyCannotBeList> {
            idViewTestEntity.copy(
                indexes = listOf(toManyIdViewUniqueIndex)
            ).existByValidItems
        }
    }
}