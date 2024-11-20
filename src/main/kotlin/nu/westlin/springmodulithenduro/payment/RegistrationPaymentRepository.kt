package nu.westlin.springmodulithenduro.payment

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface RegistrationPaymentRepository : CrudRepository<RegistrationPayment, Long> {

    fun findByRiderId(riderId: Long): RegistrationPayment?
}
