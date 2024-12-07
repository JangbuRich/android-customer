package com.project.jangburich.api.response.store

data class OrderResponse(
    val orderId: Long,
    val totalAmount: Int,
    val items: List<OrderedItem>?
)

data class OrderedItem(
    val menuName: String,
    val quantity: Int,
    val price: Int
)
