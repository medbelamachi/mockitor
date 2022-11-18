package com.mockitor.server.listeners

import com.mockitor.server.domain.DependencyStub
import com.mockitor.server.domain.StubSummary
import com.mockitor.server.repositories.EndpointRepository
import com.mockitor.server.repositories.MockServerRepository
import mu.KotlinLogging
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class ContextEventHandler(
    val mockServerRepository: MockServerRepository,
    val endpointRepository: EndpointRepository
) {

    private val log = KotlinLogging.logger {}

    @EventListener
    fun onContextRefresh(event: ContextRefreshedEvent) {
        val stubs = endpointRepository.findAllEndpointSummary()

        stubs.groupBy { Triple(it.appName, it.depName, it.url) }.let {
            it.entries.parallelStream()
                .peek {
                    mockServerRepository
                        .configureNewDependency(DependencyStub.fromTriple(it.key))
                }
                
//                .forEach { entry ->
//                    {
//                        log.info { "entry : " + entry }
//                        mockServerRepository
//                            .configureNewDependency(DependencyStub.fromTriple(entry.key))
//                            .thenMany(Flux.fromIterable(entry.value))
//                            .doOnNext(mockServerRepository::newStub)
//                            .subscribe()
//                    }
//                }

        }


//            .log.info { "====> " + stubs.groupBy { StubSummary::appName } }
        log.info { "====> done" }
    }

    fun configureDependency(appName: String, depName: String, url: String) {

    }

    fun configureStubs(stubSummary: List<StubSummary>) {

    }

    fun Triple<String, String, String>.c() {

    }
}


