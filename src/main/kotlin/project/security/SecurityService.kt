package project.security

import jakarta.inject.Singleton
import org.jooq.generated.tables.records.UserRecord
import project.repository.UserRepo
import project.utils.exception.exceptions.UnauthorizedRequestException

@Singleton
class SecurityService(
    private val userRepo: UserRepo,
    private val jwtService: JwtService,
) {

    fun verifyAuthentication(token: String): UserRecord {
        val authenticatedUser = jwtService.parseJwtIntoUser(token)
        val user =
            userRepo.getUser(username = authenticatedUser.username)
            ?: throw UnauthorizedRequestException("Invalid credentials")
        if (user.password != authenticatedUser.password) {
            throw UnauthorizedRequestException("Invalid credentials")
        }

        return user
    }
}