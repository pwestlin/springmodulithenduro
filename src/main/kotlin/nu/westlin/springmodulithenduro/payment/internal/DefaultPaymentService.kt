package nu.westlin.springmodulithenduro.payment.internal

import nu.westlin.springmodulithenduro.domain.RiderRepository
import nu.westlin.springmodulithenduro.payment.PaymentService
import nu.westlin.springmodulithenduro.payment.RegistrationPaid
import nu.westlin.springmodulithenduro.payment.RegistrationPaymentRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DefaultPaymentService(
    private val applicationEventPublisher: ApplicationEventPublisher,
    private val registrationPaymentRepository: RegistrationPaymentRepository,
    private val riderRepository: RiderRepository
) : PaymentService {
    @Transactional
    override fun payRegistration(riderId: Long, amount: Int): RegistrationPaymentResult {
        // TODO pwestlin: Now I've got a hard dependency to registrationPaymentRepository which I dindn't want
        //  Move this to PaymentService
        // TODO pwestlin: Refactor
        return when (registrationPaymentRepository.findByRiderId(riderId)) {
            null -> {
                when (riderRepository.findById(riderId).isPresent) {
                    true -> {
                        // TODO pwestlin: Why send an event in same module?
                        applicationEventPublisher.publishEvent(RegistrationPaid(riderId, amount))
                        RegistrationPaymentResult.Paid
                    }

                    else -> RegistrationPaymentResult.RiderDoesNotExistError
                }
            }

            else -> RegistrationPaymentResult.AlreadyPaidError
        }
    }
}

sealed interface RegistrationPaymentResult {
    data object Paid : RegistrationPaymentResult
    data object AlreadyPaidError : RegistrationPaymentResult
    data object RiderDoesNotExistError : RegistrationPaymentResult
}