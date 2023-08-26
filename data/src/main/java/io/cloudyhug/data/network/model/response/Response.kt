package io.cloudyhug.data.network.model.response

import io.cloudyhug.data.network.model.error.ErrorResponse

data class Response<out T>(
    val statusCode: Int,
    val onSuccess: suspend () -> T,
    val onError: suspend () -> ErrorResponse
)
