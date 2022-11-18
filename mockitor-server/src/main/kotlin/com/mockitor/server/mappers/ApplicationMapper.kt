package com.mockitor.server.mappers

import com.mockitor.common.dto.ApplicationDto
import com.mockitor.server.domain.Application
import org.mapstruct.Mapper

@Mapper
interface ApplicationMapper {
    fun toDomain(applicationDto: ApplicationDto): Application
    fun toDto(application: Application): ApplicationDto
}