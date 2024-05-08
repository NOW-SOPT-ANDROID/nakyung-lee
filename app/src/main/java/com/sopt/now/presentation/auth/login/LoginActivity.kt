package com.sopt.now.presentation.auth.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.sopt.now.databinding.ActivityLoginBinding
import com.sopt.now.presentation.RequestLoginDto
import com.sopt.now.presentation.auth.signup.SignupActivity
import com.sopt.now.presentation.main.MainActivity

class LoginActivity : AppCompatActivity() {
    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        clickButtonListener()
        initObserver()
        getMemberId()
    }

    private fun clickButtonListener() {
        // 로그인 버튼 클릭
        binding.btnLogin.setOnClickListener {
            viewModel.login(getLoginRequestDto())
        }
        // 회원가입 버튼 클릭
        binding.btnSignup.setOnClickListener {
            Intent(this@LoginActivity, SignupActivity::class.java).let {
                startActivity(it)
            }
        }
    }

    private fun initObserver() {
        viewModel.loginStateLiveData.observe(this) { loginState ->
            Toast.makeText(
                this@LoginActivity,
                loginState.message,
                Toast.LENGTH_SHORT
            ).show()

            when (loginState.status) {
                LoginStatus.SUCCESS -> Intent(this@LoginActivity, MainActivity::class.java).apply {
                    loginState.memberId?.let { memberId ->
                        putExtra("memberId", memberId)
                        startActivity(this)
                    }
                }
                LoginStatus.INPUT_ERROR -> {
                    Toast.makeText(
                        this@LoginActivity,
                        "잘못된 입력입니다. 아이디와 비밀번호를 확인해주세요.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                LoginStatus.DUPLICATE_USER -> {
                    Toast.makeText(
                        this@LoginActivity,
                        "이미 등록된 사용자입니다.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                LoginStatus.ERROR -> {
                    Toast.makeText(
                        this@LoginActivity,
                        "서버 오류가 발생했습니다. 잠시 후 다시 시도해주세요.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                LoginStatus.NETWORK_ERROR -> TODO()
            }
        }
    }

    private fun getMemberId() {
        val memberId = intent.getStringExtra("userId")?.toIntOrNull() ?: 0
        viewModel.getUserInfo(memberId)
    }

    private fun getLoginRequestDto(): RequestLoginDto {
        val id = binding.edtLoginId.text.toString()
        val password = binding.edtLoginPassword.text.toString()
        return RequestLoginDto(
            authenticationId = id,
            password = password,
        )
    }
}
