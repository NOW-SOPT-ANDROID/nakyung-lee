package com.sopt.now.presentation.auth.login

import androidx.lifecycle.LiveData
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
    private val _loginStateLiveData = MutableLiveData<LoginState>()

    val loginStateLiveData: LiveData<LoginState>
        get() = _loginStateLiveData

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
                    _loginStateLiveData.value = LoginState(
                        LoginStatus.SUCCESS,
                        "로그인 성공 user의 id: $memberId",
                        memberId
                    )
                } else {
                    handleError(response)
                }
            }

            override fun onFailure(call: Call<ResponseLoginDto>, t: Throwable) {
                _loginStateLiveData.value = LoginState(
                    LoginStatus.NETWORK_ERROR,
                    "서버 에러 발생: ${t.localizedMessage}"
                )
            }
        })
    }

    private fun handleError(response: Response<ResponseLoginDto>) {
        val message = when (response.code()) {
            400 -> "잘못된 요청입니다. 입력 값을 확인하세요."
            409 -> "이미 등록된 사용자입니다."
            else -> "서버 오류: ${response.code()}"
        }
        _loginStateLiveData.value = LoginState(LoginStatus.ERROR, message)
    }
}

enum class LoginStatus {
    SUCCESS,
    NETWORK_ERROR,
    INPUT_ERROR,
    DUPLICATE_USER,
    ERROR
}
