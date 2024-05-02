package com.sopt.now.presentation.main.mypage

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.sopt.now.databinding.FragmentMypageBinding
import com.sopt.now.presentation.ServicePool
import com.sopt.now.presentation.ResponseUserInfoDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyPageFragment : Fragment() {
    private var _binding: FragmentMypageBinding? = null
    private val binding get() = _binding!!

    private var memberId: Int? = null

    companion object {
        fun newInstance(memberId: Int): MyPageFragment {
            val fragment = MyPageFragment()
            val args = Bundle().apply {
                putInt("memberId", memberId)
            }
            fragment.arguments = args
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Arguments로 전달된 memberId 가져오기
        memberId = arguments?.getInt("memberId")

        // 회원 정보를 가져오는 API 호출
        memberId?.let { memberId ->
            ServicePool.authService.getUserInfo(memberId).enqueue(object : Callback<ResponseUserInfoDto> {
                override fun onResponse(
                    call: Call<ResponseUserInfoDto>,
                    response: Response<ResponseUserInfoDto>
                ) {
                    if (response.isSuccessful) {
                        val userInfo = response.body()?.data

                        userInfo?.let {
                            // 가져온 회원 정보를 TextView 등에 표시
                            binding.tvId.text = "아이디: ${it.authenticationId}"
                            binding.tvNickname.text = "닉네임: ${it.nickname}"
                        }
                    } else {
                        // 오류 처리
                        Toast.makeText(requireContext(), "회원 정보를 가져오는데 실패했습니다.", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ResponseUserInfoDto>, t: Throwable) {
                    // 실패 시 처리
                    Log.e("MyPageFragment", "Failed to fetch user info: ${t.message}")
                    Toast.makeText(requireContext(), "서버와의 통신 실패", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
