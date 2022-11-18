package com.mockitor.common.dto


data class DependencyDto(
    val id: Long?,
    var name: String?,
    var description: String?,
    var url: String
) {

    companion object {
        fun of(depUrl: String) = DependencyDto(null, null, null, depUrl)
    }
}
