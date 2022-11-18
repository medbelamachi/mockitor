package com.mockitor.starter.feign

import com.mockitor.starter.feign.Targeter.RouteTarget.RouteTarget.HEADER_MOCKITOR_APP
import com.mockitor.starter.feign.Targeter.RouteTarget.RouteTarget.HEADER_MOCKITOR_DEPENDENCY
import com.mockitor.starter.services.AdminService
import com.mockitor.starter.utils.SpringUtils
import feign.Feign
import feign.Request
import feign.RequestTemplate
import feign.Target.HardCodedTarget
import mu.KotlinLogging
import org.springframework.cloud.openfeign.FeignClientFactoryBean
import org.springframework.cloud.openfeign.FeignContext

class Targeter : org.springframework.cloud.openfeign.Targeter {

    class RouteTarget<T>(type: Class<T>?, url: String?) : HardCodedTarget<T>(type, url) {
        object RouteTarget {
            const val HEADER_MOCKITOR_APP = "mockitor-app"
            const val HEADER_MOCKITOR_DEPENDENCY = "mockitor-dependency"

        }

        private val log = KotlinLogging.logger {}
        override fun apply(input: RequestTemplate?): Request {
            when {
                SpringUtils.initialzed() -> {
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
                }
            }
            return super.apply(input)
        }
    }

    override fun <T : Any?> target(
        factory: FeignClientFactoryBean,
        feign: Feign.Builder,
        context: FeignContext,
        target: HardCodedTarget<T>
    ): T {
        return feign.target(RouteTarget<T>(target.type(), target.url()))
    }


}