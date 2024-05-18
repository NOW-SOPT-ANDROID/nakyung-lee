package com.sopt.now.presentation.data.interceptor

import com.sopt.now.presentation.auth.login.LoginActivity.Companion.memberId
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val authResponse = chain.proceed(chain.request().newBuilder().addHeader("memberId", memberId).build())
        memberId = authResponse.header("location").toString()

        return chain.proceed(chain.request().newBuilder().addHeader("memberId", memberId).build())
    }
}