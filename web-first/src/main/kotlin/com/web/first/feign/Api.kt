package com.web.first.feign

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.mockitor.starter.feign.RoutedBuilder
import feign.Logger
import feign.jackson.JacksonDecoder
import feign.jackson.JacksonEncoder
import feign.okhttp.OkHttpClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class Api(
    @Autowired
    private var feignSecondClient: FeignSecondClient
) {
    private var mapper = jacksonObjectMapper()
    private var feignClient: SecondClient = RoutedBuilder.instance()
        .client(OkHttpClient())
        .encoder(JacksonEncoder(mapper))
        .decoder(JacksonDecoder(mapper))
        .logLevel(Logger.Level.FULL)
        .target(SecondClient::class.java, "http://localhost:8280")


    fun callSecond() = feignClient.hello()
    fun callWithFeign() = feignSecondClient.hello()
}