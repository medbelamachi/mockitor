package com.web.first

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class WebFirstApplication

fun main(args: Array<String>) {
    runApplication<WebFirstApplication>(*args)
}
