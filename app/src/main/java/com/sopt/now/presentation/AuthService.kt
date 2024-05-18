package com.sopt.now.presentation

import com.sopt.now.presentation.Dto.RequestLoginDto
import com.sopt.now.presentation.Dto.RequestSignUpDto
import com.sopt.now.presentation.Dto.ResponseLoginDto
import com.sopt.now.presentation.Dto.ResponseSignUpDto
import com.sopt.now.presentation.Dto.ResponseUserInfoDto
import retrofit2.Call
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
    suspend fun getUserInfo(
        @Header("memberid") memberId: Int
    ): ResponseUserInfoDto
}
