package com.project.jangburich.api.request.home

data class ReadyKakaoPayRequest(
    val paymentType: String,
    val totalAmount: String
)
