package top.potmot.core

import org.babyfish.jimmer.sql.kt.KSqlClient
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test-h2-mem")
class AppTest(
    @Autowired private val sqlClient: KSqlClient
) {
    @Test
    fun test() {

    }
}