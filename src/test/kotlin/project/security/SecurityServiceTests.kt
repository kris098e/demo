package project.security

import io.mockk.mockk
import org.jooq.DSLContext
import project.repository.RolesRepo
import project.repository.UserRepo

class SecurityServiceTests {

    private val userRepo = mockk<UserRepo>()
    private val jwtService = mockk<JwtService>()
    private val securityService = SecurityService(
        userRepo = userRepo, jwtService = jwtService
    )
}