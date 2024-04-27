package com.sopt.now.presentation.main.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sopt.now.presentation.main.home.FriendListAdapter
import com.sopt.now.databinding.FragmentMypageBinding
class MyPageFragment: Fragment() {
    private var _binding: FragmentMypageBinding? = null
    private val binding get() = _binding!!
    private lateinit var friendListAdapter: FriendListAdapter //
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMypageBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = arguments?.getString("id")
        val password = arguments?.getString("password")
        val nickname = arguments?.getString("nickname")

        with(binding) {
            tvId.text = "아이디: $id"
            tvPassword.text =  "비밀번호: $password"
            tvNickname.text = "닉네임: $nickname"
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}