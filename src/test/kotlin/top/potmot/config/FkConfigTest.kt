package top.potmot.config

import org.babyfish.jimmer.sql.DissociateAction
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import top.potmot.core.entity.convert.toGenEntity
import top.potmot.enumeration.AssociationType
import top.potmot.enumeration.DataSourceType
import top.potmot.enumeration.GenLanguage
import top.potmot.enumeration.TableType
import top.potmot.model.dto.GenTableAssociationsView
import top.potmot.service.ConfigService
import java.sql.Types
import java.time.LocalDateTime

@SpringBootTest
@ActiveProfiles("test-kotlin", "h2")
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class FkConfigTest(
    @Autowired val configService: ConfigService
) {
    @Test
    @Order(1)
    fun testRealAssociationRealFkConfig() {
        configService.setConfig(GenConfigProperties(realFk = true, idViewProperty = false))
        assertEquals(true, GenConfig.realFk)

        val entity = table.toGenEntity(
            null,
            DataSourceType.MySQL,
            GenLanguage.KOTLIN,
            "",
            emptyList()
        )

        assert(entity.properties.size == 1)

        val propertyStr = entity.properties[0].toString()
        assert(!propertyStr.contains("foreignKeyType = ForeignKeyType.REAL"))
        assert(!propertyStr.contains("foreignKeyType = ForeignKeyType.FAKE"))
    }

    @Test
    @Order(2)
    fun testRealAssociationFakeFkConfig() {
        configService.setConfig(GenConfigProperties(realFk = false, idViewProperty = false))
        assertEquals(false, GenConfig.realFk)

        val entity = table.toGenEntity(
            null,
            DataSourceType.MySQL,
            GenLanguage.KOTLIN,
            "",
            emptyList()
        )

        assert(entity.properties.size == 1)

        val propertyStr = entity.properties[0].toString()
        assert(propertyStr.contains("foreignKeyType = ForeignKeyType.REAL"))
        assert(!propertyStr.contains("foreignKeyType = ForeignKeyType.FAKE"))
    }

    @Test
    @Order(3)
    fun testFakeAssociationRealFkConfig() {
        table.outAssociations[0].fake = true

        configService.setConfig(GenConfigProperties(realFk = true, idViewProperty = false))
        assertEquals(true, GenConfig.realFk)

        val entity = table.toGenEntity(
            null,
            DataSourceType.MySQL,
            GenLanguage.KOTLIN,
            "",
            emptyList()
        )

        assert(entity.properties.size == 1)

        val propertyStr = entity.properties[0].toString()
        assert(!propertyStr.contains("foreignKeyType = ForeignKeyType.REAL"))
        assert(propertyStr.contains("foreignKeyType = ForeignKeyType.FAKE"))
    }

    @Test
    @Order(4)
    fun testFakeAssociationFakeFkConfig() {
        table.outAssociations[0].fake = true

        configService.setConfig(GenConfigProperties(realFk = false, idViewProperty = false))
        assertEquals(false, GenConfig.realFk)

        val entity = table.toGenEntity(
            null,
            DataSourceType.MySQL,
            GenLanguage.KOTLIN,
            "",
            emptyList()
        )

        assert(entity.properties.size == 1)

        val propertyStr = entity.properties[0].toString()
        assert(!propertyStr.contains("foreignKeyType = ForeignKeyType.REAL"))
        assert(!propertyStr.contains("foreignKeyType = ForeignKeyType.FAKE"))
    }

    val table = GenTableAssociationsView(
        id = 1,
        createdTime = LocalDateTime.now(),
        modifiedTime = LocalDateTime.now(),
        remark = "Some remark",
        name = "table1",
        comment = "Table comment",
        type = TableType.TABLE,
        orderKey = 1,
        entityId = 123,
        schema = null,
        columns = listOf(
            GenTableAssociationsView.TargetOf_columns(
                id = 1,
                createdTime = LocalDateTime.now(),
                modifiedTime = LocalDateTime.now(),
                remark = "Some remark",
                orderKey = 1,
                name = "column1",
                typeCode = Types.BIGINT,
                overwriteByType = false,
                type = "",
                displaySize = 11,
                numericPrecision = 0,
                defaultValue = "",
                comment = "Column comment",
                partOfPk = false,
                autoIncrement = false,
                typeNotNull = true,
                businessKey = false,
                logicalDelete = false,
                tableId = 1,
                enumId = null,
            )
        ),
        indexes = emptyList(),
        inAssociations = emptyList(),
        outAssociations = listOf(
            GenTableAssociationsView.TargetOf_outAssociations(
                id = 2,
                createdTime = LocalDateTime.now(),
                modifiedTime = LocalDateTime.now(),
                remark = "Some remark",
                name = "association2",
                associationType = AssociationType.MANY_TO_ONE,
                dissociateAction = DissociateAction.SET_NULL,
                fake = false,
                orderKey = 1,
                targetTable = GenTableAssociationsView.TargetOf_outAssociations.TargetOf_targetTable_2(
                    id = 2,
                    name = "table2",
                    comment = ""
                ),
                columnReferences = listOf(
                    GenTableAssociationsView.TargetOf_outAssociations.TargetOf_columnReferences_2(
                        sourceColumn = GenTableAssociationsView.TargetOf_outAssociations.TargetOf_columnReferences_2.TargetOf_sourceColumn_3(
                            id = 1
                        ),
                        targetColumn = GenTableAssociationsView.TargetOf_outAssociations.TargetOf_columnReferences_2.TargetOf_targetColumn_3(
                            id = 2,
                            name = "column2",
                            comment = "",
                            type = "",
                        )
                    )
                )
            )
        )
    )
}
