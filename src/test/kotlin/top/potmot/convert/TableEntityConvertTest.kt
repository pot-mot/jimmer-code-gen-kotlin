package top.potmot.convert

import org.babyfish.jimmer.sql.DissociateAction
import org.babyfish.jimmer.sql.GenerationType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import top.potmot.core.convert.columnToProperties
import top.potmot.enum.AssociationType
import top.potmot.model.dto.GenTableAssociationView
import java.sql.Types
import java.time.LocalDateTime

@SpringBootTest
@ActiveProfiles("test-kotlin")
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class TableEntityConvertTest {
    @Test
    @Order(1)
    fun testIdColumnConvert() {
        val name = "id_property"
        val comment = "PK COLUMN"

        val column = GenTableAssociationView.TargetOf_columns(
            1, LocalDateTime.now(), LocalDateTime.now(), "remark", 0,
            name, Types.BIGINT, "bigint", 0, 0, null, comment,
            true, true, false, true, true, 1
        )

        val properties = columnToProperties(column)

        assertEquals(1, properties.size)

        val idProperty = properties[0]

        assertEquals("idProperty", idProperty.name)
        assertEquals("kotlin.Long", idProperty.type)
        assertEquals(comment, idProperty.comment)
        assertEquals(column.id, idProperty.columnId)

        assert(idProperty.isId)
        assert(idProperty.isNotNull)
        assertEquals(GenerationType.IDENTITY, idProperty.idGenerationType)
        assert(!idProperty.isKey)
        assert(!idProperty.isList)
        assert(!idProperty.isLogicalDelete)
        assert(!idProperty.isIdView)
    }

    @Test
    @Order(2)
    fun testOutColumnConvert() {
        val column = GenTableAssociationView.TargetOf_columns(
            1, LocalDateTime.now(), LocalDateTime.now(), "remark", 0,
            "many_to_one_property", Types.BIGINT, "bigint", 0, 0, null, "comment",
            false, false, true, false, false, 1,
            outAssociations = listOf(
                GenTableAssociationView.TargetOf_columns.TargetOf_outAssociations(
                    1, AssociationType.MANY_TO_ONE, DissociateAction.DELETE,
                    GenTableAssociationView.TargetOf_columns.TargetOf_outAssociations.TargetOf_targetColumn(
                        2, "one_to_many_property", "",
                        GenTableAssociationView.TargetOf_columns.TargetOf_outAssociations.TargetOf_targetColumn.TargetOf_table(
                            2, "table2", ""
                        )
                    )
                )
            )
        )

        val properties = columnToProperties(column)

        assertEquals(2, properties.size)

        val manyToOneProperty = properties[0]

        assertEquals("table2", manyToOneProperty.name)
        assertEquals("Table2", manyToOneProperty.type)
        assertEquals(2, manyToOneProperty.typeTableId)

        assert(!manyToOneProperty.isNotNull)
        assert(!manyToOneProperty.isKey)
        assertEquals(AssociationType.MANY_TO_ONE, manyToOneProperty.associationType)
        assertEquals("@ManyToOne", manyToOneProperty.associationAnnotation)
        assertEquals("@OnDissociate(DissociateAction.DELETE)", manyToOneProperty.dissociateAnnotation)

        val idViewProperty = properties[1]

        assertEquals("table2Id", idViewProperty.name)
        assertEquals("kotlin.Long", idViewProperty.type)

        assert(idViewProperty.isIdView)
        assertEquals(AssociationType.MANY_TO_ONE, idViewProperty.associationType)
        assertEquals("@IdView(\"${manyToOneProperty.name}\")", idViewProperty.idViewAnnotation)
    }

    @Test
    @Order(3)
    fun testInColumnConvert() {
        val column = GenTableAssociationView.TargetOf_columns(
            1, LocalDateTime.now(), LocalDateTime.now(), "remark", 0,
            "one_to_many_property", Types.BIGINT, "bigint", 0, 0, null, "comment",
            false, false, false, false, false, 1,
            inAssociations = listOf(
                GenTableAssociationView.TargetOf_columns.TargetOf_inAssociations(
                    1, AssociationType.MANY_TO_ONE, DissociateAction.DELETE,
                    GenTableAssociationView.TargetOf_columns.TargetOf_inAssociations.TargetOf_sourceColumn(
                        2, "many_to_one_property", "",
                        GenTableAssociationView.TargetOf_columns.TargetOf_inAssociations.TargetOf_sourceColumn.TargetOf_table(
                            2, "table2", ""
                        )
                    )
                )
            )
        )

        val properties = columnToProperties(column)

        assertEquals(3, properties.size)

        val baseProperty = properties[0]

        assertEquals("oneToManyProperty", baseProperty.name)
        assertEquals("kotlin.Long", baseProperty.type)

        val oneToManyProperty = properties[1]

        assertEquals("table2s", oneToManyProperty.name)
        assertEquals("Table2", oneToManyProperty.type)
        assertEquals(2, oneToManyProperty.typeTableId)
        assert(oneToManyProperty.isList)

        assert(!oneToManyProperty.isNotNull)
        assert(!oneToManyProperty.isKey)
        assertEquals(AssociationType.ONE_TO_MANY, oneToManyProperty.associationType)
        assertEquals("@OneToMany(mappedBy = \"table2\")", oneToManyProperty.associationAnnotation)

        val idViewProperty = properties[2]

        assertEquals("table2Ids", idViewProperty.name)
        assertEquals("kotlin.Long", idViewProperty.type)
        assert(oneToManyProperty.isList)

        assert(idViewProperty.isIdView)
        assertEquals(AssociationType.ONE_TO_MANY, idViewProperty.associationType)
        assertEquals("@IdView(\"${oneToManyProperty.name}\")", idViewProperty.idViewAnnotation)
    }
}
