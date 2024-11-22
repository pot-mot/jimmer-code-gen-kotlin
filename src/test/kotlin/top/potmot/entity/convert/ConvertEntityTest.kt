package top.potmot.entity.convert

import org.babyfish.jimmer.kt.unload
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import top.potmot.entity.GenEntity
import top.potmot.entity.GenProperty
import top.potmot.entity.GenTable
import top.potmot.entity.copy
import top.potmot.entity.dto.GenConfigProperties
import top.potmot.entity.dto.GenEntityDetailView
import top.potmot.entity.id
import top.potmot.entity.tableId
import top.potmot.service.ConvertService
import top.potmot.utils.json.prettyObjectWriter

@SpringBootTest
@ActiveProfiles("test", "h2", "hide-sql")
class ConvertEntityTest {
    @Autowired
    lateinit var sqlClient: KSqlClient

    @Autowired
    lateinit var convertService: ConvertService

    private val GenEntityDetailView.result
        get() = prettyObjectWriter.writeValueAsString(
            toEntity {
                unload(this, GenEntity::id)
                unload(this, GenEntity::modifiedTime)
                unload(this, GenEntity::createdTime)
                properties = properties.map {
                    it.copy {
                        unload(this, GenProperty::id)
                        unload(this, GenProperty::modifiedTime)
                        unload(this, GenProperty::createdTime)
                        unload(this, GenProperty::column)
                    }
                }
            }
        )

    @Test
    fun `test base convert`() {
        val tableId = sqlClient.save(baseTable).modifiedEntity.id

        convertService.convertTable(listOf(tableId), null, GenConfigProperties())

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
    "comment" : "table comment",
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
            "comment" : "column comment",
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
            "comment" : "id comment",
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
    fun `test with super convert`() {
        val superTableId = sqlClient.save(superTable).modifiedEntity.id

        val tableId = sqlClient.save(baseTable.copy(
            superTableIds = listOf(superTableId)
        )).modifiedEntity.id

        convertService.convertTable(listOf(tableId, superTableId), null, GenConfigProperties())

        val superEntityId = sqlClient.createQuery(GenEntity::class) {
            where(table.tableId eq superTableId)
            select(table.id)
        }.fetchOne()

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
    "superEntities" : [
        {
            "id" : $superEntityId
        }
    ],
    "name" : "Table",
    "comment" : "table comment",
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
            "comment" : "column comment",
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
            "comment" : "id comment",
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

        sqlClient.deleteByIds(GenTable::class, listOf(tableId, superTableId))
    }

    @Test
    fun `test MergeExistAndConvert`() {
        val tableId = sqlClient.save(baseTable).modifiedEntity.id

        convertService.convertTable(listOf(tableId), null, GenConfigProperties())

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
    "comment" : "table comment",
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
            "comment" : "column comment",
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
            "comment" : "id comment",
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
            comment = firstConvertEntity.comment + " changed"
            remark = firstConvertEntity.remark + " changed"

            canAdd = !firstConvertEntity.canAdd
            canEdit = !firstConvertEntity.canEdit
            canDelete = !firstConvertEntity.canDelete
            canQuery = !firstConvertEntity.canQuery
            hasPage = !firstConvertEntity.hasPage

            properties = firstConvertEntity.properties.map { property ->
                property.toEntity {
                    name = property.name + " changed"
                    comment = property.comment + " changed"
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
    "comment" : "table comment changed",
    "author" : "",
    "canAdd" : false,
    "canEdit" : false,
    "canDelete" : false,
    "canQuery" : false,
    "hasPage" : false,
    "remark" : "table remark changed",
    "properties" : [
        {
            "name" : "id changed",
            "comment" : "id comment changed",
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
            "comment" : "column comment changed",
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

        convertService.convertTable(listOf(tableId), null, GenConfigProperties())

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