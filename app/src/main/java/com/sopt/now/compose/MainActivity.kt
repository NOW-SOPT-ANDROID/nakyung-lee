package com.sopt.now.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sopt.now.compose.ui.theme.NOWSOPTAndroidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val id = intent.getStringExtra("id")
        val password = intent.getStringExtra("password")
        val name = intent.getStringExtra("name")

        setContent {
            MainActivityContent(id ?: "", password ?: "", name ?: "")
        }
    }
}

@Composable
fun MainActivityContent(id: String, password: String, name: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        UserInfoComposable(id = id, password = password, name = name)
    }
}

@Composable
fun UserInfoComposable(id: String, password: String, name: String) {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = "아이디: $id", fontSize = 18.sp)
        Text(text = "비밀번호: $password", fontSize = 18.sp)
        Text(text = "닉네임: $name", fontSize = 18.sp)

    }
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    NOWSOPTAndroidTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ){
            UserInfoComposable(
                id = "exampleId",
                password = "examplePassword",
                name = "exampleName",
            )
        }
    }
}