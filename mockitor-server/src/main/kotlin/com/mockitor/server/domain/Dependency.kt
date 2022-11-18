package com.mockitor.server.domain

import javax.persistence.*

@Entity
data class Dependency(
    val name: String,
    val url: String,
    var description: String?,
    @ManyToOne(fetch = FetchType.EAGER)
    var application: Application?,
    @OneToMany
    @JoinColumn(name = "dependency_id")
    /*@OrderColumn(name = "priority")*/
    var endpoints: MutableSet<Endpoint>?
) : BaseEntity()
