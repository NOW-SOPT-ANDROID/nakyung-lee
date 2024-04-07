package com.sopt.now.compose

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import com.sopt.now.compose.ui.theme.NOWSOPTAndroidTheme

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val getId = intent.getStringExtra("id") ?: ""
        val getPassword = intent.getStringExtra("password") ?: ""
        val getNickname = intent.getStringExtra("nickname") ?: ""
        val getMbti = intent.getStringExtra("mbti") ?: ""

        setContent {
            LoginContent(getId, getPassword, getNickname, getMbti)
        }
    }
}

@Composable
fun LoginContent(getId: String, getPassword: String, getNickname: String, getMbti: String) {
    var isLogged by remember { mutableStateOf(false) }

    if (!isLogged) {
        SoptComposable(
            getId = getId,
            getPassword = getPassword,
            getNickname = getNickname,
            getMbti = getMbti
        )
    } else {
        val context = LocalContext.current
        context.startActivity(Intent(context, MainActivity::class.java))
    }
}

fun isValid(userId: String?, userPassword: String?, getId: String, getPassword: String): Boolean {
    return userId != "" && userPassword != "" && userId == getId && userPassword == getPassword
}

fun Success(context: Context, userId: String, userPassword: String, userNickname: String, userMbti: String?) {

    val intent = Intent(context, MainActivity::class.java).apply {
        putExtra("id", userId)
        putExtra("password", userPassword)
        putExtra("nickname", userNickname)
        putExtra("mbti", userMbti)
    }
    context.startActivity(intent)
    Toast.makeText(context, "로그인 성공!", Toast.LENGTH_SHORT).show()
}

@Composable
fun SoptComposable(
    getId: String,
    getPassword: String,
    getNickname: String,
    getMbti: String

) {
    var userId by remember { mutableStateOf("") }
    var userPassword by remember { mutableStateOf("") }
    var userNickname by remember { mutableStateOf("") }
    var userMbti by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "Welcome To SOPT",
            color = Color.Gray,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally),
        )

        // Welcome 문구와 ID 텍스트 사이의 간격
        Spacer(modifier = Modifier.height(100.dp)) // 중간 간격을 위한 투명 view

        // ID 입력하는 부분 (ID 텍스트는 좌측 정렬, 텍스트 필드는 중앙 정렬)
        Column(verticalArrangement = Arrangement.Center) {
            Text(text = "ID")

            TextField(
                value = userId,
                onValueChange = { userId = it },
                label = { Text("ID를 입력하세요") },
                placeholder = { Text("EX) helen0816") },
                singleLine = true,
            )
        }

        // ID 텍스트 필드와 PASSWORD 텍스트 사이의 간격
        Spacer(modifier = Modifier.height(50.dp)) // 중간 간격을 위한 투명 view

        // PW 입력하는 부분 (PW 텍스트는 좌측 정렬, 텍스트 필드는 중앙 정렬)
        Column(verticalArrangement = Arrangement.Center) {
            Text(text = "PASSWORD")

            TextField(
                value = userPassword,
                onValueChange = { userPassword = it },
                label = { Text("PASSWORD를 입력하세요") },
                placeholder = { Text("EX) qwaszx@!") },
                singleLine = true,
            )
        }

        // PW 텍스트 필드와 버튼 사이의 간격
        Spacer(modifier = Modifier.height(250.dp)) // 중간 간격을 위한 투명 view

        val context = LocalContext.current

        Button(
            onClick = { if (isValid(userId, userPassword, getId ?: "", getPassword ?: "")) {
                Success(context, userId, userPassword, userNickname, userMbti)
            }
                else Toast.makeText(context, "아이디 또는 비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show()
            },
            contentPadding = PaddingValues(start = 90.dp, end = 90.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.LightGray,
                contentColor = Color.Black
            )
        ) {
            Text(text = "로그인",
                fontWeight = FontWeight.Bold,
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = {
                val intent = Intent(context, SignupActivity::class.java)
                context.startActivity(intent)
            },
            contentPadding = PaddingValues(start = 90.dp, end = 90.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.LightGray,
                contentColor = Color.Black
            )
        )
        {
            Text(text = "회원가입",
                fontWeight = FontWeight.Bold,
            )
        }

    }
}
@Preview(showBackground = true)

@Composable
fun LoginPreview() {
    NOWSOPTAndroidTheme {
        SoptComposable(getId ="", getPassword ="", getNickname = "", getMbti = "")
    }
}