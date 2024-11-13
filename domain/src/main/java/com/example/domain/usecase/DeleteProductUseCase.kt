package com.example.domain.usecase

import com.example.domain.model.CartItemModel
import com.example.domain.model.CartModel
import com.example.domain.network.ResultWrapper
import com.example.domain.repository.CartRepository

class DeleteProductUseCase(private val cartRepository: CartRepository) {
   suspend fun execute(cartItemId: Int, userId: Int) : ResultWrapper<CartModel> = cartRepository.deleteItem(cartItemId, userId)
}