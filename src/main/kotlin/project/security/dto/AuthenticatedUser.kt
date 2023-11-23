package project.security.dto

import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class AuthenticatedUser(
    val username: String,
    val password: String,
)