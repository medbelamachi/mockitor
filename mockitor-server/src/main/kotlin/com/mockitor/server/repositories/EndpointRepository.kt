package com.mockitor.server.repositories

import com.mockitor.server.domain.Endpoint
import com.mockitor.server.domain.StubSummary
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface EndpointRepository : JpaRepository<Endpoint, Long> {
    @Query(
        "SELECT new com.mockitor.server.domain.StubSummary(a.name, d.name, d.url, e.id) " +
                "FROM Endpoint e " +
                "JOIN e.dependency d " +
                "JOIN e.dependency.application a"
    )
    fun findAllEndpointSummary(): List<StubSummary>
}