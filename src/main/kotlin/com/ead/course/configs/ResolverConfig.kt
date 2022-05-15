package com.ead.course.configs

import net.kaczmarzyk.spring.data.jpa.web.SpecificationArgumentResolver
import org.springframework.context.annotation.Configuration
import org.springframework.data.web.PageableHandlerMethodArgumentResolver
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport


@Configuration
class ResolverConfig : WebMvcConfigurationSupport() {
    public override fun addArgumentResolvers(argumentResolvers: MutableList<HandlerMethodArgumentResolver>) {
        argumentResolvers.add(SpecificationArgumentResolver())
        val resolver = PageableHandlerMethodArgumentResolver()
        argumentResolvers.add(resolver)
        super.addArgumentResolvers(argumentResolvers)
    }
}