package top.potmot.view.properties

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import top.potmot.core.business.view.generate.builder.property.ViewProperties
import top.potmot.entity.dto.GenEntityBusinessView
import top.potmot.entity.dto.GenEntityBusinessView.TargetOf_properties
import top.potmot.entity.dto.GenEntityBusinessView.TargetOf_idProperties
import top.potmot.entity.dto.GenEntityBusinessView.TargetOf_properties.TargetOf_typeEntity
import top.potmot.enumeration.AssociationType
import java.time.LocalDateTime

class ViewPropertiesTest : ViewProperties {
    private val baseProperty = TargetOf_properties(
        id = 0,
        name = "property",
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

    private val idProperty = baseProperty.copy(
        idProperty = true,
        name = "id",
        comment = "id",
        type = "kotlin.Int",
    )

    private val entity = GenEntityBusinessView(
        id = 0,
        name = "Entity",
        comment = "comment",
        author = "author",
        remark = "remark",
        packagePath = "EntityPackage",
        createdTime = LocalDateTime.now(),
        modifiedTime = LocalDateTime.now(),
        idProperties = listOf(
            TargetOf_idProperties(idProperty.toEntity())
        ),
        properties = listOf(
            idProperty,
            baseProperty.copy(
                name = "property1",
                type = "Entity1",
                comment = "property1",
                associationType = AssociationType.MANY_TO_ONE,
                typeEntity = TargetOf_typeEntity(
                    id = 0,
                    name = "Entity1",
                    packagePath = "Entity1PackagePath",
                    comment = "comment",
                    idProperties = listOf(
                        TargetOf_typeEntity.TargetOf_idProperties(
                            id = 0,
                            name = "id",
                            type = "kotlin.Long",
                            typeNotNull = true,
                        )
                    )
                )
            ),
            baseProperty.copy(
                name = "property2s",
                type = "Entity2",
                comment = "property2s",
                listType = true,
                associationType = AssociationType.MANY_TO_MANY,
                typeEntity = TargetOf_typeEntity(
                    id = 0,
                    name = "Entity2",
                    packagePath = "Entity2PackagePath",
                    comment = "comment",
                    idProperties = listOf(
                        TargetOf_typeEntity.TargetOf_idProperties(
                            id = 0,
                            name = "id",
                            type = "kotlin.String",
                            typeNotNull = true,
                        )
                    )
                )
            ),
        )
    )

    @Test
    fun testSelectProperties() {
        val selectProperties = entity.selectProperties
        assertEquals(1, selectProperties.size)
        assertEquals("property1Id", selectProperties[0].name)
        assertEquals("kotlin.Long", selectProperties[0].type)
        assertEquals("property1", selectProperties[0].comment)
        assertEquals(true, selectProperties[0].idView)
    }

    @Test
    fun testTableProperties() {
        val tableProperties = entity.tableProperties
        assertEquals(1, tableProperties.size)
        assertEquals("property1Id", tableProperties[0].name)
        assertEquals("kotlin.Long", tableProperties[0].type)
        assertEquals("property1", tableProperties[0].comment)
        assertEquals(true, tableProperties[0].idView)
    }

    @Test
    fun testQueryProperties() {
        val queryProperties = entity.queryProperties
        assertEquals(1, queryProperties.size)
        assertEquals("property1Id", queryProperties[0].name)
        assertEquals("kotlin.Long", queryProperties[0].type)
        assertEquals("property1", queryProperties[0].comment)
        assertEquals(true, queryProperties[0].idView)
    }

    @Test
    fun testAddFormProperties() {
        val addFormProperties = entity.addFormProperties
        assertEquals(1, addFormProperties.size)
        assertEquals("property1Id", addFormProperties[0].name)
        assertEquals("kotlin.Long", addFormProperties[0].type)
        assertEquals("property1", addFormProperties[0].comment)
        assertEquals(true, addFormProperties[0].idView)
    }

    @Test
    fun testEditFormProperties() {
        val editFormProperties = entity.editFormProperties
        assertEquals(2, editFormProperties.size)
        assertEquals("id", editFormProperties[0].name)
        assertEquals("kotlin.Int", editFormProperties[0].type)
        assertEquals("id", editFormProperties[0].comment)
        assertEquals(true, editFormProperties[0].idProperty)
        assertEquals(false, editFormProperties[0].idView)
        assertEquals("property1Id", editFormProperties[1].name)
        assertEquals("kotlin.Long", editFormProperties[1].type)
        assertEquals("property1", editFormProperties[1].comment)
        assertEquals(true, editFormProperties[1].idView)
    }

    @Test
    fun testEditTableProperties() {
        val editTableProperties = entity.editTableProperties
        assertEquals(1, editTableProperties.size)
        assertEquals("property1Id", editTableProperties[0].name)
        assertEquals("kotlin.Long", editTableProperties[0].type)
        assertEquals("property1", editTableProperties[0].comment)
        assertEquals(true, editTableProperties[0].idView)
    }
}
