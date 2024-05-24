package com.sopt.now.presentation.main.home

import androidx.annotation.DrawableRes
data class MyProfile(
    @DrawableRes val profileImage: Int,
    val name: String,
    val description: String
)