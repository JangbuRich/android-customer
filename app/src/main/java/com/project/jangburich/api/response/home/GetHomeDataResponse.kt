package com.project.jangburich.api.response.home

data class GetHomeDataResponse(
    val userId: Int,
    val currentDate: String,
    val userName: String,
    val teams: List<Team>,
    val usablePoint: Int,
    val joinedTeamCount: Int,
    val reservationCount: Int
)

data class Team(
    val teamId: Int,
    val storeId: Int,
    val storeImgUrl: String,
    val isLikedAtStore: Boolean,
    val teamName: String,
    val storeName: String?,
    val totalAmount: Int,
    val currentAmount: Int
)
