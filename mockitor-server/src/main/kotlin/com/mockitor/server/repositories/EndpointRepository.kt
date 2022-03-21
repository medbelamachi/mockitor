package com.mockitor.server.repositories

import com.mockitor.server.domain.Endpoint
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EndpointRepository : JpaRepository<Endpoint, Long>