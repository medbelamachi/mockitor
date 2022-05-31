package com.mockitor.starter.autoconfigure

import com.mockitor.starter.properties.MockitorProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(MockitorProperties::class)
@ComponentScan("com.mockitor.starter")
class MockitorAutoConfiguration {
}