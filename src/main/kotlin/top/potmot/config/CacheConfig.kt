package top.potmot.config

import org.babyfish.jimmer.meta.ImmutableProp
import org.babyfish.jimmer.meta.ImmutableType
import org.babyfish.jimmer.spring.cache.CaffeineBinder
import org.babyfish.jimmer.sql.cache.Cache
import org.babyfish.jimmer.sql.cache.CacheFactory
import org.babyfish.jimmer.sql.cache.chain.ChainCacheBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Duration

@Configuration
class CacheConfig {
    @Bean
    fun cacheFactory(): CacheFactory {
        return object : CacheFactory {
            override fun createObjectCache(type: ImmutableType): Cache<*, *> {
                return ChainCacheBuilder<Any, Any>()
                    .add(CaffeineBinder(1024, Duration.ofMinutes(3)))
                    .build()
            }

            override fun createAssociatedIdCache(prop: ImmutableProp): Cache<*, *> {
                return ChainCacheBuilder<Any, Any>()
                    .add(CaffeineBinder(1024, Duration.ofMinutes(3)))
                    .build()
            }

            override fun createAssociatedIdListCache(prop: ImmutableProp): Cache<*, List<*>> {
                return ChainCacheBuilder<Any, List<*>>()
                    .add(CaffeineBinder(1024, Duration.ofMinutes(3)))
                    .build()
            }

            override fun createResolverCache(prop: ImmutableProp): Cache<*, *>? {
                return ChainCacheBuilder<Any, Any>()
                    .add(CaffeineBinder(1024, Duration.ofMinutes(3)))
                    .build()
            }
        }
    }
}
