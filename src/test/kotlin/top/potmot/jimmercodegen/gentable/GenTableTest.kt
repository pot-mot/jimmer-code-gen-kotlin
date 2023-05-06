package top.potmot.jimmercodegen.gentable

import com.alibaba.druid.pool.DruidDataSource
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import top.potmot.jimmercodegen.dao.GenTableRepository

@SpringBootTest
class GenTableTest(
    @Autowired val dataSource: DruidDataSource,
    @Autowired val genTableRepository: GenTableRepository
) {
    @Test
    fun getAll() {
        println(dataSource.url)
        println(genTableRepository.findAll())
    }
}