package com.project.jangburich.api

import com.project.jangburich.api.request.group.CreateGroupRequest
import com.project.jangburich.api.request.login.SaveSignUpInfoRequest
import com.project.jangburich.api.request.store.PrepayRequest
import com.project.jangburich.api.response.BaseResponse
import com.project.jangburich.api.response.group.CreateGroupResponse
import com.project.jangburich.api.response.group.GetGroupInfoWithCodeResponse
import com.project.jangburich.api.response.group.GetGroupResponse
import com.project.jangburich.api.response.home.GetHomeDataResponse
import com.project.jangburich.api.response.home.GetWalletDataResponse
import com.project.jangburich.api.response.login.LoginResponse
import com.project.jangburich.api.response.login.MessageResponse
import com.project.jangburich.api.response.store.GetStoreDetailResponse
import com.project.jangburich.api.response.store.GetStoreListResponse
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PartMap
import retrofit2.http.Path

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

    // 지갑 내역 조회
    @GET("/user/wallet")
    fun getWalletData(
        @Header("Authorization") token: String
    ): Call<BaseResponse<GetWalletDataResponse>>
    // 그룹 생성
    @POST("/teams")
    fun createGroup(
        @Header("Authorization") token: String,
        @Body parameters: CreateGroupRequest
    ): Call<BaseResponse<CreateGroupResponse>>

    // 그룹 정보 가져오기
    @GET("/teams")
    fun getGroup(
        @Header("Authorization") token: String
    ): Call<BaseResponse<List<GetGroupResponse>>>

    // 매장 상세 페이지
    @GET("/store/{storeId}")
    fun getStoreDetail(
        @Header("Authorization") token: String,
        @Path("storeId") storeId: Long
    ): Call<BaseResponse<GetStoreDetailResponse>>

    // 비밀 코드로 팀 정보 조회
    @GET("/teams/info/secretcode/{secretCode}")
    fun getGroupInfoWithCode(
        @Header("Authorization") token: String,
        @Path("secretCode") secretCode: String
    ): Call<BaseResponse<GetGroupInfoWithCodeResponse>>

    // 비밀 코드로 팀 입장
    @POST("/teams/join/{joinCode}")
    fun enterGroup(
        @Header("Authorization") token: String,
        @Path("joinCode") joinCode: String
    ): Call<BaseResponse<MessageResponse>>

    // 선결제
    @POST("/prepay")
    fun prepay(
        @Header("Authorization") token: String,
        @Body parameters: PrepayRequest
    ): Call<BaseResponse<MessageResponse>>

    // 매장 정보 가져오기
    @Multipart
    @POST("/store/category")
    fun getStoreList(
        @Header("Authorization") token: String,
        @PartMap parameters: Map<String, @JvmSuppressWildcards RequestBody>
    ): Call<BaseResponse<GetStoreListResponse>>
}