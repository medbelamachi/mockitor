package com.mockitor.server.domain

import java.util.*
import javax.persistence.*

@MappedSuperclass
abstract class BaseEntity {
    @Id
    @Column(columnDefinition = "NUMERIC(38,0)")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Column(columnDefinition = "varbinary")
    val uuid: UUID = UUID.randomUUID()


    override fun hashCode(): Int = Objects.hash(uuid)

    override fun equals(that: Any?): Boolean =
        this == that || (that is BaseEntity && Objects.equals(uuid, that.uuid))


}