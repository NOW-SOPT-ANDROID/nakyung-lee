package com.sopt.now.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sopt.now.compose.ui.theme.NOWSOPTAndroidTheme
import com.sopt.now.compose.ui.theme.PurpleGrey40

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val id = intent.getStringExtra("id")
        val password = intent.getStringExtra("password")
        val nickname = intent.getStringExtra("nickname")

        setContent {
            MainActivityContent(id = id ?: "", password = password ?: "", nickname = nickname?: "")
        }
    }
}

@Composable
fun MainActivityContent(id: String, password: String, nickname: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image (
            painter = painterResource(id = R.drawable.apple), // 이미지 리소스 추가
            contentDescription = null,
            modifier = Modifier
                .aspectRatio(1.5f) // aspectRatio 설정
                .padding(top = 30.dp)
                .padding(bottom = 10.dp)
        )
        UserInfoComposable(id, password, nickname)
    }
}

@Composable
fun UserInfoComposable(id: String, password: String, nickname: String) {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = "안녕하세요, $id 님!\n",
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


@Preview(showBackground = true)
@Composable
fun MainPreview() {
    NOWSOPTAndroidTheme {
        UserInfoComposable(
            id = "exampleId",
            password = "examplePassword",
            nickname = "exampleNickname"
        )
    }
}