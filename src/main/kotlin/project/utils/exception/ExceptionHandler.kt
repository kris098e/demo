package project.utils.exception

import io.micronaut.context.annotation.Requires
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Produces
import io.micronaut.http.server.exceptions.ExceptionHandler
import jakarta.inject.Singleton
import project.utils.exception.exceptions.BaseRestApiException

@Produces
@Singleton
@Requires(classes = [BaseRestApiException::class])
class ExceptionHandler : ExceptionHandler<BaseRestApiException, HttpResponse<Any>>{
    override fun handle(request: HttpRequest<*>?, exception: BaseRestApiException?): HttpResponse<Any> {
        return HttpResponse.status<Any>(exception?.statusCode).body(exception?.error)
    }
}