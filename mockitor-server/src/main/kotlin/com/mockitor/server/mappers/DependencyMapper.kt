package com.mockitor.server.mappers

import com.mockitor.common.dto.DependencyDto
import com.mockitor.server.domain.Dependency
import org.mapstruct.Mapper

@Mapper
interface DependencyMapper {
    fun toDomain(dependencyDto: DependencyDto): Dependency
    fun toDto(dependency: Dependency): DependencyDto
}
