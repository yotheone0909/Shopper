package com.example.domain.usecase

import com.example.domain.model.request.AddedCartRequestModel
import com.example.domain.repository.CartRepository

class AddToCartUseCase(private val cartRepository: CartRepository) {
    suspend fun execute(request: AddedCartRequestModel, userId: Long) =  cartRepository.addProductToCart(request, userId)
}