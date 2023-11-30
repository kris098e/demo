package project.controller.dto

import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class ShiftsResponseDto(
    val uuid: String,
    val role: String,
    val show: ShowsResponseDto,
    val user: UserResponseDto,
)
