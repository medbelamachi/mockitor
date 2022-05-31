package com.mockitor.server.services

import com.mockitor.common.dto.ApplicationDto
import com.mockitor.common.dto.DependencyDto
import com.mockitor.common.dto.EndpointDto
import com.mockitor.server.domain.DependencyStub
import com.mockitor.server.mappers.ApplicationMapper
import com.mockitor.server.mappers.DependencyMapper
import com.mockitor.server.mappers.EndpointMapper
import com.mockitor.server.repositories.ApplicationRepository
import com.mockitor.server.repositories.DependencyRepository
import com.mockitor.server.repositories.EndpointRepository
import com.mockitor.server.repositories.MockServerRepository
import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono

@Service
@Transactional
class AdminService(
    val applicationRepository: ApplicationRepository,
    val applicationMapper: ApplicationMapper,
    val dependencyRepository: DependencyRepository,
    val dependencyMapper: DependencyMapper,
    val endpointRepository: EndpointRepository,
    val endpointMapper: EndpointMapper,
    val mockServerRepository: MockServerRepository
) {
    private val log = KotlinLogging.logger {}

    /**
     *create new Application
     */
    fun createApp(applicationDto: ApplicationDto) =
        Mono.just(
            applicationRepository
                .save(applicationMapper.toDomain(applicationDto))
        )
            .map(applicationMapper::toDto)
            .doOnSuccess { log.info { "new app created : ${it.name} , with id : ${it.id}" } }

    /**
     * add new Dependency to an existing application
     */
    fun addDependency(appId: Long, dependencyDto: DependencyDto): Mono<DependencyDto> =
        Mono.justOrEmpty(applicationRepository.findById(appId))
            .flatMap {
                log.info { "adding new dependency to the app : ${it.name}" }
                var newDependency = dependencyMapper.toDomain(dependencyDto)
                newDependency.application = it
                mockServerRepository.configureNewDependency(
                    DependencyStub.from(
                        it.name,
                        newDependency.name,
                        newDependency.url
                    )
                )
                    .doOnError { throw IllegalArgumentException(it) }
                    .then(
                        Mono.just(dependencyRepository.save(newDependency))
                    )

            }
            .map(dependencyMapper::toDto)
            .doOnSuccess { log.info { "new dependency added : ${it.name} , with id : ${it.id}" } }
            .doOnError { log.error("Unable to create new Dependency", it) }


    fun findDependencyBy(url: String, appName: String): Mono<DependencyDto> =
        Mono.justOrEmpty(dependencyRepository.findByUrlAndApplication_Name(url, appName))
            .map { dependencyMapper.toDto(it) }
            .doOnSuccess { log.info { "Dependency existance for appName=$appName  and depUrl= $url ... result found : $it " } }
            .doOnError { log.error("Unable to find dependency with the given criteria", it) }


    fun addEndpoint(appId: Long, depId: Long, endpointDto: EndpointDto) =
        Mono.justOrEmpty(dependencyRepository.findByIdAndApplication_Id(depId, appId))
            .flatMap {
                //   log.info { "adding new endPoint to the dependency : $depId" }

                var newEndpoint = endpointMapper.toDomain(endpointDto)
                mockServerRepository.newStub(it.application!!.name, it.name, endpointDto.data)
                    .doOnError { throw IllegalArgumentException(it) }
                    .map { stub -> newEndpoint.with(it, stub) }
                    .map { endpointRepository.save(newEndpoint) }

            }.map(endpointMapper::toDto)
            .doOnSuccess { log.info { "new endpoint added : ${it.name} , with id : ${it.id}" } }
            .doOnError { log.error("Unable to create new Endpoint for  the depId: $depId and appId: $appId ") }

}