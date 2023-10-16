package project.utils.exception.exceptions

import io.micronaut.http.HttpStatus

class NotFoundException(
        message: String,
        cause: Throwable? = null,
) : BaseRestApiException(
        statusCode = HttpStatus.NOT_FOUND,
        error = RestApiError.notFound(message),
        cause = cause,
)