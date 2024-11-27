package com.project.jangburich.ui.store.viewModel

import android.util.Log
import android.view.Menu
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.jangburich.MyApplication
import com.project.jangburich.R
import com.project.jangburich.api.ApiClient
import com.project.jangburich.api.TokenManager
import com.project.jangburich.api.request.login.SaveSignUpInfoRequest
import com.project.jangburich.api.request.store.GetStoreListRequest
import com.project.jangburich.api.response.BaseResponse
import com.project.jangburich.api.response.home.GetHomeDataResponse
import com.project.jangburich.api.response.home.Team
import com.project.jangburich.api.response.login.MessageResponse
import com.project.jangburich.api.response.store.GetStoreListResponse
import com.project.jangburich.api.response.store.Store
import com.project.jangburich.api.response.store.StoreMenu
import com.project.jangburich.ui.MainActivity
import com.project.jangburich.ui.home.HomeFragment
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoreViewModel : ViewModel() {

    var storeList = MutableLiveData<MutableList<Store>>()
    var menuList = MutableLiveData<MutableList<StoreMenu>>()
    var storeDetailData = MutableLiveData<GetStoreDetailResponse>()


    init {
        storeList.value = mutableListOf<Store>()
        menuList.value = mutableListOf<StoreMenu>()
    }

    fun getStoreList(activity: MainActivity, category: String) {
        val apiClient = ApiClient(activity)
        val tokenManager = TokenManager(activity)

        var tempStoreList = mutableListOf<Store>()

        val params = HashMap<String, RequestBody>()
        params["searchRadius"] = 3.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        params["category"] = MyApplication.category.toRequestBody("text/plain".toMediaTypeOrNull())
        params["lat"] = MyApplication.lat.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        params["lon"] = MyApplication.long.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        params["size"] = 10.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        params["offset"] = 0.toString().toRequestBody("text/plain".toMediaTypeOrNull())

        apiClient.apiService.getStoreList("Bearer ${tokenManager.getAccessToken()}", params).enqueue(object :
            Callback<BaseResponse<GetStoreListResponse>> {
            override fun onResponse(
                call: Call<BaseResponse<GetStoreListResponse>>,
                response: Response<BaseResponse<GetStoreListResponse>>
            ) {
                Log.d("##", "onResponse 성공: " + response.body().toString())
                if (response.isSuccessful) {
                    // 정상적으로 통신이 성공된 경우
                    val result: BaseResponse<GetStoreListResponse>? = response.body()
                    Log.d("##", "onResponse 성공: " + result?.toString())

                    if (!(result?.data?.content.isNullOrEmpty())) {
                        for (i in 0 until result?.data?.content?.size!!) {
                            var storeId = result.data.content[i].storeId
                            var storeName = result.data.content[i].name
                            var latitude = result.data.content[i].latitude
                            var longitude = result.data.content[i].longitude
                            var isFavorite = result.data.content[i].isFavorite
                            var category = result.data.content[i].category
                            var distance = result.data.content[i].distance
                            var businessStatus = result.data.content[i].businessStatus
                            var closeTime = result.data.content[i].closeTime
                            var phoneNumber = result.data.content[i].phoneNumber
                            var storeImage = result.data.content[i].imageUrl

                            var s1 = Store(
                                storeId,
                                storeName,
                                latitude,
                                longitude,
                                isFavorite,
                                category,
                                distance,
                                businessStatus,
                                closeTime,
                                phoneNumber,
                                storeImage)

                            tempStoreList.add(s1)
                        }
                    }

                    storeList.value = tempStoreList
                } else {
                    // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                    var result: BaseResponse<GetStoreListResponse>? = response.body()
                    Log.d("##", "onResponse 실패")
                    Log.d("##", "onResponse 실패: " + response.code())
                    Log.d("##", "onResponse 실패: " + response.body())
                    val errorBody = response.errorBody()?.string() // 에러 응답 데이터를 문자열로 얻음
                    Log.d("##", "Error Response: $errorBody")

                }
            }

            override fun onFailure(call: Call<BaseResponse<GetStoreListResponse>>, t: Throwable) {
                // 통신 실패
                Log.d("##", "onFailure 에러: " + t.message.toString())
            }
        })
    }

}