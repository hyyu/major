package io.cloudyhug.domain.model.error

import io.cloudyhug.data.network.model.error.ErrorResponse

data class ErrorModel(
    val errorCode: Int,
    val message: String
)

fun ErrorResponse.toModel() = ErrorModel(
    errorCode = errorCode,
    message = message
)
