package com.project.jangburich.api.request.store

data class AddCartRequest(
    val storeId: Long,
    val menuId: Long,
    val quantity: Int
)