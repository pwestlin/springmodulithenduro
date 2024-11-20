package nu.westlin.springmodulithenduro.domain

import org.springframework.data.annotation.Id


data class Rider(@Id val id: Long, val name: String )