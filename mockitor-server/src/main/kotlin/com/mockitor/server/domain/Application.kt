package com.mockitor.server.domain

import javax.persistence.*

@Entity
data class Application(@Id
                       @GeneratedValue(strategy = GenerationType.IDENTITY)
                       val id: Long?,
                       val name: String,
                       val url: String,
                       var description: String?,
                       @OneToMany
                       @JoinColumn(name = "application_id")
                       var dependencies: Set<Dependency>?
)
