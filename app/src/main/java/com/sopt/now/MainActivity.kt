package com.sopt.now

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.sopt.now.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getStringExtra("id")
        val password = intent.getStringExtra("password")
        val nickname = intent.getStringExtra("nickname")

        binding.txtfieldId.text = "아이디: $id"
        binding.txtfieldPw.text = "비밀번호: $password"
        binding.txtfieldName.text = "안녕하세요, $nickname 님"
    }
}