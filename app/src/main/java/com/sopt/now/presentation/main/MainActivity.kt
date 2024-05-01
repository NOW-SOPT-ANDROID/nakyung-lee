package com.sopt.now.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.sopt.now.presentation.main.mypage.MyPageFragment
import com.sopt.now.R
import com.sopt.now.databinding.ActivityMainBinding
import com.sopt.now.presentation.main.home.HomeFragment
import com.sopt.now.presentation.main.search.SearchFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val currentFragment = supportFragmentManager.findFragmentById(binding.fcvHome.id)
        if (currentFragment == null) {
            supportFragmentManager.beginTransaction()
                .add(binding.fcvHome.id, HomeFragment())
                .commit()
        }
        setBottomNavigation()
    }
    private fun setBottomNavigation() {
        binding.bnvHome.setOnItemSelectedListener{
            when (it.itemId) {
                R.id.menu_home -> {
                    replaceFragment(HomeFragment())
                    true
                }

                R.id.menu_search -> {
                    replaceFragment(SearchFragment())
                    true
                }

                R.id.menu_mypage -> {
                    val id = intent.getStringExtra("id")
                    val password = intent.getStringExtra("password")
                    val nickname = intent.getStringExtra("nickname")

                    // MyPageFragment로 데이터 전달
                    val bundle = Bundle().apply {
                        putString("id", id)
                        putString("password", password)
                        putString("nickname", nickname)
                    }

                    val myPageFragment = MyPageFragment()
                    myPageFragment.arguments = bundle

                    replaceFragment(myPageFragment)
                    true
                }

                else -> false
            }
        }
    }
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fcv_home, fragment)
            .commit()
    }
}

