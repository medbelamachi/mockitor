package com.mockitor.starter.feign

import feign.Feign

class RoutedBuilder : Feign.Builder() {

    companion object {
        fun instance() = RoutedBuilder()
    }

    override fun <T : Any?> target(apiType: Class<T>?, url: String?): T {
        return super.target(Targeter.RouteTarget(apiType, url))
    }


}