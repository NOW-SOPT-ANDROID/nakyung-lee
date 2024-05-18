package com.sopt.now.presentation.auth.signup

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.presentation.Dto.RequestSignUpDto
import com.sopt.now.presentation.Dto.ResponseSignUpDto
import com.sopt.now.presentation.ServicePool
import kotlinx.coroutines.launch
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Call
import retrofit2.HttpException

class SignUpViewModel : ViewModel() {
    private val authService by lazy { ServicePool.authService }
    val liveData = MutableLiveData<SignUpState>()
    fun signUp(request: RequestSignUpDto) {
        viewModelScope.launch {
            try {
                val response = authService.signUp(request)
                liveData.value = SignUpState(true, "회원가입 성공")
            } catch (e: HttpException) {
                liveData.value = SignUpState(false, "회원가입 실패 ${e.code()}")
            }
        }
    }
}