package com.web.first.feign

import com.web.first.api.Dto
import feign.Headers
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@Headers(
    "Accept: application/json",
    "Content-Type: application/json"
)
@FeignClient(value = "feignSecondClient", url = "http://localhost:8280")
interface FeignSecondClient {

    @RequestMapping(method = [RequestMethod.GET], value = ["/hello"])
    fun hello(): Dto
}