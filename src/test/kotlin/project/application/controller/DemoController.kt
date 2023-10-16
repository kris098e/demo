package project.application.controller

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.client.BlockingHttpClient
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.Assert.assertEquals
import org.junit.jupiter.api.Test
import project.controller.Person
import kotlin.reflect.KClass

@MicronautTest
class DemoController {

    @Inject
    @field:Client("/demo")
    lateinit var client: HttpClient

    @Test
    fun testIndex() {
        val response = client.toBlocking()
            .send(
                HttpRequest.GET<Any>("/hej/hans"),
                String::class
            )
        assertEquals("hans", response.body())
    }

    @Test
    fun testSss() {
        val response = client.toBlocking()
                .send(HttpRequest.POST("/hej", Person("1")), String::class)

        assertEquals("{\"greet\":\"hej 1 1\"}", response.body())
    }
}

fun <I, O : Any> BlockingHttpClient.send(request: HttpRequest<I>, bodyType: KClass<O>): HttpResponse<O> =
        exchange(request, bodyType.java)