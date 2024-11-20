package nu.westlin.springmodulithenduro.payment.internal

import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging
import nu.westlin.springmodulithenduro.payment.RegistrationPaid
import nu.westlin.springmodulithenduro.payment.RegistrationPayment
import nu.westlin.springmodulithenduro.payment.RegistrationPaymentRepository
import org.springframework.modulith.events.ApplicationModuleListener
import org.springframework.stereotype.Service

@Suppress("unused")
@Service
class DefaultPayForRegistration(
    private val registrationPaymentRepository: RegistrationPaymentRepository
) {
    private val logger: KLogger = KotlinLogging.logger {}

    // Note: There has to be an active transaction created before sending event (RegistrationPaid), otherwise this function will not be called.
    // From doc for @TransactionalEventListener: "If the event is not published within an active transaction, the event is discarded"
    @ApplicationModuleListener
    fun savePayment(registrationPaid: RegistrationPaid) {
        logger.info { "Saving registrationPaid: $registrationPaid" }
        registrationPaymentRepository.save(
            RegistrationPayment(
                id = 0,
                registrationPaid.riderId,
                registrationPaid.amount
            )
        )
    }
}