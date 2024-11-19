package nu.westlin.springmodulithenduro.payment.internal

import nu.westlin.springmodulithenduro.payment.RegistrationPaid
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

@Service
class DefaultPayForRegistration {

    //@TransactionalEventListener
    @EventListener
    fun pay(registrationPaid: RegistrationPaid) {
        TODO("not implemented")
    }
}