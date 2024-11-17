package top.potmot.business.view.properties

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import top.potmot.business.idViewTestEntity
import top.potmot.business.testEntity
import top.potmot.core.business.view.generate.builder.property.ViewProperties

class ViewPropertiesTest : ViewProperties {
    private val testEntities = listOf(testEntity, idViewTestEntity)

    @Test
    fun testSelectProperties() {
        testEntities.forEach { testEntity ->
            val selectProperties = testEntity.selectProperties
            assertEquals(2, selectProperties.size)

            val toOneProperty = selectProperties[0]
            assertEquals("toOnePropertyId", toOneProperty.name)
            assertEquals("kotlin.Long", toOneProperty.type)
            assertEquals("toOneProperty", toOneProperty.comment)
            assertEquals(true, toOneProperty.idView)
            assertEquals(true, toOneProperty.typeNotNull)

            val toOneNullableProperty = selectProperties[1]
            assertEquals("toOneNullablePropertyId", toOneNullableProperty.name)
            assertEquals("kotlin.Long", toOneNullableProperty.type)
            assertEquals("toOneNullableProperty", toOneNullableProperty.comment)
            assertEquals(true, toOneNullableProperty.idView)
            assertEquals(false, toOneNullableProperty.typeNotNull)
        }
    }

    @Test
    fun testTableProperties() {
        testEntities.forEach { testEntity ->
            val tableProperties = testEntity.tableProperties
            assertEquals(4, tableProperties.size)

            val enumProperty = tableProperties[0]
            assertEquals("enumProperty", enumProperty.name)
            assertEquals("Enum", enumProperty.type)
            assertEquals("enumProperty", enumProperty.comment)
            assertEquals(true, enumProperty.typeNotNull)

            val enumNullableProperty = tableProperties[1]
            assertEquals("enumNullableProperty", enumNullableProperty.name)
            assertEquals("Enum", enumNullableProperty.type)
            assertEquals("enumNullableProperty", enumNullableProperty.comment)
            assertEquals(false, enumNullableProperty.typeNotNull)

            val toOneProperty = tableProperties[2]
            assertEquals("toOnePropertyId", toOneProperty.name)
            assertEquals("kotlin.Long", toOneProperty.type)
            assertEquals("toOneProperty", toOneProperty.comment)
            assertEquals(true, toOneProperty.idView)
            assertEquals(true, toOneProperty.typeNotNull)

            val toOneNullableProperty = tableProperties[3]
            assertEquals("toOneNullablePropertyId", toOneNullableProperty.name)
            assertEquals("kotlin.Long", toOneNullableProperty.type)
            assertEquals("toOneNullableProperty", toOneNullableProperty.comment)
            assertEquals(true, toOneNullableProperty.idView)
            assertEquals(false, toOneNullableProperty.typeNotNull)
        }
    }

    @Test
    fun testQueryProperties() {
        testEntities.forEach { testEntity ->
            val queryProperties = testEntity.queryProperties
            assertEquals(4, queryProperties.size)

            val enumProperty = queryProperties[0]
            assertEquals("enumProperty", enumProperty.name)
            assertEquals("Enum", enumProperty.type)
            assertEquals("enumProperty", enumProperty.comment)
            assertEquals(false, enumProperty.typeNotNull)

            val enumNullableProperty = queryProperties[1]
            assertEquals("enumNullableProperty", enumNullableProperty.name)
            assertEquals("Enum", enumNullableProperty.type)
            assertEquals("enumNullableProperty", enumNullableProperty.comment)
            assertEquals(false, enumNullableProperty.typeNotNull)

            val toOneProperty = queryProperties[2]
            assertEquals("toOnePropertyId", toOneProperty.name)
            assertEquals("kotlin.Long", toOneProperty.type)
            assertEquals("toOneProperty", toOneProperty.comment)
            assertEquals(true, toOneProperty.idView)
            assertEquals(false, toOneProperty.typeNotNull)

            val toOneNullableProperty = queryProperties[3]
            assertEquals("toOneNullablePropertyId", toOneNullableProperty.name)
            assertEquals("kotlin.Long", toOneNullableProperty.type)
            assertEquals("toOneNullableProperty", toOneNullableProperty.comment)
            assertEquals(true, toOneNullableProperty.idView)
            assertEquals(false, toOneNullableProperty.typeNotNull)
        }
    }

