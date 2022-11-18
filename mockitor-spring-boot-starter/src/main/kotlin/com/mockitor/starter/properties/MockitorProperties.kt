package com.mockitor.starter.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("mockitor")
data class MockitorProperties(
    var enabled: Boolean?,
    var appName: String?,
    var adminUrl: String?,
    var serverUrl: String?
) {
}