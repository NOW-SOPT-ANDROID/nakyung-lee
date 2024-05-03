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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.sopt.now.compose.Dto.RequestSignUpDto
import com.sopt.now.compose.Dto.ResponseSignUpDto
import com.sopt.now.compose.ServicePool
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : ComponentActivity() {
    private val viewModel by lazy { SignUpViewModel() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SignUpContent(viewModel)
        }
    }
}

@Composable
fun SignUpContent(viewModel: SignUpViewModel) {
    // 현재 컨텍스트 가져오기
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var userId by remember { mutableStateOf("") }
        var userPassword by remember { mutableStateOf("") }
        var userNickname by remember { mutableStateOf("") }
        var userPhone by remember { mutableStateOf("") }

        Text(
            text = "SIGN UP PAGE",
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

        SignUpTextField(
            value = userPassword,
            onValueChange = { userPassword = it },
            label = "Password",
            hint = "Enter your password",
            isPassword = true
        )

        Spacer(modifier = Modifier.height(20.dp))

        SignUpTextField(
            value = userNickname,
            onValueChange = { userNickname = it },
            label = "Nickname",
            hint = "Enter your nickname"
        )

        Spacer(modifier = Modifier.height(20.dp))

        SignUpTextField(
            value = userPhone,
            onValueChange = { userPhone = it },
            label = "PHONE",
            hint = "Enter your phone number"
        )

        Spacer(modifier = Modifier.height(30.dp))

        Button(onClick = {
            viewModel.signUp(userId, userPassword, userNickname, userPhone, context)
        }) {
            Text("Sign Up")
        }
    }
}

@Composable
fun SignUpTextField(
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

class SignUpViewModel : ViewModel() {
    private val _signUpState = mutableStateOf(SignUpState())
    fun signUp(
        userId: String,
        userPassword: String,
        userNickname: String,
        userPhone: String,
        context: Context
    ) {
        val signUpRequestDto = RequestSignUpDto(userId, userPassword, userNickname, userPhone)
        val authService = ServicePool.authService

        authService.signUp(signUpRequestDto).enqueue(object : Callback<ResponseSignUpDto> {
            override fun onResponse(
                call: Call<ResponseSignUpDto>,
                response: Response<ResponseSignUpDto>
            ) {
                if (response.isSuccessful) {
                    val userId = response.headers()["location"]
                    _signUpState.value = SignUpState(isSuccess = true, message = "회원가입 성공!")
                    Log.e("userId", "sign up success for $userId")

                    // 회원가입 성공 시 로그인 화면으로 이동
                    val intent = Intent(context, LoginActivity::class.java)
                    context.startActivity(intent)
                    // 회원가입 성공 메시지 토스트로 띄우기
                    Toast.makeText(context, "회원가입 성공!", Toast.LENGTH_SHORT).show()
                } else {
                    val errorMessage = response.errorBody()?.string() ?: "Unknown error"
                    _signUpState.value = SignUpState(isSuccess = false, message = errorMessage)
                    // 회원가입 실패 시 실패 원인 토스트로 띄우기
                    Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseSignUpDto>, t: Throwable) {
                val errorMessage = "서버 통신 오류: ${t.localizedMessage}"
                _signUpState.value = SignUpState(isSuccess = false, message = errorMessage)
                // 서버 통신 오류 시 토스트로 띄우기
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }
}

data class SignUpState(
    val isSuccess: Boolean = false,
    val message: String = ""
)
