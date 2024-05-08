package com.sopt.now.presentation.auth.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
                val intent = Intent(this@LoginActivity, MainActivity::class.java).apply {
                    loginState.memberId?.let { memberId ->
                        putExtra("memberId", memberId)
                    }
                }
                startActivity(intent)
                finish()
            }
        }
    }

    private fun setUserData() {
        val memberId = intent.getStringExtra("userId")?.toIntOrNull() ?: 0
        viewModel.getUserInfo(memberId)
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