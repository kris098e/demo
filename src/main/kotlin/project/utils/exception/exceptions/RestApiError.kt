package project.utils.exception.exceptions

import io.micronaut.http.HttpStatus
import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class RestApiError(
    val code: HttpStatus,
    val message: String,
) {

    companion object {
        fun notFound(message: String): RestApiError {
            return RestApiError(HttpStatus.NOT_FOUND, message)
        }

        fun badRequest(message: String): RestApiError {
            return RestApiError(HttpStatus.BAD_REQUEST, message)
        }

        fun unauthorized(message: String): RestApiError {
            return RestApiError(HttpStatus.UNAUTHORIZED, message)
        }

        fun internalServerError(message: String): RestApiError {
            return RestApiError(HttpStatus.INTERNAL_SERVER_ERROR, message)
        }
    }
}