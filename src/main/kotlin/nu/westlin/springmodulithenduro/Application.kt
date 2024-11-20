package nu.westlin.springmodulithenduro

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync

@SpringBootApplication
@EnableAsync
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
