package com.project.jangburich

import android.app.Application
import com.project.jangburich.api.response.group.GetGroupInfoWithCodeResponse

class MyApplication: Application() {
    companion object {

        lateinit var preferences: PreferenceUtil

        // 회원가입
        var signUpName = ""
        var signUpPhoneNum = ""
        var agreement4 = false
        var agreement5 = false

        // 유저 정보
        var userName = ""

        // 그룹 생성
        var groupType = ""
        var groupName = ""
        var groupDescription = ""
        var groupSecretCode = ""
        var groupAccountNumber = ""
        var groupBankName = ""

        // 내 위치
        var lat = 37.49757415
        var long = 127.0278389
        var category = "ALL"

        var storeId = 0L
        var storeName = ""
        var storeCategory = ""
        var storeImage = ""

        // 선결제
        var prepaymentGroupId = 2L
        var prepaymentTotalPrice = 0
        var prepaymentIndividualPrice = 0

        // 코드 정보
        var code = ""
        var codeGroupInfo = GetGroupInfoWithCodeResponse("","","",0,null,"")
    }
}