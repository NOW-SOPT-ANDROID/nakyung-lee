package com.sopt.now.presentation.auth.login

data class LoginState(
    val status: LoginStatus,
    val message: String,
    val memberId: String? = null
)