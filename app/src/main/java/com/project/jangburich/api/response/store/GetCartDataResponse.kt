package com.project.jangburich.api.response.store

data class GetCartDataResponse(
    val storeId: Long,
    val storeName: String,
    val storeCategory: String,
    val cartItems: List<CartItem>,
    val totalAmount: Int,
    val discountAmount: Int,
    val finalAmount: Int
)

data class CartItem(
    val menuId: Long,
    val menuImg: String,
    val menuName: String,
    val menuDescription: String,
    val quantity: Int,
    val menuPrice: Int
)
