package com.sopt.now.presentation.auth.signup

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.sopt.now.databinding.ActivitySignupBinding
import com.sopt.now.presentation.data.model.dto.RequestSignUpDto
import com.sopt.now.presentation.auth.login.LoginActivity

class SignupActivity : AppCompatActivity() {
    private val binding by lazy { ActivitySignupBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<SignUpViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        SignupButtonClickListener()
        observeSignupState()
    }

    private fun SignupButtonClickListener() {
        binding.btnSignup.setOnClickListener {
            viewModel.signUp(getSignUpRequestDto())
        }
    }

    private fun observeSignupState() {
        viewModel.liveData.observe(this) { SignUpState ->
            if (SignUpState.isSuccess) {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
                Toast.makeText(
                    this@SignupActivity,
                    SignUpState.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
    private fun getSignUpRequestDto(): RequestSignUpDto {
        val id = binding.edtSignupId.text.toString()
        val password = binding.edtSignupPassword.text.toString()
        val nickname = binding.edtSignupNickname.text.toString()
        val phoneNumber = binding.edtSignupPhonenumber.text.toString()
        return RequestSignUpDto(
            authenticationId = id,
            password = password,
            nickname = nickname,
            phone = phoneNumber
        )
    }
}