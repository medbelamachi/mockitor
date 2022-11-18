package com.web.first.api

import com.web.first.feign.Api
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloController(
    @Autowired val api: Api
) {


    @GetMapping("/hello")
    fun hello() = Dto("hello from First Web")

    @GetMapping("/call")
    fun call() = api.callSecond()

    @GetMapping("/callWithFeign")
    fun callWithFeign(): Dto {

        return api.callWithFeign()
    }
}

data class Dto(val data: String?)