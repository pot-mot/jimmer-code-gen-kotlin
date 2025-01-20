package top.potmot.business.view.properties

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import top.potmot.business.idViewTestEntityBusiness
import top.potmot.business.testEntityBusiness
import top.potmot.core.business.meta.PropertyBusiness
import top.potmot.entity.dto.GenEntityBusinessView

class EntityPropertyCategoriesTest {
    private val testEntities = listOf(testEntityBusiness, idViewTestEntityBusiness)

    private val Iterable<PropertyBusiness>.extract: List<GenEntityBusinessView.TargetOf_properties>
        get() = map {
            it.property
        }

    @Test
    fun `test pageSelectProperties`() {
        testEntities.forEach { testEntity ->
            val selectProperties = testEntity.pageSelectPropertyBusiness.extract
            assertEquals(2, selectProperties.size)

            val toOneProperty = selectProperties[0]
            assertEquals("toOnePropertyId", toOneProperty.name)
            assertEquals("kotlin.Int", toOneProperty.type)
            assertEquals("toOneProperty", toOneProperty.comment)
            assertEquals(true, toOneProperty.idView)
            assertEquals(true, toOneProperty.typeNotNull)

            val toOneNullableProperty = selectProperties[1]
            assertEquals("toOneNullablePropertyId", toOneNullableProperty.name)
            assertEquals("kotlin.Int", toOneNullableProperty.type)
            assertEquals("toOneNullableProperty", toOneNullableProperty.comment)
            assertEquals(true, toOneNullableProperty.idView)
            assertEquals(false, toOneNullableProperty.typeNotNull)
        }
    }

    @Test
    fun `test tableProperties`() {
        testEntities.forEach { testEntity ->
            val tableProperties = testEntity.tablePropertyBusiness.extract
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
            assertEquals("kotlin.Int", toOneProperty.type)
            assertEquals("toOneProperty", toOneProperty.comment)
            assertEquals(true, toOneProperty.idView)
            assertEquals(true, toOneProperty.typeNotNull)

            val toOneNullableProperty = tableProperties[3]
            assertEquals("toOneNullablePropertyId", toOneNullableProperty.name)
            assertEquals("kotlin.Int", toOneNullableProperty.type)
            assertEquals("toOneNullableProperty", toOneNullableProperty.comment)
            assertEquals(true, toOneNullableProperty.idView)
            assertEquals(false, toOneNullableProperty.typeNotNull)
        }
    }

    @Test
    fun `test queryProperties`() {
        testEntities.forEach { testEntity ->
            val queryProperties = testEntity.queryPropertyBusiness.extract
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
            assertEquals("kotlin.Int", toOneProperty.type)
            assertEquals("toOneProperty", toOneProperty.comment)
            assertEquals(true, toOneProperty.idView)
            assertEquals(false, toOneProperty.typeNotNull)

            val toOneNullableProperty = queryProperties[3]
            assertEquals("toOneNullablePropertyId", toOneNullableProperty.name)
            assertEquals("kotlin.Int", toOneNullableProperty.type)
            assertEquals("toOneNullableProperty", toOneNullableProperty.comment)
            assertEquals(true, toOneNullableProperty.idView)
            assertEquals(false, toOneNullableProperty.typeNotNull)
        }
    }

    @Test
    fun `test addFormProperties`() {
        testEntities.forEach { testEntity ->
            val addFormProperties = testEntity.addFormPropertyBusiness.extract
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
            assertEquals("kotlin.Int", toOneProperty.type)
            assertEquals("toOneProperty", toOneProperty.comment)
            assertEquals(true, toOneProperty.idView)
            assertEquals(false, toOneProperty.typeNotNull)

            val toOneNullableProperty = addFormProperties[3]
            assertEquals("toOneNullablePropertyId", toOneNullableProperty.name)
            assertEquals("kotlin.Int", toOneNullableProperty.type)
            assertEquals("toOneNullableProperty", toOneNullableProperty.comment)
            assertEquals(true, toOneNullableProperty.idView)
            assertEquals(false, toOneNullableProperty.typeNotNull)
        }
    }

    @Test
    fun `test editFormProperties`() {
        testEntities.forEach { testEntity ->
            val editFormProperties = testEntity.editFormPropertyBusiness.extract
            assertEquals(4, editFormProperties.size)

            val enumProperty = editFormProperties[0]
            assertEquals("enumProperty", enumProperty.name)
            assertEquals("Enum", enumProperty.type)
            assertEquals("enumProperty", enumProperty.comment)
            assertEquals(true, enumProperty.typeNotNull)

            val enumNullableProperty = editFormProperties[1]
            assertEquals("enumNullableProperty", enumNullableProperty.name)
            assertEquals("Enum", enumNullableProperty.type)
            assertEquals("enumNullableProperty", enumNullableProperty.comment)
            assertEquals(false, enumNullableProperty.typeNotNull)

            val toOneProperty = editFormProperties[2]
            assertEquals("toOnePropertyId", toOneProperty.name)
            assertEquals("kotlin.Int", toOneProperty.type)
            assertEquals("toOneProperty", toOneProperty.comment)
            assertEquals(true, toOneProperty.idView)
            assertEquals(true, toOneProperty.typeNotNull)

            val toOneNullableProperty = editFormProperties[3]
            assertEquals("toOneNullablePropertyId", toOneNullableProperty.name)
            assertEquals("kotlin.Int", toOneNullableProperty.type)
            assertEquals("toOneNullableProperty", toOneNullableProperty.comment)
            assertEquals(true, toOneNullableProperty.idView)
            assertEquals(false, toOneNullableProperty.typeNotNull)
        }
    }

    @Test
    fun `test editTableProperties`() {
        testEntities.forEach { testEntity ->
            val editTableProperties = testEntity.editTablePropertyBusiness.extract
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
            assertEquals("kotlin.Int", toOneProperty.type)
            assertEquals("toOneProperty", toOneProperty.comment)
            assertEquals(true, toOneProperty.idView)
            assertEquals(false, toOneProperty.typeNotNull)

            val toOneNullableProperty = editTableProperties[3]
            assertEquals("toOneNullablePropertyId", toOneNullableProperty.name)
            assertEquals("kotlin.Int", toOneNullableProperty.type)
            assertEquals("toOneNullableProperty", toOneNullableProperty.comment)
            assertEquals(true, toOneNullableProperty.idView)
            assertEquals(false, toOneNullableProperty.typeNotNull)
        }
    }
}
