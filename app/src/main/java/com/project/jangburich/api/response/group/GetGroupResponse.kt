package com.project.jangburich.api.response.group

data class GetGroupResponse(
    val teamName: String,
    val createdDate: String,
    val teamType: String,
    val isLiked: Boolean,
    val peopleCount: Int,
    val isMeLeader: Boolean,
    val profileImageUrl: List<String>?
)