package top.potmot.config

import org.babyfish.jimmer.sql.meta.DatabaseNamingStrategy
import org.babyfish.jimmer.sql.runtime.DefaultDatabaseNamingStrategy
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class DatabaseNamingStrategyConfig {
    @Bean
    open fun databaseNamingStrategy(): DatabaseNamingStrategy =
        DefaultDatabaseNamingStrategy.LOWER_CASE
}
