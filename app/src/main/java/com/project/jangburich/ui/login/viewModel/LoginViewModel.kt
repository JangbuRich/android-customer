package com.project.jangburich.ui.login.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.project.jangburich.MyApplication
import com.project.jangburich.R
import com.project.jangburich.api.ApiClient
import com.project.jangburich.api.TokenManager
import com.project.jangburich.api.request.login.SaveSignUpInfoRequest
import com.project.jangburich.api.response.BaseResponse
import com.project.jangburich.api.response.login.LoginResponse
import com.project.jangburich.api.response.login.MessageResponse
import com.project.jangburich.ui.MainActivity
import com.project.jangburich.ui.home.HomeFragment
import com.project.jangburich.ui.login.SignUpUserInfoFragment
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginViewModel : ViewModel() {

    fun login(activity: MainActivity, token: String) {
        val apiClient = ApiClient(activity)
        val tokenManager = TokenManager(activity)

        val params = HashMap<String, RequestBody>()
        params["authorizationHeader"] = token.toRequestBody("text/plain".toMediaTypeOrNull())

        apiClient.apiService.login(params).enqueue(object :
            Callback<BaseResponse<LoginResponse>> {
                override fun onResponse(
                    call: Call<BaseResponse<LoginResponse>>,
                    response: Response<BaseResponse<LoginResponse>>
                ) {
                    Log.d("##", "onResponse 성공: " + response.body().toString())
                    if (response.isSuccessful) {
                        // 정상적으로 통신이 성공된 경우
                        val result: BaseResponse<LoginResponse>? = response.body()
                        Log.d("##", "onResponse 성공: " + result?.toString())
                        tokenManager.saveTokens(result?.data?.accessToken!!, result.data.refreshToken!!)

                        if(result?.data.alreadyExists) {
                            val nextFragment = HomeFragment()

                            val transaction = activity.supportFragmentManager.beginTransaction()
                            transaction.replace(R.id.fragmentContainerView_main, nextFragment)
                            transaction.addToBackStack(null)
                            transaction.commit()
                        } else {
                            val nextFragment = SignUpUserInfoFragment()

                            val transaction = activity.supportFragmentManager.beginTransaction()
                            transaction.replace(R.id.fragmentContainerView_main, nextFragment)
                            transaction.addToBackStack(null)
                            transaction.commit()
                        }

                    } else {
                        // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                        var result: BaseResponse<LoginResponse>? = response.body()
                        Log.d("##", "onResponse 실패")
                        Log.d("##", "onResponse 실패: " + response.code())
                        Log.d("##", "onResponse 실패: " + response.body())
                        val errorBody = response.errorBody()?.string() // 에러 응답 데이터를 문자열로 얻음
                        Log.d("##", "Error Response: $errorBody")

                    }
                }

                override fun onFailure(call: Call<BaseResponse<LoginResponse>>, t: Throwable) {
                    // 통신 실패
                    Log.d("##", "onFailure 에러: " + t.message.toString())
                }
            })
    }

    fun saveSignUpInfo(activity: MainActivity) {
        val apiClient = ApiClient(activity)
        val tokenManager = TokenManager(activity)

        val params = HashMap<String, RequestBody>()
        params["name"] = MyApplication.signUpName.toRequestBody("text/plain".toMediaTypeOrNull())
        params["phoneNum"] = MyApplication.signUpPhoneNum.toRequestBody("text/plain".toMediaTypeOrNull())
        params["agreeMarketing"] = MyApplication.agreement4.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        params["agreeAdvertisement"] = MyApplication.agreement5.toString().toRequestBody("text/plain".toMediaTypeOrNull())

        var signUpInfo = SaveSignUpInfoRequest(MyApplication.signUpName, MyApplication.signUpPhoneNum, MyApplication.agreement4, MyApplication.agreement5)

        apiClient.apiService.saveSignUpInfo("Bearer ${tokenManager.getAccessToken()}", signUpInfo).enqueue(object :
            Callback<BaseResponse<MessageResponse>> {
            override fun onResponse(
                call: Call<BaseResponse<MessageResponse>>,
                response: Response<BaseResponse<MessageResponse>>
            ) {
                Log.d("##", "onResponse 성공: " + response.body().toString())
                if (response.isSuccessful) {
                    // 정상적으로 통신이 성공된 경우
                    val result: BaseResponse<MessageResponse>? = response.body()
                    Log.d("##", "onResponse 성공: " + result?.toString())

                    val nextFragment = HomeFragment()

                    val transaction = activity.supportFragmentManager.beginTransaction()
                    transaction.replace(R.id.fragmentContainerView_main, nextFragment)
                    transaction.addToBackStack(null)
                    transaction.commit()

                } else {
                    // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                    var result: BaseResponse<MessageResponse>? = response.body()
                    Log.d("##", "onResponse 실패")
                    Log.d("##", "onResponse 실패: " + response.code())
                    Log.d("##", "onResponse 실패: " + response.body())
                    val errorBody = response.errorBody()?.string() // 에러 응답 데이터를 문자열로 얻음
                    Log.d("##", "Error Response: $errorBody")

                }
            }

            override fun onFailure(call: Call<BaseResponse<MessageResponse>>, t: Throwable) {
                // 통신 실패
                Log.d("##", "onFailure 에러: " + t.message.toString())
            }
        })
    }
}