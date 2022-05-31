package com.web.second.api

import lombok.AllArgsConstructor
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloController {
    @GetMapping("/hello")
    fun hello() = Dto("hello from Second Web")

}

@AllArgsConstructor
class Dto(val data: String?)