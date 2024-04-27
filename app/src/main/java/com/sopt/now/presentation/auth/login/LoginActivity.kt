package com.sopt.now.presentation.auth.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.sopt.now.presentation.main.MainActivity
import com.sopt.now.databinding.ActivityLoginBinding
import com.sopt.now.presentation.auth.signup.SignupActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    // 회원가입 시 받은 id랑 pw를 저장할 (임시) 변수
    private var registeredId: String? = null
    private var registeredPassword: String? = null
    private var registeredNickname: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            // 임시 변수에 받은 id랑 pw를 저장
            val inputId = binding.txtfieldId.text.toString()
            val inputPassword = binding.txtfieldPw.text.toString()

            if (inputId == registeredId && inputPassword == registeredPassword) {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("id", inputId)
                intent.putExtra("password", inputPassword)
                intent.putExtra("nickname", registeredNickname)
                startActivity(intent)
            }
            else if (inputId != registeredId){
                Snackbar.make(
                    binding.root,
                    "아이디가 일치하지 않습니다",
                    Snackbar.LENGTH_LONG
                ).show()
            }
            else if (inputPassword != registeredPassword){
                Snackbar.make(
                    binding.root,
                    "비밀번호가 일치하지 않습니다",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
        binding.btnSignup.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivityForResult(intent, REQUEST_SIGNUP)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 123 && resultCode == Activity.RESULT_OK) {
            registeredId = data?.getStringExtra("id")
            registeredPassword = data?.getStringExtra("password")
            registeredNickname = data?.getStringExtra("nickname")
        }
    }
    companion object {
        const val REQUEST_SIGNUP = 123
    }
}