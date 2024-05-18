package com.sopt.now.presentation.main.mypage

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.presentation.data.ServicePool
import kotlinx.coroutines.launch
import retrofit2.HttpException

class MyPageViewModel : ViewModel() {
    private val _authService by lazy { ServicePool.authService }
    private val _userinfo = MutableLiveData<UserInfo>()

    fun getUserInfo() {
        viewModelScope.launch {
            runCatching {
                _authService.getUserInfo()
            }.onSuccess {
                _userinfo.value = UserInfo(
                    isSuccess = true,
                    message = it.message,
                    id = it.data.authenticationId,
                    nickname = it.data.nickname,
                    phone = it.data.phone
                )
            }.onFailure {
                if(it is HttpException) {
                    _userinfo.value = UserInfo(
                        isSuccess = false,
                        message = "실패입니당"
                    )
                }
            }
        }
    }
}