package com.mockitor.server.api

import com.mockitor.common.dto.ApplicationDto
import com.mockitor.common.dto.DependencyDto
import com.mockitor.common.dto.EndpointDto
import com.mockitor.server.services.AdminService
import mu.KotlinLogging
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/admin/api")
class AdminController(val adminService: AdminService) {
    private val log = KotlinLogging.logger {}

    @PostMapping("/apps")
    fun newApp(@RequestBody applicationDto: ApplicationDto): Mono<ApplicationDto> =
        adminService.createApp(applicationDto)

    @GetMapping("/apps")
    fun getAllApps(): Flux<ApplicationDto> =
        adminService.findApps()

    @GetMapping("/apps/{appId:[0-9]+}/dependencies")
    fun getAppDependencies(@PathVariable appId: Long): Flux<DependencyDto> =
        adminService.findDependenciesBy(appId)

    @PostMapping("/apps/{appId}/dependencies")
    fun addDependency(@PathVariable appId: Long, @RequestBody dependencyDto: DependencyDto) =
        adminService.addDependency(appId, dependencyDto)

    @GetMapping("/apps/{appName:^[\\D].*\$}/dependencies")
    fun findDependency(@PathVariable appName: String, @RequestParam("url") baseUrl: String) =
        adminService.findDependencyBy(baseUrl, appName)

    @PostMapping("/apps/{appId}/dependencies/{depId}/endpoints")
    fun addEndpoint(@PathVariable appId: Long, @PathVariable depId: Long, @RequestBody endpointDto: EndpointDto) =
        adminService.addEndpoint(appId, depId, endpointDto)

}