package com.sopt.now

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sopt.now.databinding.FragmentHomeBinding
import com.sopt.now.databinding.ItemFriendBinding

class HomeFragment: Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var friendListAdapter: FriendListAdapter //
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val myProfile = MyProfile(
            profileImage = R.drawable.ic_person_black_24,
            name = "이나경",
            description = "Last Chance - 소수빈"
        )

        val mockFriendList = listOf<Friend>(
            Friend(
                profileImage = R.drawable.ic_person_black_24,
                name = "배인혁",
                selfDescription = "아 제 이상형입니다",
            ),
            Friend(
                profileImage = R.drawable.ic_person_black_24,
                name = "이정하",
                selfDescription = "귀여운 사람이 좋습니다",
            ),
            Friend(
                profileImage = R.drawable.ic_person_black_24,
                name = "이도현",
                selfDescription = "청순한 상도 좋아합니다",
            ),
            Friend(
                profileImage = R.drawable.ic_person_black_24,
                name = "장영도",
                selfDescription = "좋아했는데 이제는 좋아하지 않는..",
            ),
            Friend(
                profileImage = R.drawable.ic_person_black_24,
                name = "하현상",
                selfDescription = "말랑뽀짝 현상이",
            ),
            Friend(
                profileImage = R.drawable.ic_person_black_24,
                name = "서호철",
                selfDescription = "이적한다면 따라갈지도..",
            ),
            Friend(
                profileImage = R.drawable.ic_person_black_24,
                name = "김원필",
                selfDescription = "아직도 직캠을 보며 자는 날이 많습니다",
            ),
            Friend(
                profileImage = R.drawable.ic_person_black_24,
                name = "김석진",
                selfDescription = "제 인생 첫 최애이자 마지막 최애입니다",
            ),
            Friend(
                profileImage = R.drawable.ic_person_black_24,
                name = "이민혁",
                selfDescription = "수많은 민혁 중 몬스타엑스 이민혁",
            ),
            Friend(
                profileImage = R.drawable.ic_person_black_24,
                name = "이현우",
                selfDescription = "이 분은 나이를 안 먹으시더라구요..",
            ),
        )

        friendListAdapter = FriendListAdapter(myProfile)
        binding.rvFriends.adapter = friendListAdapter
        binding.rvFriends.layoutManager = LinearLayoutManager(requireContext())

        friendListAdapter.setFriendList(mockFriendList)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}