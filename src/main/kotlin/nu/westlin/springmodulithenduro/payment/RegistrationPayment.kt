package nu.westlin.springmodulithenduro.payment

import org.springframework.data.annotation.Id
import java.time.LocalDateTime

data class RegistrationPayment(
    @Id
    val id: Long,
    // TODO pevest: Type this
    val riderId: Long,
    val amount: Int,
    val timestamp: LocalDateTime = LocalDateTime.now()
)