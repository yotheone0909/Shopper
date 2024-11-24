package com.example.data.repository

import com.example.domain.model.CartItemModel
import com.example.domain.model.CartModel
import com.example.domain.model.CartSummary
import com.example.domain.model.request.AddedCartRequestModel
import com.example.domain.network.NetworkService
import com.example.domain.network.ResultWrapper
import com.example.domain.repository.CartRepository

class CartRepositoryImpl(val networkService: NetworkService): CartRepository {
    override suspend fun addProductToCart(
        request: AddedCartRequestModel,
        userId: Long
    ): ResultWrapper<CartModel> {
        return networkService.addProductToCart(request,userId)
    }

    override suspend fun getCart(userId: Long): ResultWrapper<CartModel> {
        return networkService.getCart(userId)
    }

    override suspend fun updateQuantity(
        cartItemModel: CartItemModel,
        userId: Long
    ): ResultWrapper<CartModel> {
        return networkService.updateQuantity(cartItemModel, userId)
    }

    override suspend fun deleteItem(cartItemId: Int, userId: Long): ResultWrapper<CartModel> {
        return networkService.deleteItem(cartItemId, userId)
    }

    override suspend fun getCartSummary(userId: Long): ResultWrapper<CartSummary> {
        return networkService.getCartSummary(userId)
    }
}