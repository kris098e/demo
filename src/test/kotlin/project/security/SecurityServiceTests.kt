package project.security

import io.mockk.every
import io.mockk.mockk
import org.jooq.DSLContext
import org.jooq.generated.tables.records.UserRecord
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import project.repository.RolesRepo
import project.repository.UserRepo
import project.security.dto.AuthenticatedUser
import project.utils.exception.exceptions.UnauthorizedRequestException

class SecurityServiceTests {

    private val userRepo = mockk<UserRepo>()
    private val jwtService = mockk<JwtService>()
    private val securityService = SecurityService(
        userRepo = userRepo, jwtService = jwtService
    )

    @Test
    fun `verifyAuthentication should return user when valid token is provided`() {
        // Given
        val uuid = "chairman uuid"
        val userRecord = UserRecord().apply {
            id = -1
            this.uuid = uuid
            name = "Chairman"
            username = "chairman"
            password = "chairman"
            email = "chairman@localcinema.com"
            phoneNumber = "123456789"
            isSuper = true
            ticketCount = 0
            totalShifts = 0
        }
        every {
            jwtService.parseJwtIntoUser("chairman jwt")
        } returns AuthenticatedUser(
            username = "chairman", password = "chairman"
        )
        every {
            userRepo.getUser(username = "chairman")
        } returns (userRecord to listOf("Cleaning"))

        // When
        val result = securityService.verifyAuthentication("chairman jwt")

        // Then
        Assertions.assertEquals(userRecord, result)
    }

    @Test
    fun `verifyAuthentication should throw UnauthorizedRequestException when invalid token is provided`() {
        // Given
        every {
            jwtService.parseJwtIntoUser("invalid jwt")
        } returns AuthenticatedUser(
            username = "invalid", password = "invalid"
        )
        every {
            userRepo.getUser(username = "invalid")
        } returns null

        // When
        val exception = Assertions.assertThrows(
            UnauthorizedRequestException::class.java
        ) {
            securityService.verifyAuthentication("invalid jwt")
        }

        // Then
        Assertions.assertEquals("Invalid credentials", exception.message)
    }

    @Test
    fun `verifyAuthentication should throw UnauthorizedRequestException when invalid password is provided`() {
        // Given
        every {
            jwtService.parseJwtIntoUser("invalid jwt")
        } returns AuthenticatedUser(
            username = "some account",
            password = "invalid password"
        )
        val userRecord = UserRecord().apply {
            this.password = "not matching"
        }
        every {
            userRepo.getUser(username = "some account")
        } returns (userRecord to listOf("Cleaning"))

        // When
        val exception = Assertions.assertThrows(
            UnauthorizedRequestException::class.java
        ) {
            securityService.verifyAuthentication("invalid jwt")
        }

        // Then
        Assertions.assertEquals("Invalid credentials", exception.message)
    }

}