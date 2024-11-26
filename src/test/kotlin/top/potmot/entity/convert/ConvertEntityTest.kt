package top.potmot.entity.convert

import org.babyfish.jimmer.kt.unload
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.transaction.annotation.Transactional
import top.potmot.business.baseProperty
import top.potmot.entity.GenEntity
import top.potmot.entity.GenProperty
import top.potmot.entity.GenTable
import top.potmot.entity.dto.GenEntityDetailView
import top.potmot.entity.dto.PropertyOtherAnnotation
import top.potmot.entity.tableId

class ConvertEntityTest : BaseConvertTest() {
    @Test
    @Transactional
    fun `test base convert`() {
        val tableId = sqlClient.save(baseTable).modifiedEntity.id

        convertService.convertTable(listOf(tableId), null)

        val entity = sqlClient.createQuery(GenEntity::class) {
            where(table.tableId eq tableId)
            select(table.fetch(GenEntityDetailView::class))
        }.fetchOne()

        assertEquals(
            """
{
    "model" : null,
    "packagePath" : "com.example.entity",
    "table" : {
        "id" : $tableId
    },
    "superEntities" : [ ],
    "name" : "Table",
    "overwriteName" : false,
    "comment" : "table comment",
    "overwriteComment" : false,
    "author" : "",
    "canAdd" : true,
    "canEdit" : true,
    "canDelete" : true,
    "canQuery" : true,
    "hasPage" : true,
    "remark" : "table remark",
    "properties" : [
        {
            "name" : "column",
            "overwriteName" : false,
            "comment" : "column comment",
            "overwriteComment" : false,
            "type" : "kotlin.Int",
            "typeTable" : null,
            "listType" : false,
            "typeNotNull" : true,
            "idProperty" : false,
            "idGenerationAnnotation" : null,
            "keyProperty" : false,
            "keyGroup" : null,
            "logicalDelete" : false,
            "idView" : false,
            "idViewTarget" : null,
            "associationType" : null,
            "longAssociation" : false,
            "mappedBy" : null,
            "inputNotNull" : null,
            "joinColumnMetas" : null,
            "joinTableMeta" : null,
            "dissociateAnnotation" : null,
            "otherAnnotation" : null,
            "orderKey" : 0,
            "inListView" : true,
            "inDetailView" : true,
            "inInsertInput" : true,
            "inUpdateInput" : true,
            "inSpecification" : true,
            "inOptionView" : false,
            "inShortAssociationView" : false,
            "inLongAssociationView" : true,
            "inLongAssociationInput" : true,
            "remark" : "column remark",
            "enum" : null
        },
        {
            "name" : "id",
            "overwriteName" : false,
            "comment" : "id comment",
            "overwriteComment" : false,
            "type" : "kotlin.Int",
            "typeTable" : null,
            "listType" : false,
            "typeNotNull" : true,
            "idProperty" : true,
            "idGenerationAnnotation" : "@GeneratedValue(strategy = GenerationType.IDENTITY)",
            "keyProperty" : false,
            "keyGroup" : null,
            "logicalDelete" : false,
            "idView" : false,
            "idViewTarget" : null,
            "associationType" : null,
            "longAssociation" : false,
            "mappedBy" : null,
            "inputNotNull" : null,
            "joinColumnMetas" : null,
            "joinTableMeta" : null,
            "dissociateAnnotation" : null,
            "otherAnnotation" : null,
            "orderKey" : 1,
            "inListView" : true,
            "inDetailView" : true,
            "inInsertInput" : true,
            "inUpdateInput" : true,
            "inSpecification" : true,
            "inOptionView" : false,
            "inShortAssociationView" : false,
            "inLongAssociationView" : true,
            "inLongAssociationInput" : true,
            "remark" : "id remark",
            "enum" : null
        }
    ]
}
            """.trimIndent(),
            entity.result
        )

        sqlClient.deleteByIds(GenTable::class, listOf(tableId))
    }

