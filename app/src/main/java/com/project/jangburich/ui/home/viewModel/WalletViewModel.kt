package com.project.jangburich.ui.home.viewModel

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.jangburich.api.ApiClient
import com.project.jangburich.api.TokenManager
import com.project.jangburich.api.request.home.ReadyKakaoPayRequest
import com.project.jangburich.api.response.BaseResponse
import com.project.jangburich.api.response.home.GetWalletDataResponse
import com.project.jangburich.api.response.home.PurchaseHistory
import com.project.jangburich.api.response.home.ReadyKakaoPayResponse
import com.project.jangburich.ui.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WalletViewModel: ViewModel() {

    var userPoint = MutableLiveData<Int>()

    var purchaseHistoryList = MutableLiveData<MutableList<PurchaseHistory>>()

    init {
        purchaseHistoryList.value = mutableListOf<PurchaseHistory>()
    }

    fun getWalletData(activity: MainActivity) {

        var tempPurchaseHistory = mutableListOf<PurchaseHistory>()

        val apiClient = ApiClient(activity)
        val tokenManager = TokenManager(activity)

        apiClient.apiService.getWalletData("Bearer ${tokenManager.getAccessToken()}").enqueue(object :
            Callback<BaseResponse<GetWalletDataResponse>> {
            override fun onResponse(
                call: Call<BaseResponse<GetWalletDataResponse>>,
                response: Response<BaseResponse<GetWalletDataResponse>>
            ) {
                Log.d("##", "onResponse 성공: " + response.body().toString())
                if (response.isSuccessful) {
                    // 정상적으로 통신이 성공된 경우
                    val result: BaseResponse<GetWalletDataResponse>? = response.body()
                    Log.d("##", "onResponse 성공: " + result?.toString())

                    userPoint.value = result?.data?.point

                    if (!(result?.data?.purchaseHistories.isNullOrEmpty())) {
                        for (i in 0 until result?.data?.purchaseHistories?.size!!) {
                            var date = result.data.purchaseHistories[i].date
                            var amount = result.data.purchaseHistories[i].amount
                            var transactionTitle = result.data.purchaseHistories[i].transactionTitle
                            var transactionType = result.data.purchaseHistories[i].transactionType

                            var t1 = PurchaseHistory(date, amount, transactionTitle, transactionType)

                            tempPurchaseHistory.add(t1)
                        }
                    }

                    purchaseHistoryList.value = tempPurchaseHistory

                } else {
                    // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                    var result: BaseResponse<GetWalletDataResponse>? = response.body()
                    Log.d("##", "onResponse 실패")
                    Log.d("##", "onResponse 실패: " + response.code())
                    Log.d("##", "onResponse 실패: " + response.body())
                    val errorBody = response.errorBody()?.string() // 에러 응답 데이터를 문자열로 얻음
                    Log.d("##", "Error Response: $errorBody")

                }
            }

            override fun onFailure(call: Call<BaseResponse<GetWalletDataResponse>>, t: Throwable) {
                // 통신 실패
                Log.d("##", "onFailure 에러: " + t.message.toString())
            }
        })
    }
}