package project.utils.exception.exceptions

import io.micronaut.http.HttpStatus

abstract class BaseRestApiException(
        val statusCode: HttpStatus,
        val error: RestApiError,
        cause: Throwable? = null,
) : RuntimeException(error.message, cause)