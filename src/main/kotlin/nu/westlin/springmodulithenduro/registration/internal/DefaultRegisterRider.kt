package nu.westlin.springmodulithenduro.registration.internal

import nu.westlin.springmodulithenduro.domain.Rider
import nu.westlin.springmodulithenduro.payment.RegistrationPaid
import nu.westlin.springmodulithenduro.registration.RegisterDriver
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

/*
@Service
class DefaultRegisterDriver(
    private val payForRegistration: PayForRegistration
) : RegisterDriver {

    override fun registerAndPay(i: Int) {
        payForRegistration.pay(i)
    }
}
*/

@Service
class DefaultRegisterRider(
    private val applicationEventPublisher: ApplicationEventPublisher
) : RegisterDriver {

    override fun registerAndPay(amount: Int) {
        applicationEventPublisher.publishEvent(
            RegistrationPaid(
                rider = Rider(1, "Foo bar"),
                amount = amount
            )
        )
    }
}
