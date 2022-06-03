package com.ead.course.configs

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import java.time.format.DateTimeFormatter


//@Configuration
class DateConfig {
    @Bean
    @Primary
    fun objectMapper(): ObjectMapper {
        val module = JavaTimeModule()
        module.addSerializer(LOCAL_DATETIME_SERIALIZER)
        return ObjectMapper()
            .registerModule(module)
    }

    companion object {
        const val DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'"
        var LOCAL_DATETIME_SERIALIZER = LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DATETIME_FORMAT))
    }
}