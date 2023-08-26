package io.cloudyhug.domain.model.error

class ErrorResponseException(
    val error: ErrorModel
): Exception()
