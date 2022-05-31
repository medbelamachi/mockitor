package com.mockitor.starter.feign

import com.mockitor.starter.feign.Targeter.RouteTarget.RouteTarget.HEADER_MOCKITOR_APP
import com.mockitor.starter.feign.Targeter.RouteTarget.RouteTarget.HEADER_MOCKITOR_DEPENDENCY
import com.mockitor.starter.services.AdminService
import com.mockitor.starter.utils.SpringUtils
import feign.Request
import feign.RequestTemplate
import feign.Target.HardCodedTarget
import mu.KotlinLogging

class Targeter {

    class RouteTarget<T>(type: Class<T>?, url: String?) : HardCodedTarget<T>(type, url) {
        object RouteTarget {
            const val HEADER_MOCKITOR_APP = "mockitor-app"
            const val HEADER_MOCKITOR_DEPENDENCY = "mockitor-dependency"

        }

        private val log = KotlinLogging.logger {}
        override fun apply(input: RequestTemplate?): Request {
            val baseUrl = this.url()
            val adminService = SpringUtils.getBean(AdminService::class.java)
            adminService.route(baseUrl)?.let {
                input?.apply {
                    header(HEADER_MOCKITOR_APP, it.appName)
                    header(HEADER_MOCKITOR_DEPENDENCY, it.depName)
                    target(it.url)
                    log.info { "switching target url to ${it.url}" }
                }
            }
            return super.apply(input)
        }
    }


}