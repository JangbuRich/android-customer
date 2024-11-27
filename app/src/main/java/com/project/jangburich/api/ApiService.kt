package com.project.jangburich.api

import com.project.jangburich.api.request.group.CreateGroupRequest
import com.project.jangburich.api.request.login.SaveSignUpInfoRequest
import com.project.jangburich.api.response.BaseResponse
import com.project.jangburich.api.response.group.GetGroupInfoWithCodeResponse
import com.project.jangburich.api.response.home.GetHomeDataResponse
import com.project.jangburich.api.response.login.LoginResponse
import com.project.jangburich.api.response.login.MessageResponse
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

    // 추가 정보 저장
    @POST("/user/additionalInfo")
    fun saveSignUpInfo(
        @Header("Authorization") token: String,
        @Body parameters: SaveSignUpInfoRequest
    ): Call<BaseResponse<MessageResponse>>

    // 홈화면
    @GET("/user/home")
    fun getHomeData(
        @Header("Authorization") token: String
    ): Call<BaseResponse<GetHomeDataResponse>>

    // 그룹 생성
    @POST("/teams")
    fun createGroup(
        @Header("Authorization") token: String,
        @Body parameters: CreateGroupRequest
    ): Call<BaseResponse<MessageResponse>>

    // 비밀 코드로 팀 정보 조회
    @GET("/teams/secretCode/{secretCode}")
    fun getGroupInfoWithCode(
        @Header("Authorization") token: String,
        @Path("secretCode") secretCode: String
    ): Call<BaseResponse<GetGroupInfoWithCodeResponse>>
}