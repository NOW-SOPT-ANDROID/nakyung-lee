package com.sopt.now.compose

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sopt.now.compose.ui.theme.NOWSOPTAndroidTheme

class UserInfo(
    val userId: String,
    val userPassword: String,
    val userName: String,
    val userMbti: String
)

@Composable
fun Signup () {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    )
    {
        var userId by remember { mutableStateOf("") }
        var userPassword by remember { mutableStateOf("") }
        var userName by remember { mutableStateOf("") }
        var userMbti by remember { mutableStateOf("") }

        Text(
            text = "SIGN UP PAGE",
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "ID",
            fontSize = 20.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Left
        )

        TextField(
            value = userId,
            onValueChange = { newValue -> userId = newValue },
            modifier = Modifier
                .fillMaxWidth(),
            label = { Text("아이디를 입력하세요(6~10자리)") },
            singleLine = true,
        )

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "PASSWORD",
            fontSize = 20.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Left
        )

        TextField(
            value = userPassword,
            onValueChange = { newValue -> userPassword = newValue },
            modifier = Modifier
                .fillMaxWidth(),
            label = { Text("비밀번호를 입력하세요(8~12자리)") },
            singleLine = true,
        )

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "NICKNAME",
            fontSize = 20.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Left,
        )

        TextField(
            value = userName,
            onValueChange = { newValue -> userName = newValue },
            modifier = Modifier
                .fillMaxWidth(),
            label = { Text("닉네임을 입력하세요(공백 안됨)") },
            singleLine = true,
        )

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "MBTI",
            fontSize = 20.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Left,
        )

        TextField(
            value = userMbti,
            onValueChange = { newValue -> userMbti = newValue },
            modifier = Modifier
                .fillMaxWidth(),
            label = { Text("MBTI를 입력하세요") },
            singleLine = true,
        )

        Spacer(modifier = Modifier.height(30.dp))

        val context = LocalContext.current

        Button(onClick = {
            if (CheckId(context, userId) && CheckPassword(context, userPassword) && CheckName(context, userName) && CheckMbti(context, userMbti)) {
                val intent = Intent(context, LoginActivity::class.java).apply {
                    putExtra("id", userId)
                    putExtra("password", userPassword)
                    putExtra("name", userName)
                    putExtra("mbti", userMbti)
                }

                context.startActivity(intent) // 회원가입 성공 시 MainActivity로 이동
                Toast.makeText(context, "회원가입 성공!", Toast.LENGTH_SHORT).show()

            } else {

            }
        }) {
            Text("Sign Up")
        }

    }
}

fun CheckId(context: Context, id: String): Boolean {
    if (id.length >= 6 && id.length <= 10) {
        return true
    } else {
        Toast.makeText(context, "ID는 6자 이상, 10자 이하로 입력해주세요", Toast.LENGTH_SHORT).show()
        return false
    }
}

fun CheckPassword(context: Context, password: String): Boolean {
    if(password.length >= 8 && password.length <= 12){
        return true
    }
    else {
        Toast.makeText(context, "PASSWORD는 8자 이상, 12자 이하로 입력해주세요", Toast.LENGTH_SHORT).show()
        return false
    }
}

fun CheckName(context: Context, name: String): Boolean {
    if (name.isNotBlank() && name.length > 1) {
        return true
    } else {
        Toast.makeText(context, "닉네임은 2자 이상의 유효한 문자로 입력해주세요", Toast.LENGTH_SHORT).show()
        return false
    }
}
fun CheckMbti(context: Context, mbti: String): Boolean {
    if ((mbti.length == 4)
        && (mbti[0].uppercase() == "E" || mbti[0].uppercase() == "I")
        && (mbti[1].uppercase() == "S" || mbti[1].uppercase() == "N")
        && (mbti[2].uppercase() == "F" || mbti[2].uppercase() == "T")
        && (mbti[3].uppercase() == "P" || mbti[3].uppercase() == "J")
    )
        return true
    else {
        Toast.makeText(context, "MBTI를 제대로 입력해주세요(ex. ENTJ)", Toast.LENGTH_SHORT).show()
        return false
    }
}
class SignupActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            NOWSOPTAndroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Signup()
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SignupPreview() {
    NOWSOPTAndroidTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ){
            Signup()
        }
    }
}
