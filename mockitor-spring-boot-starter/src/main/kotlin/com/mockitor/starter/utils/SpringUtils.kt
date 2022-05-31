package com.mockitor.starter.utils

import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.stereotype.Component

@Component
class SpringUtils : ApplicationContextAware {
    companion object {
        private lateinit var ctx: ApplicationContext

        fun <T> getBean(beanClass: Class<T>): T {
            return ctx.getBean(beanClass)
        }
    }

    override fun setApplicationContext(applicationContext: ApplicationContext) {
        ctx = applicationContext
    }

}