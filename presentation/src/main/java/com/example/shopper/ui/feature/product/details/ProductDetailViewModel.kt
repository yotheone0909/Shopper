package com.example.shopper.ui.feature.product.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.request.AddedCartRequestModel
import com.example.domain.network.ResultWrapper
import com.example.domain.usecase.AddToCartUseCase
import com.example.shopper.ShopperSession
import com.example.shopper.model.UiProductModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductDetailViewModel(val useCase: AddToCartUseCase): ViewModel() {
    private val _state = MutableStateFlow<ProductDetailEvent>(ProductDetailEvent.Nothing)
    val state = _state.asStateFlow()
    private val userDomainModel = ShopperSession.getUser()

    fun addProductToCart(product:UiProductModel) {
        viewModelScope.launch {
            _state.value = ProductDetailEvent.Loading
            val result = useCase.execute(AddedCartRequestModel(
                product.id,
                product.title,
                product.price,
                1,
                userDomainModel!!.id!!
            ) ,userDomainModel!!.id!!.toLong())
            when(result) {
                is ResultWrapper.Success -> {
                    _state.value = ProductDetailEvent.Success("Product added to cart")
                }
                is ResultWrapper.Failure -> {
                    _state.value = ProductDetailEvent.Error("Something went wrong!")
                }
            }
        }
    }

}

sealed class ProductDetailEvent {
    data object Loading: ProductDetailEvent()
    data object Nothing: ProductDetailEvent()
    data class Success(val message: String): ProductDetailEvent()
    data class Error(val message: String): ProductDetailEvent()
}