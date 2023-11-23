package project.controller.dto

data class UserResponseDto(
    val uuid: String,
    val name: String,
    val email: String,
    val phoneNumber: String,
    val roles: List<String>,
)
