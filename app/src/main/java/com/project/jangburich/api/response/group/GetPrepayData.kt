package com.project.jangburich.api.response.group

data class GetPrepayData(
    val minPrepayAmount: Int,
    val wallet: Int,
    val remainPrepay: Int
)