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
import com.sopt.now.compose.viewModel.SignUpViewModel
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

data class SignUpState(
    val isSuccess: Boolean = false,
    val message: String = ""
)