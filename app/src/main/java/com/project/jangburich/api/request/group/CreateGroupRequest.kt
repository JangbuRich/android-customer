package com.project.jangburich.api.request.group

data class CreateGroupRequest(
    val teamType: String,
    val teamName: String,
    val description: String,
    val teamLeaderAccountNumber: String,
    val bankName: String
)
