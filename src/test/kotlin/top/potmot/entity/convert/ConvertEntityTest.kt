package top.potmot.entity.convert

import org.babyfish.jimmer.kt.unload
import org.babyfish.jimmer.sql.ast.mutation.AssociatedSaveMode
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import top.potmot.business.baseProperty
import top.potmot.config.GlobalGenConfig
import top.potmot.core.model.business.EntityModelBusinessInput
import top.potmot.entity.GenEntity
import top.potmot.entity.GenModel
import top.potmot.entity.GenProperty
import top.potmot.entity.copy
import top.potmot.entity.dto.GenEntityConfigInput
import top.potmot.entity.dto.GenEntityDetailView
import top.potmot.entity.dto.GenPropertyEntityConfigInput
import top.potmot.entity.id
import top.potmot.entity.property.OtherAnnotation
import top.potmot.entity.tableId
import top.potmot.service.ConvertService
import top.potmot.service.EntityService
import top.potmot.utils.json.prettyObjectWriter

@SpringBootTest
@ActiveProfiles("test", "h2", "hide-sql")
class ConvertEntityTest {
    @Autowired
    lateinit var sqlClient: KSqlClient

    @Autowired
    lateinit var globalGenConfig: GlobalGenConfig

    @Autowired
    lateinit var convertService: ConvertService

    @Autowired
    lateinit var entityService: EntityService

