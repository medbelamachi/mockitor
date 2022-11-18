package com.mockitor.server

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MockitorServerApplication

fun main(args: Array<String>) {
    runApplication<MockitorServerApplication>(*args)
}
