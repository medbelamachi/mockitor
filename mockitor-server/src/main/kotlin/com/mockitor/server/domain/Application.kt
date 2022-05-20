package com.mockitor.server.domain

import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.JoinColumn
import javax.persistence.OneToMany

@Entity
data class Application(
    val name: String,
    val url: String,
    var description: String?,
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id")
    var dependencies: MutableSet<Dependency>?
) : BaseEntity()
