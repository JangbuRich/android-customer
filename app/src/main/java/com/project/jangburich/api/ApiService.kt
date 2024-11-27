package com.project.jangburich.api

import com.project.jangburich.api.request.LoginRequest
import com.project.jangburich.api.response.BaseResponse
import com.project.jangburich.api.response.LoginResponse
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PartMap

interface ApiService {
    // 로그인
    @Multipart
    @POST("/user/join/user")
    fun login(
        @PartMap parameters: Map<String, @JvmSuppressWildcards RequestBody>
    ): Call<BaseResponse<LoginResponse>>
}