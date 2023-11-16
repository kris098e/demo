package project.utils.exception.exceptions

import io.micronaut.http.HttpStatus

class UnauthorizedRequestException(
        message: String,
        cause: Throwable? = null,
) : BaseRestApiException(
        statusCode = HttpStatus.UNAUTHORIZED,
        error = RestApiError.unauthorized(message),
        cause = cause,
)