package com.mockitor.server.listeners

import com.mockitor.server.domain.DependencyStub
import com.mockitor.server.repositories.EndpointRepository
import com.mockitor.server.repositories.MockServerRepository
import mu.KotlinLogging
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux

@Component
class ContextEventHandler(
    val mockServerRepository: MockServerRepository,
    val endpointRepository: EndpointRepository
) {

    private val log = KotlinLogging.logger {}

    @EventListener(ContextRefreshedEvent::class)
    fun loadStubs() {
        val stubs = endpointRepository.findAllEndpointSummary()

        stubs?.groupBy { Triple(it.appName, it.depName, it.url) }.let {
            log.info { "Loading all pre-configured stubs..." }
            it.entries.parallelStream()
                .forEach { entry ->
                    mockServerRepository
                            .configureNewDependency(DependencyStub.fromTriple(entry.key))
                        .thenMany(Flux.fromIterable(entry.value))
                        .flatMap (mockServerRepository::newStub)
                        .subscribe()
                }
        }
    }
}


