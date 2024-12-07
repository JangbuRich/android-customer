package com.project.jangburich.api.response.store

data class GetStoreDetailResponse(
    val reservable: String,
    val isLiked: Boolean,
    val category: String,
    val address: String,
    val operationStatus: String,
    val closeTime: String,
    val contactNumber: String,
    val representativeImg: String,
    val storeMenus: List<StoreMenu>?
)

data class StoreMenu(
    val menuId: Long,
    val menuName: String,
    val isSignatureMenu: Boolean?, // null 허용
    val description: String,
    val price: Int,
    val menuImgUrl: String
)