package top.potmot.config

import org.babyfish.jimmer.sql.meta.DatabaseNamingStrategy
import org.babyfish.jimmer.sql.runtime.DefaultDatabaseNamingStrategy
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test", "h2")
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class DatabaseNamingStrategyTest(
    @Autowired val databaseNamingStrategy: DatabaseNamingStrategy
) {
    @Test
    @Order(1)
    fun testNamingStrategy() {
        assert(databaseNamingStrategy == DefaultDatabaseNamingStrategy.LOWER_CASE)
    }
}
