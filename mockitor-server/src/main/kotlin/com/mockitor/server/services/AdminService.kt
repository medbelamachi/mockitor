package com.mockitor.server.services

import com.mockitor.common.dto.ApplicationDto
import com.mockitor.server.mappers.ApplicationMapper
import com.mockitor.server.repositories.ApplicationRepository
import mu.KotlinLogging
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class AdminService(
    val applicationRepository: ApplicationRepository,
    val applicationMapper: ApplicationMapper
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
}