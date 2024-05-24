package com.sopt.now.presentation.main.mypage

data class UserInfo (
    val isSuccess : Boolean,
    val message : String,
    val id : String ="",
    val nickname : String ="",
    val phone : String=""
)
