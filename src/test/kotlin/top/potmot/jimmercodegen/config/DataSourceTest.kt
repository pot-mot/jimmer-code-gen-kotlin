package top.potmot.jimmercodegen.config

import com.alibaba.druid.pool.DruidDataSource
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.SpringBootTest
import top.potmot.dao.GenTableRepository

@SpringBootTest
class DataSourceTest(
    @Autowired val dataSource: DruidDataSource,
    @Autowired @Qualifier("GenDataSource") val genDataSource: DruidDataSource,
    @Autowired val genTableRepository: GenTableRepository
) {
    @Test
    fun getAll() {
        println(dataSource.url)
        println(genDataSource.url)
        println(genTableRepository.findAll())
    }
}