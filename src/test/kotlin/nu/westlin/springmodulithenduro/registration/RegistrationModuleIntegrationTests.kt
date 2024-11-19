package nu.westlin.springmodulithenduro.registration

import io.github.oshai.kotlinlogging.KotlinLogging
import io.mockk.clearAllMocks
import nu.westlin.springmodulithenduro.domain.Rider
import nu.westlin.springmodulithenduro.payment.RegistrationPaid
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.modulith.test.ApplicationModuleTest
import org.springframework.modulith.test.AssertablePublishedEvents
import org.springframework.modulith.test.PublishedEvents

/*
@ApplicationModuleTest
class RegistrationModuleIntegrationTests(
    @Autowired private val registerDriver: RegisterDriver
) {

    @MockkBean
    lateinit var payForRegistration: PayForRegistration

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `sf jg f`() {
        val amount = 1
        justRun { payForRegistration.pay(amount) }

        registerDriver.registerAndPay(amount)

        verifySequence {
            payForRegistration.pay(amount)
        }
    }
}
*/

@ApplicationModuleTest
class RegistrationModuleIntegrationTests(
    @Autowired private val registerDriver: RegisterDriver
) {

    private val logger = KotlinLogging.logger {}

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `register and pay`(events: AssertablePublishedEvents) {
        val amount = 1
            registerDriver.registerAndPay(amount)

        logger.info { events }
        assertThat(events)
            .contains(RegistrationPaid::class.java)
            .matching {
                it == RegistrationPaid(
                    rider = Rider(1, "Foo bar"),
                    amount = amount
                )
            }
        assertThat(events.ofType(RegistrationPaid::class.java)).hasSize(1)
    }
}