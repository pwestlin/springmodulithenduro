package nu.westlin.springmodulithenduro.payment

interface PaymentService {
    fun payRegistration(riderId: Long, amount: Int): RegistrationPaymentResult
}