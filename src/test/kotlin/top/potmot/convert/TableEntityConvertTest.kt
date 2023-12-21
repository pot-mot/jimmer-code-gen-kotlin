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
import top.potmot.core.entity.convert.toGenEntity
import top.potmot.enumeration.AssociationType
import java.time.LocalDateTime
import top.potmot.enumeration.TableType
import top.potmot.model.dto.GenTableAssociationsView
import java.sql.Types

@SpringBootTest
@ActiveProfiles("test-kotlin", "mysql")
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class TableEntityConvertTest {
    @Test
    @Order(1)
    fun testIdColumnConvert() {
        val table = GenTableAssociationsView(
            id = 1,
            createdTime = LocalDateTime.now(),
            modifiedTime = LocalDateTime.now(),
            remark = "",
            name = "table1",
            comment = "",
            type = TableType.TABLE,
            orderKey = 1,
            entityId = null,
            schema = null,
            columns = listOf(
                GenTableAssociationsView.TargetOf_columns(
                    id = 1,
                    createdTime = LocalDateTime.now(),
                    modifiedTime = LocalDateTime.now(),
                    remark = "",
                    orderKey = 1,
                    name = "id_column",
                    typeCode = Types.BIGINT,
                    type = "BIGINT",
                    displaySize = 0,
                    numericPrecision = 0,
                    defaultValue = "",
                    comment = "PK COLUMN",
                    partOfPk = true,
                    autoIncrement = true,
                    typeNotNull = true,
                    businessKey = false,
                    logicalDelete = false,
                    tableId = 1,
                )
            ),
            uniqueIndexes = emptyList(),
            inAssociations = emptyList(),
            outAssociations = emptyList(),
        )

        // 在 test 中的 dto 不是 main 中的 dto，所以需要转成共用的 Entity
        val properties = table.toEntity().toGenEntity().properties

        assertEquals(1, properties.size)

        val idProperty = properties[0]

        assertEquals("idColumn", idProperty.name)
        assertEquals("kotlin.Long", idProperty.type)
        assertEquals("PK COLUMN", idProperty.comment)
        assertEquals(table.columns[0].id, idProperty.columnId)

        assert(idProperty.idProperty)
        assert(idProperty.typeNotNull)
        assertEquals(GenerationType.IDENTITY, idProperty.idGenerationType)
        assert(!idProperty.keyProperty)
        assert(!idProperty.listType)
        assert(!idProperty.logicalDelete)
        assert(!idProperty.idView)
    }

    @Test
    @Order(2)
    fun testManyToOneColumnConvert() {
        val table = GenTableAssociationsView(
            id = 1,
            createdTime = LocalDateTime.now(),
            modifiedTime = LocalDateTime.now(),
            remark = "",
            name = "table1",
            comment = "",
            type = TableType.TABLE,
            orderKey = 1,
            entityId = null,
            schema = null,
            columns = listOf(
                GenTableAssociationsView.TargetOf_columns(
                    id = 1,
                    createdTime = LocalDateTime.now(),
                    modifiedTime = LocalDateTime.now(),
                    remark = "",
                    orderKey = 1,
                    name = "many_to_one_column",
                    typeCode = Types.BIGINT,
                    type = "BIGINT",
                    displaySize = 0,
                    numericPrecision = 0,
                    defaultValue = "",
                    comment = "",
                    partOfPk = false,
                    autoIncrement = false,
                    typeNotNull = true,
                    businessKey = false,
                    logicalDelete = false,
                    tableId = 1,
                )
            ),
            uniqueIndexes = emptyList(),
            inAssociations = emptyList(),
            outAssociations = listOf(
                GenTableAssociationsView.TargetOf_outAssociations(
                    id = 1,
                    createdTime = LocalDateTime.now(),
                    modifiedTime = LocalDateTime.now(),
                    remark = "",
                    name = "one_to_many_column",
                    associationType = AssociationType.MANY_TO_ONE,
                    dissociateAction = DissociateAction.DELETE,
                    fake = false,
                    orderKey = 0,
                    targetTable = GenTableAssociationsView.TargetOf_outAssociations.TargetOf_targetTable_2(
                        id = 2,
                        name = "table2",
                        comment = ""
                    ),
                    columnReferences = listOf(
                        GenTableAssociationsView.TargetOf_outAssociations.TargetOf_columnReferences_2(
                            GenTableAssociationsView.TargetOf_outAssociations.TargetOf_columnReferences_2.TargetOf_targetColumn_3(
                                id = 2,
                                name = "one_to_many_column",
                                comment = "",
                                type = "BIGINT",
                            ),
                            1
                        )
                    )
                )
            ),
        )

        val properties = table.toEntity().toGenEntity().properties

        assertEquals(2, properties.size)

        val manyToOneProperty = properties[0]

        assertEquals("table2", manyToOneProperty.name)
        assertEquals("Table2", manyToOneProperty.type)
        assertEquals(2, manyToOneProperty.typeTableId)

        assert(manyToOneProperty.typeNotNull)
        assert(!manyToOneProperty.keyProperty)
        assertEquals(AssociationType.MANY_TO_ONE, manyToOneProperty.associationType)
        assertEquals(
            """@ManyToOne
@JoinColumn(
    name = "many_to_one_column",
    referencedColumnName = "one_to_many_column"
)""", manyToOneProperty.associationAnnotation
        )
        assertEquals("@OnDissociate(DissociateAction.DELETE)", manyToOneProperty.dissociateAnnotation)

        val idViewProperty = properties[1]

        assertEquals("table2Id", idViewProperty.name)
        assertEquals("kotlin.Long", idViewProperty.type)

        assert(idViewProperty.idView)
        assertEquals(AssociationType.MANY_TO_ONE, idViewProperty.associationType)
        assertEquals("@IdView(\"${manyToOneProperty.name}\")", idViewProperty.idViewAnnotation)
    }

    @Test
    @Order(3)
    fun testOneToOneColumnConvert() {
        val table = GenTableAssociationsView(
            id = 1,
            createdTime = LocalDateTime.now(),
            modifiedTime = LocalDateTime.now(),
            remark = "",
            name = "table1",
            comment = "",
            type = TableType.TABLE,
            orderKey = 1,
            entityId = null,
            schema = null,
            columns = listOf(
                GenTableAssociationsView.TargetOf_columns(
                    id = 1,
                    createdTime = LocalDateTime.now(),
                    modifiedTime = LocalDateTime.now(),
                    remark = "",
                    orderKey = 1,
                    name = "one_to_one_column",
                    typeCode = Types.BIGINT,
                    type = "BIGINT",
                    displaySize = 0,
                    numericPrecision = 0,
                    defaultValue = "",
                    comment = "",
                    partOfPk = false,
                    autoIncrement = false,
                    typeNotNull = true,
                    businessKey = true,
                    logicalDelete = false,
                    tableId = 1,
                )
            ),
            uniqueIndexes = emptyList(),
            inAssociations = emptyList(),
            outAssociations = listOf(
                GenTableAssociationsView.TargetOf_outAssociations(
                    id = 1,
                    createdTime = LocalDateTime.now(),
                    modifiedTime = LocalDateTime.now(),
                    remark = "",
                    name = "one_to_one_column",
                    associationType = AssociationType.ONE_TO_ONE,
                    dissociateAction = DissociateAction.DELETE,
                    fake = false,
                    orderKey = 0,
                    targetTable = GenTableAssociationsView.TargetOf_outAssociations.TargetOf_targetTable_2(
                        id = 2,
                        name = "table2",
                        comment = ""
                    ),
                    columnReferences = listOf(
                        GenTableAssociationsView.TargetOf_outAssociations.TargetOf_columnReferences_2(
                            GenTableAssociationsView.TargetOf_outAssociations.TargetOf_columnReferences_2.TargetOf_targetColumn_3(
                                id = 2,
                                name = "one_to_one_column",
                                comment = "",
                                type = "BIGINT",
                            ),
                            1
                        )
                    )
                )
            ),
        )

        val properties = table.toEntity().toGenEntity().properties

        assertEquals(2, properties.size)

        val oneToOneProperty = properties[0]

        assertEquals("table2", oneToOneProperty.name)
        assertEquals("Table2", oneToOneProperty.type)
        assertEquals(2, oneToOneProperty.typeTableId)

        assert(oneToOneProperty.typeNotNull)
        assert(oneToOneProperty.keyProperty)
        assertEquals(AssociationType.ONE_TO_ONE, oneToOneProperty.associationType)
        assertEquals(
            """@OneToOne
@JoinColumn(
    name = "one_to_one_column",
    referencedColumnName = "one_to_one_column"
)"""
            , oneToOneProperty.associationAnnotation)
        assertEquals("@OnDissociate(DissociateAction.DELETE)", oneToOneProperty.dissociateAnnotation)

        val idViewProperty = properties[1]

        assertEquals("table2Id", idViewProperty.name)
        assertEquals("kotlin.Long", idViewProperty.type)

        assert(idViewProperty.idView)
        assertEquals(AssociationType.ONE_TO_ONE, idViewProperty.associationType)
        assertEquals("@IdView(\"${oneToOneProperty.name}\")", idViewProperty.idViewAnnotation)
    }

//    @Test
//    @Order(4)
//    fun testManyToManyColumnConvert() {
////        val tableJson = """
////{
////    "createdTime": "$now",
////    "modifiedTime": "$now",
////    "remark": "remark",
////    "id": 1,
////    "table": {
////        "id": 1,
////        "name": "table1",
////        "comment": ""
////    },
////    "orderKey": 0,
////    "name": "many_to_many_column",
////    "typeCode": -5,
////    "type": "bigint",
////    "displaySize": 0,
////    "numericPrecision": 0,
////    "defaultValue": null,
////    "comment": "comment",
////    "partOfPk": false,
////    "autoIncrement": false,
////    "partOfFk": true,
////    "partOfUniqueIdx": false,
////    "typeNotNull": true,
////    "logicalDelete": false,
////    "businessKey": false,
////    "enumId": null,
////    "inAssociations": [],
////    "outAssociations": [
////        {
////            "id": 1,
////            "targetColumn": {
////                "id": 2,
////                "table": {
////                    "id": 2,
////                    "name": "table2",
////                    "comment": ""
////                },
////                "name": "mapped_many_to_many_column",
////                "comment": ""
////            },
////            "associationType": "MANY_TO_MANY",
////            "dissociateAction": null,
////            "fake": true
////        }
////    ]
////}""".trimIndent()
////
////        val table = processJsonToEntity(tableJson)
//
//        val table = GenTableAssociationsView(
//            id = 1,
//            createdTime = LocalDateTime.now(),
//            modifiedTime = LocalDateTime.now(),
//            remark = "",
//            name = "",
//            comment = "",
//            type = TableType.TABLE,
//            orderKey = 1,
//            entityId = null,
//            schema = null,
//            columns = listOf(
//                GenTableAssociationsView.TargetOf_columns(
//                    id = 1,
//                    createdTime = LocalDateTime.now(),
//                    modifiedTime = LocalDateTime.now(),
//                    remark = "",
//                    orderKey = 1,
//                    name = "id",
//                    typeCode = Types.BIGINT,
//                    type = "BIGINT",
//                    displaySize = 0,
//                    numericPrecision = 0,
//                    defaultValue = "",
//                    comment = "",
//                    partOfPk = true,
//                    autoIncrement = true,
//                    typeNotNull = true,
//                    businessKey = false,
//                    logicalDelete = false,
//                    tableId = 1,
//                )
//            ),
//            uniqueIndexes = emptyList(),
//            inAssociations = emptyList(),
//            outAssociations = emptyList(),
//        )
//
//        val properties = table.toEntity().toGenEntity().properties
//
//        assertEquals(3, properties.size)
//
//        val defaultProperty = properties[2]
//
//        assertEquals("manyToManyColumn", defaultProperty.name)
//        assertEquals("kotlin.Long", defaultProperty.type)
//        assert(defaultProperty.typeNotNull)
//
//        val manyToManyProperty = properties[0]
//
//        assertEquals("table2s", manyToManyProperty.name)
//        assertEquals(2, manyToManyProperty.typeTableId)
//        assert(manyToManyProperty.listType)
//
//        assert(manyToManyProperty.typeNotNull)
//        assert(!manyToManyProperty.keyProperty)
//
//        assertEquals(AssociationType.MANY_TO_MANY, manyToManyProperty.associationType)
//        assertEquals(
//            """@ManyToMany
//@JoinTable(
//    name = "table1_table2_mapping",
//    joinColumnName = "table1_id",
//    inverseJoinColumnName = "table2_id"
//)"""
//            , manyToManyProperty.associationAnnotation)
//
//        val idViewProperty = properties[1]
//
//        assertEquals("table2Ids", idViewProperty.name)
//        assertEquals("kotlin.Long", idViewProperty.type)
//
//        assert(idViewProperty.idView)
//        assertEquals(AssociationType.MANY_TO_MANY, idViewProperty.associationType)
//        assertEquals("@IdView(\"${manyToManyProperty.name}\")", idViewProperty.idViewAnnotation)
//    }
//
//
//    @Test
//    @Order(6)
//    fun testMappedOneToManyColumnConvert() {
////        val tableJson = """
////{
////    "createdTime": "$now",
////    "modifiedTime": "$now",
////    "remark": "remark",
////    "id": 1,
////    "table": {
////        "id": 1,
////        "name": "table1",
////        "comment": ""
////    },
////    "orderKey": 0,
////    "name": "one_to_many_column",
////    "typeCode": -5,
////    "type": "bigint",
////    "displaySize": 0,
////    "numericPrecision": 0,
////    "defaultValue": null,
////    "comment": "comment",
////    "partOfPk": false,
////    "autoIncrement": false,
////    "partOfFk": false,
////    "partOfUniqueIdx": false,
////    "typeNotNull": false,
////    "logicalDelete": false,
////    "businessKey": false,
////    "enumId": null,
////    "inAssociations": [
////        {
////            "id": 1,
////            "sourceColumn": {
////                "id": 2,
////                "table": {
////                    "id": 2,
////                    "name": "table2",
////                    "comment": ""
////                },
////                "name": "many_to_one_column",
////                "comment": ""
////            },
////            "associationType": "MANY_TO_ONE",
////            "dissociateAction": "DELETE",
////            "fake": true
////        }
////    ],
////    "outAssociations": []
////}""".trimIndent()
////
////        val table = processJsonToEntity(tableJson)
//
//        val table = GenTableAssociationsView(
//            id = 1,
//            createdTime = LocalDateTime.now(),
//            modifiedTime = LocalDateTime.now(),
//            remark = "",
//            name = "",
//            comment = "",
//            type = TableType.TABLE,
//            orderKey = 1,
//            entityId = null,
//            schema = null,
//            columns = listOf(
//                GenTableAssociationsView.TargetOf_columns(
//                    id = 1,
//                    createdTime = LocalDateTime.now(),
//                    modifiedTime = LocalDateTime.now(),
//                    remark = "",
//                    orderKey = 1,
//                    name = "id",
//                    typeCode = Types.BIGINT,
//                    type = "BIGINT",
//                    displaySize = 0,
//                    numericPrecision = 0,
//                    defaultValue = "",
//                    comment = "",
//                    partOfPk = true,
//                    autoIncrement = true,
//                    typeNotNull = true,
//                    businessKey = false,
//                    logicalDelete = false,
//                    tableId = 1,
//                )
//            ),
//            uniqueIndexes = emptyList(),
//            inAssociations = emptyList(),
//            outAssociations = emptyList(),
//        )
//
//        val properties = table.toEntity().toGenEntity().properties
//
//        assertEquals(3, properties.size)
//
//        val defaultProperty = properties[2]
//
//        assertEquals("oneToManyColumn", defaultProperty.name)
//        assertEquals("kotlin.Long", defaultProperty.type)
//
//        val oneToManyProperty = properties[0]
//
//        assertEquals("table2s", oneToManyProperty.name)
//        assertEquals("Table2", oneToManyProperty.type)
//        assertEquals(2, oneToManyProperty.typeTableId)
//        assert(oneToManyProperty.listType)
//
//        assert(oneToManyProperty.typeNotNull)
//        assert(!oneToManyProperty.keyProperty)
//        assertEquals(AssociationType.ONE_TO_MANY, oneToManyProperty.associationType)
//        assertEquals("@OneToMany(mappedBy = \"table1\")", oneToManyProperty.associationAnnotation)
//
//        val idViewProperty = properties[1]
//
//        assertEquals("table2Ids", idViewProperty.name)
//        assertEquals("kotlin.Long", idViewProperty.type)
//        assert(oneToManyProperty.listType)
//
//        assert(idViewProperty.idView)
//        assertEquals(AssociationType.ONE_TO_MANY, idViewProperty.associationType)
//        assertEquals("@IdView(\"${oneToManyProperty.name}\")", idViewProperty.idViewAnnotation)
//    }
//
//    @Test
//    @Order(7)
//    fun testMappedOneToOneColumnConvert() {
////        val tableJson = """
////{
////    "createdTime": "$now",
////    "modifiedTime": "$now",
////    "remark": "remark",
////    "id": 1,
////    "table": {
////        "id": 1,
////        "name": "table1",
////        "comment": ""
////    },
////    "orderKey": 0,
////    "name": "one_to_one_column",
////    "typeCode": -5,
////    "type": "bigint",
////    "displaySize": 0,
////    "numericPrecision": 0,
////    "defaultValue": null,
////    "comment": "comment",
////    "partOfPk": false,
////    "autoIncrement": false,
////    "partOfFk": false,
////    "partOfUniqueIdx": true,
////    "typeNotNull": false,
////    "logicalDelete": false,
////    "businessKey": false,
////    "enumId": null,
////    "inAssociations": [
////        {
////            "id": 1,
////            "sourceColumn": {
////                "id": 2,
////                "table": {
////                    "id": 2,
////                    "name": "table2",
////                    "comment": ""
////                },
////                "name": "one_to_one_column",
////                "comment": ""
////            },
////            "associationType": "ONE_TO_ONE",
////            "dissociateAction": "DELETE",
////            "fake": true
////        }
////    ],
////    "outAssociations": []
////}""".trimIndent()
////
////        val table = processJsonToEntity(tableJson)
//
//        val table = GenTableAssociationsView(
//            id = 1,
//            createdTime = LocalDateTime.now(),
//            modifiedTime = LocalDateTime.now(),
//            remark = "",
//            name = "",
//            comment = "",
//            type = TableType.TABLE,
//            orderKey = 1,
//            entityId = null,
//            schema = null,
//            columns = listOf(
//                GenTableAssociationsView.TargetOf_columns(
//                    id = 1,
//                    createdTime = LocalDateTime.now(),
//                    modifiedTime = LocalDateTime.now(),
//                    remark = "",
//                    orderKey = 1,
//                    name = "id",
//                    typeCode = Types.BIGINT,
//                    type = "BIGINT",
//                    displaySize = 0,
//                    numericPrecision = 0,
//                    defaultValue = "",
//                    comment = "",
//                    partOfPk = true,
//                    autoIncrement = true,
//                    typeNotNull = true,
//                    businessKey = false,
//                    logicalDelete = false,
//                    tableId = 1,
//                )
//            ),
//            uniqueIndexes = emptyList(),
//            inAssociations = emptyList(),
//            outAssociations = emptyList(),
//        )
//
//        val properties = table.toEntity().toGenEntity().properties
//
//        assertEquals(3, properties.size)
//
//        val defaultProperty = properties[2]
//
//        assertEquals("oneToOneColumn", defaultProperty.name)
//        assertEquals("kotlin.Long", defaultProperty.type)
//        assert(defaultProperty.keyProperty)
//
//        val oneToManyProperty = properties[0]
//
//        assertEquals("table2", oneToManyProperty.name)
//        assertEquals("Table2", oneToManyProperty.type)
//        assertEquals(2, oneToManyProperty.typeTableId)
//
//        assert(!oneToManyProperty.typeNotNull)
//        assert(!oneToManyProperty.keyProperty)
//        assertEquals(AssociationType.ONE_TO_ONE, oneToManyProperty.associationType)
//        assertEquals("@OneToOne(mappedBy = \"table1\")", oneToManyProperty.associationAnnotation)
//
//        val idViewProperty = properties[1]
//
//        assertEquals("table2Id", idViewProperty.name)
//        assertEquals("kotlin.Long", idViewProperty.type)
//
//        assert(idViewProperty.idView)
//        assertEquals(AssociationType.ONE_TO_ONE, idViewProperty.associationType)
//        assertEquals("@IdView(\"${oneToManyProperty.name}\")", idViewProperty.idViewAnnotation)
//    }
//
//    @Test
//    @Order(8)
//    fun testMappedManyToManyColumnConvert() {
////        val tableJson = """
////{
////    "createdTime": "$now",
////    "modifiedTime": "$now",
////    "remark": "remark",
////    "id": 1,
////    "table": {
////        "id": 1,
////        "name": "table1",
////        "comment": ""
////    },
////    "orderKey": 0,
////    "name": "mapped_many_to_many_column",
////    "typeCode": -5,
////    "type": "bigint",
////    "displaySize": 0,
////    "numericPrecision": 0,
////    "defaultValue": null,
////    "comment": "comment",
////    "partOfPk": false,
////    "autoIncrement": false,
////    "partOfFk": true,
////    "partOfUniqueIdx": false,
////    "typeNotNull": true,
////    "logicalDelete": false,
////    "businessKey": false,
////    "enumId": null,
////    "inAssociations": [
////        {
////            "id": 1,
////            "sourceColumn": {
////                "id": 2,
////                "table": {
////                    "id": 2,
////                    "name": "table2",
////                    "comment": ""
////                },
////                "name": "many_to_many_column",
////                "comment": ""
////            },
////            "associationType": "MANY_TO_MANY",
////            "dissociateAction": null,
////            "fake": true
////        }
////    ],
////    "outAssociations": []
////}""".trimIndent()
////
////        val table = processJsonToEntity(tableJson)
//
//        val table = GenTableAssociationsView(
//            id = 1,
//            createdTime = LocalDateTime.now(),
//            modifiedTime = LocalDateTime.now(),
//            remark = "",
//            name = "",
//            comment = "",
//            type = TableType.TABLE,
//            orderKey = 1,
//            entityId = null,
//            schema = null,
//            columns = listOf(
//                GenTableAssociationsView.TargetOf_columns(
//                    id = 1,
//                    createdTime = LocalDateTime.now(),
//                    modifiedTime = LocalDateTime.now(),
//                    remark = "",
//                    orderKey = 1,
//                    name = "id",
//                    typeCode = Types.BIGINT,
//                    type = "BIGINT",
//                    displaySize = 0,
//                    numericPrecision = 0,
//                    defaultValue = "",
//                    comment = "",
//                    partOfPk = true,
//                    autoIncrement = true,
//                    typeNotNull = true,
//                    businessKey = false,
//                    logicalDelete = false,
//                    tableId = 1,
//                )
//            ),
//            uniqueIndexes = emptyList(),
//            inAssociations = emptyList(),
//            outAssociations = emptyList(),
//        )
//
//        val properties = table.toEntity().toGenEntity().properties
//
//        assertEquals(3, properties.size)
//
//        val defaultProperty = properties[2]
//
//        assertEquals("mappedManyToManyColumn", defaultProperty.name)
//        assertEquals("kotlin.Long", defaultProperty.type)
//        assert(defaultProperty.typeNotNull)
//
//        val manyToManyProperty = properties[0]
//
//        assertEquals("table2s", manyToManyProperty.name)
//        assertEquals(2, manyToManyProperty.typeTableId)
//        assert(manyToManyProperty.listType)
//
//        assert(manyToManyProperty.typeNotNull)
//        assert(!manyToManyProperty.keyProperty)
//
//        assertEquals(AssociationType.MANY_TO_MANY, manyToManyProperty.associationType)
//        assertEquals("@ManyToMany(mappedBy = \"table1s\")", manyToManyProperty.associationAnnotation)
//
//        val idViewProperty = properties[1]
//
//        assertEquals("table2Ids", idViewProperty.name)
//        assertEquals("kotlin.Long", idViewProperty.type)
//
//        assert(idViewProperty.idView)
//        assertEquals(AssociationType.MANY_TO_MANY, idViewProperty.associationType)
//        assertEquals("@IdView(\"${manyToManyProperty.name}\")", idViewProperty.idViewAnnotation)
//    }
}
