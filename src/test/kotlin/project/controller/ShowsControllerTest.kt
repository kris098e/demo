package project.controller

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.client.BlockingHttpClient
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import io.mockk.every
import io.mockk.mockk
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import project.factory.UuidGenerator
import project.repository.ShowsRepo
import project.security.SecurityService
import project.utils.exception.exceptions.UnauthorizedRequestException
import kotlin.reflect.KClass

/**
@MicronautTest
class ShowsControllerTest {

    @field:Client("/api/shows")
    private lateinit var httpClient: HttpClient
    @Inject
    private lateinit var showsRepo: ShowsRepo
    @Inject
    private lateinit var securityService: SecurityService
    @Inject
    private lateinit var uudGenerator: UuidGenerator

    @MockBean(ShowsRepo::class)
    fun showsRepo(): ShowsRepo = mockk()
    @MockBean(SecurityService::class)
    fun securityService(): SecurityService = mockk()
    @MockBean(UuidGenerator::class)
    fun uuidGenerator(): UuidGenerator = mockk()

    @ParameterizedTest(name = "test unauthorized with invalid JWT, url: {0}")
    @MethodSource("unauthorized")
    fun `get all shows unauthorized `(
        url: String,
    ) {
        // Given
        every {
            securityService.verifyAuthentication(invalidJwt)
        } throws UnauthorizedRequestException("Invalid credentials")

        // When / Then
        val exception = Assertions.assertThrows(HttpClientResponseException::class.java) {
            httpClient.toBlocking().send(
                HttpRequest.GET<Any>(url)
                    .header("Authorization", invalidJwt),
                String::class
            )
        }
        Assertions.assertEquals(401, exception.status.code)
    }


    companion object {
        @JvmStatic
        val invalidJwt = "invalid"
        @JvmStatic
        fun unauthorized() = listOf(
            Arguments.of(
                "/all"
            ),
            Arguments.of(
                ""
            ),
            Arguments.of(
                "uuid/Cleaner"
            )
        )
    }

    fun <I, O : Any> BlockingHttpClient.send(request: HttpRequest<I>, bodyType: KClass<O>): HttpResponse<O> =
        exchange(request, bodyType.java)
}
*/