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
import com.project.jangburich.api.request.store.PrepayRequest
import com.project.jangburich.api.response.BaseResponse
import com.project.jangburich.api.response.group.CreateGroupResponse
import com.project.jangburich.api.response.group.GetGroupDetailResponse
import com.project.jangburich.api.response.group.GetGroupInfoWithCodeResponse
import com.project.jangburich.api.response.group.GetGroupResponse
import com.project.jangburich.api.response.group.GetGroupStoreDetailResponse
import com.project.jangburich.api.response.group.GetPrepayData
import com.project.jangburich.api.response.login.MessageResponse
import com.project.jangburich.ui.MainActivity
import com.project.jangburich.ui.group.CreateGroupInviteFragment
import com.project.jangburich.ui.group.EnterCodeGroupFragment
import com.project.jangburich.ui.group.GroupDetailFragment
import com.project.jangburich.ui.group.GroupStoreDetailFragment
import com.project.jangburich.ui.group.PrePaymentCompleteFragment
import com.project.jangburich.ui.group.PrePaymentTotalFragment
import com.project.jangburich.ui.home.HomeFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GroupViewModel: ViewModel() {

    var groupList = MutableLiveData<MutableList<GetGroupResponse>>()
    var groupInfo = MutableLiveData<GetGroupInfoWithCodeResponse?>()

    var prepayData = MutableLiveData<GetPrepayData>()

    init {
        groupList.value = mutableListOf<GetGroupResponse>()
    }

    fun createGroup(activity: MainActivity) {

        val apiClient = ApiClient(activity)
        val tokenManager = TokenManager(activity)

        var groupInfo = CreateGroupRequest(MyApplication.groupType, MyApplication.groupName, MyApplication.groupDescription, MyApplication.groupAccountNumber, MyApplication.groupBankName)

        apiClient.apiService.createGroup("Bearer ${tokenManager.getAccessToken()}", groupInfo).enqueue(object :
            Callback<BaseResponse<CreateGroupResponse>> {
            override fun onResponse(
                call: Call<BaseResponse<CreateGroupResponse>>,
                response: Response<BaseResponse<CreateGroupResponse>>
            ) {
                Log.d("##", "onResponse 성공: " + response.body().toString())
                if (response.isSuccessful) {
                    // 정상적으로 통신이 성공된 경우
                    val result: BaseResponse<CreateGroupResponse>? = response.body()
                    Log.d("##", "onResponse 성공: " + result?.toString())

                    MyApplication.groupSecretCode = result?.data?.uuid!!

                    val nextFragment = CreateGroupInviteFragment()

                    val transaction = activity.manager.beginTransaction()
                    transaction.replace(R.id.fragmentContainerView_main, nextFragment)
                    transaction.addToBackStack("")
                    transaction.commit()
                } else {
                    // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                    var result: BaseResponse<CreateGroupResponse>? = response.body()
                    Log.d("##", "onResponse 실패")
                    Log.d("##", "onResponse 실패: " + response.code())
                    Log.d("##", "onResponse 실패: " + response.body())
                    val errorBody = response.errorBody()?.string() // 에러 응답 데이터를 문자열로 얻음
                    Log.d("##", "Error Response: $errorBody")

                }
            }

            override fun onFailure(call: Call<BaseResponse<CreateGroupResponse>>, t: Throwable) {
                // 통신 실패
                Log.d("##", "onFailure 에러: " + t.message.toString())
            }
        })
    }

    fun getGroupList(activity: MainActivity, category: String) {

        var tempGroupList = mutableListOf<GetGroupResponse>()

        val apiClient = ApiClient(activity)
        val tokenManager = TokenManager(activity)

        apiClient.apiService.getGroup("Bearer ${tokenManager.getAccessToken()}", category).enqueue(object :
            Callback<BaseResponse<List<GetGroupResponse>>> {
            override fun onResponse(
                call: Call<BaseResponse<List<GetGroupResponse>>>,
                response: Response<BaseResponse<List<GetGroupResponse>>>
            ) {
                Log.d("##", "onResponse 성공: " + response.body().toString())
                if (response.isSuccessful) {
                    // 정상적으로 통신이 성공된 경우
                    val result: BaseResponse<List<GetGroupResponse>>? = response.body()
                    Log.d("##", "onResponse 성공: " + result?.toString())

                    if (!(result?.data.isNullOrEmpty())) {
                        for (i in 0 until result?.data?.size!!) {
                            var teamId = result.data[i].teamId
                            var teamName = result.data[i].teamName
                            var teamType = result.data[i].teamType
                            var date = result.data[i].createdDate
                            var isLike = result.data[i].isLiked
                            var peopleCount = result.data[i].peopleCount
                            var isLeader = result.data[i].isMeLeader

                            var imageList = result.data[i].profileImageUrl

                            var tempImageList = mutableListOf<String>()
                            for(j in 0 until imageList?.size!!) {
                                tempImageList.add(imageList[j])
                            }

                            var g1 = GetGroupResponse(
                                teamId,
                                teamName,
                                date,
                                teamType,
                                isLike,
                                peopleCount,
                                isLeader,
                                tempImageList
                                )

                            tempGroupList.add(g1)
                        }
                    }

                    groupList.value = tempGroupList

                } else {
                    // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                    var result: BaseResponse<List<GetGroupResponse>>? = response.body()
                    Log.d("##", "onResponse 실패")
                    Log.d("##", "onResponse 실패: " + response.code())
                    Log.d("##", "onResponse 실패: " + response.body())
                    val errorBody = response.errorBody()?.string() // 에러 응답 데이터를 문자열로 얻음
                    Log.d("##", "Error Response: $errorBody")

                }
            }

            override fun onFailure(call: Call<BaseResponse<List<GetGroupResponse>>>, t: Throwable) {
                // 통신 실패
                Log.d("##", "onFailure 에러: " + t.message.toString())
            }
        })
    }

    fun getGroupDetail(activity: MainActivity) {

        var tempGroupList = mutableListOf<GetGroupResponse>()

        val apiClient = ApiClient(activity)
        val tokenManager = TokenManager(activity)

        apiClient.apiService.getGroupDetail("Bearer ${tokenManager.getAccessToken()}", MyApplication.selectedTeamId.toString()).enqueue(object :
            Callback<BaseResponse<GetGroupDetailResponse>> {
            override fun onResponse(
                call: Call<BaseResponse<GetGroupDetailResponse>>,
                response: Response<BaseResponse<GetGroupDetailResponse>>
            ) {
                Log.d("##", "onResponse 성공: " + response.body().toString())
                if (response.isSuccessful) {
                    // 정상적으로 통신이 성공된 경우
                    val result: BaseResponse<GetGroupDetailResponse>? = response.body()
                    Log.d("##", "onResponse 성공: " + result?.toString())

                    MyApplication.selectedGroupDetail = result?.data!!

                    val nextFragment = GroupDetailFragment()

                    val transaction = activity.manager.beginTransaction()
                    transaction.replace(R.id.fragmentContainerView_main, nextFragment)
                    transaction.addToBackStack(null)
                    transaction.commit()
                } else {
                    // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                    var result: BaseResponse<GetGroupDetailResponse>? = response.body()
                    Log.d("##", "onResponse 실패")
                    Log.d("##", "onResponse 실패: " + response.code())
                    Log.d("##", "onResponse 실패: " + response.body())
                    val errorBody = response.errorBody()?.string() // 에러 응답 데이터를 문자열로 얻음
                    Log.d("##", "Error Response: $errorBody")

                }
            }

            override fun onFailure(call: Call<BaseResponse<GetGroupDetailResponse>>, t: Throwable) {
                // 통신 실패
                Log.d("##", "onFailure 에러: " + t.message.toString())
            }
        })
    }

    fun getGroupStoreDetail(activity: MainActivity) {

        val apiClient = ApiClient(activity)
        val tokenManager = TokenManager(activity)

        apiClient.apiService.getGroupStoreDetail("Bearer ${tokenManager.getAccessToken()}", MyApplication.selectedTeamId, MyApplication.selectedGroupStoreId).enqueue(object :
            Callback<BaseResponse<GetGroupStoreDetailResponse>> {
            override fun onResponse(
                call: Call<BaseResponse<GetGroupStoreDetailResponse>>,
                response: Response<BaseResponse<GetGroupStoreDetailResponse>>
            ) {
                Log.d("##", "onResponse 성공: " + response.body().toString())
                if (response.isSuccessful) {
                    // 정상적으로 통신이 성공된 경우
                    val result: BaseResponse<GetGroupStoreDetailResponse>? = response.body()
                    Log.d("##", "onResponse 성공: " + result?.toString())

                    MyApplication.selectedGroupStoreDetail = result?.data!!

                    val nextFragment = GroupStoreDetailFragment()

                    val transaction = activity.manager.beginTransaction()
                    transaction.replace(R.id.fragmentContainerView_main, nextFragment)
                    transaction.addToBackStack(null)
                    transaction.commit()
                } else {
                    // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                    var result: BaseResponse<GetGroupStoreDetailResponse>? = response.body()
                    Log.d("##", "onResponse 실패")
                    Log.d("##", "onResponse 실패: " + response.code())
                    Log.d("##", "onResponse 실패: " + response.body())
                    val errorBody = response.errorBody()?.string() // 에러 응답 데이터를 문자열로 얻음
                    Log.d("##", "Error Response: $errorBody")

                }
            }

            override fun onFailure(call: Call<BaseResponse<GetGroupStoreDetailResponse>>, t: Throwable) {
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

    fun enterGroup(activity: MainActivity) {

        val apiClient = ApiClient(activity)
        val tokenManager = TokenManager(activity)

        apiClient.apiService.enterGroup("Bearer ${tokenManager.getAccessToken()}", MyApplication.code).enqueue(object :
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


                    activity.fragmentManager?.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)

                    val nextFragment = HomeFragment()

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

    fun getPrepayData(activity: MainActivity, storeId: Long, teamId: Long) {

        val apiClient = ApiClient(activity)
        val tokenManager = TokenManager(activity)

        apiClient.apiService.getPrepayData("Bearer ${tokenManager.getAccessToken()}", storeId, teamId).enqueue(object :
            Callback<BaseResponse<GetPrepayData>> {
            override fun onResponse(
                call: Call<BaseResponse<GetPrepayData>>,
                response: Response<BaseResponse<GetPrepayData>>
            ) {
                Log.d("##", "onResponse 성공: " + response.body().toString())
                if (response.isSuccessful) {
                    // 정상적으로 통신이 성공된 경우
                    val result: BaseResponse<GetPrepayData>? = response.body()
                    Log.d("##", "onResponse 성공: " + result?.toString())

                    val nextFragment = PrePaymentTotalFragment()

                    val transaction = activity.manager.beginTransaction()
                    transaction.replace(R.id.fragmentContainerView_main, nextFragment)
                    transaction.addToBackStack(null)
                    transaction.commit()

                    prepayData.value = result?.data!!

                } else {
                    // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                    var result: BaseResponse<GetPrepayData>? = response.body()
                    Log.d("##", "onResponse 실패")
                    Log.d("##", "onResponse 실패: " + response.code())
                    Log.d("##", "onResponse 실패: " + response.body())
                    val errorBody = response.errorBody()?.string() // 에러 응답 데이터를 문자열로 얻음
                    Log.d("##", "Error Response: $errorBody")
                }
            }

            override fun onFailure(call: Call<BaseResponse<GetPrepayData>>, t: Throwable) {
                // 통신 실패
                Log.d("##", "onFailure 에러: " + t.message.toString())
            }
        })
    }

    fun prepay(activity: MainActivity) {

        val apiClient = ApiClient(activity)
        val tokenManager = TokenManager(activity)

        apiClient.apiService.prepay("Bearer ${tokenManager.getAccessToken()}", PrepayRequest(MyApplication.selectedStore.storeId.toLong(), MyApplication.prepaymentGroupId, MyApplication.prepaymentTotalPrice, MyApplication.prepaymentIndividualPrice)).enqueue(object :
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

                    val nextFragment = PrePaymentCompleteFragment()

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
}