package com.project.jangburich.api.response.store

data class GetStoreListResponse(
    val content: List<Store>,
    val pageable: Pageable,
    val last: Boolean,
    val totalPages: Int,
    val totalElements: Int,
    val size: Int,
    val first: Boolean,
    val number: Int,
    val sort: Sort,
    val numberOfElements: Int,
    val empty: Boolean
)

data class Store(
    val storeId: Int,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val isFavorite: Boolean,
    val category: String,
    val distance: Double,
    val businessStatus: String,
    val closeTime: String?,
    val phoneNumber: String?,
    val imageUrl: String?
)

data class Pageable(
    val pageNumber: Int,
    val pageSize: Int,
    val sort: Sort,
    val offset: Int,
    val unpaged: Boolean,
    val paged: Boolean
)

data class Sort(
    val empty: Boolean,
    val sorted: Boolean,
    val unsorted: Boolean
)