package com.sopt.now.compose.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.sopt.now.compose.Dto.RequestLoginDto
import com.sopt.now.compose.Dto.ResponseLoginDto
import com.sopt.now.compose.ServicePool
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : ComponentActivity() {
    private val viewModel by lazy { LoginViewModel() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginContent(viewModel)
        }
    }
}

@Composable
fun LoginContent(viewModel: LoginViewModel) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var userId by remember { mutableStateOf("") }
        var userPassword by remember { mutableStateOf("") }

        Text(
            text = "LOGIN PAGE",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(30.dp))

        SignUpTextField(
            value = userId,
            onValueChange = { userId = it },
            label = "ID",
            hint = "Enter your ID"
        )

        Spacer(modifier = Modifier.height(20.dp))

        LoginTextField(
            value = userPassword,
            onValueChange = { userPassword = it },
            label = "Password",
            hint = "Enter your password",
            isPassword = true
        )
        Spacer(modifier = Modifier.height(30.dp))

        Button(onClick = {
            viewModel.login(userId, userPassword, context)
        }) {
            Text("로그인")
        }
        Spacer(modifier = Modifier.height(10.dp))

        // 회원가입으로 이동할 수 있는 버튼 추가
        Button(onClick = {
            val intent = Intent(context, SignUpActivity::class.java)
            context.startActivity(intent)
        }) {
            Text("회원가입")
        }
    }
}

@Composable
fun LoginTextField (
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    hint: String,
    isPassword: Boolean = false
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        placeholder = { Text(hint) },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth(),
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None
    )
}

class LoginViewModel : ViewModel() {
    private val _loginState = mutableStateOf(LoginState())
    val loginState: State<LoginState> = _loginState

    fun login(
        userId: String,
        userPassword: String,
        context: Context
    ) {
        val loginRequestDto = RequestLoginDto(userId, userPassword)
        val authService = ServicePool.authService

        authService.login(loginRequestDto).enqueue(object : Callback<ResponseLoginDto> {
            override fun onResponse(
                call: Call<ResponseLoginDto>,
                response: Response<ResponseLoginDto>
            ) {
                if (response.isSuccessful) {
                    val userId = response.headers()["location"]
                    _loginState.value = LoginState(isSuccess = true, message = "로그인 성공!")
                    Log.e("userId", "login success for $userId")

                    // 로그인 성공 시 메인(홈) 화면으로 이동
                    val intent = Intent(context, MainActivity::class.java)
                    context.startActivity(intent)
                    // 로그인 성공 메시지 토스트로 띄우기
                    Toast.makeText(context, "로그인 성공! 유저 ID: $userId\"", Toast.LENGTH_SHORT).show()
                } else {
                    val errorMessage = response.errorBody()?.string() ?: "Unknown error"
                    _loginState.value = LoginState(isSuccess = false, message = errorMessage)
                    // 회원가입 실패 시 실패 원인 토스트로 띄우기
                    Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseLoginDto>, t: Throwable) {
                val errorMessage = "서버 통신 오류: ${t.localizedMessage}"
                _loginState.value = LoginState(isSuccess = false, message = errorMessage)
                // 서버 통신 오류 시 토스트로 띄우기
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }
}
data class LoginState(
    val isSuccess: Boolean = false,
    val message: String = ""
)