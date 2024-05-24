package com.sopt.now.compose.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sopt.now.compose.viewModel.LoginViewModel

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
            viewModel.login(userId, userPassword)
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

data class LoginState(
    val isSuccess: Boolean = false,
    val message: String = ""
)