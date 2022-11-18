package com.mockitor.starter.services

import com.mockitor.starter.properties.MockitorProperties
import com.mockitor.starter.rest.MockitorClient
import io.netty.handler.timeout.ReadTimeoutException
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClientRequestException
import reactor.core.publisher.Mono
import java.net.ConnectException

@Service
class AdminService(
    val props: MockitorProperties,
    val mockitorClient: MockitorClient
) {

    fun route(baseUrl: String): RootingInfo? = mockitorClient.findDependency(props.appName, baseUrl)
        .map { RootingInfo(props.appName, it.name, props.serverUrl) }
        .onErrorResume {
            if (it is ReadTimeoutException || it is ConnectException
                || (it is WebClientRequestException && it.cause is ConnectException)
            ) Mono.empty() else Mono.error(it)
        }
        .block()
}

data class RootingInfo(val appName: String?, val depName: String?, val url: String?)