package com.sopt.now.presentation.data

import com.sopt.now.presentation.data.model.dto.RequestLoginDto
import com.sopt.now.presentation.data.model.dto.RequestSignUpDto
import com.sopt.now.presentation.data.model.dto.ResponseLoginDto
import com.sopt.now.presentation.data.model.dto.ResponseSignUpDto
import com.sopt.now.presentation.data.model.dto.ResponseUserInfoDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthService {
    @POST("member/join")
    suspend fun signUp(
        @Body request: RequestSignUpDto,
    ): ResponseSignUpDto

    @POST("member/login")
    suspend fun login(
        @Body request: RequestLoginDto
    ): ResponseLoginDto

    @GET("member/info")
    suspend fun getUserInfo(): ResponseUserInfoDto
}
