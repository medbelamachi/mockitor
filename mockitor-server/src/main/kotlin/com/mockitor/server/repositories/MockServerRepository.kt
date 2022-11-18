package com.mockitor.server.repositories

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch
import com.mockitor.server.clients.MocksClient
import com.mockitor.server.domain.DependencyStub
import com.mockitor.server.domain.StubSummary
import mu.KotlinLogging
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class MockServerRepository(val mocksClient: MocksClient) {

    private val log = KotlinLogging.logger {}
    private val objectMapper = ObjectMapper()

    fun configureNewDependency(dependency: DependencyStub) =
        mocksClient.add(dependency)
            .doOnNext { log.info { "new dependency created with stubId ${it.id}" } }

    fun newStub(appName: String, depName: String, stubData: String): Mono<String> {
        val stubDataWithHeaders = applyHeaders(appName, depName, stubData)
        return mocksClient
            .add(stubDataWithHeaders)
            .doOnNext { log.info { "new Stub created : ${it.id}" } }
            .map { stubDataWithHeaders }

    }

    fun newStub(stubSummary: StubSummary): Mono<String> =
        newStub(stubSummary.appName, stubSummary.depName, stubSummary.url)

    fun getMapping() = mocksClient.getMappings().doOnNext { log.info { "mockClient returned : $it" } }.subscribe()

    private fun applyHeaders(appName: String, depName: String, stubData: String): String {
        val patch = JsonMergePatch.fromJson(objectMapper.readTree(headersTemplate(appName, depName)))
        val original = objectMapper.readTree(stubData)
        return patch.apply(original).toString()
    }

    private fun headersTemplate(appName: String, depName: String) = """
        {"request":{"headers":{"mockitor-app":{"equalTo":"$appName"},"mockitor-dependency":{"equalTo":"$depName"}}}}
    """.trimIndent()
}

