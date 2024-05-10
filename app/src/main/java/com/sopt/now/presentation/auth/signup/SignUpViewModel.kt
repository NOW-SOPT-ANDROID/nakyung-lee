package com.sopt.now.presentation.auth.signup

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sopt.now.presentation.Dto.RequestSignUpDto
import com.sopt.now.presentation.Dto.ResponseSignUpDto
import com.sopt.now.presentation.ServicePool
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Call

class SignUpViewModel : ViewModel() {
    private val authService by lazy { ServicePool.authService }
    val liveData = MutableLiveData<SignUpState>()

    fun signUp(signUpRequest: RequestSignUpDto) {
        authService.signUp(signUpRequest).enqueue(object : Callback<ResponseSignUpDto> {
            override fun onResponse(
                call: Call<ResponseSignUpDto>,
                response: Response<ResponseSignUpDto>,
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    val userId = response.headers()["location"]

                    Log.d("SignUpSuccess", "Response data: $data, UserID: $userId")
                    liveData.value = SignUpState(isSuccess = true, message = "회원가입 성공! 유저 ID: $userId")
                } else {
                    Log.e(
                        "SignUpError",
                        "HTTP ${response.code()}: ${response.errorBody()?.string()}"
                    )
                    handleError(response)
                }
            }

            override fun onFailure(call: Call<ResponseSignUpDto>, t: Throwable) {
                Log.e("SignUpFailure", "Error during sign-up: ${t.localizedMessage}")
                liveData.value = SignUpState(isSuccess = false, message = "서버 에러 발생: ${t.localizedMessage}")
            }
        })
    }

    private fun handleError(response: Response<ResponseSignUpDto>) {
        val message = when (response.code()) {
            400 -> "잘못된 요청입니다. 입력 값을 확인하세요."
            409 -> "이미 등록된 사용자입니다."
            else -> "회원가입 실패: ${response.message()}"
        }
        liveData.value = SignUpState(isSuccess = false, message = message)
    }
}