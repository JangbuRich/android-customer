package com.project.jangburich.api.response.home

data class GetWalletDataResponse(
    val point: Int,
    val purchaseHistories: List<PurchaseHistory>
)

data class PurchaseHistory(
    val date: String,
    val amount: Int,
    val transactionTitle: String,
    val transactionType: String
)
