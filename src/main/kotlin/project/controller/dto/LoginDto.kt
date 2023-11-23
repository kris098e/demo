package project.controller.dto

import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class LoginDto(
    val username: String,
    val password: String,
)
