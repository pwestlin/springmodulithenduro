package nu.westlin.springmodulithenduro.domain

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface RiderRepository : CrudRepository<Rider, Long>
