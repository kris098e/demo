package project.controller.dto

import io.micronaut.serde.annotation.Serdeable
import java.util.UUID

@Serdeable
data class CreaseUserResponseDto(
    val uuid: UUID,
    val name: String,
    val password: String, // Bcrypted
    val username: String,
    val email: String,
    val phoneNumber: String,
    val isSuper: Boolean,
    val roles: List<String>,
    val ticketCount: Int,
    val totalShifs: Int,
    val jwt: String,
)