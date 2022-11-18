package com.mockitor.starter.autoconfigure

import com.mockitor.starter.feign.RoutedBuilder
import com.mockitor.starter.feign.Targeter
import com.mockitor.starter.properties.MockitorProperties
import feign.Feign
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope

@Configuration
@EnableConfigurationProperties(MockitorProperties::class)
@ComponentScan("com.mockitor.starter")
@ConditionalOnProperty("mockitor.enabled", havingValue = "true", matchIfMissing = false)
class MockitorAutoConfiguration {


    @Configuration
    @ConditionalOnClass(Feign::class)
    class FeignAutoConfiguration {

        @Bean
        fun feignTargeter(): Targeter {
            return Targeter()
        }

        @Bean
        @Scope("prototype")
        @ConditionalOnClass(Feign::class)
        fun feignBuilder(): Feign.Builder {
            return RoutedBuilder.instance()
        }


    }
}