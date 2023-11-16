package project.controller

import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.validation.Validated
import jakarta.validation.Valid
import project.controller.dto.CreateUserDto
import project.controller.dto.CreaseUserResponseDto
import project.controller.mapper.toUserRecord

@Controller("/api/users")
@Validated
class UserController {

    @Post("/")
    fun createUser(@Valid @Body createUserDto: CreateUserDto): CreaseUserResponseDto {
        val userRecord = createUserDto.toUserRecord()
        val roles = createUserDto.roles.map { it.name }

    }
}