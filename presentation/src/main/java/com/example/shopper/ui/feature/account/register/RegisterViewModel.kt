package com.example.shopper.ui.feature.account.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.network.ResultWrapper
import com.example.domain.usecase.LoginUseCase
import com.example.domain.usecase.RegisterUseCase
import com.example.shopper.ShopperSession
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val registerUseCase: RegisterUseCase,
    private val shopperSession: ShopperSession
) : ViewModel() {

    private val _state = MutableStateFlow<RegisterState>(RegisterState.Idle)
    val state = _state.asStateFlow()

    fun register(email: String, password: String, name: String) {
        _state.value = RegisterState.Loading
        viewModelScope.launch {
           when(val result = registerUseCase.execute(email, password, name)) {
                is ResultWrapper.Failure -> {
                    _state.value = RegisterState.Error(result.exception.message ?: "Something went wrong!")
                }
                is ResultWrapper.Success -> {
                    shopperSession.storeUser(result.value)
                    _state.value = RegisterState.Success
                }
            }
        }
    }
}

sealed class RegisterState {
    data object Idle : RegisterState()
    data object Loading : RegisterState()
    data object Success : RegisterState()
    data class Error(val message: String) : RegisterState()
}