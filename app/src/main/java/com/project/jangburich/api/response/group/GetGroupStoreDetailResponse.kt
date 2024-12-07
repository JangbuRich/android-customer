package com.project.jangburich.api.response.group

data class GetGroupStoreDetailResponse(
    val storeId: Int,
    val isMeLeader: Boolean,
    val storeName: String,
    val isLiked: Boolean,
    val remainingAmount: Int?,
    val availableAmount: Int?,
    val myUsedAmount: Int?,
    val totalPrepayedAmount: Int?,
    val remainingPrepayedAmount: Int?,
    val personalUsableAmount: Int?,
    val usageStartDate: String,
    val usageEndDate: String,
    val myPaymentHistories: List<StorePaymentHistory>
)

data class StorePaymentHistory(
    val paymentDate: String,
    val paymentTime: String,
    val menuName: String,
    val price: Int,
    val userName: String
)