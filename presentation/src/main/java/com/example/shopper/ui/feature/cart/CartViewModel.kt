package com.example.shopper.ui.feature.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.CartItemModel
import com.example.domain.network.ResultWrapper
import com.example.domain.usecase.DeleteProductUseCase
import com.example.domain.usecase.GetCartUseCase
import com.example.domain.usecase.UpdateQuantityUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CartViewModel(val cartUseCase: GetCartUseCase, private val updateQuantityUseCase: UpdateQuantityUseCase,
    private val deleteItemUseCase: DeleteProductUseCase): ViewModel() {
    private val _uiState = MutableStateFlow<CartEvent>(CartEvent.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        getCart()
    }

    fun getCart() {
        viewModelScope.launch {
            _uiState.value = CartEvent.Loading
            cartUseCase.execute().let { result ->
                when(result) {
                    is ResultWrapper.Success -> {
                        _uiState.value = CartEvent.Success(result.value.data)
                    }
                    is ResultWrapper.Failure -> {
                        _uiState.value = CartEvent.Error("Something went wrong!")
                    }
                }
            }
        }
    }

    fun incrementQuantity(cartItem: CartItemModel) {
        if (cartItem.quantity == 10) return
        updateQuantity(cartItem.copy(quantity = cartItem.quantity + 1))
    }

    fun decrementQuantity(cartItem: CartItemModel) {
        if (cartItem.quantity == 1) return
        updateQuantity(cartItem.copy(quantity = cartItem.quantity - 1))
    }

    private fun updateQuantity(cartItem: CartItemModel) {
        viewModelScope.launch {
            _uiState.value = CartEvent.Loading
            when(val result = updateQuantityUseCase.execute(cartItem)) {
                is ResultWrapper.Failure -> {
                    _uiState.value = CartEvent.Error("Something went wrong!")
                }
                is ResultWrapper.Success -> {
                    _uiState.value = CartEvent.Success(result.value.data)
                }
            }
        }
    }

    fun removeItem(cartItem: CartItemModel) {
        viewModelScope.launch {
            _uiState.value = CartEvent.Loading
            when(val result = deleteItemUseCase.execute(cartItem.id, 1)) {
                is ResultWrapper.Failure -> _uiState.value = CartEvent.Error("Something went wrong!")
                is ResultWrapper.Success -> _uiState.value = CartEvent.Success(result.value.data)
            }
        }
    }
}

sealed class CartEvent {
    data object Loading: CartEvent()
    data class Success(val message: List<CartItemModel>) : CartEvent()
    data class Error(val message: String): CartEvent()
}