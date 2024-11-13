package com.example.domain.usecase

import com.example.domain.model.CartItemModel
import com.example.domain.model.CartModel
import com.example.domain.network.ResultWrapper
import com.example.domain.repository.CartRepository

class UpdateQuantityUseCase(private val cartRepository: CartRepository) {
   suspend fun execute(cartItemModel: CartItemModel) : ResultWrapper<CartModel> = cartRepository.updateQuantity(cartItemModel)
}