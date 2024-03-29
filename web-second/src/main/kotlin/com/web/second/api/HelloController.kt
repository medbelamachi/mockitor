package com.web.second.api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloController {
    @GetMapping("/hello")
    fun hello(): Dto {
        System.out.println("called....")
        return Dto("hello from Second Web")
    }

}

class Dto(val data: String?)