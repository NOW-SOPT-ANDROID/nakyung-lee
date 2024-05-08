package com.sopt.now.presentation.auth.signup

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sopt.now.databinding.ActivitySignupBinding
import com.sopt.now.presentation.RequestSignUpDto
import com.sopt.now.presentation.auth.login.LoginActivity

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