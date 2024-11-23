package nu.westlin.springmodulithenduro.registration

import com.ninjasquad.springmockk.MockkBean
import io.github.oshai.kotlinlogging.KotlinLogging
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.verifySequence
import nu.westlin.springmodulithenduro.domain.Rider
import nu.westlin.springmodulithenduro.domain.RiderRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.modulith.test.ApplicationModuleTest

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
    private val registerRider: RegisterRider
) {

    private val logger = KotlinLogging.logger {}

    @MockkBean
    private lateinit var riderRepository: RiderRepository

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `new rider`() {
        val rider = Rider(1, "Moto Cross")

        every { riderRepository.save(rider) } returns rider

        registerRider.registerRider(rider)

        verifySequence {
            riderRepository.save(rider)
        }
    }

    // TODO pevest: Move to other modules integration test
/*
    @Test
    fun `pay registration`(events: AssertablePublishedEvents) {
        val riderId = 1L
        val amount = 500
        registerRider.payRegistration(riderId = 1, amount = amount)

        logger.info { events }
        assertThat(events)
            .contains(RegistrationPaid::class.java)
            .matching {
                it == RegistrationPaid(
                    riderId = riderId,
                    amount = amount
                )
            }
        assertThat(events.ofType(RegistrationPaid::class.java)).hasSize(1)
    }
*/
}