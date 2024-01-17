package top.potmot.config

import org.springframework.boot.web.server.ErrorPage
import org.springframework.boot.web.server.ErrorPageRegistrar
import org.springframework.boot.web.server.ErrorPageRegistry
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import kotlin.reflect.full.findAnnotation

const val API = "/api"

@Configuration
class WebConfig : WebMvcConfigurer, ErrorPageRegistrar {
    override fun configurePathMatch(configurer: PathMatchConfigurer) {
        configurer.addPathPrefix(API) {controller ->
            controller.kotlin.findAnnotation<RestController>() != null
        }
    }

    override fun registerErrorPages(registry: ErrorPageRegistry) {
        val notFoundPage = ErrorPage(HttpStatus.NOT_FOUND, "/index.html")
        registry.addErrorPages(notFoundPage)
    }
}
