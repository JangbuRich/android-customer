package com.project.jangburich.api.request.store

data class OrderReqeust(
    val storeId: Long,
    val teamId: Long,
    val items: List<OrderItem>
)

data class OrderItem(
    val menuId: Long,
    val quantity: Int
)