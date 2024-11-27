package com.project.jangburich.ui.group.viewModel

import android.util.Log
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.jangburich.MyApplication
import com.project.jangburich.R
import com.project.jangburich.api.ApiClient
import com.project.jangburich.api.TokenManager
import com.project.jangburich.api.request.group.CreateGroupRequest
import com.project.jangburich.api.response.BaseResponse
import com.project.jangburich.api.response.group.GetGroupInfoWithCodeResponse
import com.project.jangburich.api.response.home.Team
import com.project.jangburich.api.response.login.MessageResponse
import com.project.jangburich.ui.MainActivity
import com.project.jangburich.ui.group.CreateGroupInviteFragment
import com.project.jangburich.ui.group.EnterCodeGroupFragment
import com.project.jangburich.ui.group.PrePaymentCompleteFragment
import com.project.jangburich.ui.home.HomeFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GroupViewModel: ViewModel() {

    var groupList = MutableLiveData<MutableList<GetGroupResponse>>()
    var groupInfo = MutableLiveData<GetGroupInfoWithCodeResponse?>()

    init {
        groupList.value = mutableListOf<GetGroupResponse>()
    }

    fun createGroup(activity: MainActivity) {

        val apiClient = ApiClient(activity)
        val tokenManager = TokenManager(activity)

        var groupInfo = CreateGroupRequest(MyApplication.groupType, MyApplication.groupName, MyApplication.groupDescription, MyApplication.groupSecretCode, MyApplication.groupAccountNumber, MyApplication.groupBankName)

        apiClient.apiService.createGroup("Bearer ${tokenManager.getAccessToken()}", groupInfo).enqueue(object :
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

                    val nextFragment = CreateGroupInviteFragment()

                    val transaction = activity.manager.beginTransaction()
                    transaction.replace(R.id.fragmentContainerView_main, nextFragment)
                    transaction.addToBackStack("")
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
    fun getGroupInfoWithCode(activity: MainActivity, code: String) {

        val apiClient = ApiClient(activity)
        val tokenManager = TokenManager(activity)

        apiClient.apiService.getGroupInfoWithCode("Bearer ${tokenManager.getAccessToken()}", code).enqueue(object :
            Callback<BaseResponse<GetGroupInfoWithCodeResponse>> {
            override fun onResponse(
                call: Call<BaseResponse<GetGroupInfoWithCodeResponse>>,
                response: Response<BaseResponse<GetGroupInfoWithCodeResponse>>
            ) {
                Log.d("##", "onResponse 성공: " + response.body().toString())
                if (response.isSuccessful) {
                    // 정상적으로 통신이 성공된 경우
                    val result: BaseResponse<GetGroupInfoWithCodeResponse>? = response.body()
                    Log.d("##", "onResponse 성공: " + result?.toString())

                    MyApplication.codeGroupInfo = result?.data!!

                    groupInfo.value =  result?.data

                    val nextFragment = EnterCodeGroupFragment()

                    val transaction = activity.manager.beginTransaction()
                    transaction.replace(R.id.fragmentContainerView_main, nextFragment)
                    transaction.addToBackStack("")
                    transaction.commit()

                } else {
                    // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                    var result: BaseResponse<GetGroupInfoWithCodeResponse>? = response.body()
                    Log.d("##", "onResponse 실패")
                    Log.d("##", "onResponse 실패: " + response.code())
                    Log.d("##", "onResponse 실패: " + response.body())
                    val errorBody = response.errorBody()?.string() // 에러 응답 데이터를 문자열로 얻음
                    Log.d("##", "Error Response: $errorBody")

                }
            }

            override fun onFailure(call: Call<BaseResponse<GetGroupInfoWithCodeResponse>>, t: Throwable) {
                // 통신 실패
                Log.d("##", "onFailure 에러: " + t.message.toString())
            }
        })
    }
}