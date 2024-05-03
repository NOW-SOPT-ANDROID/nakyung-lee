package com.sopt.now.compose

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sopt.now.compose.Dto.ResponseUserInfoDto
import com.sopt.now.compose.user.UserInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
@Composable
fun MyPageFragment(context: Context, userId: String) {
    var userInfo by remember { mutableStateOf<UserInfo?>(null) }

    LaunchedEffect(userId) {
        try {
            userInfo = getUserInfo(userId)
        } catch (e: Exception) {
            Log.e("MyPageFragment", "Error: ${e.message}")
        }
    }

    userInfo?.let { user ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp, vertical = 10.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.width(20.dp))
                Text(
                    text = user.nickname,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "ID",
                    fontSize = 25.sp
                )
                Text(
                    text = user.id,
                    fontSize = 20.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Phone",
                    fontSize = 25.sp
                )
                Text(
                    text = user.phone,
                    fontSize = 20.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

suspend fun getUserInfo(userId: String): UserInfo {
    return suspendCoroutine { continuation ->
        ServicePool.authService.getUserInfo(userId).enqueue(object : Callback<ResponseUserInfoDto> {
            override fun onResponse(
                call: Call<ResponseUserInfoDto>,
                response: Response<ResponseUserInfoDto>,
            ) {
                if (response.isSuccessful) {
                    val data: ResponseUserInfoDto? = response.body()
                    data?.let {
                        continuation.resume(
                            UserInfo(
                                it.data.authenticationId,
                                "",
                                it.data.nickname,
                                it.data.phone
                            )
                        )
                    }
                } else {
                    continuation.resumeWithException(Exception("Failed to fetch user info"))
                }
            }

            override fun onFailure(call: Call<ResponseUserInfoDto>, t: Throwable) {
                continuation.resumeWithException(t)
            }
        })
    }
}