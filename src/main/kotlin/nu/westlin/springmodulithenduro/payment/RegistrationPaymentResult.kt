package nu.westlin.springmodulithenduro.payment

sealed interface RegistrationPaymentResult {
    data object Paid : RegistrationPaymentResult
    data object AlreadyPaidError : RegistrationPaymentResult
    data object RiderDoesNotExistError : RegistrationPaymentResult
}