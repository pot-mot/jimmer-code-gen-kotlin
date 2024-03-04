package top.potmot.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import kotlin.reflect.full.findAnnotation

const val API = "/api"

@Configuration
class WebConfig : WebMvcConfigurer {
    override fun configurePathMatch(configurer: PathMatchConfigurer) {
        configurer.addPathPrefix(API) { controller ->
            controller.kotlin.findAnnotation<RestController>() != null
        }
    }
}
