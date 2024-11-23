package com.project.jangburich.api.request.store

data class PrepayRequest(
    val storeId: Long,
    val teamId: Long,
    val prepayAmount: Int,
    val personalAllocatedAmount: Int
)