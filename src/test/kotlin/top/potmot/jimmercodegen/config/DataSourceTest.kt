package top.potmot.jimmercodegen.config

import com.zaxxer.hikari.HikariDataSource
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.SpringBootTest
import top.potmot.dao.GenTableRepository

@SpringBootTest
class DataSourceTest(
    @Autowired val dataSource: HikariDataSource,
    @Autowired @Qualifier("GenDataSource") val genDataSource: HikariDataSource,
    @Autowired val genTableRepository: GenTableRepository
) {
    @Test
    fun testDataSource() {
        println(dataSource.jdbcUrl)
        println(genDataSource.jdbcUrl)
        println(genTableRepository.findAll())
    }
}