    @Test
    fun testAddFormProperties() {
        testEntities.forEach { testEntity ->
            val addFormProperties = testEntity.addFormProperties
            assertEquals(4, addFormProperties.size)

            val enumProperty = addFormProperties[0]
            assertEquals("enumProperty", enumProperty.name)
            assertEquals("Enum", enumProperty.type)
            assertEquals("enumProperty", enumProperty.comment)
            assertEquals(true, enumProperty.typeNotNull)

            val enumNullableProperty = addFormProperties[1]
            assertEquals("enumNullableProperty", enumNullableProperty.name)
            assertEquals("Enum", enumNullableProperty.type)
            assertEquals("enumNullableProperty", enumNullableProperty.comment)
            assertEquals(false, enumNullableProperty.typeNotNull)

            val toOneProperty = addFormProperties[2]
            assertEquals("toOnePropertyId", toOneProperty.name)
            assertEquals("kotlin.Long", toOneProperty.type)
            assertEquals("toOneProperty", toOneProperty.comment)
            assertEquals(true, toOneProperty.idView)
            assertEquals(false, toOneProperty.typeNotNull)

            val toOneNullableProperty = addFormProperties[3]
            assertEquals("toOneNullablePropertyId", toOneNullableProperty.name)
            assertEquals("kotlin.Long", toOneNullableProperty.type)
            assertEquals("toOneNullableProperty", toOneNullableProperty.comment)
            assertEquals(true, toOneNullableProperty.idView)
            assertEquals(false, toOneNullableProperty.typeNotNull)
        }
    }

    @Test
    fun testEditFormProperties() {
        testEntities.forEach { testEntity ->
            val editFormProperties = testEntity.editFormProperties
            assertEquals(5, editFormProperties.size)

            val enumProperty = editFormProperties[1]
            assertEquals("enumProperty", enumProperty.name)
            assertEquals("Enum", enumProperty.type)
            assertEquals("enumProperty", enumProperty.comment)
            assertEquals(true, enumProperty.typeNotNull)

            val enumNullableProperty = editFormProperties[2]
            assertEquals("enumNullableProperty", enumNullableProperty.name)
            assertEquals("Enum", enumNullableProperty.type)
            assertEquals("enumNullableProperty", enumNullableProperty.comment)
            assertEquals(false, enumNullableProperty.typeNotNull)

            val toOneProperty = editFormProperties[3]
            assertEquals("toOnePropertyId", toOneProperty.name)
            assertEquals("kotlin.Long", toOneProperty.type)
            assertEquals("toOneProperty", toOneProperty.comment)
            assertEquals(true, toOneProperty.idView)
            assertEquals(true, toOneProperty.typeNotNull)

            val toOneNullableProperty = editFormProperties[4]
            assertEquals("toOneNullablePropertyId", toOneNullableProperty.name)
            assertEquals("kotlin.Long", toOneNullableProperty.type)
            assertEquals("toOneNullableProperty", toOneNullableProperty.comment)
            assertEquals(true, toOneNullableProperty.idView)
            assertEquals(false, toOneNullableProperty.typeNotNull)
        }
    }

    @Test
    fun testEditTableProperties() {
        testEntities.forEach { testEntity ->
            val editTableProperties = testEntity.editTableProperties
            assertEquals(4, editTableProperties.size)

            val enumProperty = editTableProperties[0]
            assertEquals("enumProperty", enumProperty.name)
            assertEquals("Enum", enumProperty.type)
            assertEquals("enumProperty", enumProperty.comment)
            assertEquals(true, enumProperty.typeNotNull)

            val enumNullableProperty = editTableProperties[1]
            assertEquals("enumNullableProperty", enumNullableProperty.name)
            assertEquals("Enum", enumNullableProperty.type)
            assertEquals("enumNullableProperty", enumNullableProperty.comment)
            assertEquals(false, enumNullableProperty.typeNotNull)

            val toOneProperty = editTableProperties[2]
            assertEquals("toOnePropertyId", toOneProperty.name)
            assertEquals("kotlin.Long", toOneProperty.type)
            assertEquals("toOneProperty", toOneProperty.comment)
            assertEquals(true, toOneProperty.idView)
            assertEquals(false, toOneProperty.typeNotNull)

            val toOneNullableProperty = editTableProperties[3]
            assertEquals("toOneNullablePropertyId", toOneNullableProperty.name)
            assertEquals("kotlin.Long", toOneNullableProperty.type)
            assertEquals("toOneNullableProperty", toOneNullableProperty.comment)
            assertEquals(true, toOneNullableProperty.idView)
            assertEquals(false, toOneNullableProperty.typeNotNull)
        }
    }
}
