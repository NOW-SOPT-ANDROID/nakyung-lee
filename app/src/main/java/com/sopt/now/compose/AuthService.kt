package com.sopt.now.compose

import com.sopt.now.compose.Dto.RequestLoginDto
import com.sopt.now.compose.Dto.RequestSignUpDto
import com.sopt.now.compose.Dto.ResponseLoginDto
import com.sopt.now.compose.Dto.ResponseSignUpDto
import com.sopt.now.compose.Dto.ResponseUserInfoDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
interface AuthService {
    @POST("member/join")
    fun signUp(
        @Body request: RequestSignUpDto,
    ): Call<ResponseSignUpDto>

    @POST("member/login")
    fun login(
        @Body request: RequestLoginDto
    ): Call<ResponseLoginDto>

    @GET("member/info")
    fun getUserInfo(
        @Header("memberid") memberId: Int
    ): Call<ResponseUserInfoDto>
}