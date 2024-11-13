package com.example.shopper.ui.feature.summary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.CartSummary
import com.example.domain.network.ResultWrapper
import com.example.domain.usecase.CartSummaryUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CartSummaryViewModel(private val getCartSummaryUseCase: CartSummaryUseCase): ViewModel() {

    private val _uiState = MutableStateFlow<CartSummaryEvent>(CartSummaryEvent.Loading)
    val uiState = _uiState.asStateFlow()

    init {

    }

    fun getCartSummary(userId: Int) {
        viewModelScope.launch {
            _uiState.value = CartSummaryEvent.Loading
            when(val summary = getCartSummaryUseCase.execute(userId)) {
                is ResultWrapper.Failure -> {
                    _uiState.value = CartSummaryEvent.Error("Something went wrong!")
                }
                is ResultWrapper.Success -> {
                    _uiState.value = CartSummaryEvent.Success(summary.value)
                }
            }
        }
    }

}

sealed class CartSummaryEvent {
    data object Loading: CartSummaryEvent()
    data class Error(val error: String): CartSummaryEvent()
    data class Success(val summary: CartSummary): CartSummaryEvent()
}