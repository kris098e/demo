package project.utils

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.client.BlockingHttpClient
import kotlin.reflect.KClass


fun <I, O : Any> BlockingHttpClient.send(request: HttpRequest<I>, bodyType: KClass<O>): HttpResponse<O> =
    exchange(request, bodyType.java)