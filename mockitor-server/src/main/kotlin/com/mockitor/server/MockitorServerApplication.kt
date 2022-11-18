package com.mockitor.server

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock

@SpringBootApplication
@AutoConfigureWireMock
class MockitorServerApplication

fun main(args: Array<String>) {
    runApplication<MockitorServerApplication>(*args)
}