    @Test
    @Transactional
    fun `test MergeExistAndConvert`() {
        val tableId = sqlClient.save(baseTable).modifiedEntity.id

        convertService.convertTable(listOf(tableId), null)

        val firstConvertEntity = sqlClient.createQuery(GenEntity::class) {
            where(table.tableId eq tableId)
            select(table.fetch(GenEntityDetailView::class))
        }.fetchOne()

        val firstResult = """
{
    "model" : null,
    "packagePath" : "com.example.entity",
    "table" : {
        "id" : $tableId
    },
    "superEntities" : [ ],
    "name" : "Table",
    "overwriteName" : false,
    "comment" : "table comment",
    "overwriteComment" : false,
    "author" : "",
    "canAdd" : true,
    "canEdit" : true,
    "canDelete" : true,
    "canQuery" : true,
    "hasPage" : true,
    "remark" : "table remark",
    "properties" : [
        {
            "name" : "column",
            "overwriteName" : false,
            "comment" : "column comment",
            "overwriteComment" : false,
            "type" : "kotlin.Int",
            "typeTable" : null,
            "listType" : false,
            "typeNotNull" : true,
            "idProperty" : false,
            "idGenerationAnnotation" : null,
            "keyProperty" : false,
            "keyGroup" : null,
            "logicalDelete" : false,
            "idView" : false,
            "idViewTarget" : null,
            "associationType" : null,
            "longAssociation" : false,
            "mappedBy" : null,
            "inputNotNull" : null,
            "joinColumnMetas" : null,
            "joinTableMeta" : null,
            "dissociateAnnotation" : null,
            "otherAnnotation" : null,
            "orderKey" : 0,
            "inListView" : true,
            "inDetailView" : true,
            "inInsertInput" : true,
            "inUpdateInput" : true,
            "inSpecification" : true,
            "inOptionView" : false,
            "inShortAssociationView" : false,
            "inLongAssociationView" : true,
            "inLongAssociationInput" : true,
            "remark" : "column remark",
            "enum" : null
        },
        {
            "name" : "id",
            "overwriteName" : false,
            "comment" : "id comment",
            "overwriteComment" : false,
            "type" : "kotlin.Int",
            "typeTable" : null,
            "listType" : false,
            "typeNotNull" : true,
            "idProperty" : true,
            "idGenerationAnnotation" : "@GeneratedValue(strategy = GenerationType.IDENTITY)",
            "keyProperty" : false,
            "keyGroup" : null,
            "logicalDelete" : false,
            "idView" : false,
            "idViewTarget" : null,
            "associationType" : null,
            "longAssociation" : false,
            "mappedBy" : null,
            "inputNotNull" : null,
            "joinColumnMetas" : null,
            "joinTableMeta" : null,
            "dissociateAnnotation" : null,
            "otherAnnotation" : null,
            "orderKey" : 1,
            "inListView" : true,
            "inDetailView" : true,
            "inInsertInput" : true,
            "inUpdateInput" : true,
            "inSpecification" : true,
            "inOptionView" : false,
            "inShortAssociationView" : false,
            "inLongAssociationView" : true,
            "inLongAssociationInput" : true,
            "remark" : "id remark",
            "enum" : null
        }
    ]
}
        """.trimIndent()

        assertEquals(
            firstResult,
            firstConvertEntity.result
        )

        sqlClient.save(firstConvertEntity.toEntity {
            name = firstConvertEntity.name + " changed"
            overwriteName = true
            comment = firstConvertEntity.comment + " changed"
            overwriteComment = true
            remark = firstConvertEntity.remark + " changed"

            canAdd = !firstConvertEntity.canAdd
            canEdit = !firstConvertEntity.canEdit
            canDelete = !firstConvertEntity.canDelete
            canQuery = !firstConvertEntity.canQuery
            hasPage = !firstConvertEntity.hasPage

            properties = firstConvertEntity.properties.map { property ->
                property.toEntity {
                    name = property.name + " changed"
                    overwriteName = true
                    comment = property.comment + " changed"
                    overwriteComment = true
                    remark = property.remark + " changed"
                    orderKey = property.orderKey * -1

                    inListView = !property.inListView
                    inDetailView = !property.inDetailView
                    inSpecification = !property.inSpecification
                    inInsertInput = !property.inInsertInput
                    inUpdateInput = !property.inUpdateInput
                    inOptionView = !property.inOptionView
                    inShortAssociationView = !property.inShortAssociationView
                    inLongAssociationInput = !property.inLongAssociationInput
                    inLongAssociationView = !property.inLongAssociationView
                }
            } + baseProperty.toEntity {
                unload(this, GenProperty::id)
                name = "newProperty"
                orderKey = -2
                typeTable = null
                otherAnnotation = PropertyOtherAnnotation(
                    importLines = listOf(
                        "org.babyfish.jimmer.sql.Transient",
                        "com.example.entity.EntityNewPropertyResolver",
                    ),
                    annotations = listOf(
                        "@Transient(EntityNewPropertyResolver::class)"
                    )
                )
            }
        })

        val changedEntity = sqlClient.createQuery(GenEntity::class) {
            where(table.tableId eq tableId)
            select(table.fetch(GenEntityDetailView::class))
        }.fetchOne()

        val result = """
{
    "model" : null,
    "packagePath" : "com.example.entity",
    "table" : {
        "id" : $tableId
    },
    "superEntities" : [ ],
    "name" : "Table changed",
    "overwriteName" : true,
    "comment" : "table comment changed",
    "overwriteComment" : true,
    "author" : "",
    "canAdd" : false,
    "canEdit" : false,
    "canDelete" : false,
    "canQuery" : false,
    "hasPage" : false,
    "remark" : "table remark changed",
    "properties" : [
        {
            "name" : "newProperty",
            "overwriteName" : false,
            "comment" : "comment",
            "overwriteComment" : false,
            "type" : "kotlin.String",
            "typeTable" : null,
            "listType" : false,
            "typeNotNull" : true,
            "idProperty" : false,
            "idGenerationAnnotation" : null,
            "keyProperty" : false,
            "keyGroup" : null,
            "logicalDelete" : false,
            "idView" : false,
            "idViewTarget" : null,
            "associationType" : null,
            "longAssociation" : false,
            "mappedBy" : null,
            "inputNotNull" : null,
            "joinColumnMetas" : null,
            "joinTableMeta" : null,
            "dissociateAnnotation" : null,
            "otherAnnotation" : {
                "importLines" : [
                    "org.babyfish.jimmer.sql.Transient",
                    "com.example.entity.EntityNewPropertyResolver"
                ],
                "annotations" : [
                    "@Transient(EntityNewPropertyResolver::class)"
                ]
            },
            "orderKey" : -2,
            "inListView" : true,
            "inDetailView" : true,
            "inInsertInput" : true,
            "inUpdateInput" : true,
            "inSpecification" : true,
            "inOptionView" : false,
            "inShortAssociationView" : false,
            "inLongAssociationView" : true,
            "inLongAssociationInput" : true,
            "remark" : "remark",
            "enum" : null
        },
        {
            "name" : "id changed",
            "overwriteName" : true,
            "comment" : "id comment changed",
            "overwriteComment" : true,
            "type" : "kotlin.Int",
            "typeTable" : null,
            "listType" : false,
            "typeNotNull" : true,
            "idProperty" : true,
            "idGenerationAnnotation" : "@GeneratedValue(strategy = GenerationType.IDENTITY)",
            "keyProperty" : false,
            "keyGroup" : null,
            "logicalDelete" : false,
            "idView" : false,
            "idViewTarget" : null,
            "associationType" : null,
            "longAssociation" : false,
            "mappedBy" : null,
            "inputNotNull" : null,
            "joinColumnMetas" : null,
            "joinTableMeta" : null,
            "dissociateAnnotation" : null,
            "otherAnnotation" : null,
            "orderKey" : -1,
            "inListView" : false,
            "inDetailView" : false,
            "inInsertInput" : false,
            "inUpdateInput" : false,
            "inSpecification" : false,
            "inOptionView" : true,
            "inShortAssociationView" : true,
            "inLongAssociationView" : false,
            "inLongAssociationInput" : false,
            "remark" : "id remark changed",
            "enum" : null
        },
        {
            "name" : "column changed",
            "overwriteName" : true,
            "comment" : "column comment changed",
            "overwriteComment" : true,
            "type" : "kotlin.Int",
            "typeTable" : null,
            "listType" : false,
            "typeNotNull" : true,
            "idProperty" : false,
            "idGenerationAnnotation" : null,
            "keyProperty" : false,
            "keyGroup" : null,
            "logicalDelete" : false,
            "idView" : false,
            "idViewTarget" : null,
            "associationType" : null,
            "longAssociation" : false,
            "mappedBy" : null,
            "inputNotNull" : null,
            "joinColumnMetas" : null,
            "joinTableMeta" : null,
            "dissociateAnnotation" : null,
            "otherAnnotation" : null,
            "orderKey" : 0,
            "inListView" : false,
            "inDetailView" : false,
            "inInsertInput" : false,
            "inUpdateInput" : false,
            "inSpecification" : false,
            "inOptionView" : true,
            "inShortAssociationView" : true,
            "inLongAssociationView" : false,
            "inLongAssociationInput" : false,
            "remark" : "column remark changed",
            "enum" : null
        }
    ]
}
            """.trimIndent()

        assertEquals(
            result,
            changedEntity.result
        )

        convertService.convertTable(listOf(tableId), null)

        val secondConvertEntity = sqlClient.createQuery(GenEntity::class) {
            where(table.tableId eq tableId)
            select(table.fetch(GenEntityDetailView::class))
        }.fetchOne()

        assertEquals(
            result,
            secondConvertEntity.result
        )

        sqlClient.deleteByIds(GenTable::class, listOf(tableId))
    }
}