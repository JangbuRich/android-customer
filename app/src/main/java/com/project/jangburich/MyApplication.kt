package com.project.jangburich

import android.app.Application
import com.project.jangburich.api.request.store.OrderItem
import com.project.jangburich.api.response.group.GetGroupDetailResponse
import com.project.jangburich.api.response.group.GetGroupInfoWithCodeResponse
import com.project.jangburich.api.response.group.GetGroupStoreDetailResponse
import com.project.jangburich.api.response.group.PrepayedStore
import com.project.jangburich.api.response.group.StorePaymentHistory
import com.project.jangburich.api.response.group.TodayPayment
import com.project.jangburich.api.response.store.OrderResponse
import com.project.jangburich.api.response.store.Store
import com.project.jangburich.api.response.store.StoreMenu

class MyApplication: Application() {
    companion object {

        lateinit var preferences: PreferenceUtil

        var todayDate = ""

        var isKakaoPayComplete = false
        var isFirst = true

        // 유저 정보
        var userName = ""

        // 내 그룹
        var groupFilterList = listOf("전체", "그룹장", "그룹원")
        var groupFilterCategory = "ALL"

        var selectedTeamId = 0L
        var selectedGroupStoreId = 0L
        var selectedGroupDetail = GetGroupDetailResponse(
            teamId = 0,
            isMeLeader = false,
            storeName = "",
            teamName = "",
            description = "",
            totalPrepaidAmount = 0,
            remainingAmount = 0,
            personalAllocatedAmount = 0,
            userUsedAmount = 0,
            prepayedStores = mutableListOf<PrepayedStore>(),
            teamMemberImgUrl = mutableListOf<String>(),
            totalMemberCount = 0,
            todayPayments = mutableListOf<TodayPayment>(),
            totalTodayTransactionCount = 0
        )
        var selectedGroupStoreDetail = GetGroupStoreDetailResponse(
            storeId = 0,
            isMeLeader = false,
            storeName = "",
            isLiked = false,
            remainingAmount = null,
            availableAmount = null,
            myUsedAmount = null,
            totalPrepayedAmount = null,
            remainingPrepayedAmount = null,
            personalUsableAmount = null,
            usageStartDate = "",
            usageEndDate = "",
            myPaymentHistories = mutableListOf<StorePaymentHistory>()
        )

        // 내 위치
        var lat = 37.49757415
        var long = 127.0278389
        var category = "ALL"

        var storeId = 0L
        var storeName = ""
        var storeCategory = ""
        var storeImage = ""

        // 충전
        var preChargeAmount = 0

        // 선결제
        var prepaymentGroupId = 0L
        var prepaymentTotalPrice = 0
        var prepaymentIndividualPrice = 0
        var remainPrepayAmount = 0

        var selectedMenuList = StoreMenu(0,"", false, "", 0, "")
        
        var selectedStore = Store(
            storeId = 0,
            name = "",
            latitude = 0.0,
            longitude = 0.0,
            isFavorite = false,
            category = "",
            distance = 1.0,
            businessStatus = "",
            closeTime = "",
            phoneNumber = "",
            imageUrl = ""
        )

        // 장바구니
        var cartStoreId = 0L
        var cartItem = mutableListOf<OrderItem>()

        // 주문 완료
        var orderedData = OrderResponse(0, 0, null)
    }
}