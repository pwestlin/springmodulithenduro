package nu.westlin.springmodulithenduro.api

import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging
import nu.westlin.springmodulithenduro.payment.PaymentService
import nu.westlin.springmodulithenduro.payment.RegistrationPayment
import nu.westlin.springmodulithenduro.payment.RegistrationPaymentRepository
import nu.westlin.springmodulithenduro.payment.RegistrationPaymentResult
import org.springframework.http.ResponseEntity
import org.springframework.modulith.events.CompletedEventPublications
import org.springframework.modulith.events.EventPublication
import org.springframework.modulith.events.IncompleteEventPublications
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.Duration

@RestController
@RequestMapping(path = ["/payment"])
class PaymentController(
    private val paymentRepository: RegistrationPaymentRepository,
    private val incompleteEventPublications: IncompleteEventPublications,
    private val completedEventPublications: CompletedEventPublications,
    private val paymentService: PaymentService
) {

    private val logger: KLogger = KotlinLogging.logger {}

    @PostMapping("/registration/{riderId}/{amount}")
    // TODO pwestlin: I don't like ResponseEntity<Any> as return type
    fun payRegistration(@PathVariable riderId: Long, @PathVariable amount: Int): ResponseEntity<BadRequestResponse?> {
        logger.info { "New registration payment: $riderId, $amount" }
        return when(paymentService.payRegistration(riderId, amount)) {
            RegistrationPaymentResult.AlreadyPaidError -> ResponseEntity.badRequest().body(BadRequestResponse("Rider with id $riderId has already paid for registration"))
            RegistrationPaymentResult.Paid -> ResponseEntity.ok().build()
            RegistrationPaymentResult.RiderDoesNotExistError -> ResponseEntity.badRequest().body(BadRequestResponse("Rider with id $riderId does not exist"))
        }
    }

    @GetMapping("/registration")
    fun listAllPayments(): List<RegistrationPayment> {
        return paymentRepository.findAll().toList()
    }

    // TODO pevest: Move
    @PutMapping("/events/republish")
    fun republishEvents() {
        incompleteEventPublications.resubmitIncompletePublicationsOlderThan(Duration.ofMillis(1))
    }

    // TODO pevest: Move
    @GetMapping("/events/completed")
    fun completed(): List<EventPublication> {
        return completedEventPublications.findAll().toList()
    }
}

data class BadRequestResponse(val errormessage: String)