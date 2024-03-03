package com.markvasilyevv.workrest.config

import com.markvasilyevv.workrest.constant.*
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@Configuration
@EnableWebMvc
class MVCConfig : WebMvcConfigurer {
    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {

        // CSS
        registry.addResourceHandler(CSS_RESOURCE)
            .addResourceLocations(CSS_RESOURCE_CLASSPATH)
            .addResourceLocations(CSS_BOOTSTRAP_RESOURCE_CLASSPATH)

        // JS
        registry.addResourceHandler(JS_RESOURCE)
            .addResourceLocations(JS_RESOURCE_CLASSPATH)
            .addResourceLocations(JS_BOOTSTRAP_RESOURCE_CLASSPATH)

        // Image
        registry.addResourceHandler(IMAGE_RESOURCE)
            .addResourceLocations(IMAGE_RESOURCE_CLASSPATH)
    }
}