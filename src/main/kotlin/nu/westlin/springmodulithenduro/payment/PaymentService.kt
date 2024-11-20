package nu.westlin.springmodulithenduro.payment

import nu.westlin.springmodulithenduro.payment.internal.RegistrationPaymentResult

interface PaymentService {
    fun payRegistration(riderId: Long, amount: Int): RegistrationPaymentResult
}