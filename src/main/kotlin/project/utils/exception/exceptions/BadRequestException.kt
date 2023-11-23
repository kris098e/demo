package project.utils.exception.exceptions

import io.micronaut.http.HttpStatus

class BadRequestException(
        message: String,
        cause: Throwable? = null,
) : BaseRestApiException(
        statusCode = HttpStatus.BAD_REQUEST,
        error = RestApiError.badRequest(message),
        cause = cause,
)