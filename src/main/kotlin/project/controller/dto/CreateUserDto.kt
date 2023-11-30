package project.controller.dto

import io.micronaut.serde.annotation.Serdeable
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Size

@Serdeable
data class CreateUserDto(
    val name: String,
    val username: String,
    val password: String,

    @field:Email
    val email: String,

    @field:Size(min = 8, max = 8)
    val phoneNumber: String,
    val isSuper: Boolean,
    val roles: List<Role>,
)

@Serdeable
enum class Role(val roleName: String) {
    CLEANING("Cleaning"),
    TECHNICAL("Technical"),
    SALES("Sales"),
    MAINTENANCE("Maintenance")
}
