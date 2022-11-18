package com.web.first.api

import com.web.first.feign.Api
import lombok.AllArgsConstructor
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloController {
    var api = Api()

    @GetMapping("/hello")
    fun hello() = Dto("hello from First Web")

    @GetMapping("/call")
    fun call() = api.callSecond()
}

@AllArgsConstructor
data class Dto(val data: String?)