package com.mockitor.server.domain

import javax.persistence.*

@Entity
data class Dependency(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,
    val name: String,
    val url: String,
    var description: String?,
    @OneToMany
    @JoinColumn(name = "dependency_id")
    /*@OrderColumn(name = "priority")*/
    var endpoints: Set<Endpoint>?
)
