package com.mockitor.starter.rest

import com.mockitor.common.dto.DependencyDto
import com.mockitor.starter.rest.MockitorClient.Paths.GET_DEPENDENCY
import com.mockitor.starter.rest.MockitorClient.Paths.PARAM_URL
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
class MockitorClient(@Value("\${mockitor.adminUrl}") private val adminUrl: String) {

    private val log = KotlinLogging.logger {}
    private val client = WebClient.builder().baseUrl(adminUrl).build()

    object Paths {
        const val PARAM_APPNAME = "appName"
        const val PARAM_URL = "url"
        const val GET_DEPENDENCY = "/admin/api/apps/{$PARAM_APPNAME}/dependencies"
    }

    fun findDependency(appName: String?, baseUrl: String) =
        client.get()
            .uri { uri ->
                uri.path(GET_DEPENDENCY)
                    .queryParam(PARAM_URL, baseUrl)
                    .build(appName)
            }
            .retrieve()
            .bodyToMono(DependencyDto::class.java)

}