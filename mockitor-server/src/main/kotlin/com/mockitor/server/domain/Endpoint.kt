package com.mockitor.server.domain

import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.Lob
import javax.persistence.ManyToOne

@Entity
data class Endpoint(
    var name: String?,
    var description: String?,
    @Lob
    var data: String?,
    @ManyToOne(fetch = FetchType.LAZY)
    var dependency: Dependency?,
) : BaseEntity() {

    fun with(dependency: Dependency?, data: String?): Endpoint {
        this.data = data
        this.dependency = dependency
        return this
    }
}
