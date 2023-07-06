package top.potmot.config

import com.zaxxer.hikari.HikariDataSource
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import javax.sql.DataSource

@Configuration
class DataSourceConfig {
    @get:ConfigurationProperties(prefix = "spring.datasource.main")
    @get:Primary
    @get:Bean(name = ["MainDataSource"])
    val mainDateSource: DataSource
        get() = HikariDataSource()

    @get:ConfigurationProperties(prefix = "spring.datasource.gen")
    @get:Bean(name = ["GenDataSource"])
    val genDateSource: DataSource
        get() = HikariDataSource()
}
