package com.example.domain.network

import com.example.domain.model.AddressDomainModel
import com.example.domain.model.CartItemModel
import com.example.domain.model.CartModel
import com.example.domain.model.CartSummary
import com.example.domain.model.CategoriesListModel
import com.example.domain.model.OrdersListModel
import com.example.domain.model.ProductListModel
import com.example.domain.model.request.AddedCartRequestModel

interface NetworkService {
    suspend fun getProducts(category: Int?): ResultWrapper<ProductListModel>
    suspend fun getCategories(): ResultWrapper<CategoriesListModel>

    suspend fun addProductToCart(
        request: AddedCartRequestModel
    ): ResultWrapper<CartModel>

    suspend fun getCart() : ResultWrapper<CartModel>
    suspend fun updateQuantity(cartItemModel: CartItemModel) : ResultWrapper<CartModel>
    suspend fun deleteItem(cartItemId: Int, userId: Int): ResultWrapper<CartModel>
    suspend fun getCartSummary(userId: Int): ResultWrapper<CartSummary>
    suspend fun placeOrder(address: AddressDomainModel, userId: Int): ResultWrapper<Long>
    suspend fun getOrderList(): ResultWrapper<OrdersListModel>
}

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T) : ResultWrapper<T>()
    data class Failure(val exception: Exception) : ResultWrapper<Nothing>()
}