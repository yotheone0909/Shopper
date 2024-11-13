package com.example.shopper.ui.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Product
import com.example.domain.network.ResultWrapper
import com.example.domain.usecase.GetCategoriesUseCase
import com.example.domain.usecase.GetProductUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getProductUseCase: GetProductUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeScreenUiEvent>(HomeScreenUiEvent.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        getAllProducts()
    }

    private fun getAllProducts() {
        viewModelScope.launch {
            _uiState.value = HomeScreenUiEvent.Loading
            val feature = getProduct(1)
            val popularProducts = getProduct(2)
            val categories = getCategory()
            if (feature.isEmpty() || popularProducts.isEmpty()) {
                _uiState.value = HomeScreenUiEvent.Error("Failed to load products")
                return@launch
            }
            _uiState.value = HomeScreenUiEvent.Success(feature, popularProducts, categories)
        }
    }

    private suspend fun getCategory(): List<String> {
        getCategoriesUseCase.execute().let { result ->
            when (result) {
                is ResultWrapper.Success -> {
                    return (result).value.categories.map { it.title }
                }

                is ResultWrapper.Failure -> {
                    return emptyList()
                }
            }

        }
    }

    private suspend fun getProduct(category: Int?): List<Product> {
        getProductUseCase.execute(category).let { result ->
            when (result) {
                is ResultWrapper.Failure -> {
                    return emptyList()
                }

                is ResultWrapper.Success -> {
                    return result.value.products
                }
            }
        }
    }
}

sealed class HomeScreenUiEvent {
    data object Loading : HomeScreenUiEvent()
    data class Success(
        val feature: List<Product>,
        val popularProduct: List<Product>,
        val categories: List<String>
    ) : HomeScreenUiEvent()

    data class Error(val message: String) : HomeScreenUiEvent()
}