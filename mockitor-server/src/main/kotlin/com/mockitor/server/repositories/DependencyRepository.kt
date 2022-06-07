package com.mockitor.server.repositories

import com.mockitor.server.domain.Dependency
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface DependencyRepository : JpaRepository<Dependency, Long> {
    fun findByUrlAndApplication_Name(url: String, appName: String): Optional<Dependency>
    fun findByIdAndApplication_Id(depId: Long, appId: Long): Optional<Dependency>
    fun findDependenciesByApplication_Id(appId: Long): MutableList<Dependency>

}