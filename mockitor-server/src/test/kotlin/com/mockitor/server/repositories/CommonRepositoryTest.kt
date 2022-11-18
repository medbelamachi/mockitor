package com.mockitor.server.repositories

import mu.KotlinLogging
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles

@DataJpaTest
@ActiveProfiles("local")
class CommonRepositoryTest(
    @Autowired val applicationRepository: ApplicationRepository
) {

    private val log = KotlinLogging.logger {}

    @Test
    internal fun applicationInitialized() {
        val application = applicationRepository.findById(1)
        Assertions.assertTrue(application.isPresent)
    }


}