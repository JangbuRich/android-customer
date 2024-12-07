package com.project.jangburich.api.response.group

data class GetGroupDetailResponse(
    val teamId: Int,
    val isMeLeader: Boolean,
    val storeName: String,
    val teamName: String,
    val description: String,
    val totalPrepaidAmount: Int,
    val remainingAmount: Int,
    val personalAllocatedAmount: Int,
    val userUsedAmount: Int,
    val prepayedStores: List<PrepayedStore>,
    val teamMemberImgUrl: List<String>,
    val totalMemberCount: Int,
    val todayPayments: List<TodayPayment>,
    val totalTodayTransactionCount: Int
)

data class PrepayedStore(
    val storeId: Int,
    val storeName: String,
    val storeImgUrl: String,
    val address: String,
    val isLiked: Boolean
)

data class TodayPayment(
    val currentDate: String,
    val transactionTime: String,
    val menuName: String,
    val consumeUserName: String,
    val useAmount: Int
)
