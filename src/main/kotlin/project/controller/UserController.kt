package project.controller

import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Header
import io.micronaut.http.annotation.Post
import io.micronaut.validation.Validated
import jakarta.validation.Valid
import project.controller.dto.CreateUserDto
import project.controller.dto.CreaseUserResponseDto
import project.controller.mapper.toUserRecord
import project.controller.mapper.toUserResponseDto
import project.factory.UuidGenerator
import project.repository.RolesRepo
import project.repository.UserRepo
import project.security.JwtService
import project.security.SecurityService
import project.utils.exception.exceptions.UnauthorizedRequestException

@Controller("/api/users")
@Validated
class UserController (
    private val securityService: SecurityService,
    private val jwtService: JwtService,
    private val uuidGenerator: UuidGenerator,
    private val userRepo: UserRepo,
    private val rolesRepo: RolesRepo,
) {

    @Post("/")
    fun createUser(
        @Valid @Body createUserDto: CreateUserDto,
        @Header("Authorization") token: String,
    ): CreaseUserResponseDto {
        val user = securityService.verifyAuthentication(token)
        if (!user.isSuper) {
            throw UnauthorizedRequestException("Only super users can create users")
        }

        val userUuid = uuidGenerator.generateUuid()
        val userRecord = createUserDto.toUserRecord(uuid = userUuid)
        val storedUser = userRepo.insertUser(userRecord)
        val roles = createUserDto.roles.map { it.name }
        val storedRoles = rolesRepo.storeRoles(userRecord.id, roles)
        val jwt = jwtService.generateJwt(
            username = storedUser.username,
            password = storedUser.password,
        )

        return userRecord.toUserResponseDto(roles = storedRoles, jwt = jwt)
    }
}
