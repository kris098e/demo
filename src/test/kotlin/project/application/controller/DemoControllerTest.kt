package project.application.controller

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.client.BlockingHttpClient
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import io.mockk.every
import io.mockk.mockk
import jakarta.inject.Inject
import org.jooq.generated.tables.records.UsersRecord
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import project.controller.Person
import project.repository.Repo
import project.repository.TestRepo
import kotlin.reflect.KClass

@MicronautTest
class DemoControllerTest {

    @Inject
    @field:Client("/demo")
    lateinit var client: HttpClient

    @Inject
    lateinit var repo: Repo

    @MockBean(Repo::class)
    fun testRepo(): TestRepo {
        return mockk()
    }

    @Test
    fun testIndex() {
        every {
            repo.getUsers("hans")
        } returns listOf(
            UsersRecord(1, "hans", "password", true)
        )

        val response = client.toBlocking()
            .send(
                HttpRequest.GET<Any>("/hej/hans"),
                String::class
            )

        assertEquals("hans", response.body())
    }

    @Test
    fun testSss() {
        every {
            repo.insertUser("1")
        } returns 1
        val response = client.toBlocking()
                .send(HttpRequest.POST("/hej", Person("1")), String::class)

        assertEquals("{\"greet\":\"hej 1 1\"}", response.body())
    }
}

fun <I, O : Any> BlockingHttpClient.send(request: HttpRequest<I>, bodyType: KClass<O>): HttpResponse<O> =
        exchange(request, bodyType.java)