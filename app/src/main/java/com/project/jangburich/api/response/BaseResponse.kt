package com.project.jangburich.api.response

data class BaseResponse<T>(
    val data: T?,
    val transaction_time: String,
    val status: String,
    val description: String?,
    val statusCode: Int
)
