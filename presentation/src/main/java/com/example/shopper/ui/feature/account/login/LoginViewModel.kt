package com.example.shopper.ui.feature.account.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.network.ResultWrapper
import com.example.domain.usecase.LoginUseCase
import com.example.shopper.ShopperSession
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<LoginState>(LoginState.Idle)
    val state = _state.asStateFlow()

    fun login(email: String, password: String) {
        _state.value = LoginState.Loading
        viewModelScope.launch {
           when(val result = loginUseCase.execute(email, password)) {
                is ResultWrapper.Failure -> {
                    _state.value = LoginState.Error(result.exception.message ?: "Something went wrong!")
                }
                is ResultWrapper.Success -> {
                    ShopperSession.storeUser(result.value)
                    _state.value = LoginState.Success
                }
            }
        }
    }
}

sealed class LoginState {
    data object Idle : LoginState()
    data object Loading : LoginState()
    data object Success : LoginState()
    data class Error(val message: String) : LoginState()
}