package com.sopt.now.presentation.auth.login

import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sopt.now.presentation.RequestLoginDto
import com.sopt.now.presentation.ResponseLoginDto
import com.sopt.now.presentation.ResponseUserInfoDto
import com.sopt.now.presentation.ServicePool
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class LoginViewModel : ViewModel() {
    private val authService by lazy { ServicePool.authService }
    val liveData = MutableLiveData<LoginState>()
    fun getUserInfo(memberId: Int): Call<ResponseUserInfoDto> {
        return authService.getUserInfo(memberId)
    }
    fun login(request: RequestLoginDto) {
        authService.login(request).enqueue(object : Callback<ResponseLoginDto> {
            override fun onResponse(
                call: Call<ResponseLoginDto>,
                response: Response<ResponseLoginDto>
            ) {
                if (response.isSuccessful) {
                    val memberId = response.headers()["location"]

                    liveData.value = LoginState(
                        isSuccess = true,
                        message = "로그인 성공 user의 id: $memberId ",
                        memberId = memberId
                    )
                } else {
                    Log.e(
                        "LoginError",
                        "HTTP ${response.code()}: ${response.errorBody()?.string()}"
                    )
                    handleError(response)
                }
            }

            override fun onFailure(call: Call<ResponseLoginDto>, t: Throwable) {
                Log.e("LoginError", "로그인 중 오류 발생: ${t.localizedMessage}")
                liveData.value = LoginState(isSuccess = false, message = "서버 에러 발생: ${t.localizedMessage}")
            }
        })
    }
    private fun handleError(response: Response<ResponseLoginDto>) {
        val message = when (response.code()) {
            400 -> "잘못된 요청입니다. 입력 값을 확인하세요."
            409 -> "이미 등록된 사용자입니다."
            else -> "로그인 실패: ${response.message()}"
        }
        liveData.value = LoginState(isSuccess = false, message = message)
    }
}