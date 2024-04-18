package com.sopt.now.compose
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.sopt.now.compose.friend.Friend
import com.sopt.now.compose.friend.FriendItem
import com.sopt.now.compose.user.UserInfo
import com.sopt.now.compose.user.UserItem


val userList = listOf<UserInfo>(
    UserInfo(
        id = "nagaeng",
        password = "qazwsx!@#",
        nickname = "이나경",
        mbti = "ENTJ"
    )
)

val friendList = listOf<Friend>(
    Friend(
        profileImage = R.drawable.baeinhyeok,
        name = "배인혁",
        selfDescription = "아 제 이상형입니다",
    ),
    Friend(
        profileImage = R.drawable.leejungha,
        name = "이정하",
        selfDescription = "귀여운 사람이 좋습니다",
    ),
    Friend(
        profileImage = R.drawable.leedohyun,
        name = "이도현",
        selfDescription = "청순한 상도 좋아합니다",
    ),
    Friend(
        profileImage = R.drawable.jangyeongdo,
        name = "장영도",
        selfDescription = "좋아했는데 이제는 좋아하지 않는..",
    ),
    Friend(
        profileImage = R.drawable.hahyunsang,
        name = "하현상",
        selfDescription = "말랑뽀짝 현상이",
    ),
    Friend(
        profileImage = R.drawable.seohocheol,
        name = "서호철",
        selfDescription = "이적한다면 따라갈지도..",
    ),
    Friend(
        profileImage = R.drawable.kimwonpil,
        name = "김원필",
        selfDescription = "김원필 직캠 보며 잠들기",
    ),
    Friend(
        profileImage = R.drawable.kimseokjin,
        name = "김석진",
        selfDescription = "인생 처음이자 마지막 최애입니다",
    ),
    Friend(
        profileImage = R.drawable.minhyeok,
        name = "이민혁",
        selfDescription = "수많은 민혁 중 몬스타엑스 이민혁",
    ),
    Friend(
        profileImage = R.drawable.last,
        name = "이현우",
        selfDescription = "이 분은 나이를 안 먹으시더라구요..",
    ),
)
@Composable
fun HomeView() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn {
            items(userList) {
                UserItem(it)
            }
            items(friendList) {
                FriendItem(it)
            }
        }
    }
}