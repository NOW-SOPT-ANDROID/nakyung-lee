package com.sopt.now.presentation.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.presentation.Dto.RequestLoginDto
import com.sopt.now.presentation.Dto.ResponseLoginDto
import com.sopt.now.presentation.Dto.ResponseUserInfoDto
import com.sopt.now.presentation.ServicePool
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
class LoginViewModel : ViewModel() {
    private val authService by lazy { ServicePool.authService }
    private val _loginStateLiveData = MutableLiveData<LoginState>()

    suspend fun getUserInfo(memberId: Int): ResponseUserInfoDto {
        return authService.getUserInfo(memberId)
    }

    fun login(request: RequestLoginDto) {
        viewModelScope.launch {
            try {
                val response = authService.login(request)
                _loginStateLiveData.value = LoginState(true, "로그인 성공")
            } catch (e: HttpException) {
                _loginStateLiveData.value = LoginState(false, "로그인 실패 ${e.code()}")
            }
        }
    }
}

enum class LoginStatus {
    SUCCESS,
    NETWORK_ERROR,
    INPUT_ERROR,
    DUPLICATE_USER,
    ERROR
}
