package project.factory

import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.Factory
import jakarta.inject.Singleton
import java.util.UUID

@Singleton
class UuidGenerator {
    fun generateUuid(): UUID {
        return UUID.randomUUID()
    }
}