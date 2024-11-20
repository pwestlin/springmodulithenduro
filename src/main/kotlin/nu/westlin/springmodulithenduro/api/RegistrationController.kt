package nu.westlin.springmodulithenduro.api

import nu.westlin.springmodulithenduro.domain.Rider
import nu.westlin.springmodulithenduro.registration.RegisterRider
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/registration"])
class RegistrationController(
    private val registerRider: RegisterRider
) {

    @PostMapping("")
    fun newRider(@RequestBody name: String): Long {
        return registerRider.registerRider(Rider(0, name)).id
    }
}