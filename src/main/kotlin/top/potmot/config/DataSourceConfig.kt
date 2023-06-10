package top.potmot.config

import com.alibaba.druid.pool.DruidDataSource
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import javax.sql.DataSource

@Configuration
class DataSourceConfig {
    @get:ConfigurationProperties(prefix = "spring.datasource.primary")
    @get:Primary
    @get:Bean(name = ["PrimaryDataSource"])
    val primaryDateSource: DataSource
        get() = DruidDataSource()

    @get:ConfigurationProperties(prefix = "spring.datasource.gen")
    @get:Bean(name = ["GenDataSource"])
    val genDateSource: DataSource
        get() = DruidDataSource()
}
