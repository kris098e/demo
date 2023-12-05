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
import io.mockk.verify
import jakarta.inject.Inject
import org.jooq.generated.tables.records.ShowRecord
import org.jooq.generated.tables.records.UserRecord
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import project.controller.dto.CreateShowDto
import project.factory.UuidGenerator
import project.repository.ShowsRepo
import project.security.SecurityService
import project.utils.exception.exceptions.UnauthorizedRequestException
import project.utils.send
import java.time.OffsetDateTime
import java.util.*
import kotlin.reflect.KClass

@MicronautTest
class ShowsControllerTest {

    @Inject
    @field:Client("/api/shows")
    private lateinit var httpClient: HttpClient
    @Inject
    private lateinit var showsRepo: ShowsRepo
    @Inject
    private lateinit var securityService: SecurityService
    @Inject
    private lateinit var uuidGenerator: UuidGenerator

    @MockBean(ShowsRepo::class)
    fun showsRepo(): ShowsRepo = mockk()
    @MockBean(SecurityService::class)
    fun securityService(): SecurityService = mockk()
    @MockBean(UuidGenerator::class)
    fun uuidGenerator(): UuidGenerator = mockk()

    @ParameterizedTest(name = "test unauthorized with invalid JWT, url: {0}")
    @MethodSource("unauthorized")
    fun `get all shows unauthorized `(
        httpBlock: HttpClient.() -> HttpResponse<Any>,
    ) {
        // Given
        every {
            securityService.verifyAuthentication(invalidJwt)
        } throws UnauthorizedRequestException("Invalid credentials")

        // When / Then
        val exception = Assertions.assertThrows(HttpClientResponseException::class.java) {
            httpClient.httpBlock()
        }
        Assertions.assertEquals(401, exception.status.code)
        Assertions.assertEquals("Invalid credentials", exception.message)
    }

    @Test
    fun `can get all shows`() {
        // Given
        val jwt = "valid"
        every {
            securityService.verifyAuthentication(token = jwt)
        } returns UserRecord().apply {
            username = "test"
            password = "test"
        }
        every {
            showsRepo.fetchAllShows()
        } returns listOf(
            ShowRecord().apply {
                id = 1
                uuid = "d0129af0-5cbf-4d47-b12a-f85b7675b6fc"
                from = OffsetDateTime.parse("2021-01-01T00:00:00+00:00")
                to = OffsetDateTime.parse("2021-01-01T00:00:00+00:00")
                title = "test"
            },
            ShowRecord().apply {
                id = 2
                uuid = "b3652f82-ca37-4498-809e-9df783cd5504"
                from = OffsetDateTime.parse("2021-01-01T00:00:00+00:00")
                to = OffsetDateTime.parse("2021-01-01T00:00:00+00:00")
                title = "test"
            },
        )

        // When
        val response = httpClient.toBlocking().send(
            HttpRequest.GET<Any>("/all")
                .header("Authorization", jwt),
            String::class
        )

        // Then
        Assertions.assertEquals(200, response.status.code)
        Assertions.assertEquals(
            """
            [{"from":"2021-01-01T00:00:00Z","to":"2021-01-01T00:00:00Z","uuid":"d0129af0-5cbf-4d47-b12a-f85b7675b6fc","title":"test"},{"from":"2021-01-01T00:00:00Z","to":"2021-01-01T00:00:00Z","uuid":"b3652f82-ca37-4498-809e-9df783cd5504","title":"test"}]
            """.trimIndent(),
            response.body.get(),
        )
    }

    @Test
    fun `can create shows`() {
        // Given
        val jwt = "valid"
        val uuid = UUID.fromString("d0129af0-5cbf-4d47-b12a-f85b7675b6fc")
        val createShowDto = CreateShowDto(
            from = OffsetDateTime.parse("2021-01-01T00:00:00+00:00"),
            to = OffsetDateTime.parse("2021-01-01T00:00:00+00:00"),
            title = "test"
        )
        every {
            securityService.verifyAuthentication(token = jwt)
        } returns UserRecord().apply {
            username = "test"
            password = "test"
        }
        every {
            uuidGenerator.generateUuid()
        } returns uuid
        every {
            showsRepo.createShow(
                match { showRecord: ShowRecord ->
                    return@match showRecord.from == createShowDto.from &&
                            showRecord.to == createShowDto.to &&
                            showRecord.title == createShowDto.title &&
                            showRecord.uuid == uuid.toString()
                }
            )
        } returns ShowRecord().apply {
            id = 1
            this.uuid = uuid.toString()
            from = OffsetDateTime.parse("2021-01-01T00:00:00+00:00")
            to = OffsetDateTime.parse("2021-01-01T00:00:00+00:00")
            title = "test"
        }

        // When
        val response = httpClient.toBlocking().send(
            HttpRequest.POST<Any>(
                "/create",
                createShowDto
            ).header("Authorization", jwt),
            String::class
        )

        // Then
        verify(exactly = 1) { uuidGenerator.generateUuid() }
        Assertions.assertEquals(201, response.status.code)
        Assertions.assertEquals(
            """
            {"from":"2021-01-01T00:00:00Z","to":"2021-01-01T00:00:00Z","uuid":"$uuid","title":"test"}
            """.trimIndent(),
            response.body.get(),
        )

    }

    companion object {
        @JvmStatic
        val invalidJwt = "invalid"
        @JvmStatic
        fun unauthorized() = listOf(
            Arguments.of(
                { httpClient: HttpClient -> httpClient.toBlocking().send(
                    HttpRequest.GET<Any>("/all")
                        .header("Authorization", invalidJwt),
                    String::class
                )}
            ),
            Arguments.of(
                { httpClient: HttpClient -> httpClient.toBlocking().send(
                    HttpRequest.POST<Any>(
                        "/create",
                        CreateShowDto(
                            from = OffsetDateTime.now(),
                            to = OffsetDateTime.now(),
                            title = "test"
                        )
                    ).header("Authorization", invalidJwt),
                    String::class
                )
                }
            )
        )
    }

    fun <I, O : Any> BlockingHttpClient.send(request: HttpRequest<I>, bodyType: KClass<O>): HttpResponse<O> =
        exchange(request, bodyType.java)
}