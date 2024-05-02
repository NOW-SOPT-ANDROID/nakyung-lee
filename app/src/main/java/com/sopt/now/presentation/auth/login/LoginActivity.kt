package com.sopt.now.presentation.auth.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.sopt.now.presentation.main.MainActivity
import com.sopt.now.databinding.ActivityLoginBinding
import com.sopt.now.databinding.ActivitySignupBinding
import com.sopt.now.presentation.RequestLoginDto
import com.sopt.now.presentation.ResponseLoginDto
import com.sopt.now.presentation.ResponseSignUpDto
import com.sopt.now.presentation.ServicePool
import com.sopt.now.presentation.auth.signup.SignUpState
import com.sopt.now.presentation.auth.signup.SignUpViewModel
import com.sopt.now.presentation.auth.signup.SignupActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

data class LoginState(
    val isSuccess: Boolean,
    val message: String,
    val memberId: String? = null
)
class LoginViewModel : ViewModel() {
    private val authService by lazy { ServicePool.authService }
    val liveData = MutableLiveData<LoginState>()

    fun getUserInfo(memberId: Int) {
        authService.getUserInfo(memberId).enqueue(object : Callback<ResponseLoginDto> {
            override fun onResponse(
                call: Call<ResponseLoginDto>,
                response: Response<ResponseLoginDto>,
            ) {
                if (response.isSuccessful) {
//                    val data: ResponseLoginDto? = response.body()
//                    val authenticationId = data?.data?.authenticationId
//
//                    // memberId를 통해 회원 정보 받아옴
//                    liveData.value = LoginState(
//                        isSuccess = true,
//                        message = "회원 조회 성공: ${data?.message}",
//                        memberId = authenticationId
//                    )
                } else {
                    // 오류 응답 처리

                }
            }

            override fun onFailure(call: Call<ResponseLoginDto>, t: Throwable) {
                liveData.value = LoginState(
                    isSuccess = false,
                    message = "서버 에러"
                )
            }
        })
    }
    fun login(request: RequestLoginDto) {
        authService.login(request).enqueue(object : Callback<ResponseLoginDto> {
            override fun onResponse(
                call: Call<ResponseLoginDto>,
                response: Response<ResponseLoginDto>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
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
            else -> "회원가입 실패: ${response.message()}"
        }
        liveData.value = LoginState(isSuccess = false, message = message)
    }
}
class LoginActivity : AppCompatActivity() {
    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews()
        initObserver()
        setUserData() // user data 세팅
    }

    private fun initViews() {
        // 로그인 버튼 CLICK
        binding.btnLogin.setOnClickListener {
            viewModel.login(getLoginRequestDto())
        }
        // 회원가입 버튼 CLICK
        binding.btnSignup.setOnClickListener {
            Intent(this@LoginActivity, SignupActivity::class.java).let {
                startActivity(it)
            }
        }
    }

    private fun initObserver() {
        viewModel.liveData.observe(this) { loginState ->
            Toast.makeText(
                this@LoginActivity,
                loginState.message,
                Toast.LENGTH_SHORT
            ).show()

            if (loginState.isSuccess) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
    }

    private fun setUserData() {
        val memberId = intent.getStringExtra("userId")?.toIntOrNull() ?: 0
        viewModel.getUserInfo(memberId)
        observeLoginState()
    }
    private fun observeLoginState() {
        viewModel.liveData.observe(this) { userState ->
            if (userState.isSuccess) {
                binding.etId.setText(userState.memberId)
                // 회원 조회 성공 메시지 표시
                Toast.makeText(this, userState.message, Toast.LENGTH_SHORT).show()
            } else {
                // 실패 메시지 표시
                Toast.makeText(this, userState.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun getLoginRequestDto(): RequestLoginDto {
        val id = binding.etId.text.toString()
        val password = binding.etPw.text.toString()
        return RequestLoginDto(
            authenticationId = id,
            password = password,
        )
    }
}