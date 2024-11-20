package com.project.jangburich.api.response

data class LoginResponse(
    val accessToken: String,
    val accessTokenExpires: Long,
    val refreshToken: String,
    val refreshTokenExpires: Long
)
