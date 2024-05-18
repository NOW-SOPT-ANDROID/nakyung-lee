package com.sopt.now.presentation.auth.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.sopt.now.databinding.ActivityLoginBinding
import com.sopt.now.presentation.data.model.dto.RequestLoginDto
import com.sopt.now.presentation.auth.signup.SignupActivity

class LoginActivity : AppCompatActivity() {
    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        clickButtonListener()
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

//    private suspend fun getMemberId() {
//        val memberId = intent.getStringExtra("userId")?.toIntOrNull() ?: 0
//        viewModel.getUserInfo(memberId)
//    }

    private fun getLoginRequestDto(): RequestLoginDto {
        val id = binding.edtLoginId.text.toString()
        val password = binding.edtLoginPassword.text.toString()
        return RequestLoginDto(
            authenticationId = id,
            password = password,
        )
    }
    companion object {
        var memberId = "1"
    }
}
