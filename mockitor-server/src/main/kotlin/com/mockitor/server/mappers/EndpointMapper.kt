package com.mockitor.server.mappers

import com.mockitor.common.dto.EndpointDto
import com.mockitor.server.domain.Endpoint
import org.mapstruct.Mapper

@Mapper
interface EndpointMapper {
    fun toDomain(endpointDto: EndpointDto): Endpoint
    fun toDto(endpoint: Endpoint): EndpointDto
}