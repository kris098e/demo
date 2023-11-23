package project.factory

import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.Factory
import java.util.UUID

@Factory
class UuidGeneratorFactory {

    @Bean
    fun uuidGenerator(): UuidGenerator {
        return UuidGenerator()
    }
}

class UuidGenerator {
    fun generateUuid(): UUID {
        return UUID.randomUUID()
    }
}