package com.sopt.now.compose.viewModel

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.compose.Dto.RequestLoginDto
import com.sopt.now.compose.Dto.ResponseLoginDto
import com.sopt.now.compose.ServicePool
import com.sopt.now.compose.activity.LoginState
import com.sopt.now.compose.activity.MainActivity
import com.sopt.now.compose.activity.SignUpActivity
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {
    private val _loginState = MutableLiveData<LoginState>(LoginState())
    val loginState: LiveData<LoginState> = _loginState

    fun login(userId: String, userPassword: String) {
        val loginRequestDto = RequestLoginDto(userId, userPassword)
        val authService = ServicePool.authService

        authService.login(loginRequestDto).enqueue(object : Callback<ResponseLoginDto> {
            override fun onResponse(
                call: Call<ResponseLoginDto>,
                response: Response<ResponseLoginDto>
            ) {
                if (response.isSuccessful) {
                    _loginState.value = LoginState(isSuccess = true, message = "로그인 성공!")
                } else {
                    val errorMessage = response.errorBody()?.string() ?: "Unknown error"
                    _loginState.value = LoginState(isSuccess = false, message = errorMessage)
                }
            }

            override fun onFailure(call: Call<ResponseLoginDto>, t: Throwable) {
                val errorMessage = "서버 통신 오류: ${t.localizedMessage}"
                _loginState.value = LoginState(isSuccess = false, message = errorMessage)
            }
        })
    }

    fun navigateToSignUp(context: Context) {
        val intent = Intent(context, SignUpActivity::class.java)
        context.startActivity(intent)
    }
}