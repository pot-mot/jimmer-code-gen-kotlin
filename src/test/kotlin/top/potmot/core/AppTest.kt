package top.potmot.core

import org.babyfish.jimmer.sql.ast.mutation.SaveMode
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import top.potmot.entity.model.GenModel
import top.potmot.enums.database.DatabaseNamingStrategyType
import top.potmot.enums.database.DatabaseType
import top.potmot.enums.model.DevLanguage
import java.time.LocalDateTime

@SpringBootTest
@ActiveProfiles("test-h2-mem")
class AppTest(
    @Autowired private val sqlClient: KSqlClient
) {
    @Test
    fun test() {
        sqlClient.saveCommand(
            GenModel {
                name = "test"
                author = "potmot"
                language = DevLanguage.Kotlin
                databaseType = DatabaseType.MySQL
                databaseNamingStrategy = DatabaseNamingStrategyType.LOWER_CASE
                createdTime = LocalDateTime.now()
                modifiedTime = LocalDateTime.now()
            }
        ) {
            setMode(SaveMode.INSERT_ONLY)
        }.execute()
    }
}