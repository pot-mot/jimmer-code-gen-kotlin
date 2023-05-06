package top.potmot.jimmercodegen.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {
    @Bean(name = "PrimaryDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.primary")
    public DataSource getPrimaryDateSource() {
        return new DruidDataSource();
    }

    @Bean(name = "GenDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.gen")
    public DataSource getGenDateSource() {
        return new DruidDataSource();
    }

}
