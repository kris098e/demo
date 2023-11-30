package project.controller.dto

import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class UserResponseDto(
    val uuid: String,
    val name: String,
    val email: String,
    val phoneNumber: String,
    val roles: List<String>,
)
