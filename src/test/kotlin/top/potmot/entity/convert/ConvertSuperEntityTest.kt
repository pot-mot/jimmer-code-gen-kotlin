package top.potmot.entity.convert

import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.transaction.annotation.Transactional
import top.potmot.entity.GenEntity
import top.potmot.entity.GenTable
import top.potmot.entity.dto.GenEntityDetailView
import top.potmot.entity.id
import top.potmot.entity.tableId

class ConvertSuperEntityTest: BaseConvertTest() {
    @Test
    @Transactional
    fun `test with super convert`() {
        val superTableId = sqlClient.save(superTable).modifiedEntity.id

        val tableId = sqlClient.save(
            baseTable.copy(
                superTableIds = listOf(superTableId)
            )
        ).modifiedEntity.id

        convertService.convertTable(listOf(tableId, superTableId), null)

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
    "overwriteName" : false,
    "comment" : "table comment",
    "overwriteComment" : false,
    "author" : "",
    "otherAnnotation" : null,
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
            "body" : null,
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
            "body" : null,
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
}
