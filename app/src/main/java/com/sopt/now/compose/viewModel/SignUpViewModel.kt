package com.sopt.now.compose.viewModel

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sopt.now.compose.Dto.RequestSignUpDto
import com.sopt.now.compose.Dto.ResponseSignUpDto
import com.sopt.now.compose.ServicePool
import com.sopt.now.compose.activity.LoginActivity
import com.sopt.now.compose.activity.LoginState
import com.sopt.now.compose.activity.SignUpState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpViewModel : ViewModel() {
    private val _signUpState = MutableLiveData<SignUpState>(SignUpState())
    val signUpState: LiveData<SignUpState> = _signUpState
    fun signUp(
        userId: String,
        userPassword: String,
        userNickname: String,
        userPhone: String,
        context: Context
    ) {
        val signUpRequestDto = RequestSignUpDto(userId, userPassword, userNickname, userPhone)
        val authService = ServicePool.authService

        authService.signUp(signUpRequestDto).enqueue(object : Callback<ResponseSignUpDto> {
            override fun onResponse(
                call: Call<ResponseSignUpDto>,
                response: Response<ResponseSignUpDto>
            ) {
                if (response.isSuccessful) {
                    val userId = response.headers()["location"]
                    _signUpState.value = SignUpState(isSuccess = true, message = "회원가입 성공!")

                    // 회원가입 성공 시 로그인 화면으로 이동
                    val intent = Intent(context, LoginActivity::class.java)
                    context.startActivity(intent)
                    // 회원가입 성공 메시지 토스트로 띄우기
                    Toast.makeText(context, "회원가입 성공!", Toast.LENGTH_SHORT).show()
                } else {
                    val errorMessage = response.errorBody()?.string() ?: "Unknown error"
                    _signUpState.value = SignUpState(isSuccess = false, message = errorMessage)
                    // 회원가입 실패 시 실패 원인 토스트로 띄우기
                    Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseSignUpDto>, t: Throwable) {
                val errorMessage = "서버 통신 오류: ${t.localizedMessage}"
                _signUpState.value = SignUpState(isSuccess = false, message = errorMessage)
                // 서버 통신 오류 시 토스트로 띄우기
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }
}