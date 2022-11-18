package com.mockitor.server.domain

import javax.persistence.*

@Entity
data class Endpoint(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,
    var name: String?,
    var description: String?,
    @Lob
    var data: String?
)