    val GenEntityDetailView.result: String
        get() = prettyObjectWriter.writeValueAsString(
            toEntity {
                unload(this, GenEntity::model)
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
    @Transactional
    fun `test base convert`() {
        val modelId = sqlClient.insert(
            globalGenConfig.toEntity().copy {
                name = ""
                graphData = ""
                remark = ""
            },
        ).modifiedEntity.id

        val tableId = sqlClient.save(baseTable.copy(
            modelId = modelId
        )).modifiedEntity.id

        convertService.convertModel(modelId, null)

        val entity = sqlClient.createQuery(GenEntity::class) {
            where(table.tableId eq tableId)
            select(table.fetch(GenEntityDetailView::class))
        }.fetchOne()

        assertEquals(
            """
{
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
            "inListView" : false,
            "inDetailView" : false,
            "inInsertInput" : false,
            "inUpdateInput" : true,
            "inSpecification" : true,
            "inOptionView" : true,
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

        sqlClient.deleteByIds(GenModel::class, listOf(modelId))
    }

    // 测试转换过程中如果对属性做修改，添加新的属性，是否可以正确将变更和新的属性保存下来
    @Test
    @Transactional
    fun `test merge convert`() {
        val modelId = sqlClient.insert(
            globalGenConfig.toEntity().copy {
                name = ""
                graphData = ""
                remark = ""
            },
        ).modifiedEntity.id

        val tableId = sqlClient.save(baseTable.copy(
            modelId = modelId
        )).modifiedEntity.id

        convertService.convertModel(modelId, null)

        val entity1 = sqlClient.createQuery(GenEntity::class) {
            where(table.tableId eq tableId)
            select(table.fetch(GenEntityDetailView::class))
        }.fetchOne()

        val result1 = """
{
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
            "inListView" : false,
            "inDetailView" : false,
            "inInsertInput" : false,
            "inUpdateInput" : true,
            "inSpecification" : true,
            "inOptionView" : true,
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
            result1,
            entity1.result
        )

        // 对保存后的实体进行一些修改
        sqlClient.save(entity1.toEntity {
            name = entity1.name + " changed"
            overwriteName = true
            comment = entity1.comment + " changed"
            overwriteComment = true
            remark = entity1.remark + " changed"

            canAdd = !entity1.canAdd
            canEdit = !entity1.canEdit
            canDelete = !entity1.canDelete
            canQuery = !entity1.canQuery
            hasPage = !entity1.hasPage

            properties = entity1.properties.map { property ->
                property.toEntity {
                    unload(this, GenProperty::column)

                    name = property.name + " changed"
                    overwriteName = true
                    comment = property.comment + " changed"
                    overwriteComment = true
                    remark = property.remark + " changed"

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
                unload(this, GenProperty::column)

                name = "newProperty"
                orderKey = -2
                typeTable = null
                otherAnnotation = OtherAnnotation(
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

        val entity2 = sqlClient.createQuery(GenEntity::class) {
            where(table.tableId eq tableId)
            select(table.fetch(GenEntityDetailView::class))
        }.fetchOne()

        val result2 = """
{
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
    "otherAnnotation" : null,
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
            "body" : null,
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
            "body" : null,
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
            "body" : null,
            "orderKey" : 1,
            "inListView" : true,
            "inDetailView" : true,
            "inInsertInput" : true,
            "inUpdateInput" : false,
            "inSpecification" : false,
            "inOptionView" : false,
            "inShortAssociationView" : true,
            "inLongAssociationView" : false,
            "inLongAssociationInput" : false,
            "remark" : "id remark changed",
            "enum" : null
        }
    ]
}
            """.trimIndent()

        assertEquals(
            result2,
            entity2.result
        )

        convertService.convertModel(modelId, null)

        val entity3 = sqlClient.createQuery(GenEntity::class) {
            where(table.tableId eq tableId)
            select(table.fetch(GenEntityDetailView::class))
        }.fetchOne()

        assertEquals(
            result2,
            entity3.result
        )

        sqlClient.deleteByIds(GenModel::class, listOf(modelId))
    }

    // 测试表转换实体后，原表中有列被删除，该列对应的旧属性在下次转换时是否会被正确删除
    @Test
    @Transactional
    fun `test column delete convert`() {
        val modelId = sqlClient.insert(
            globalGenConfig.toEntity().copy {
                name = ""
                graphData = ""
                remark = ""
            },
        ).modifiedEntity.id

        val tableId = sqlClient.save(baseTable.copy(
            modelId = modelId
        )).modifiedEntity.id

        convertService.convertModel(modelId, null)

        val entity1 = sqlClient.createQuery(GenEntity::class) {
            where(table.tableId eq tableId)
            select(table.fetch(GenEntityDetailView::class))
        }.fetchOne()

        val result1 = """
{
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
            "inListView" : false,
            "inDetailView" : false,
            "inInsertInput" : false,
            "inUpdateInput" : true,
            "inSpecification" : true,
            "inOptionView" : true,
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
            result1,
            entity1.result
        )

        // 使 baseTable 中仅有 primaryColumn
        sqlClient.save(baseTable.copy(
            modelId = modelId,
            columns = listOf(primaryColumn)
        ), AssociatedSaveMode.REPLACE)

        convertService.convertModel(modelId, null)

        val entity2 = sqlClient.createQuery(GenEntity::class) {
            where(table.tableId eq tableId)
            select(table.fetch(GenEntityDetailView::class))
        }.fetchOne()

        val result2 = """
{
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
    "otherAnnotation" : null,
    "canAdd" : true,
    "canEdit" : true,
    "canDelete" : true,
    "canQuery" : true,
    "hasPage" : true,
    "remark" : "table remark",
    "properties" : [
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
            "orderKey" : 0,
            "inListView" : false,
            "inDetailView" : false,
            "inInsertInput" : false,
            "inUpdateInput" : true,
            "inSpecification" : true,
            "inOptionView" : true,
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
            result2,
            entity2.result
        )

        sqlClient.deleteByIds(GenModel::class, listOf(modelId))
    }


    @Test
    @Transactional
    fun `test superTable convert`() {
        val modelId = sqlClient.insert(
            globalGenConfig.toEntity().copy {
                name = ""
                graphData = ""
                remark = ""
            },
        ).modifiedEntity.id


        val superTableId = sqlClient.save(
            superTable.copy(
                modelId = modelId
            )
        ).modifiedEntity.id

        val tableId = sqlClient.save(
            baseTable.copy(
                modelId = modelId,
                superTableIds = listOf(superTableId)
            )
        ).modifiedEntity.id

        convertService.convertModel(modelId, null)

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
            "inListView" : false,
            "inDetailView" : false,
            "inInsertInput" : false,
            "inUpdateInput" : true,
            "inSpecification" : true,
            "inOptionView" : true,
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

        sqlClient.deleteByIds(GenModel::class, listOf(modelId))
    }

    @Test
    @Transactional
    fun `test entity config convert`() {
        val modelId = sqlClient.insert(
            globalGenConfig.toEntity().copy {
                name = ""
                graphData = ""
                remark = ""
            },
        ).modifiedEntity.id

        val tableId = sqlClient.save(baseTable.copy(
            modelId = modelId
        )).modifiedEntity.id

        convertService.convertModel(modelId, null)

        val firstConvertEntity = sqlClient.createQuery(GenEntity::class) {
            where(table.tableId eq tableId)
            select(table.fetch(GenEntityDetailView::class))
        }.fetchOne()

        val firstResult = """
{
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
            "inListView" : false,
            "inDetailView" : false,
            "inInsertInput" : false,
            "inUpdateInput" : true,
            "inSpecification" : true,
            "inOptionView" : true,
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

        entityService.config(
            EntityModelBusinessInput(
                entity = GenEntityConfigInput(
                    id = firstConvertEntity.id,
                    name = firstConvertEntity.name + " changed",
                    overwriteName = true,
                    comment = firstConvertEntity.comment + " changed",
                    overwriteComment = true,
                    remark = firstConvertEntity.remark + " changed",

                    canAdd = !firstConvertEntity.canAdd,
                    canEdit = !firstConvertEntity.canEdit,
                    canDelete = !firstConvertEntity.canDelete,
                    canQuery = !firstConvertEntity.canQuery,
                    hasPage = !firstConvertEntity.hasPage,

                    properties = firstConvertEntity.properties.map { property ->
                        GenEntityConfigInput.TargetOf_properties(
                            id = property.id,
                            name = property.name + " changed",
                            overwriteName = true,
                            comment = property.comment + " changed",
                            overwriteComment = true,
                            remark = property.remark + " changed",
                            orderKey = property.orderKey,

                            inListView = !property.inListView,
                            inDetailView = !property.inDetailView,
                            inSpecification = !property.inSpecification,
                            inInsertInput = !property.inInsertInput,
                            inUpdateInput = !property.inUpdateInput,
                            inOptionView = !property.inOptionView,
                            inShortAssociationView = !property.inShortAssociationView,
                            inLongAssociationInput = !property.inLongAssociationInput,
                            inLongAssociationView = !property.inLongAssociationView,
                        )
                    }
                ),
                properties = listOf(
                    GenPropertyEntityConfigInput(baseProperty.toEntity {
                        name = "newProperty"
                        orderKey = -2
                        otherAnnotation = OtherAnnotation(
                            importLines = listOf(
                                "org.babyfish.jimmer.sql.Transient",
                                "com.example.entity.EntityNewPropertyResolver",
                            ),
                            annotations = listOf(
                                "@Transient(EntityNewPropertyResolver::class)"
                            )
                        )
                    })
                )
            )
        )

        val changedEntity = sqlClient.createQuery(GenEntity::class) {
            where(table.tableId eq tableId)
            select(table.fetch(GenEntityDetailView::class))
        }.fetchOne()

        val result = """
{
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
    "otherAnnotation" : null,
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
            "body" : null,
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
            "body" : null,
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
            "body" : null,
            "orderKey" : 1,
            "inListView" : true,
            "inDetailView" : true,
            "inInsertInput" : true,
            "inUpdateInput" : false,
            "inSpecification" : false,
            "inOptionView" : false,
            "inShortAssociationView" : true,
            "inLongAssociationView" : false,
            "inLongAssociationInput" : false,
            "remark" : "id remark changed",
            "enum" : null
        }
    ]
}
            """.trimIndent()

        assertEquals(
            result,
            changedEntity.result
        )

        convertService.convertModel(modelId, null)

        val secondConvertEntity = sqlClient.createQuery(GenEntity::class) {
            where(table.tableId eq tableId)
            select(table.fetch(GenEntityDetailView::class))
        }.fetchOne()

        assertEquals(
            result,
            secondConvertEntity.result
        )

        sqlClient.deleteByIds(GenModel::class, listOf(modelId))
    }
}