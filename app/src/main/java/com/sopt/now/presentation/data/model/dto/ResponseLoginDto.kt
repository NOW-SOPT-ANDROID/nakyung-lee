package com.sopt.now.presentation.data.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseLoginDto(
    @SerialName("code")
    val code: Int,
    @SerialName("message")
    val message: String,
)