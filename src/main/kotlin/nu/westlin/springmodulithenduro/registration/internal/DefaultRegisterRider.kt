package nu.westlin.springmodulithenduro.registration.internal

import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging
import nu.westlin.springmodulithenduro.domain.Rider
import nu.westlin.springmodulithenduro.domain.RiderRepository
import nu.westlin.springmodulithenduro.registration.RegisterRider
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DefaultRegisterRider(
    private val riderRepository: RiderRepository
) : RegisterRider {
    private val logger: KLogger = KotlinLogging.logger {}

    @Transactional
    override fun registerRider(rider: Rider): Rider {
        logger.info { "Register Rider: $rider" }
        return riderRepository.save(rider)
    }
}
