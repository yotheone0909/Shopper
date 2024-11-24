package com.example.domain.repository

import com.example.domain.model.CartItemModel
import com.example.domain.model.CartModel
import com.example.domain.model.CartSummary
import com.example.domain.model.request.AddedCartRequestModel
import com.example.domain.network.ResultWrapper

interface CartRepository {
    suspend fun addProductToCart(
        request: AddedCartRequestModel,
        userId: Long
    ) : ResultWrapper<CartModel>

    suspend fun getCart(userId: Long) : ResultWrapper<CartModel>
    suspend fun updateQuantity(cartItemModel: CartItemModel, userId: Long) : ResultWrapper<CartModel>
    suspend fun deleteItem(cartItemId: Int, userId: Long): ResultWrapper<CartModel>
    suspend fun getCartSummary(userId: Long): ResultWrapper<CartSummary>
}