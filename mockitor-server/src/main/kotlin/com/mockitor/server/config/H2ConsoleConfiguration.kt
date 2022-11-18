package com.mockitor.server.config

import org.h2.tools.Server
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.context.event.ContextClosedEvent
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener
import java.sql.SQLException

@Profile("local")
@Configuration
@ConditionalOnProperty("h2.console.enabled", havingValue = "true")
class H2ConsoleConfiguration(
    @Value("\${h2.console.webServerPort:8082}")
    private val webServerPort: String,
    @Value("\${h2.console.tcpServerPort:9092}")
    val tcpServerPort: String
) {
    @Bean
    fun h2Console() = H2Console(webServerPort, tcpServerPort)


}

class H2Console(val webServerPort: String, val tcpServerPort: String) {

    lateinit var webServer: Server
    lateinit var tcpServer: Server


    @EventListener(ContextRefreshedEvent::class)
    @Throws(SQLException::class)
    fun start() {
        webServer = Server.createWebServer("-webPort", webServerPort, "-tcpAllowOthers").start()
        tcpServer = Server.createTcpServer("-tcpPort", tcpServerPort, "-tcpAllowOthers").start()
    }

    @EventListener(ContextClosedEvent::class)
    fun stop() {
        tcpServer.stop()
        webServer.stop()
    }
}