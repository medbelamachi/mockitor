package com.mockitor.server.repositories

import com.mockitor.server.domain.Dependency
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DependencyRepository : JpaRepository<Dependency, Long>