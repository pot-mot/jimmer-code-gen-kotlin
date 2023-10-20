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
import top.potmot.enumeration.AssociationType
import top.potmot.model.dto.GenTableAssociationView
import java.sql.Types
import java.time.LocalDateTime

@SpringBootTest
@ActiveProfiles("test-kotlin", "mysql")
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class ColumnPropertyConvertTest {
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

        assert(idProperty.idProperty)
        assert(idProperty.notNull)
        assertEquals(GenerationType.IDENTITY, idProperty.idGenerationType)
        assert(!idProperty.keyProperty)
        assert(!idProperty.listType)
        assert(!idProperty.logicalDelete)
        assert(!idProperty.idView)
    }

    @Test
    @Order(2)
    fun testOutManyColumnConvert() {
        val column = GenTableAssociationView.TargetOf_columns(
            1, LocalDateTime.now(), LocalDateTime.now(), "remark", 0,
            "many_to_one_property", Types.BIGINT, "bigint", 0, 0, null, "comment",
            false, false, true, false, false, 1,
            outAssociations = listOf(
                GenTableAssociationView.TargetOf_columns.TargetOf_outAssociations(
                    1, AssociationType.MANY_TO_ONE, DissociateAction.DELETE, true,
                    GenTableAssociationView.TargetOf_columns.TargetOf_outAssociations.TargetOf_targetColumn(
                        2, "one_to_many_property", "",
                        GenTableAssociationView.TargetOf_columns.TargetOf_outAssociations.TargetOf_targetColumn.TargetOf_table(
                            2, "table2", ""
                        )
                    )
                ),
            )
        )

        val properties = columnToProperties(column)

        assertEquals(2, properties.size)

        val manyToOneProperty = properties[0]

        assertEquals("manyToOneProperty", manyToOneProperty.name)
        assertEquals("Table2", manyToOneProperty.type)
        assertEquals(2, manyToOneProperty.typeTableId)

        assert(!manyToOneProperty.notNull)
        assert(!manyToOneProperty.keyProperty)
        assertEquals(AssociationType.MANY_TO_ONE, manyToOneProperty.associationType)
        assertEquals("@ManyToOne", manyToOneProperty.associationAnnotation)
        assertEquals("@OnDissociate(DissociateAction.DELETE)", manyToOneProperty.dissociateAnnotation)

        val idViewProperty = properties[1]

        assertEquals("manyToOnePropertyId", idViewProperty.name)
        assertEquals("kotlin.Long", idViewProperty.type)

        assert(idViewProperty.idView)
        assertEquals(AssociationType.MANY_TO_ONE, idViewProperty.associationType)
        assertEquals("@IdView(\"${manyToOneProperty.name}\")", idViewProperty.idViewAnnotation)
    }

    @Test
    @Order(3)
    fun testInManyColumnConvert() {
        val column = GenTableAssociationView.TargetOf_columns(
            1, LocalDateTime.now(), LocalDateTime.now(), "remark", 0,
            "one_to_many_property", Types.BIGINT, "bigint", 0, 0, null, "comment",
            false, false, false, false, false, 1,
            inAssociations = listOf(
                GenTableAssociationView.TargetOf_columns.TargetOf_inAssociations(
                    1, AssociationType.MANY_TO_ONE, DissociateAction.DELETE, true,
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
        assert(oneToManyProperty.listType)

        assert(oneToManyProperty.notNull)
        assert(!oneToManyProperty.keyProperty)
        assertEquals(AssociationType.ONE_TO_MANY, oneToManyProperty.associationType)
        assertEquals("@OneToMany(mappedBy = \"manyToOneProperty\")", oneToManyProperty.associationAnnotation)

        val idViewProperty = properties[2]

        assertEquals("table2Ids", idViewProperty.name)
        assertEquals("kotlin.Long", idViewProperty.type)
        assert(oneToManyProperty.listType)

        assert(idViewProperty.idView)
        assertEquals(AssociationType.ONE_TO_MANY, idViewProperty.associationType)
        assertEquals("@IdView(\"${oneToManyProperty.name}\")", idViewProperty.idViewAnnotation)
    }

    @Test
    @Order(4)
    fun testOutOneColumnConvert() {
        val column = GenTableAssociationView.TargetOf_columns(
            1, LocalDateTime.now(), LocalDateTime.now(), "remark", 0,
            "one_to_one_property", Types.BIGINT, "bigint", 0, 0, null, "comment",
            false, false, true, true, true, 1,
            outAssociations = listOf(
                GenTableAssociationView.TargetOf_columns.TargetOf_outAssociations(
                    1, AssociationType.ONE_TO_ONE, DissociateAction.DELETE, true,
                    GenTableAssociationView.TargetOf_columns.TargetOf_outAssociations.TargetOf_targetColumn(
                        2, "one_to_one_property", "",
                        GenTableAssociationView.TargetOf_columns.TargetOf_outAssociations.TargetOf_targetColumn.TargetOf_table(
                            2, "table2", ""
                        )
                    )
                ),
            )
        )

        val properties = columnToProperties(column)

        assertEquals(2, properties.size)

        val oneToOneProperty = properties[0]

        assertEquals("oneToOneProperty", oneToOneProperty.name)
        assertEquals("Table2", oneToOneProperty.type)
        assertEquals(2, oneToOneProperty.typeTableId)

        assert(oneToOneProperty.notNull)
        assert(oneToOneProperty.keyProperty)
        assertEquals(AssociationType.ONE_TO_ONE, oneToOneProperty.associationType)
        assertEquals("@OneToOne", oneToOneProperty.associationAnnotation)
        assertEquals("@OnDissociate(DissociateAction.DELETE)", oneToOneProperty.dissociateAnnotation)

        val idViewProperty = properties[1]

        assertEquals("oneToOnePropertyId", idViewProperty.name)
        assertEquals("kotlin.Long", idViewProperty.type)

        assert(idViewProperty.idView)
        assertEquals(AssociationType.ONE_TO_ONE, idViewProperty.associationType)
        assertEquals("@IdView(\"${oneToOneProperty.name}\")", idViewProperty.idViewAnnotation)
    }

    @Test
    @Order(5)
    fun testInOneColumnConvert() {
        val column = GenTableAssociationView.TargetOf_columns(
            1, LocalDateTime.now(), LocalDateTime.now(), "remark", 0,
            "one_to_one_property", Types.BIGINT, "bigint", 0, 0, null, "comment",
            false, false, false, true, false, 1,
            inAssociations = listOf(
                GenTableAssociationView.TargetOf_columns.TargetOf_inAssociations(
                    1, AssociationType.ONE_TO_ONE, DissociateAction.DELETE, true,
                    GenTableAssociationView.TargetOf_columns.TargetOf_inAssociations.TargetOf_sourceColumn(
                        2, "one_to_one_property", "",
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

        assertEquals("oneToOneProperty", baseProperty.name)
        assertEquals("kotlin.Long", baseProperty.type)
        assert(baseProperty.keyProperty)

        val oneToManyProperty = properties[1]

        assertEquals("table2", oneToManyProperty.name)
        assertEquals("Table2", oneToManyProperty.type)
        assertEquals(2, oneToManyProperty.typeTableId)

        assert(!oneToManyProperty.notNull)
        assert(!oneToManyProperty.keyProperty)
        assertEquals(AssociationType.ONE_TO_ONE, oneToManyProperty.associationType)
        assertEquals("@OneToOne(mappedBy = \"oneToOneProperty\")", oneToManyProperty.associationAnnotation)

        val idViewProperty = properties[2]

        assertEquals("table2Id", idViewProperty.name)
        assertEquals("kotlin.Long", idViewProperty.type)

        assert(idViewProperty.idView)
        assertEquals(AssociationType.ONE_TO_ONE, idViewProperty.associationType)
        assertEquals("@IdView(\"${oneToManyProperty.name}\")", idViewProperty.idViewAnnotation)
    }
}
