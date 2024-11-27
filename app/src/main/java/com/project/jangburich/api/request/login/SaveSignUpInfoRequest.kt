package com.project.jangburich.api.request.login

data class SaveSignUpInfoRequest(
    val name: String,
    val phoneNum: String,
    val agreeMarketing: Boolean,
    val agreeAdvertisement: Boolean
)