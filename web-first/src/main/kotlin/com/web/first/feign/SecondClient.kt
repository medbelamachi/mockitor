package com.web.first.feign

import com.web.first.api.Dto
import feign.Headers
import feign.RequestLine

@Headers(
    "Accept: application/json",
    "Content-Type: application/json"
)
interface SecondClient {

    @RequestLine("GET /hello")
    fun hello(): Dto
}

