package top.potmot.service

import org.babyfish.jimmer.kt.unload
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import top.potmot.entity.database.DatabaseType
import top.potmot.entity.database.DbNameStrategy
import top.potmot.entity.model.EnumerationStrategy
import top.potmot.entity.model.JvmLanguage
import top.potmot.entity.model.ModelDraft
import top.potmot.entity.model.ModelForeignKeyType
import top.potmot.entity.model.ModelViewport
import top.potmot.entity.model.dto.ModelInsertInput
import top.potmot.entity.model.dto.ModelUpdateInput

@SpringBootTest
@Transactional(rollbackFor = [Throwable::class])
open class ModelServiceTest(
    @Autowired
    private val modelService: ModelService
) {
    @Test
    fun testInsert() {
        val input = ModelInsertInput(
            name = "test",
            description = "test",
            databaseType = DatabaseType.POSTGRESQL,
            databaseNameStrategy = DbNameStrategy.LOWER_SNAKE,
            defaultForeignKeyType = ModelForeignKeyType.REAL,
            jvmLanguage = JvmLanguage.KOTLIN,
            defaultEnumerationStrategy = EnumerationStrategy.NAME,
            viewport = ModelViewport(0.0, 0.0, 1.0),
            jsonData = ""
        )
        val view = modelService.insert(input)

        Assertions.assertEquals(input.toEntity{
            unload(this, ModelDraft::jsonData)
        }, view.toEntity {
            unload(this, ModelDraft::id)
            unload(this, ModelDraft::createdTime)
            unload(this, ModelDraft::modifiedTime)
        })

        val histories = modelService.fetchHistories(view.id)
        Assertions.assertEquals(1, histories.size)
        Assertions.assertEquals(view.modifiedTime, histories[0].modifiedTime)
    }

    @Test
    fun testUpdate() {
        val input = ModelInsertInput(
            name = "test",
            description = "test",
            databaseType = DatabaseType.POSTGRESQL,
            databaseNameStrategy = DbNameStrategy.LOWER_SNAKE,
            defaultForeignKeyType = ModelForeignKeyType.REAL,
            jvmLanguage = JvmLanguage.KOTLIN,
            defaultEnumerationStrategy = EnumerationStrategy.NAME,
            viewport = ModelViewport(0.0, 0.0, 1.0),
            jsonData = ""
        )
        val view = modelService.insert(input)

        val updateInput = ModelUpdateInput(
            id = view.id,
            name = "test2",
            description = "test2",
            databaseType = DatabaseType.POSTGRESQL,
            databaseNameStrategy = DbNameStrategy.LOWER_SNAKE,
            defaultForeignKeyType = ModelForeignKeyType.REAL,
            jvmLanguage = JvmLanguage.KOTLIN,
            defaultEnumerationStrategy = EnumerationStrategy.NAME,
            viewport = ModelViewport(0.0, 0.0, 1.0),
            jsonData = ""
        )
        val updateView = modelService.update(updateInput)

        Assertions.assertEquals(updateInput.toEntity{
            unload(this, ModelDraft::jsonData)
        }, updateView.toEntity())

        val histories = modelService.fetchHistories(view.id)
        Assertions.assertEquals(2, histories.size)
        Assertions.assertTrue(histories[0].modifiedTime < histories[1].modifiedTime)
        Assertions.assertEquals(view.modifiedTime, histories[0].modifiedTime)
        Assertions.assertEquals(updateView.modifiedTime, histories[1].modifiedTime)
    }

    @Test
    fun testDelete() {
        val input = ModelInsertInput(
            name = "test",
            description = "test",
            databaseType = DatabaseType.POSTGRESQL,
            databaseNameStrategy = DbNameStrategy.LOWER_SNAKE,
            defaultForeignKeyType = ModelForeignKeyType.REAL,
            jvmLanguage = JvmLanguage.KOTLIN,
            defaultEnumerationStrategy = EnumerationStrategy.NAME,
            viewport = ModelViewport(0.0, 0.0, 1.0),
            jsonData = ""
        )
        val view = modelService.insert(input)

        val deleteCount = modelService.delete(view.id)
        // self and history
        Assertions.assertEquals(2, deleteCount)
    }
}