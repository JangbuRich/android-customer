package com.project.jangburich.ui.home.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.jangburich.R
import com.project.jangburich.api.ApiClient
import com.project.jangburich.api.TokenManager
import com.project.jangburich.api.response.BaseResponse
import com.project.jangburich.api.response.home.GetHomeDataResponse
import com.project.jangburich.api.response.home.Team
import com.project.jangburich.api.response.login.LoginResponse
import com.project.jangburich.ui.MainActivity
import com.project.jangburich.ui.home.HomeFragment
import com.project.jangburich.ui.login.SignUpUserInfoFragment
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    var userName = MutableLiveData<String>()
    var userPoint = MutableLiveData<String>()
    var joinedTeamCount = MutableLiveData<Int>()
    var reservationCount = MutableLiveData<Int>()
    var currentDate = MutableLiveData<String>()

    var teamList = MutableLiveData<MutableList<Team>>()

    init {
        teamList.value = mutableListOf<Team>()
    }

    fun getHomeData(activity: MainActivity) {

        var tempTeamList = mutableListOf<Team>()

        val apiClient = ApiClient(activity)
        val tokenManager = TokenManager(activity)

        apiClient.apiService.getHomeData("Bearer ${tokenManager.getAccessToken()}").enqueue(object :
            Callback<BaseResponse<GetHomeDataResponse>> {
            override fun onResponse(
                call: Call<BaseResponse<GetHomeDataResponse>>,
                response: Response<BaseResponse<GetHomeDataResponse>>
            ) {
                Log.d("##", "onResponse 성공: " + response.body().toString())
                if (response.isSuccessful) {
                    // 정상적으로 통신이 성공된 경우
                    val result: BaseResponse<GetHomeDataResponse>? = response.body()
                    Log.d("##", "onResponse 성공: " + result?.toString())

                    if (!(result?.data?.teams.isNullOrEmpty())) {
                        for (i in 0 until result?.data?.teams?.size!!) {
                            var teamId = result.data.teams[i].teamId
                            var storeId = result.data.teams[i].storeId
                            var dDay = result.data.teams[i].dDay
                            var storeImage = result.data.teams[i].storeImgUrl
                            var isLike = result.data.teams[i].isLikedAtStore
                            var teamName = result.data.teams[i].teamName
                            var storeName = result.data.teams[i].storeName
                            var totalAmount = result.data.teams[i].totalAmount
                            var currentAmount = result.data.teams[i].currentAmount


                            var t1 = Team(
                                teamId,
                                storeId,
                                dDay,
                                storeImage,
                                isLike,
                                teamName,
                                storeName,
                                totalAmount,
                                currentAmount)

                            tempTeamList.add(t1)
                        }
                    }

                    teamList.value = tempTeamList

                    userName.value = result?.data?.userName
                    userPoint.value = result?.data?.usablePoint.toString()
                    joinedTeamCount.value = result?.data?.joinedTeamCount?.toInt()
                    reservationCount.value = result?.data?.reservationCount?.toInt()
                    currentDate.value = result?.data?.currentDate

                } else {
                    // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                    var result: BaseResponse<GetHomeDataResponse>? = response.body()
                    Log.d("##", "onResponse 실패")
                    Log.d("##", "onResponse 실패: " + response.code())
                    Log.d("##", "onResponse 실패: " + response.body())
                    val errorBody = response.errorBody()?.string() // 에러 응답 데이터를 문자열로 얻음
                    Log.d("##", "Error Response: $errorBody")

                }
            }

            override fun onFailure(call: Call<BaseResponse<GetHomeDataResponse>>, t: Throwable) {
                // 통신 실패
                Log.d("##", "onFailure 에러: " + t.message.toString())
            }
        })
    }
}