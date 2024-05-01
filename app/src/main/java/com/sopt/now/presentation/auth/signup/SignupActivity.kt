package com.sopt.now.presentation.auth.signup

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sopt.now.databinding.ActivitySignupBinding
import com.sopt.now.presentation.RequestSignUpDto
import com.sopt.now.presentation.ResponseSignUpDto
import com.sopt.now.presentation.ServicePool
import com.sopt.now.presentation.auth.login.LoginActivity
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Call

class SignupActivity : AppCompatActivity() {

    private val binding by lazy { ActivitySignupBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<SignUpViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews()
        initObserver()
    }

    private fun initViews() {
        binding.btnSignup.setOnClickListener {
            viewModel.signUp(getSignUpRequestDto())
        }
    }

    private fun initObserver() {
        viewModel.liveData.observe(this) { state ->
            if (state.isSuccess) {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            } else {
                Toast.makeText(
                    this@SignupActivity,
                    state.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
    private fun getSignUpRequestDto(): RequestSignUpDto {
        val id = binding.etId.text.toString()
        val password = binding.etPw.text.toString()
        val nickname = binding.etName.text.toString()
        val phoneNumber = binding.etNumber.text.toString()
        return RequestSignUpDto(
            authenticationId = id,
            password = password,
            nickname = nickname,
            phone = phoneNumber
        )
    }
}

data class SignUpState(
    val isSuccess: Boolean,
    val message: String
)

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