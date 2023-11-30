package project.security

import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import project.security.dto.AuthenticatedUser

@MicronautTest
class JwtServiceTests {
    @Inject
    var jwtConfig: JwtConfig? = null

    @Inject
    var jwtService: JwtService? = null

    @Test
    fun `test secret key`() {
        // Given
        val expectedSecret = "heeeeeeeeeeeej"

        // when
        val key = jwtConfig!!.secretKey

        // Then
        Assertions.assertEquals(expectedSecret, key)
    }

    @Test
    fun `test can parse jwt to correct user`() {
        // Given
        val expectedUser = AuthenticatedUser(
            username = "chairman",
            password = "3d7eb3d3dab17ee509a6911e0feb928eb46e956adcada792493614bfb97a7a9c",
        )

        // When
        val user = jwtService!!.parseJwtIntoUser(jwt = correctToken)

        // Then
        Assertions.assertEquals(expectedUser, user)
    }

    @Test
    fun `generate jwt - returns expected jwt`() {
        // Given
        val username = "chairman"
        val password = "john dillermand"
        val expectedJwt = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6ImNoYWlybWFuIiwicGFzc3dvcmQiOiJqb2huIGRpbGxlcm1hbmQifQ.-mcllp6s51JX5tfm9hRrzhTbPhcGPcmYpjliO-TfNS0"

        // When
        val jwt = jwtService!!.generateJwt(username = username, password = password)

        // Then
        Assertions.assertEquals(expectedJwt, jwt)
    }

    companion object {
        private val correctToken = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6ImNoYWlybWFuIiwicGFzc3dvcmQiOiIzZDdlYjNkM2RhYjE3ZWU1MDlhNjkxMWUwZmViOTI4ZWI0NmU5NTZhZGNhZGE3OTI0OTM2MTRiZmI5N2E3YTljIn0.o4WPQUx8dQqSrMS8g5cvK90TAKOQginU9Cl2srh7_c8"
    }
}
