package com.mockitor.server.config

import org.hibernate.boot.model.naming.Identifier
import org.hibernate.boot.model.naming.PhysicalNamingStrategy
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class HibernateNamingConfig {
    @Bean
    fun physical(@Value("\${spring.jpa.hibernate.naming.prefix:MCK_}") prefix: String): PhysicalNamingStrategy =
        TablesNamingStrategy(prefix)
}

class TablesNamingStrategy(
    private val prefix: String
) : PhysicalNamingStrategyStandardImpl() {
    override fun toPhysicalTableName(identifier: Identifier?, jdbcEnv: JdbcEnvironment?): Identifier? {
        if (identifier == null) {
            return null
        }
        val newName: String = prefix + identifier.getText()
        return Identifier.toIdentifier(newName)
    }
}