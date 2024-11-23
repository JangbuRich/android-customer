package com.project.jangburich.api.request.store

data class GetStoreListRequest(
    val searchRadius: Int,
    val category: String,
    val lat: Double,
    val lon: Double,
    val size: Int,
    val offset: Int
)
