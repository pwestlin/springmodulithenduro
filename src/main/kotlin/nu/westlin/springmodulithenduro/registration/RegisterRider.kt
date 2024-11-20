package nu.westlin.springmodulithenduro.registration

import nu.westlin.springmodulithenduro.domain.Rider

interface RegisterRider {
    fun registerRider(rider: Rider): Rider
}