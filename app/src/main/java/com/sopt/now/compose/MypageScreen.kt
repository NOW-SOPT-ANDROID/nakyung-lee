package com.sopt.now.compose

import android.content.Intent
import android.os.Bundle
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sopt.now.compose.ui.theme.PurpleGrey40
import com.sopt.now.compose.user.UserInfo
@Composable
fun MypageView(userInfo: UserInfo) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
        verticalArrangement = Arrangement.spacedBy(40.dp),
    ) {
        if (userInfo != null) {
            Image(
                painter = painterResource(id = R.drawable.apple),
                contentDescription = null,
                modifier = Modifier
                    .size(110.dp)
                    .aspectRatio(1f)
            )
            Text(
                text = "안녕하세요, ${userInfo.nickname}님",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.height(15.dp))
            Text(text = userInfo.id)
            Spacer(modifier = Modifier.height(15.dp))
            Text(text = userInfo.password)
        } else {
            Text(
                text = "사용자 정보를 불러올 수 없습니다.",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
@Composable
fun UserInfoComposable(id: String, password: String, nickname: String) {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = "안녕하세요, $nickname 님!\n",
            fontSize = 24.sp,
            fontWeight = FontWeight.ExtraBold)

        Text(text = "아이디: $id \n",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = PurpleGrey40)
        Text(text = "비밀번호: $password \n",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = PurpleGrey40)

        Button(
            onClick = { },

            contentPadding = PaddingValues(start = 90.dp, end = 90.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.LightGray,
                contentColor = Color.Black
            )
        ) {
            Text(text = "수정하기",
                fontWeight = FontWeight.Bold,
            )
        }
    }

}