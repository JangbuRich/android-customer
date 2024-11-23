package com.project.jangburich.api.response.group

data class GetGroupInfoWithCodeResponse(
    val teamName: String,
    val createdAt: String,
    val teamType: String,
    val teamMembers: Int,
    val teamMemberProfileImages: List<String?>?,
    val status: String
)
