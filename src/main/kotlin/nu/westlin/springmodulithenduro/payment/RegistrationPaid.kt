package nu.westlin.springmodulithenduro.payment

import nu.westlin.springmodulithenduro.domain.Rider

data class RegistrationPaid(val rider: Rider, val amount: Int) {}
