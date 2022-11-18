package com.mockitor.server.clients

import com.mockitor.server.domain.DependencyStub
import com.mockitor.server.domain.StubId
import io.netty.handler.logging.LogLevel
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.http.client.reactive.ClientHttpConnector
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import reactor.netty.http.client.HttpClient
import reactor.netty.transport.logging.AdvancedByteBufFormat


@Component
class MocksClient(
    @Value("http://localhost:\${wiremock.server.port}")
    val url: String
) {
    private val log = KotlinLogging.logger {}

    private val httpClient: HttpClient = HttpClient.create()
        .wiretap("reactor.netty.http.client.HttpClient", LogLevel.DEBUG, AdvancedByteBufFormat.TEXTUAL)
    private val conn: ClientHttpConnector = ReactorClientHttpConnector(httpClient)
    private val client = WebClient.builder().baseUrl(url)
        .clientConnector(conn)
        .codecs { configurer -> configurer.defaultCodecs().enableLoggingRequestDetails(true) }
        .build()

    private val mappingsUrl = "/__admin/mappings"

    fun add(dependencyStub: DependencyStub) =
        client.post().uri(mappingsUrl)
            .body(Mono.just(dependencyStub), DependencyStub::class.java)
            .retrieve()
            .bodyToMono(StubId::class.java)

    fun add(stubJson: String) = client.post().uri(mappingsUrl)
        .header(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON.toString())
        .body(Mono.just(stubJson), String::class.java)
        .retrieve()
        .bodyToMono(StubId::class.java)

    fun getMappings() =
        client.get().uri(mappingsUrl)
            .retrieve()
            .bodyToMono(String::class.java)


}

