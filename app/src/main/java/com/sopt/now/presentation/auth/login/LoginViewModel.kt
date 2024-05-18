package com.sopt.now.presentation.auth.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.presentation.data.model.dto.RequestLoginDto
import com.sopt.now.presentation.data.model.dto.ResponseUserInfoDto
import com.sopt.now.presentation.data.ServicePool
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LoginViewModel : ViewModel() {
    private val authService by lazy { ServicePool.authService }
    private val _loginStateLiveData = MutableLiveData<LoginState>()

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
