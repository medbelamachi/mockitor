package com.mockitor.server.api

import com.mockitor.common.dto.ApplicationDto
import com.mockitor.server.services.AdminService
import mu.KotlinLogging
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/admin/api")
class AdminController(val adminService: AdminService) {
    private val log = KotlinLogging.logger {}

    @PostMapping("/apps")
    fun newApp(@RequestBody applicationDto: ApplicationDto): Mono<ApplicationDto> =
        adminService.createApp(applicationDto)

}