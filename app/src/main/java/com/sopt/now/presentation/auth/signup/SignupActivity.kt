package com.sopt.now.presentation.auth.signup

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.sopt.now.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSignup.setOnClickListener {
            val id = binding.txtfieldId.text.toString()
            val password = binding.txtfieldPw.text.toString()
            val nickname = binding.txtfieldName.text.toString()
            val mbti = binding.txtfieldMbti.text.toString()

            if (id.isEmpty() || password.isEmpty() || nickname.isEmpty() || mbti.isEmpty()) {
                Snackbar.make(
                    binding.root,
                    "모든 정보를 입력하세요",
                    Snackbar.LENGTH_LONG
                ).show()
            } else if (binding.txtfieldId.length() < 6 || binding.txtfieldId.length() > 10) {
                Snackbar.make(
                    binding.root,
                    "아이디를 6~10자로 설정해주세요",
                    Snackbar.LENGTH_LONG
                ).show()
            } else if (binding.txtfieldPw.length() < 8 || binding.txtfieldPw.length() > 12) {
                Snackbar.make(
                    binding.root,
                    "비밀번호를 8~12자로 설정해주세요",
                    Snackbar.LENGTH_LONG
                ).show()
            } else if (binding.txtfieldName.length() <= 0) {
                Snackbar.make(
                    binding.root,
                    "닉네임은 한 글자 이상으로 설정해주세요",
                    Snackbar.LENGTH_LONG
                ).show()
            } else {
                val resultIntent = Intent()
                resultIntent.putExtra("id", id)
                resultIntent.putExtra("password", password)
                resultIntent.putExtra("nickname", nickname)
                resultIntent.putExtra("mbti", mbti)
                setResult(Activity.RESULT_OK, resultIntent)
                Toast.makeText(this, "회원가입 성공!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}